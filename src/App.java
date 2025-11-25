import View.GameWindow;

public class App {
    public static void main(String[] args) throws Exception {
        GameWindow gameWindow = new GameWindow("Battleship");

        gameWindow.setVisible(true);
        gameWindow.pack();
    }
}
