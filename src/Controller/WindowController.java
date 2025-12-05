package Controller;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import Model.Game;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import View.GameWindow;

public class WindowController {
    private Game game;

    public WindowController(GameWindow view, Game game) {
        this.game = game;
        
        // menus
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(_ -> {
            
        });
        openItem.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        fileMenu.add(openItem);
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(_ -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Game");

            int userSelection = fileChooser.showSaveDialog(view);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                try {
                    ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileToSave.getAbsolutePath()));
                    os.writeObject(this.game);
                    os.close();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        saveItem.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        fileMenu.add(saveItem);
        view.setJMenuBar(menuBar);
    }
}
