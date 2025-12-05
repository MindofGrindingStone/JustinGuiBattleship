package Model;

import java.util.ArrayList;
import java.util.List;

public class ReverseShooter implements Shooter {
    private Coordinate initialShot;
    private List<Coordinate> reverseShots = new ArrayList<>();
    
    public ReverseShooter(Coordinate initialShot, Coordinate secondShot) {
        this.initialShot = initialShot;
        try {
            reverseShots.addAll(generateCoordinatesInReverseDirection(initialShot, secondShot));
            reverseShots.retainAll(MediumAIPlayer.shotsToTake);
        } catch (Exception e) {
            // do nothing if out of bounds
        }
    }

    @Override
    public Coordinate takeShot() {
        if (!reverseShots.isEmpty()) {
            Coordinate shot = reverseShots.remove(0);
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
            return new BracketShooter(initialShot);
        } else if (lastShotResultData.result() == ShotResult.HIT) {
            return this;
        } else if (lastShotResultData.result() == ShotResult.SUNK) {
            return new HuntShooter();
        } else {
            // should only hit this if invalid so hopefully never
            return new HuntShooter();
        }
    }

        private List<Coordinate> generateCoordinatesInReverseDirection(Coordinate initialShot, Coordinate secondShot) {
        int rowDiff = secondShot.getRow() - initialShot.getRow();
        int colDiff = secondShot.getColumn() - initialShot.getColumn();
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            try {
                coordinates.add(new Coordinate(initialShot.getRow() - i * rowDiff, initialShot.getColumn() - i * colDiff));
            } catch (Exception e) {
                // do nothing if out of bounds
            }
        }
        return coordinates;
    }

        @Override
        public Boolean noValidShots() {
            // Check if no shots
            if (reverseShots.size() == 0) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public Shooter nextShooterifNoValidShots() {
            // back to hunting
            return new HuntShooter();
        }
}