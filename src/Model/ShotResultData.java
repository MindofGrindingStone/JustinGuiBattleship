package Model;

import java.io.Serializable;

public record ShotResultData(ShotResult result, Coordinate location, String shipName, int length) implements Serializable{}
