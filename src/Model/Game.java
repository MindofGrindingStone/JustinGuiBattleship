package Model;

import java.util.ArrayList;
import java.util.List;

public class Game implements ShotDelegate {
    private HumanPlayer humanPlayer;
    private Player computerPlayer;
    private Player currentPlayer;
    private Player otherPlayer;
    private List<StatusListener> listeners = new ArrayList<StatusListener>();

    public Game() {
        // set up players
        humanPlayer = new HumanPlayer("Human", new AutomaticShipFactory(), this);
        computerPlayer = new EasyAIPlayer(new AutomaticShipFactory(), this);

    }

    public void start() {
        humanPlayer.placeShips();
        computerPlayer.placeShips();

        // ... and go!
        currentPlayer = humanPlayer;
        otherPlayer = computerPlayer;
        currentPlayer.takeShot();
    }

    public void addListener(StatusListener listener) {
        listeners.add(listener);
    }

    private void notifyStatus(String message) {
        for (StatusListener listener : listeners) {
            listener.statusMessage(message);
        }
    }

    public TargetGrid getHumanTargetGrid() {
        return humanPlayer.getTargetGrid();
    }

    public OceanGrid getHumanOceanGrid() {
        return humanPlayer.getOceanGrid();
    }

    @SuppressWarnings("incomplete-switch")
    public void handleShot(Coordinate shot, Object sender) {
        // must be this player's turn
        if (sender != currentPlayer) {
            notifyStatus("It's not your turn!\n");
            return;
        }

        if (otherPlayer.shipsAreSunk()) {
            return;
        }

        // process the shot
        ShotResult result = otherPlayer.receiveShot(shot);
        currentPlayer.receiveShotResult(result);
        notifyStatus(String.format("%s fires at %s ", currentPlayer.getName(), shot.toString()));
        switch (result) {
            case HIT -> notifyStatus(String.format(" --> HIT%n"));
            case MISS -> notifyStatus(String.format(" --> MISS%n"));
            case SUNK -> notifyStatus(String.format(" --> HIT and SUNK... You sunk my %s%n", result.getShipName()));
        }

        // check for end of game
        if (otherPlayer.shipsAreSunk()) {
            notifyStatus(String.format("GAME OVER: The winner is --> %s%n", currentPlayer.getName()));
            return;
        }

        // swap turns
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;

        // prompt current player to shoot
        currentPlayer.takeShot();
    }
}
