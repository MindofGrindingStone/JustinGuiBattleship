package Model;

public interface Shooter {
    public Coordinate takeShot();
    public Shooter nextShooter(ShotResultData lastShotResultData);
    public Boolean noValidShots();
    public Shooter nextShooterifNoValidShots();
}
