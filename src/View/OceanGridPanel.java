package View;

import java.awt.Graphics;

public class OceanGridPanel extends GridPanel {
    
    @Override
    protected void drawHitAt(int row, int col, Graphics g) {
        super.drawOccupiedAt(row, col, g);
        super.drawHitAt(row, col, g);
    }
}
