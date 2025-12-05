package Model;

import java.io.Serializable;

public class GridRep implements Serializable {
    private CellState[][] cellStates = new CellState[10][10];

    public GridRep(Grid grid){
        for(int row = 0; row < 10; row++){
            for(int col = 0; col < 10; col++){
                cellStates[row][col] = grid.cells[row][col].getState();
            }
        }
    }

    public CellState getStateAt(int row, int col){
        return cellStates[row][col];
    }
}
