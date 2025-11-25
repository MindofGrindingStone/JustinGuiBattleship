package Model;

public class TargetGrid extends Grid {
    
    public TargetGrid(){
        super();
    }

    public void receiveShotResult(ShotResult result){
        Coordinate location = result.getLocation();
        Cell affectedCell = cellAtLocation(location);
        switch(result){
            case MISS:
                affectedCell.setState(CellState.MISS);
                break;
            case HIT:
                affectedCell.setState(CellState.HIT);
                break;
            case SUNK:
                affectedCell.setState(CellState.HIT);
                break;
            case INVALID:
                // this should never happen
                break;
        }
    }

    public boolean isValidShot(Coordinate location){
        if(cellAtLocation(location).getState() == CellState.EMPTY){
            return true;
        }
        return false;
    }
}
