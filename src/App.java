import Controller.StatusController;
import Controller.TargetGridController;
import Model.Game;
import View.GameWindow;

public class App {
    public static void main(String[] args) throws Exception {
        // 1. Build out the view layer...
        GameWindow gameWindow = new GameWindow("Battleship");

        // 2. Build out the Model Layer...
        Game game = new Game();

        // 3. Connect Models and Views with Controllers
        TargetGridController tgc = new TargetGridController(gameWindow.getTargetPanel(), game.getHumanTargetGrid());
        StatusController sc = new StatusController(gameWindow.getStatusPane(), game);

        gameWindow.setVisible(true);
        gameWindow.pack();
    }
}
