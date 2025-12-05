package Model;

public class HuntShooter implements Shooter {


    @Override
    public Coordinate takeShot() {
        return MediumAIPlayer.shotsToTake.remove(0);
    }

    @Override
    public Shooter nextShooter(ShotResultData lastShotResultData) {
        if (lastShotResultData.result() == ShotResult.MISS || lastShotResultData.result() == ShotResult.SUNK) {
            return this;
        } else if (lastShotResultData.result() == ShotResult.HIT) {
            return new BracketShooter(lastShotResultData);
        } else {
            // should only hit this if invalid so hopefully never
            return this;
        }
    }

    @Override
    public Boolean noValidShots() {
        // Really should never happen so uh
        return false;
    }

    @Override
    public Shooter nextShooterifNoValidShots() {
        // again really should never happen
        return this;
    }
    
    
}
