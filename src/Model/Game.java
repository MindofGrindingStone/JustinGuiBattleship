package Model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private HumanPlayer humanPlayer;
    private Player computerPlayer;
    private Player currentPlayer;
    private Player otherPlayer;

    public Game(){
        // set up players
        humanPlayer = new HumanPlayer("Human", new AutomaticShipFactory());
        computerPlayer = new EasyAIPlayer(new AutomaticShipFactory());
        humanPlayer.placeShips();
        computerPlayer.placeShips();

        // ... and go!
        currentPlayer = humanPlayer;
        otherPlayer = computerPlayer;
        currentPlayer.takeShot();
    }

    public TargetGrid getHumanTargetGrid() {
        return humanPlayer.getTargetGrid();
    }

    public void handleShot(Coordinate shot, Object sender){
        // must be this player's turn
        if(sender != currentPlayer){
            // send message
            return;
        }

        // process the shot
		ShotResult result = otherPlayer.receiveShot(shot);
		currentPlayer.receiveShotResult(result);
            // send message

        // check for end of game
        if(otherPlayer.shipsAreSunk()){
            // send message
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
