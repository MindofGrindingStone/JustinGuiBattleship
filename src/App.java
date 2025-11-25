import java.awt.Dimension;

import View.GameWindow;

public class App {
    public static void main(String[] args) throws Exception {
        GameWindow gameWindow = new GameWindow("Battleship");

        gameWindow.setPreferredSize(new Dimension(300, 300));
        gameWindow.setVisible(true);
        gameWindow.pack();
    }
}
