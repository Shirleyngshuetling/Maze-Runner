package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MON_monster extends Entity {
    GamePanel gp;

    public MON_monster(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Green Slime";
        speed = 2;
        maxLife = 1;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getMonsterImage();

    }

    public void getMonsterImage() {

        down1 = setup("/monster/monster_front", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/monster_front", gp.tileSize, gp.tileSize);
        up1 = setup("/monster/monster_back", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/monster_back", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/monster_left", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/monster_left", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/monster_right", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/monster_right", gp.tileSize, gp.tileSize);

    }


    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    @Override
    public void update() {
        super.update(); // Calls Entity's update() which handles invincibility

        // Check if the monster is invincible
        if (monsterInvincible) {
            monsterInvincibleCounter++;
            if (monsterInvincibleCounter >= 120) {
                monsterInvincible = false; // Reset invincibility
                monsterInvincibleCounter = 0; // Reset counter
            }
            return; // Skip movement if invincible
        }

        // Set new action for the monster
        setAction();
        collisionOn = false;

        // Check for tile collisions
        gp.collisionCheck.checkTile(this);
        // Check for object collisions
        gp.collisionCheck.checkObject(this, false);

        // Check for player collision every update
        int monsterIndex = gp.collisionCheck.checkEntity(this, gp.monster);
        // Check for player collision using the monster's full solid area
        Rectangle monsterRect = new Rectangle(
                worldX + solidArea.x,
                worldY + solidArea.y,
                solidArea.width,
                solidArea.height
        );

        Rectangle playerRect = new Rectangle(
                gp.player.worldX + gp.player.solidArea.x,
                gp.player.worldY + gp.player.solidArea.y,
                gp.player.solidArea.width,
                gp.player.solidArea.height
        );

        // Check collision with player
        if (monsterRect.intersects(playerRect) && !gp.player.playerInvincible) {
            gp.player.contactmonster(monsterIndex);
        }
//        boolean contactPlayer = gp.collisionCheck.checkPlayer(this);
//        if (contactPlayer) {
//            // Handle what happens when the monster collides with the player
//            gp.player.contactmonster(monsterIndex); // Pass the index of the monster
//        }

        // If there are no collisions, update the monster's position
        if (!collisionOn) {
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
    }
}

