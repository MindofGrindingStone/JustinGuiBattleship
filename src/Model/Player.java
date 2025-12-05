package Model;

public interface Player {
    public String getName();
    public void takeShot();
    public ShotResultData receiveShot(Coordinate shot);
    public void receiveShotResult(ShotResultData shotResultData);
    public boolean shipsAreSunk();
    public void placeShips();
}
