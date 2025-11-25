package View;

import java.awt.BorderLayout;
import java.awt.Container;

public class GameWindow extends BetterWindow {

    private GridPanel targetPanel = new GridPanel();

    public GameWindow(String title) {
        super(title);

        Container contentPane = this.getContentPane();
        contentPane.add(targetPanel, BorderLayout.CENTER);
    }
    
}
