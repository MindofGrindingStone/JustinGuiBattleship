package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import Util.Constants;

public class GameWindow extends BetterWindow {

    private GridPanel targetPanel = new GridPanel();
    private GridPanel oceanPanel = new GridPanel();
    private JTextPane statusPanel = new JTextPane();
    private JScrollPane statusScroller = new JScrollPane(statusPanel);
    private FadingLabel shotLabel = new FadingLabel("Shot Counter", JLabel.CENTER);

    public GameWindow(String title) {
        super(title);

        Container contentPane = this.getContentPane();

        // setup center panel with BoxLayout
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // stack in target grid
        targetPanel.setBackground(Color.LIGHT_GRAY);
        centerPanel.add(targetPanel);

        // stack in status area
        statusPanel.setEditable(false);
        statusPanel.setText("Welcome to Battleship!\n");
        try {
            statusPanel.getStyledDocument().insertString(statusPanel.getStyledDocument().getLength(), "It is your turn", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        statusScroller.setMinimumSize(new Dimension(Constants.Dimensions.GRID_SIZE, Constants.Dimensions.STATUS_AREA_HEIGHT));
        statusScroller.setPreferredSize(new Dimension(Constants.Dimensions.GRID_SIZE, Constants.Dimensions.STATUS_AREA_HEIGHT));
        // so - answer to how to always scroll to the bottom.
        statusScroller.getVerticalScrollBar().addAdjustmentListener(e -> e.getAdjustable().setValue(e.getAdjustable().getMaximum()));
        centerPanel.add(statusScroller);

        // stack in ocean grid
        centerPanel.add(oceanPanel);

        // stack in footer
        JPanel footerPanel = new JPanel();
        footerPanel.add(shotLabel);
        centerPanel.add(footerPanel);

        // add to content pane
        contentPane.add(centerPanel, BorderLayout.CENTER);

        // window: not resizeable, sized specifically to calculation
        this.setResizable(false);
        this.setSize(new Dimension(Constants.Dimensions.WINDOW_WIDTH, Constants.Dimensions.WINDOW_HEIGHT));
        this.setPreferredSize(new Dimension(Constants.Dimensions.WINDOW_WIDTH, Constants.Dimensions.WINDOW_HEIGHT));
        
    }
    
}
