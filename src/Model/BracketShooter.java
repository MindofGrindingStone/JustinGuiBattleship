package Model;

import java.util.HashSet;
import java.util.Set;

public class BracketShooter implements Shooter {
    private Set<Coordinate> bracketShots = new HashSet<>();
    private Coordinate initialShot;
    private String shipName;
    
    public BracketShooter(ShotResultData lastShotResult) {
        initialShot = lastShotResult.location();
        shipName = lastShotResult.shipName();
        try {
            bracketShots.add(new Coordinate(initialShot.getRow() + 1, initialShot.getColumn()));
        } catch (Exception e) {
            // do nothing if out of bounds
        }
        try {
            bracketShots.add(new Coordinate(initialShot.getRow() - 1, initialShot.getColumn()));
        } catch (Exception e) {
            // do nothing if out of bounds
        }
        try {
            bracketShots.add(new Coordinate(initialShot.getRow(), initialShot.getColumn() + 1));
        } catch (Exception e) {
            // do nothing if out of bounds
        }
        try {
            bracketShots.add(new Coordinate(initialShot.getRow(), initialShot.getColumn() - 1));
        } catch (Exception e) {
            // do nothing if out of bounds
        }
        bracketShots.retainAll(MediumAIPlayer.shotsToTake);
    }

    @Override
    public Coordinate takeShot() {
        if (!bracketShots.isEmpty()) {
            Coordinate shot = bracketShots.iterator().next();
            bracketShots.remove(shot);
            MediumAIPlayer.shotsToTake.remove(shot);
            return shot;
        } else {
            return MediumAIPlayer.shotsToTake.remove(0);
            // this should hopefully never happen
        }
    }

    @Override
    public Shooter nextShooter(ShotResultData lastShotResultData) {
        ShotResult lastShotResult = lastShotResultData.result();
        if (lastShotResult == ShotResult.MISS || (lastShotResult == ShotResult.HIT && !lastShotResultData.shipName().equals(shipName))) {
            if (bracketShots.isEmpty()) {
                return new HuntShooter();
            } else {
                return this;
            }
        } else if (lastShotResult == ShotResult.HIT && lastShotResultData.shipName().equals(shipName)) {
            return new PursuitShooter(lastShotResultData, initialShot);
        } else if (lastShotResult == ShotResult.SUNK) {
            return new HuntShooter();
        } else {
            // should only hit this if invalid so hopefully never
            return this;
        }
    }

    public BracketShooter(Coordinate initialShot) {
        this.initialShot = initialShot;
        try {
            bracketShots.add(new Coordinate(initialShot.getRow() + 1, initialShot.getColumn()));
        } catch (Exception e) {
            // do nothing if out of bounds
        }
        try {
            bracketShots.add(new Coordinate(initialShot.getRow() - 1, initialShot.getColumn()));
        } catch (Exception e) {
            // do nothing if out of bounds
        }
        try {
            bracketShots.add(new Coordinate(initialShot.getRow(), initialShot.getColumn() + 1));
        } catch (Exception e) {
            // do nothing if out of bounds
        }
        try {
            bracketShots.add(new Coordinate(initialShot.getRow(), initialShot.getColumn() - 1));
        } catch (Exception e) {
            // do nothing if out of bounds
        }
        bracketShots.retainAll(MediumAIPlayer.shotsToTake);
    }

    @Override
    public Boolean noValidShots() {
        // check if there are 0 shots
        if (bracketShots.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Shooter nextShooterifNoValidShots() {
        // Back to hunting
        return new HuntShooter();
    }
}