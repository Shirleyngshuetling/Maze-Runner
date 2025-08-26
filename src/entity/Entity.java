package entity;
import main.GamePanel;
import main.UtilityTool;
import monster.MON_monster;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.IOException;

//Entity class stores variables that will be used in player, monster and NPC classes
public class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image2;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); //with this class we can create an invisible or abstract rectangle
    //and we can store data such as x,y,width,height
    public int solidAreaDefaultX, solidAreaDefaultY;
    public int solidAreaAttackX, solidAreaAttackY;
    public int solidAreaAttackWidth, solidAreaAttackHeight;

    public Rectangle attackArea = new Rectangle (0,0,0,0);
    public String direction = "down";
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public boolean collisionOn = false; //default is false
    boolean attacking = false; //default is false

    public String name;

    public int actionLockCounter=0;
    public boolean monsterdamage;
    public int monsterdamageCounter;
    // For monsters
    public boolean monsterInvincible;
    public int monsterInvincibleCounter;

    // For player
    public boolean playerInvincible;
    public int playerInvincibleCounter;

    public int monster_alive;

    public Entity(GamePanel gp) {
        this.gp = gp;
        this.monsterInvincible = false;
        this.monsterInvincibleCounter = 0;
        this.playerInvincible = false;
        this.playerInvincibleCounter = 0;
        this.monster_alive = 1; // 1 = alive, 0 = dead
        // Restore original solid area for proper collision
        this.solidArea = new Rectangle(8, 16, 32, 32);
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;

        // Set attack area (smaller than solid area)
        this.attackArea = new Rectangle(0, 0, 36, 36);

    }

    // character status
    public int maxLife;
    public int life;
    public boolean collision;

    public void setAction() {
    }

    public void update() {
//        // Handle player invincibility (only for Player instances)
//        if (playerInvincible) {
//            playerInvincibleCounter++;
//            if(playerInvincibleCounter >= 120) {
//                playerInvincible = false;
//                playerInvincibleCounter = 0;
//            }
//        }

        // Handle monster invincibility (only for Monster instances)
        if (monsterInvincible) {
            monsterInvincibleCounter++;
            if(monsterInvincibleCounter >= 120) {
                monsterInvincible = false;
                monsterInvincibleCounter = 0;
            }
            return; // Skip movement during invincibility
        }

        setAction();
        collisionOn = false;
        // First check tile collisions (walls)
        gp.collisionCheck.checkTile(this);

        // Then check object collisions (doors, etc.)
        gp.collisionCheck.checkObject(this, false);

        // Finally check entity collisions (monsters)
        // Only if not already colliding with something more important
        if (!collisionOn) {
            gp.collisionCheck.checkEntity(this, gp.monster);
        }
        // Check player contact
        boolean contactPlayer = gp.collisionCheck.checkPlayer(this);
        if(contactPlayer && !gp.player.playerInvincible) {
            if(!gp.player.monsterdamage) {
                gp.player.life -= 1;
                gp.player.monsterdamage = true;
                gp.player.playerInvincible = true;

                // **Play damage sound effect**
                gp.playSE(5); // Make sure `playSound(1)` exists and is correct
            }
        }

        // Movement if no collision
        if (!collisionOn) {
            switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        // Animation
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch(direction) {
                case "up": image = (spriteNum == 1) ? up1 : up2; break;
                case "down": image = (spriteNum == 1) ? down1 : down2; break;
                case "left": image = (spriteNum == 1) ? left1 : left2; break;
                case "right": image = (spriteNum == 1) ? right1 : right2; break;
            }

            // Apply invincibility effect (blinking)
            if (monsterInvincible) {
                // Toggle visibility based on the invincibility counter
                if (monsterInvincibleCounter % 10 < 5) { // Adjust the modulus for blink speed
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)); // Semi-transparent
                } else {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // Fully opaque
                }
            }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f)); // Reset alpha


        }
    }


    public BufferedImage setup(String imagePath, int width, int height){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream( imagePath + ".png"));
            image= uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
        }

}
