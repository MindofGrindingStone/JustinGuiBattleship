package Model;

import java.util.ArrayList;
import java.util.List;

public class ShipTracker {
    private int size;
    private String name;
    private List<Coordinate> hitCoordinates = new ArrayList<>();

    ShipTracker(int size, String name) {
        this.size = size;
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public List<Coordinate> getHitCoordinates() {
        return hitCoordinates;
    }

    public void recordHit(Coordinate coord) {
        hitCoordinates.add(coord);
    }

    public boolean hasOutstandingShots() {
        if (hitCoordinates.size() == 0) {
            return false;
        }
        return hitCoordinates.size() < size;
    }
    
}
