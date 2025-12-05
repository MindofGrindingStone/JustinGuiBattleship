package Model;

public class TargetGrid extends Grid implements ShotDelegate {
    private ShotDelegate shotDelegate;
    
    public TargetGrid(ShotDelegate delegate){
        super();
        this.shotDelegate = delegate;
    }

    public void receiveShotResult(ShotResultData result){
        Coordinate location = result.location();
        Cell affectedCell = cellAtLocation(location);
        switch(result.result()){
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
        notifyListeners();
    }

    public boolean isValidShot(Coordinate location){
        if(cellAtLocation(location).getState() == CellState.EMPTY){
            return true;
        }
        return false;
    }

    @Override
    public void handleShot(Coordinate shot, Object sender) {
        shotDelegate.handleShot(shot, this);
    }

}
