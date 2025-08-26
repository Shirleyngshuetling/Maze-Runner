package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{

    public boolean justUnpaused = false;
    public int unpauseCooldownCounter = 0;
    private final int UNPAUSE_COOLDOWN_DURATION = 30; // About 0.5s if your game updates 60 times per second

    KeyHandler keyH;
    public long lastHitTime;
    public boolean attacking;

    public final int screenX; //where do we draw the player on the screen
    public final int screenY; //where do we draw the player on the screen
    public int hasKey=0;


    private int speedBoostCount = 0; // Count of active speed boosters
    private final int maxSpeedBoosts = 5; // Maximum number of speed boosters that can be stacked
    private int speedBoostDuration = 300; // Duration of each speed boost in frames (300 frames ~ 5 seconds at 60 FPS)
    private int speedBoostCounter = 0; // Counter for the current boost duration

    public Player (GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 -(gp.tileSize/2); //top left corner of the center tile of the screen - a half tile of the tile size
        screenY = gp.screenHeight / 2 - (gp.tileSize/2); //top left corner of the center tile of the screen - a half tile of the tile size

        //when we instantiate the rectangle we can pass 4 arguments to the constructor
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        solidArea.width = 28;
        solidArea.height = 28;

        solidAreaAttackX = 0;
        solidAreaAttackY = 0;
        //Attack range to the monster
        attackArea.width = 36;
        attackArea.height = 36;

        lastHitTime = 0;
        attacking = false;

        monsterdamage = false;
        monsterdamageCounter = 0;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValues (){
        worldX = gp.tileSize * 47; //player's position on the world map
        worldY = gp.tileSize * 34; //player's position on the world map     '
        speed = 4;
        direction = "down"; //default direction

        // player life status
        maxLife = 3;
        life = maxLife;
    }

    public void getPlayerImage() {
            down1=setup("/player/playerFront1", gp.tileSize, gp.tileSize);
            down2=setup("/player/playerFront2", gp.tileSize, gp.tileSize);
            up1=setup("/player/playerBack1", gp.tileSize, gp.tileSize);
            up2=setup("/player/playerBack2", gp.tileSize, gp.tileSize);
            left1=setup("/player/playerLeft1", gp.tileSize, gp.tileSize);
            left2=setup("/player/playerLeft2", gp.tileSize, gp.tileSize);
            right1=setup("/player/playerRight1", gp.tileSize, gp.tileSize);
            right2=setup("/player/playerRight2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage(){
        attackUp1 = setup("/player/attackUp1", gp.tileSize, gp.tileSize+10);
        attackUp2 = setup("/player/attackUp2", gp.tileSize, gp.tileSize+10);
        attackDown1 = setup("/player/attackDown1", gp.tileSize+12, gp.tileSize+10);
        attackDown2 = setup("/player/attackDown2", gp.tileSize+12, gp.tileSize+10);
        attackLeft1 = setup("/player/attackLeft1", gp.tileSize, gp.tileSize);
        attackLeft2 = setup("/player/attackLeft2", gp.tileSize, gp.tileSize);
        attackRight1 = setup("/player/attackRight1", gp.tileSize, gp.tileSize);
        attackRight2 = setup("/player/attackRight2", gp.tileSize, gp.tileSize);
    }

    public void update() {

        if (justUnpaused) {
            unpauseCooldownCounter++;
            if (unpauseCooldownCounter >= UNPAUSE_COOLDOWN_DURATION) {
                justUnpaused = false;
                unpauseCooldownCounter = 0;

            }
        }

        if (speedBoostCount > 0) {
            speedBoostCounter++;
            if (speedBoostCounter >= speedBoostDuration) {
                speed -= 2; // Reset speed for one level of boost
                gp.ui.showMessage("Booster used up...");
                speedBoostCount--; // Decrease the count of active boosters
                speedBoostCounter = 0; // Reset the counter for the next boost
            }
        }

        if (gp.gameState == gp.gameOverState || life <= 0) {
            gp.gameState = gp.gameOverState;
            return;
        }

        if (attacking) {
            attackMonster();
        }

        // Always check monster collision even if player is idle
        int monsterIndex = gp.collisionCheck.checkEntity(this, gp.monster);
        contactmonster(monsterIndex);

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // Check for attack input
            if (keyH.enterPressed && !justUnpaused) {
                if (!attacking) {
                    attacking = true;
                    spriteCounter = 0;
                }
            }


            //Check tile collision
            collisionOn = false;
            gp.collisionCheck.checkTile(this);
            //Check object collision
            int objIndex = gp.collisionCheck.checkObject(this, true);
            pickUpObject(objIndex);

            // check event
            gp.eHandler.checkEvent();

            //if collision is false, player can move
            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }


            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if (monsterdamage) {
            monsterdamageCounter++;
            if (monsterdamageCounter > 60) {
                monsterdamage = false;
                monsterdamageCounter = 0;
            }
        }
        // Handle invincibility
        if (playerInvincible) {
            playerInvincibleCounter++;
            if (playerInvincibleCounter >= 120) { // 2 seconds of invincibility
                playerInvincible = false; // Reset invincibility
                playerInvincibleCounter = 0; // Reset counter
            }
        }

        // Enforce strict boundaries
        worldX = Math.max(0, Math.min(worldX, gp.worldWidth - gp.tileSize));
        worldY = Math.max(0, Math.min(worldY, gp.worldHeight - gp.tileSize));
    }

    public void attackMonster(){
        spriteCounter++;
        if(spriteCounter <= 5){ //show attacking image 1 during the first 5 frames
            spriteNum = 1;
        }
        if(spriteCounter>5 && spriteCounter<=25){ // show attacking image 2 during the next 6-25 frames
            //second image will be displayed longer than the first
            spriteNum = 2;

            //Save original position
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //check collision based on where the player weapon is, not where player is
            //so we temporarily modify player's x y and solid area during collision checking
            //we need to restore the original position after checking, that's why we need to save the data first

            //Adjust player's worldX/Y for the attackArea
            switch(direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //check monster collision with the updated worldX, worldY and solidArea
            int monsterIndex = gp.collisionCheck.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            //restore original position after checking
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (spriteCounter>25){ // on frame 26, we reset
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }
    public void pickUpObject(int i){
        if (i != 999){

            String objectName = gp.obj[i].name;

            switch(objectName){
                case "Key":
                    gp.playSE(1);
                    hasKey++;
                    gp.obj[i]=null;
                    gp.ui.showMessage("You found a key!");
                    break;
                case "Door":
                    gp.playSE(3);
                    if(hasKey>0){
                        gp.obj[i]=null;
                        hasKey--;
                        gp.ui.showMessage("You opened the door!");
                    }
                    else {
                        gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "Booster":
                    if (speedBoostCount < maxSpeedBoosts) {
                        gp.playSE(2);
                        speed += 2; // Increase speed
                        speedBoostCount++; // Increment the count of speed boosters
                        speedBoostCounter = 0; // Reset the counter for the new booster
                        gp.obj[i] = null; // Remove the booster from the game
                        gp.ui.showMessage("You found a speed booster!");
                    } else {
                        gp.ui.showMessage("You already have the maximum speed boost!");
                    }
                    break;
                case "Exit":
                    gp.gameState = gp.victoryState;
                    gp.stopMusic();
                    gp.playSE(4);
                    break;
            }


        }
    }

//    public void interactMonster(int i){
//        if(i!=999){
//            if(keyH.enterPressed){
//                attacking=true;
//            }
//        }
//    }
    public void contactmonster(int i) {
        if (i != 999 && !playerInvincible) {
            long currentTime = System.currentTimeMillis();

            // Only take damage if not recently hit (1 second cooldown)
            if (currentTime - lastHitTime > 1000) {
                life--;
                lastHitTime = currentTime;
                playerInvincible = true;
                playerInvincibleCounter = 0;
                monsterdamage = true;
                gp.playSE(5); //play player hurt sound effect.

                gp.ui.showMessage("Ouch! Lives: " + life + "/" + maxLife);

                // Check for game over
                if (life <= 0) {
                    life = 0; // Ensure life doesn't go negative
                    gp.gameState = gp.gameOverState;
                    gp.stopMusic();
                    gp.playSE(12); // Play game over sound
                }
            }
        }
    }


    public void damageMonster(int i) {
        if (i != 999 && gp.monster[i] != null) {
            // Check if monster is alive and not invincible
            if (gp.monster[i].monster_alive == 1 && !gp.monster[i].monsterInvincible) {
                gp.monster[i].life--;

                // Set invincibility if not dead
                gp.monster[i].monsterInvincible = true;
                gp.monster[i].monsterInvincibleCounter = 0;
                gp.playSE(6); // Monster hurt sound
            }
            else {
                // Monster died
                gp.monster[i].monster_alive = 0;
                gp.monster[i] = null;
                gp.ui.showMessage("You killed a monster!");
                gp.playSE(6); // Monster death sound
            }

        }
    }

    public void draw (Graphics2D g2){
//        g2.setColor(Color.white);
//
//        //fillRect draw a rect and fills it with the specified color
//        //when draw something on the screen, Graphics / Graphics2D ask for its x and y coordinates. width and height
//        g2.fillRect(x,y,gp.tileSize, gp.tileSize);

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch (direction){
            case "up":
                if (!attacking){
                    if (spriteNum == 1){image = up1;}
                    if (spriteNum == 2){image = up2;}
                }
                if (attacking){
                    tempScreenY = screenY - gp.tileSize/4;
                    if (spriteNum == 1){image = attackUp1;}
                    if (spriteNum == 2){image = attackUp2;}
                }
                break;
            case "down":
                if (!attacking){
                    if (spriteNum == 1){image = down1;}
                    if (spriteNum == 2){image = down2;}
                }
                if (attacking){
                    if (spriteNum == 1){image = attackDown1;}
                    if (spriteNum == 2){image = attackDown2;}
                }
                break;
            case "left":
                if (!attacking){
                    if (spriteNum == 1){image = left1;}
                    if (spriteNum == 2){image = left2;}
                }
                if (attacking){
                    tempScreenX = screenX - gp.tileSize/4;
                    if (spriteNum == 1){image = attackLeft1;}
                    if (spriteNum == 2){image = attackLeft2;}
                }
                break;
            case "right":
                if (!attacking){
                    if (spriteNum == 1){image = right1;}
                    if (spriteNum == 2){image = right2;}
                }
                if (attacking){
                    if (spriteNum == 1){image = attackRight1;}
                    if (spriteNum == 2){image = attackRight2;}
                }
                break;
        }

        if(monsterdamage==true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));

        }
        //drawImage() draw an image on the screen
        g2.drawImage(image, tempScreenX, tempScreenY,null);
        //screenX and screenY don't change throughout the game, they are the coordinates where we draw the player on the screen
        //they are final variables, so we don't need to update them in the update() method'

        //reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

    }

}

