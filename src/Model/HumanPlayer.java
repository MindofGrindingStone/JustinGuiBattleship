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
    public Coordinate takeShot() {
        Coordinate shot = null;
        // show grids at beginning of turn
        targetGrid.print();
        oceanGrid.print();
        // attempt a shot
        while(true){
            String input = ConsoleHelper.getInput("Enter your shot: ");
            try {
                shot = new Coordinate(input);   
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            if(targetGrid.isValidShot(shot)){
                break;
            } else {
                System.out.printf("You have already shot at %s%n", input);
            }
        }
        return shot;
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
