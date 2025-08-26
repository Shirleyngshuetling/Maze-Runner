package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
// KeyListener : listener interface for receiving keyboard events (keystrokes)
public class KeyHandler implements KeyListener{

    int previousState;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    GamePanel gp;
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //we not using key typed
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); //returns a number of the key that was pressed
        //Ex of associated keycode
        // 8--Backspace  9--Tab
        // 10--Enter     12--Clear
        // 16--Shift     17--Ctrl
        // 18--Alt
        // 65--A
        // 67--C

//TITLE STATE
        if(gp.gameState == gp.titleState){
            if(code==KeyEvent.VK_W){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
            }
            if(code==KeyEvent.VK_S){
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
            }
            if(code == KeyEvent.VK_ENTER){
                System.out.println("ENTER pressed at title state. commandNum = " + gp.ui.commandNum);
                if(gp.ui.commandNum == 0){
                    gp.gameState = gp.playState;
                    gp.playMusic(0);
                }
                if(gp.ui.commandNum == 1){
                    previousState = gp.gameState;
                    gp.gameState = gp.tutorialState;
                    return;
                }
                if(gp.ui.commandNum == 2){
                    System.exit(0);

                }
            }
            if (code == KeyEvent.VK_Z) {
                previousState = gp.gameState;
                gp.ui.previousState = gp.gameState; // Store current state before opening settings
                gp.gameState = gp.settingsState;
            }

        }
        // settings state
        if(gp.gameState == gp.settingsState){
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_A) {
                if (gp.ui.subState == 0) {
                    if (gp.ui.commandNum == 0 && gp.music.volumeScale > 0) {
                        gp.music.volumeScale--;
                        gp.music.checkVolume();
                    }
                    if (gp.ui.commandNum == 1 && gp.se.volumeScale > 0) {
                        gp.se.volumeScale--;
                    }
                }
            }
            if (code == KeyEvent.VK_D) {
                if (gp.ui.subState == 0) {
                    if (gp.ui.commandNum == 0 && gp.music.volumeScale < 5) {
                        gp.music.volumeScale++;
                        gp.music.checkVolume();
                    }
                    if (gp.ui.commandNum == 1 && gp.se.volumeScale < 5) {
                        gp.se.volumeScale++;
                    }
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0) {
                    // music
                }
                if(gp.ui.commandNum == 1) {
                    // music
                }
                if(gp.ui.commandNum == 2) {
                    // return to previous interface
                    if (previousState == 0) {
                        gp.gameState = gp.titleState;
                        gp.stopMusic();
                        gp.restart();
                    }
                    if (previousState == 1) {
                        gp.gameState = gp.playState; // Resume game
                        enterPressed = false;

                        gp.player.justUnpaused = true;
                        gp.player.unpauseCooldownCounter = 0;
                    }
                }

            }

        }

        // PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_W) { // Move up in menu
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2; // Wrap around to last option
                }
            }
            if (code == KeyEvent.VK_S) { // Move down in menu
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0; // Wrap around to first option
                }
            }
            if (code == KeyEvent.VK_ENTER) { // Select option
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState; // Resume game
                    enterPressed = false;

                    gp.player.attacking = false;
                    gp.player.spriteNum = 1;
                    gp.player.spriteCounter = 0;

                    gp.player.justUnpaused = true;
                    gp.player.unpauseCooldownCounter = 0;

                }
                if (gp.ui.commandNum == 1) {
                    previousState = gp.gameState;
                    gp.gameState = gp.tutorialState;
                    return;
                }
                if (gp.ui.commandNum == 2) {
                    gp.gameState = gp.titleState; // Return to title screen
                    gp.stopMusic();
                    gameOverState(code);
                    gp.restart();

                }
            }
        }
        // Tutorial State
        if (gp.gameState == gp.tutorialState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (previousState == 0) {
                    gp.gameState = gp.titleState;
                    gp.stopMusic();
                    gameOverState(code);
                    gp.restart();
                }
                if (previousState == 2){
                    gp.gameState = gp.playState; // Resume game
                    enterPressed = false;

                    gp.player.attacking = false;
                    gp.player.spriteNum = 1;
                    gp.player.spriteCounter = 0;

                    gp.player.justUnpaused = true;
                    gp.player.unpauseCooldownCounter = 0;
                }

//                if (gp.ui.commandNum == 0) {
//                    gp.gameState = gp.playState; // Resume game
//                }
//                if (gp.ui.commandNum == 1) {
//                    gp.gameState = gp.titleState;
//                    gp.stopMusic();
//                    gameOverState(code);
//                    gp.restart();
//                }
            }
        }

        //if user press W key
        if(gp.gameState == gp.playState){
            if(code==KeyEvent.VK_W){
                upPressed = true;
            }
            if(code==KeyEvent.VK_S){
                downPressed = true;
            }
            if(code==KeyEvent.VK_A){
                leftPressed = true;
            }
            if(code==KeyEvent.VK_D){
                rightPressed = true;
            }
            if(code==KeyEvent.VK_ENTER){
                enterPressed = true;
                if (!gp.player.attacking && !gp.player.justUnpaused) {
                    gp.player.attacking = true;
                    gp.player.spriteCounter = 0;
                }
            }
            if(code==KeyEvent.VK_Z) {
                previousState = gp.gameState;
                gp.ui.previousState = gp.gameState; // Store current state before opening settings
                gp.gameState = gp.settingsState;
            }
        }
        if(code==KeyEvent.VK_P){
            if(gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
            }
            else if(gp.gameState == gp.pauseState){
                gp.gameState = gp.playState;
            }

        }
        // GAME OVER STATE
        if(gp.gameState == gp.gameOverState){
            gameOverState(code);
        }
        if(gp.gameState == gp.victoryState){
            victoryState(code);
        }
    }
    public void victoryState(int code){
        if(code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            if(gp.ui.commandNum<0){
                gp.ui.commandNum = 1;
            }
        }
        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if(gp.ui.commandNum>1){
                gp.ui.commandNum = 0;
            }
        }
        if(code== KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.restart();
                gp.playMusic(0);
            }
            else if(gp.ui.commandNum == 1){
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }

    public void gameOverState(int code){
        //gp.stopMusic();
        if(code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            if(gp.ui.commandNum<0){
                gp.ui.commandNum = 1;
            }
        }
        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if(gp.ui.commandNum>1){
                gp.ui.commandNum = 0;
            }
        }
        if(code== KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.restart();
                gp.playMusic(0);
            }
            else if(gp.ui.commandNum == 1){
                gp.gameState = gp.titleState;
                gp.restart();
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        //if the released key is W
        if(code==KeyEvent.VK_W){
            upPressed = false;
        }
        if(code==KeyEvent.VK_S){
            downPressed = false;
        }
        if(code==KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code==KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_ENTER) {
            gp.player.attacking = false;
        }
    }


}
