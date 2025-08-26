package main;

import javax.swing.*;

public class Main {
    public static void main (String[] args){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //so that we can close window properly
        window.setResizable(false); //so that we cannot resize the window
        window.setTitle ("Maze Runner"); //Game Title

        GamePanel gamePanel = new GamePanel();
        //add gamePanel to this window
        window.add(gamePanel);

        window.pack(); //causes this window to be sized to fit the preferred size and layouts of its subcomponents (=GamePanel)


        window.setLocationRelativeTo(null); //window display on the centre of the screen
        window.setVisible(true); //so that window is visible

        gamePanel.setupGame(); //call setupGame method in GamePanel class to set up game world and objects
        gamePanel.startGameThread();
    }
}