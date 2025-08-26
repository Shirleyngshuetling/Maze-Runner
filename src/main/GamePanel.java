package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static java.awt.SystemColor.text;

//GamePartner will have all the functions of JPanel
//this class inherits JPanel class
//implements Runnable to run thread
public class GamePanel extends JPanel implements Runnable, MouseListener {

    // SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tile, default size for characters
    final int scale = 3; //scale the 16x16 pixels
    public final int tileSize = originalTileSize * scale; //48x48 tile
    //number of tiles display horizontally and vertically
    // this decides the total screen size
    // ratio will be 4:3
    public final int maxScreenCol = 24;
    public final int maxScreenRow = 18;
    //final width and height of game screen
    public final int screenWidth = tileSize * maxScreenCol; //48*24 = 1152 pixels
    public final int screenHeight = tileSize * maxScreenRow; //48*18 = 864 pixels

    //WORLD SETTINGS
    public final int maxWorldCol = 124; //the size of the world map
    public final int maxWorldRow = 100;
    public final int worldWidth = tileSize * maxWorldCol; //48*124 = 5952 pixels
    public final int worldHeight = tileSize * maxWorldRow; //48*100 = 4800 pixels
    //FPS
    int FPS = 60; //frame per second

    //Instantiate class
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread; //once a thread started, it keeps the program running until we stop it
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();


    //so that we can use GamePanel and KeyHandler in the Player Class
    TileManager tileM = new TileManager(this);
    public CollisionChecker collisionCheck = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    //entity and object:
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[15];//prepare 10 slots of objects, we can display up to 10 objects at the same time
    // we can replace the content during the game
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int tutorialState = 3;
    public final int gameOverState = 6;
    public final int victoryState = 7;
    public final int  settingsState = 5;


    // create a constructor of this GamePanel
    public GamePanel() {
        this.addMouseListener(this);

        //set the panel size of this class(JPanel)
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        //set the background color of the GamePanel
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // if set to true, all the drawing from this component will be done in an offscreen painting buffer
        // in short, enabling this can improve the game's rendering performance


        this.addKeyListener(keyH); //GamePanel can now recognise the key input
        this.setFocusable(true); //With this, this GamePanel can be focused to receive key input

    }

    // Implement MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {
        handleMouseClick(e.getX(), e.getY());
    }
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public void setupGame() {
        //call this method before the game starts
        aSetter.setObject();
        aSetter.setMonster();
        //playMusic(0);
        gameState = titleState;
    }

    public void startGameThread() {
        //instantiate a thread
        gameThread = new Thread(this); //"this" passing GamePanel class to this thread's constructor
        gameThread.start(); //will automatically call the run method
    }

    //when we start gameThread, it automatically call the run method
    @Override
    public void run() {
        // create game loop
        // as long as the gameThread exists, it repeats the process that is written inside of this loop

        double drawInterval = 1000000000 / FPS; //draw the screen every 0.016seconds, means we can draw the screen 60 times per second
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {
            //this loop does two things
            //1. update information such as character position
            //2. draw the screen with the updated information

            //call the two methods
            update();

            repaint();

            //remain sleep during the remainingTime before the nextDrawTime is reached
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                //convert the remainingTime from nanoseconds to milliseconds
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime); //sleep will pause the game loop, it won't do anything until this sleep time is over
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    //update player position
    public void update() {


        if (gameState == gameOverState) {
            stopMusic();
            return; // Stop updating the game when in Game Over state
        }

         if (gameState == playState) {
            keyH.enterPressed = false; //reset in case user pressed enter in selection menu
            player.update();
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null && monster[i].monster_alive ==1 ) {
                    monster[i].update();

                    // Check collision with player
                    collisionCheck.checkPlayer(monster[i]);

                }
            }
        }
        if (gameState == pauseState) {
            //nothing happens(we do not update the players information while the game is paused)
        }

        // Ensure camera stays within world bounds
        player.worldX = Math.max(player.screenX,
                Math.min(player.worldX,
                        worldWidth - player.screenX));
        player.worldY = Math.max(player.screenY,
                Math.min(player.worldY,
                        worldHeight - player.screenY));

    }

    //one of the standard methods to draw JPanel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Graphics2D class extends the Graphic class to provide more sophisticated control over geometry,
        //coordinate transformations, color management, and text layout
        Graphics2D g2 = (Graphics2D) g; //change g to Graphics2D graph
        //TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        } else {
            tileM.draw(g2);
            //empty the entity list;
            entityList.clear();

            //Add entities to the list
            entityList.add(player);

            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null && monster[i].monster_alive == 1) {
                    entityList.add(monster[i]);
                }

            }
            //sorting
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });

            //drawing the entities:
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            //empty the entity list;
            entityList.clear();


            ui.draw(g2);
        }
    }
        public void drawTextWithBackground (Graphics2D g2, String text,int x, int y){
            // Get text width and height for proper background sizing
            FontMetrics metrics = g2.getFontMetrics();
            int textWidth = metrics.stringWidth(text);
            int textHeight = metrics.getHeight();

            // Draw semi-transparent black rectangle behind text
            g2.setColor(new Color(0, 0, 0, 180)); // Black with 70% opacity
            g2.fillRect(x - 5, y - textHeight + 5, textWidth + 10, textHeight + 5);

            // Draw text in white on top of the background
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);
        }

        public void restart () {
            player.setDefaultValues();
            player.life = player.maxLife; // Reset to full lives
            player.lastHitTime = 0;
            player.monsterdamage = false;
            player.monsterdamageCounter = 0;

            aSetter.setObject();  // reset objects
            aSetter.setMonster(); // reset monsters
            player.hasKey = 0;  // reset key count
            ui.playTime = 0;   // reset time
            ui.message = "";
            ui.messageOn = false;

            // will need to reset npc/monsters too. refer to video 37 when this becomes relevant
        }
        public void playMusic ( int i){
            music.setFile(i);
            music.play();
            music.loop();

        }

        public void stopMusic () {
//            music.stop();
            if (music != null && music.clip != null) {  // Prevent NullPointerException
                music.stop();
            }
        }

        public void playSE ( int i){
            se.setFile(i);
            se.play();
        }

        public void handleMouseClick(int mouseX, int mouseY) {
        ui.checkMouseClick(mouseX, mouseY);
    }
}
