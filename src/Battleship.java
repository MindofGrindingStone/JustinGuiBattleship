import Controller.WindowController;
import Model.Game;
import View.GameWindow;

public class Battleship {
    public static void main(String[] args) throws Exception {
        // 0. Apple specific properties to set...
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        // 1. Build out the view layer...
        GameWindow gameWindow = new GameWindow("Battleship");

        // 2. Build out the Model Layer...
        Game game = new Game();

        // 3. Connect Models and Views with Controllers
        WindowController wc = new WindowController(gameWindow, game);

        game.start();

        gameWindow.setVisible(true);
        gameWindow.pack();
    }
}
