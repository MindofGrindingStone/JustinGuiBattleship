package View;

import javax.swing.JPanel;

import Util.Constants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class GridPanel extends JPanel {
    
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
