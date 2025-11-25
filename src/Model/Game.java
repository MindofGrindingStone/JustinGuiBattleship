package Model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private HumanPlayer humanPlayer;

    public Game(){
        humanPlayer = new HumanPlayer("Human", new AutomaticShipFactory());
    }

    public TargetGrid getHumanTargetGrid() {
        return humanPlayer.getTargetGrid();
    }

    public void play(){
        for(Player player : players){
			player.placeShips();
		}

		while(true){ // game loop

			// start turn
			Player currentPlayer = players.get(currentPlayerIndex);
            Player otherPlayer = players.get(otherPlayerIndex);
			System.out.printf("%nYou're up %s!%n", currentPlayer.getName());

            // CORE ACTIONS IN A TURN
			// send shot, receive shot, receive result
			Coordinate shot = currentPlayer.takeShot();
			ShotResult result = otherPlayer.receiveShot(shot);
			currentPlayer.receiveShotResult(result);

            // update UI
            System.out.printf("%s fires at %s ", currentPlayer.getName(), shot.toString());
            switch(result){
                case HIT -> System.out.printf(" --> HIT%n");
                case MISS -> System.out.printf(" --> MISS%n");
                case SUNK -> System.out.printf(" --> HIT and SUNK... You sunk my %s%n", result.getShipName());
                case INVALID -> System.out.printf(" --> Something bad has happened - you should stop playing immediately.%n");
            }

			// check for end of game
            if(otherPlayer.shipsAreSunk()){
                System.out.printf("GAME OVER: The winner is --> %s%n", currentPlayer.getName());
                break;
            }

			// swap turns
			if(currentPlayerIndex == 0){
				currentPlayerIndex = 1;
                otherPlayerIndex = 0;
			} else {
				currentPlayerIndex = 0;
                otherPlayerIndex = 1;
			}
		} 
    }
}
