package Model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Util.Constants;

public class MediumAIPlayer implements Player  {
    
	public static List<Coordinate> shotsToTake = new ArrayList<>();
	private OceanGrid oceanGrid = new OceanGrid();
	private List<Ship> ships = new ArrayList<>();
	private AutomaticShipFactory factory = new AutomaticShipFactory();
    private Shooter currentShooter = null;
    private List<ShipTracker> shipTrackers = new ArrayList<>();
    private ShotDelegate shotDelegate;

	public MediumAIPlayer(ShotDelegate delegate){
        this.shotDelegate = delegate;
        shotsToTake = new ArrayList<>();
        List<Integer> randomNet = new ArrayList<>();
        randomNet.add(0);
        randomNet.add(1);
        randomNet.add(2);
        Collections.shuffle(randomNet);
        // populate shots to take
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++){
				if((x+y)%3 == randomNet.get(0)){
					try{
						shotsToTake.add(new Coordinate(x,y));
					} catch (Exception e){
						// ignore - shouldn't happen
					}
				}
            }
        }
        Comparator<Coordinate> byCenter = Comparator.comparingDouble(
            coord -> Math.abs(coord.getRow() - 4.5) + Math.abs(coord.getColumn() - 4.5)
        );
		shotsToTake.sort(byCenter);
        List<Coordinate> remainingShots = new ArrayList<>();
		for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++){
				if((x+y)%3 == randomNet.get(1)){
					try{
						remainingShots.add(new Coordinate(x,y));
					} catch (Exception e){
						// ignore - shouldn"t happen
					}
				}
            }
        }
        remainingShots.sort(byCenter);
        shotsToTake.addAll(remainingShots);

        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++){
				if((x+y)%3 == randomNet.get(2)){
					try{
						shotsToTake.add(new Coordinate(x,y));
					} catch (Exception e){
						// ignore - shouldn"t happen
					}
				}
            }
        }

        this.currentShooter = new HuntShooter();
        Constants.shipSpecifications.forEach((k,v) -> shipTrackers.add(new ShipTracker(v, k)));
	}

	@Override
    public void placeShips() {
		this.ships = factory.getShips();
        oceanGrid.placeShips(this.ships);
    }

    @Override
    public void takeShot() {
        shotDelegate.handleShot(currentShooter.takeShot(), this);
    }

    @Override
    public ShotResultData receiveShot(Coordinate shot) {
        return oceanGrid.receiveShot(shot);
    }

	@Override
	public String getName() {
		return "Steve 1.0";
	}

	@Override
	public void receiveShotResult(ShotResultData result) {
		Shooter temp = this.currentShooter.nextShooter(result);
        this.currentShooter = temp;
        if (this.currentShooter.noValidShots()) {
            temp = this.currentShooter.nextShooterifNoValidShots();
            this.currentShooter = temp;
        }
        if (result.result() == ShotResult.HIT || result.result() == ShotResult.SUNK) {
            for (ShipTracker tracker : shipTrackers) {
                if (tracker.getName().equals(result.shipName())) {
                    tracker.recordHit(result.location());
                }
            }
        }
        if (this.currentShooter instanceof HuntShooter) {
            checkForOutstandingShips();
        }
	}

	@Override
	public boolean shipsAreSunk() {
		for(Ship ship : ships){
            if(!ship.isSunk()){
                return false;
            }
        }
        return true;
	}

    private void checkForOutstandingShips() {
        for (ShipTracker tracker : shipTrackers) {
            if (tracker.hasOutstandingShots()) {
                List<Coordinate> hits = tracker.getHitCoordinates();
                // sort by row first, then by column
                hits.sort(Comparator.comparingInt(Coordinate::getRow)
                    .thenComparingInt(Coordinate::getColumn));
                Coordinate firstHit = hits.get(0);
                if (hits.size() == 1) {
                    this.currentShooter = new BracketShooter(firstHit);
                    return;
                }
                if (hits.size() >= 2) {
                
                Coordinate secondHit = hits.get(1);
                ShotResultData fakeShotResult = new ShotResultData(ShotResult.HIT, secondHit, tracker.getName(), tracker.getSize());
                    this.currentShooter = new PursuitShooter(fakeShotResult, firstHit);
                    return;
                }
            }
        }
    }
}
