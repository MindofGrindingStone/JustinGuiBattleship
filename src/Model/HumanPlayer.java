package Model;

public class HumanPlayer implements Player {
    private String name;
    private OceanGrid oceanGrid = new OceanGrid();
    private TargetGrid targetGrid = new TargetGrid();
    private ShipFactory shipFactory;

    public HumanPlayer(String name, ShipFactory shipFactory){
        this.name = name;
        this.shipFactory = shipFactory;
    }

    public void placeShips(){
        oceanGrid.placeShips(shipFactory.getShips());
    }

    public TargetGrid getTargetGrid() {
        return targetGrid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void takeShot() {
        // wait for player to click the target grid
    }

    @Override
    public ShotResult receiveShot(Coordinate shot) {
        return oceanGrid.receiveShot(shot);
    }

    @Override
    public void receiveShotResult(ShotResult shotResult) {
        targetGrid.receiveShotResult(shotResult);
    }

    @Override
    public boolean shipsAreSunk() {
        return oceanGrid.shipsAreSunk();
    }
}
