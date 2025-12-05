package Model;

import java.util.ArrayList;
import java.util.List;

public class OceanGrid extends Grid {
    private List<Ship> ships = new ArrayList<Ship>();

    public OceanGrid(){
        super();
    }

    public ShotResultData receiveShot(Coordinate location){
        Cell cell = cellAtLocation(location);
        ShotResultData shotResultData;

        // shot lands on empty cell
        if(cell.getState() == CellState.EMPTY){
            cell.setState(CellState.MISS);
            ShotResult result = ShotResult.MISS;
            shotResultData = new ShotResultData(result, location, null, 0);
            notifyListeners();
            return shotResultData;
        }

        // shot lands on ship
        if(cell.getState() == CellState.OCCUPIED){
            cell.setState(CellState.HIT);
            cell.getShip().registerHit(location);
            if(cell.getShip().isSunk()){
                ShotResult result = ShotResult.SUNK;
                shotResultData = new ShotResultData(result, location, cell.getShip().getName(), cell.getShip().getLength());
                notifyListeners();
                return shotResultData;
            } else {
                ShotResult result = ShotResult.HIT;
                shotResultData = new ShotResultData(result, location, cell.getShip().getName(), cell.getShip().getLength());
                notifyListeners();
                return shotResultData;
            }
        }
        return new ShotResultData(ShotResult.INVALID, location, null, 0);
    }

    public void placeShips(List<Ship> ships){
        for(Ship ship : ships){
            this.ships.add(ship);
            for(Coordinate location: ship.getCoordinates()){
                cellAtLocation(location).setState(CellState.OCCUPIED);
                cellAtLocation(location).setShip(ship);
            }
        } 
        notifyListeners();
    }

    public boolean shipsAreSunk(){
        return ships.stream().allMatch(ship -> ship.isSunk());
        // old way
        /* 
        for(Ship ship : ships){
            if(!ship.isSunk()){
                return false;
            }
        }
        return true;
        */
    }
}
