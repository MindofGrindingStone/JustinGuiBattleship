package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EasyAIPlayer implements Player {
    private String name;
    public OceanGrid oceanGrid;
    public ShipFactory shipFactory;
    private List<Coordinate> availableShots;

    public EasyAIPlayer(ShipFactory shipFactory) {
        this.name = "Easy AI";
        this.oceanGrid = new OceanGrid();
        this.shipFactory = shipFactory;
        this.availableShots = getAvailableShots();
    }

    // Getter for available shots list
    private List<Coordinate> getAvailableShots() {
        List<Coordinate> availableShots = new ArrayList<>();
        // Pre-populate all coordinates (10x10 grid)
        try {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                availableShots.add(new Coordinate(row, col));
            }
        } } catch (Exception e) {
         // should be impossible to hit
        }

        // Shuffle coordinates for random selection
        Collections.shuffle(availableShots);
        return availableShots;
    }

    public Coordinate takeShot() {
        // Select the next available shot
        return availableShots.remove(0);
    }


    public void receiveShotResult(ShotResult result){ 
        // AI does not utilize shot results in easy mode
    }



    public ShotResult receiveShot(Coordinate coordinate) {
        return oceanGrid.receiveShot(coordinate);
    }

    public String getName() {
        return name;
    }

    public void displayGrids() { // temporary method to show the customer the printing of a grid
        // doesn't do anything
    }

    public void reset() {
        this.oceanGrid = new OceanGrid();
        this.availableShots = getAvailableShots();
    }


    public void placeShips() {
        List<Ship> ships = shipFactory.getShips();
        oceanGrid.placeShips(ships);
    }

    public boolean shipsAreSunk() {
        return oceanGrid.shipsAreSunk();
    }

}
