package View;

import javax.swing.JPanel;

import Model.CellState;
import Model.GridRep;
import Util.Constants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class GridPanel extends JPanel {
    private GridRep gridRep = null;

    public GridPanel() {
        this.setBackground(Color.CYAN);
    }

    public void setGridRep(GridRep gridRep) {
        this.gridRep = gridRep;
        this.repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        // panel dimensions
        int width = this.getWidth();
        int height = this.getHeight();
        int spacing = Constants.Dimensions.CELL_SIZE;

        // set colors
        g.setColor(Color.BLACK);

        // draw vertical lines
        for (int x = 0; x < width; x += spacing) {
            g.drawLine(x, 0, x, height);
        }

        // draw horizontal lines
        for (int y = 0; y < height; y += spacing) {
            g.drawLine(0, y, width, y);
        }

        if (gridRep != null) {
            // draw cell states
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    CellState state = gridRep.getStateAt(row, col);
                    switch (state) {
                        case MISS -> drawMissAt(row, col, g);
                        case HIT -> drawHitAt(row, col, g);
                        case OCCUPIED -> drawOccupiedAt(row, col, g);
                        case EMPTY -> {}
                    }
                }
            }
        }
    }

    private void drawOccupiedAt(int row, int col, Graphics g) {
        // draw whole cell dark grey for ship

        // location math
        int upperLeftX = col * Constants.Dimensions.CELL_SIZE;
        int upperLeftY = row * Constants.Dimensions.CELL_SIZE;
        int width = Constants.Dimensions.CELL_SIZE;
        int height = Constants.Dimensions.CELL_SIZE;

        g.setColor(Color.DARK_GRAY);
        g.fillRect(upperLeftX, upperLeftY, width, height);
    }

    private void drawHitAt(int row, int col, Graphics g) {
        // draw red circle to represent a peg

        // location math
        int upperLeftX = col * Constants.Dimensions.CELL_SIZE + Constants.Dimensions.CELL_SIZE / 2 - Constants.Dimensions.PEG_DIAMETER / 2;
        int upperLeftY = row * Constants.Dimensions.CELL_SIZE + Constants.Dimensions.CELL_SIZE / 2 - Constants.Dimensions.PEG_DIAMETER / 2;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        Ellipse2D.Double circle = new Ellipse2D.Double(upperLeftX, upperLeftY, Constants.Dimensions.PEG_DIAMETER, Constants.Dimensions.PEG_DIAMETER);
        g2d.fill(circle);
        g2d.setColor(Color.BLACK);
        g2d.draw(circle);
    }

    private void drawMissAt(int row, int col, Graphics g) {
        // draw white circle to represent a peg

        // location math
        int upperLeftX = col * Constants.Dimensions.CELL_SIZE + Constants.Dimensions.CELL_SIZE / 2 - Constants.Dimensions.PEG_DIAMETER / 2;
        int upperLeftY = row * Constants.Dimensions.CELL_SIZE + Constants.Dimensions.CELL_SIZE / 2 - Constants.Dimensions.PEG_DIAMETER / 2;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        Ellipse2D.Double circle = new Ellipse2D.Double(upperLeftX, upperLeftY, Constants.Dimensions.PEG_DIAMETER, Constants.Dimensions.PEG_DIAMETER);
        g2d.fill(circle);
        g2d.setColor(Color.BLACK);
        g2d.draw(circle);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Constants.Dimensions.GRID_SIZE, Constants.Dimensions.GRID_SIZE);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(Constants.Dimensions.GRID_SIZE, Constants.Dimensions.GRID_SIZE);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Constants.Dimensions.GRID_SIZE, Constants.Dimensions.GRID_SIZE);
    }

}
