package Model;

public enum ShotResult {
    HIT,
    MISS,
    SUNK,
    INVALID;
}

// Immutable data holder for shot result details
public record ShotResultData(ShotResult result, Coordinate location, String shipName, int length) {}
