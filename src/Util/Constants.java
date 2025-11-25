package Util;

import java.util.Map;

public class Constants {
    public final static Map<String, Integer> shipSpecifications = Map.of("Carrier", 5, "Battleship", 4, "Destroyer", 3, "Submarine", 3, "Patrol Boat", 2);

    public class Dimensions {
        public final static int CELL_SIZE = 36;
    }
    
	private Constants(){}
}
