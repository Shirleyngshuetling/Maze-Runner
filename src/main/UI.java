package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_MainMenu;
import object.OBJ_tutorial;

public class UI {

    GamePanel gp;
    KeyHandler kh;
    Graphics2D g2;
    Font roman_30, roman_60;
    BufferedImage keyImage, heart_full, heart_blank, testingtut, mainmenu;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public int commandNum = 0;
    public final int MAX_COMMAND_NUM = 3; // Number of selectable options
    int previousState;
    int subState = 0;


    private Rectangle playButton, tutorialButton, exitButton;
    private Rectangle victoryPlayAgainButton, victoryMainMenuButton;
    private Rectangle gameOverRetryButton, gameOverMainMenuButton;
    private Rectangle pauseResumeButton, pauseTutorialButton, pauseMainMenuButton;
    private Rectangle exitTutorialButton, exitSettingsButton;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;
        roman_30 = new Font("Times New Roman", Font.PLAIN, 30);
        roman_60 = new Font("Times New Roman", Font.BOLD, 60);

        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;

        OBJ_tutorial tutorial = new OBJ_tutorial(gp);
        testingtut = tutorial.image;

        OBJ_MainMenu backdrop = new OBJ_MainMenu(gp);
        mainmenu = backdrop.image;

        // create HUD object
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_blank = heart.image2;

        // Initialize button bounds
        playButton = new Rectangle();
        tutorialButton = new Rectangle();
        exitButton = new Rectangle();
        victoryPlayAgainButton = new Rectangle();
        victoryMainMenuButton = new Rectangle();
        gameOverRetryButton = new Rectangle();
        gameOverMainMenuButton = new Rectangle();
        pauseResumeButton = new Rectangle();
        pauseTutorialButton = new Rectangle();
        pauseMainMenuButton = new Rectangle();
        exitTutorialButton = new Rectangle();
        exitSettingsButton = new Rectangle();
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(roman_60);
        g2.setColor(Color.black);

        //TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        if (gp.gameState == gp.playState) {
            //do playstate stuff later
            drawPlayerLife();

            g2.setFont(roman_30);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            gp.drawTextWithBackground(g2, "Keys:  " + gp.player.hasKey, 74, 60);

            // Display playing time
            playTime += (double) 1 / 60;
            gp.drawTextWithBackground(g2, "Time: " + dFormat.format(playTime), gp.tileSize * 11, 65);

            // Display Message
            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(24F));
                gp.drawTextWithBackground(g2, message, getXforCenteredText(message), gp.tileSize * 8);

                messageCounter++;

                if (messageCounter > 120) {           // 120 frames at 60fps, so text stays for one second
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
        if (gp.gameState == gp.tutorialState) {
            drawTutorialScreen();
        }
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
        if (gp.gameState == gp.victoryState) {
            drawVictoryScreen();
        }
        // SETTINGS STATE
        if (gp.gameState == gp.settingsState) {
            drawSettingsScreen();
        }
    }

    public void drawVictoryScreen() {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setFont(roman_30);
        g2.setColor(Color.white);

        String text;
        int x;
        int y;

//        text = "You found the treasure!";
//        x = gp.screenWidth/3 - (gp.tileSize/4);
//        y = gp.screenHeight/3;
//        g2.drawString(text,x,y);

        g2.setFont(roman_60);
        text = "You Win!";
        x = getXforCenteredText(text);
        y = gp.screenHeight / 3; // change to * 3
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(30f));

        text = "Your time is: " + dFormat.format(playTime);
        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 - (gp.tileSize);
        g2.drawString(text, x, y);

        text = "Play Again";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3; // make it += and change  to *3
        g2.drawString(text, x, y);
        // start new game
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }
        victoryPlayAgainButton.setBounds(x - 40, y - 30, 200, 40);
        System.out.println("Play Again Button Bounds: " + victoryPlayAgainButton);

        // back to main menu
        text = "Return to Main Menu";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }
        victoryMainMenuButton.setBounds(x - 40, y - 30, 300, 40);
        System.out.println("Main Menu Button Bounds: " + victoryMainMenuButton);

    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int y;
        int x;
        String text;
        // shadow
        g2.setColor(Color.black);
        text = "YOU DIED!";
        x = getXforCenteredText(text);
        y = gp.screenHeight / 3;
        g2.drawString(text, x, y);
        // main text
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        // retry
        g2.setFont(g2.getFont().deriveFont(30f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }
        // Update button bounds
        gameOverRetryButton.setBounds(x - 40, y - 30, 120, 40);
        System.out.println("Retry Button Bounds: " + gameOverRetryButton);

        // back to main menu
        text = "Return to Main Menu";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }
        // Update button bounds
        gameOverMainMenuButton.setBounds(x - 40, y - 30, 300, 40);
        System.out.println("Main Menu Button Bounds: " + gameOverMainMenuButton);
    }

    public void drawPlayerLife() {
        int x;
        int y;
        int i = 0;
        x = gp.screenWidth - (gp.tileSize * 5);
        y = gp.screenHeight / 2 - (gp.tileSize * 8) - (gp.tileSize / 2);

        // draw max life
        while (i < gp.player.maxLife) {
            g2.drawImage(heart_blank, x, y, 50, 50, null);
            i++;
            x += gp.tileSize;
        }

        // reset values
        i = 0;
        x = gp.screenWidth - (gp.tileSize * 5);
        y = gp.screenHeight / 2 - (gp.tileSize * 8) - (gp.tileSize / 2);

        // draw current life
        while (i < gp.player.life) {
            g2.drawImage(heart_full, x, y, 50, 50, null);
            i++;
            x += gp.tileSize;
        }
    }

    public void drawTitleScreen() {

        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.drawImage(mainmenu, 0, 0, mainmenu.getWidth(), mainmenu.getHeight(), null);

        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
        String text = "MAZE RUNNER";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 5;
        //SHADOW
        g2.setColor(Color.gray);
        g2.drawString(text, x + 5, y + 5);
        //MAIN COLOR
        g2.setColor(Color.white);
        g2.drawString(text, x, y);


        //MAZE RUNNER IMAGE
        x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        text = "Play game";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);

        }
        // Define the "Play game" button area
        playButton = new Rectangle(x - gp.tileSize, y - 30, gp.tileSize * 4, gp.tileSize);


        text = "View tutorial";
        x = getXforCenteredText(text);
        y += gp.tileSize * 1;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);

        }
        // Define the "View tutorial" button area
        tutorialButton = new Rectangle(x - gp.tileSize, y - 30, gp.tileSize * 5, gp.tileSize);


        text = "Exit";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        x = getXforCenteredText(text);
        y += gp.tileSize * 1;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);

        }
        // Define the "Exit" button area
        exitButton = new Rectangle(x - gp.tileSize, y - 30, gp.tileSize * 2, gp.tileSize);


        text = "Press 'Z' key to access settings";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 19F));
        x = getXforCenteredText(text);
        y += gp.tileSize * 1;
        g2.drawString(text, x, y);


    }

    public void drawPauseScreen() {

        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setColor(Color.white);
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 3;

        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        text = "Resume";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);

        }
        pauseResumeButton.setBounds(x - gp.tileSize, y - 30, 200, 40);
        System.out.println("Resume Button Bounds: " + pauseResumeButton);

        text = "View tutorial";
        x = getXforCenteredText(text);
        y += gp.tileSize * 1;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);

        }
        pauseTutorialButton.setBounds(x - gp.tileSize, y - 30, 250, 40);
        System.out.println("Tutorial Button Bounds: " + pauseTutorialButton);

        text = "Return to main menu";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));
        x = getXforCenteredText(text);
        y += gp.tileSize * 1;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);

        }
        pauseMainMenuButton.setBounds(x - gp.tileSize, y - 30, 350, 40);
        System.out.println("Main Menu Button Bounds: " + pauseMainMenuButton);

    }

    public void drawTutorialScreen() {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.drawImage(testingtut, gp.tileSize + gp.tileSize / 2, gp.tileSize * 2, testingtut.getWidth(), testingtut.getHeight(), null);

        g2.setColor(Color.white);
        String text = "TUTORIAL";
        int x = getXforCenteredText(text);
        int y = gp.tileSize + gp.tileSize / 2;
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        text = "Return";
        x = getXforCenteredText(text);
        y += gp.tileSize * 16;
        g2.drawString(text, x, y);
        g2.drawString(">", x - gp.tileSize, y);

        exitTutorialButton.setBounds(x - gp.tileSize, y - 30, 200, 40);
        System.out.println("Return Button Bounds: " + exitTutorialButton);

    }

    public void drawSettingsScreen() {

        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(40F));

        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;

        switch (subState) {
            case 0:
                settings_top(frameX, frameY);
                break;
        }
        gp.keyH.enterPressed = false;

    }

    public void settings_top(int frameX, int frameY) {
        int textX;
        int textY;

        // TITLE
        String text = "SETTINGS";
        textX = getXforCenteredText(text);
        textY = gp.tileSize * 3 + gp.tileSize / 2;
        g2.drawString(text, textX, textY);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40F));

        // MUSIC
        textY += gp.tileSize * 5;
        g2.drawString("Music", textX - 96, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 96 - gp.tileSize, textY);
        }

        // SE
        textY += gp.tileSize;
        g2.drawString("Sound Effects", textX - 96, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 96 - gp.tileSize, textY);
        }
        // Exit button
        text = "Return";
        textX = getXforCenteredText(text);
        textY += gp.tileSize * 2;
        g2.drawString(text, textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - gp.tileSize, textY);

        }
        exitSettingsButton.setBounds(textX - gp.tileSize, textY - 30, 200, 40);

        // Music Volume
        textX = frameX + gp.tileSize * 7 + 24;
        textY = frameY + gp.tileSize * 7;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
        // SE Volume
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 22F));
        text = "Use 'W' and 'S' to navigate through the menu.";
        g2.drawString(text, textX / 2 + 54, textY - gp.tileSize * 3);
        text = "Use the 'A' and 'D' keys to adjust the volume.";
        g2.drawString(text, textX / 2 + 54, textY - gp.tileSize * 3 + 30);

    }


    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    public void checkMouseClick(int mouseX, int mouseY) {
//        System.out.println("Checking mouse click at: (" + mouseX + ", " + mouseY + ")");
//        System.out.println("Current gameState: " + gp.gameState);

        if (gp.gameState == gp.titleState) {
//            System.out.println("Title State - Play Button Bounds: " + playButton);
            if (playButton.contains(mouseX, mouseY)) {
                System.out.println("Play button clicked! Switching to Play State.");
                gp.restart();
                gp.gameState = gp.playState;
                gp.playMusic(0);
            } else if (tutorialButton.contains(mouseX, mouseY)) {
                System.out.println("Tutorial button clicked!");
                // Add tutorial logic here
                previousState = gp.gameState;
                gp.gameState = gp.tutorialState;
            } else if (exitButton.contains(mouseX, mouseY)) {
                System.out.println("Exit button clicked!");
                System.exit(0);
            }

        }
//        if (gp.gameState == gp.playState){
//                previousState = gp.gameState;
//        }
//        if (gp.gameState == gp.settingsState) {
//            System.out.println("Settings State - Play Button Bounds: " + playButton);
//            if (exitSettingsButton.contains(mouseX, mouseY)) {
//                if (previousState == 0){
//                    System.out.println("Settings Return button clicked! Switching to title State.");
//                    gp.gameState = gp.titleState;
//                    gp.stopMusic();
//                    gp.restart();
//                }
//                if (previousState == 1){
//                    System.out.println("Settings Return button clicked! Switching to play State.");
//                    gp.gameState = gp.playState;
//                }
//            }
//        }
        else if (gp.gameState == gp.victoryState) {
//            System.out.println("Victory State - Play Again Button Bounds: " + victoryPlayAgainButton);
            if (victoryPlayAgainButton.contains(mouseX, mouseY)) {
                System.out.println("Play Again button clicked! Restarting game.");
                gp.restart();
                gp.playMusic(0);
                gp.gameState = gp.playState;
            } else if (victoryMainMenuButton.contains(mouseX, mouseY)) {
                System.out.println("Main Menu button clicked! Switching to Title State.");
                gp.stopMusic();  // ✅ Stop game over music (if any)
                gp.gameState = gp.titleState;
            }
        } else if (gp.gameState == gp.gameOverState) {
//            System.out.println("Game Over State - Retry Button Bounds: " + gameOverRetryButton);
            if (gameOverRetryButton.contains(mouseX, mouseY)) {
                System.out.println("Retry button clicked! Restarting game.");
                gp.restart();
                gp.playMusic(0);
                gp.gameState = gp.playState;
            } else if (gameOverMainMenuButton.contains(mouseX, mouseY)) {
                System.out.println("Main Menu button clicked! Switching to Title State.");
                gp.stopMusic();  // ✅ Stop game over music (if any)
                gp.gameState = gp.titleState;

                // Reset Game Over button bounds
                gameOverRetryButton.setBounds(0, 0, 0, 0);
                gameOverMainMenuButton.setBounds(0, 0, 0, 0);
            }
        } else if (gp.gameState == gp.pauseState) {
//            System.out.println("Pause State - Resume Button Bounds: " + pauseResumeButton);
            if (pauseResumeButton.contains(mouseX, mouseY)) {
                System.out.println("Resume button clicked! Switching to Play State.");
                gp.gameState = gp.playState;
            } else if (pauseTutorialButton.contains(mouseX, mouseY)) {
                System.out.println("Tutorial button clicked!");
                // Add tutorial logic here
                previousState = gp.gameState;
                gp.gameState = gp.tutorialState;
            } else if (pauseMainMenuButton.contains(mouseX, mouseY)) {
                System.out.println("Main Menu button clicked! Switching to Title State.");
                gp.stopMusic();  // ✅ Stop game over music (if any)
                gp.gameState = gp.titleState;
            }
        } else if (gp.gameState == gp.tutorialState) {
            // add logic for return button
//            System.out.println("Tutorial State - Return Button Bounds: " + exitTutorialButton);
            if (exitTutorialButton.contains(mouseX, mouseY)) {
                System.out.println("Return button clicked! Switching to Previous State.");
                if (previousState == 0) {
                    gp.gameState = gp.titleState;
                    gp.stopMusic();
                    //gp.gameOverState(code);
                    gp.restart();
                }
                if (previousState == 2) {
                    gp.gameState = gp.playState; // Resume game
                }

            }
        } else if (gp.gameState == gp.settingsState) {
//            System.out.println("Settings Return Button: " + exitSettingsButton);
            if (exitSettingsButton.contains(mouseX, mouseY)) {
                System.out.println("RETURN BUTTON CLICKED!");
                gp.gameState = gp.ui.previousState;
            }

        }

    }
}


