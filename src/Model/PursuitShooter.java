package Model;

import java.util.ArrayList;
import java.util.List;

public class PursuitShooter implements Shooter {
    private Coordinate initialShot;
    private Coordinate secondShot;
    private List<Coordinate> pursuitShots = new ArrayList<>();

    public PursuitShooter(ShotResultData lastShotResult, Coordinate initialShot) {
        this.initialShot = initialShot;
        this.secondShot = lastShotResult.location();
            pursuitShots.addAll(generateCoordinatesFromShotResult(lastShotResult, initialShot));
            pursuitShots.retainAll(MediumAIPlayer.shotsToTake);
        }

    public PursuitShooter(Coordinate initialShot, Coordinate secondShot) {
        this.initialShot = initialShot;
        this.secondShot = secondShot;
            pursuitShots.addAll(generateCoordinatesInDirection(initialShot, secondShot));
            pursuitShots.retainAll(MediumAIPlayer.shotsToTake);
        }
    

    @Override
    public Coordinate takeShot() {
        if (!pursuitShots.isEmpty()) {
            Coordinate shot = pursuitShots.remove(0);
            MediumAIPlayer.shotsToTake.remove(shot);
            return shot;
        } else {
            return MediumAIPlayer.shotsToTake.remove(0);
            // this should hopefully never happen
        }
    }

    @Override
    public Shooter nextShooter(ShotResultData lastShotResultData) {
        if (lastShotResultData.result() == ShotResult.MISS) {
            return new ReverseShooter(initialShot, secondShot);
        } else if (lastShotResultData.result() == ShotResult.HIT) {
            return this;
        } else if (lastShotResultData.result() == ShotResult.SUNK) {
            return new HuntShooter();
        } else {
            // should only hit this if invalid so hopefully never
            return new HuntShooter();
        }
    }

    private List<Coordinate> generateCoordinatesFromShotResult(ShotResultData lastShotResult, Coordinate initialShot) {
        Coordinate secondShot = lastShotResult.location();
        int rowDiff = secondShot.getRow() - initialShot.getRow();
        int colDiff = secondShot.getColumn() - initialShot.getColumn();
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 2; i < 5; i++) {
            try {
                coordinates.add(new Coordinate(initialShot.getRow() + i * rowDiff, initialShot.getColumn() + i * colDiff));
            } catch (Exception e) {
                // do nothing if out of bounds
            }
        }
        return coordinates;
    }

    private List<Coordinate> generateCoordinatesInDirection(Coordinate initialShot, Coordinate secondShot) {
        int rowDiff = secondShot.getRow() - initialShot.getRow();
        int colDiff = secondShot.getColumn() - initialShot.getColumn();
        if (rowDiff > 0) {
            rowDiff = 1;
        } else if (rowDiff < 0) {
            rowDiff = -1;
        } else if (colDiff > 0) {
            colDiff = 1;
        } else {
            colDiff = -1;
        }
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            try {
                coordinates.add(new Coordinate(initialShot.getRow() + i * rowDiff, initialShot.getColumn() + i * colDiff));
            } catch (Exception e) {
                // do nothing if out of bounds
            }
        }
        return coordinates;
    }

    @Override
    public Boolean noValidShots() {
        // Check if 0 shots
        if (pursuitShots.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Shooter nextShooterifNoValidShots() {
        // try the reverse shooter
        return new ReverseShooter(initialShot, secondShot);
    }
}