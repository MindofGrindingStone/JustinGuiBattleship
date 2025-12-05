import Controller.OceanGridController;
import Controller.StatusController;
import Controller.TargetGridController;
import Controller.WindowController;
import Model.Game;
import View.GameWindow;

public class App {
    public static void main(String[] args) throws Exception {
        // 1. Build out the view layer...
        GameWindow gameWindow = new GameWindow("Battleship");

        // 2. Build out the Model Layer...
        Game game = new Game();

        // 3. Connect Models and Views with Controllers
        WindowController wc = new WindowController(gameWindow);
        TargetGridController tgc = new TargetGridController(gameWindow.getTargetPanel(), game.getHumanTargetGrid());
        StatusController sc = new StatusController(gameWindow.getStatusPane(), game);
        OceanGridController ogc = new OceanGridController(gameWindow.getOceanPanel(), game.getHumanOceanGrid());

        game.start();

        gameWindow.setVisible(true);
        gameWindow.pack();
    }
}
