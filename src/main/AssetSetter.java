package main;

import monster.MON_monster;
import object.OBJ_Booster;
import object.OBJ_Door;
import object.OBJ_Exit;
import object.OBJ_Key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject(){
        gp.obj[0] = new OBJ_Key(gp); //since OBJ_Key is a subclass of SuperObject, so we can instantiate it as one of this array
        gp.obj[0].worldX = 44*gp.tileSize; //place the key at index 23, 23
        gp.obj[0].worldY = 42*gp.tileSize;

        gp.obj[1] = new OBJ_Key(gp);
        gp.obj[1].worldX = 59 * gp.tileSize;
        gp.obj[1].worldY = 48 * gp.tileSize;

        gp.obj[2] = new OBJ_Key(gp);
        gp.obj[2].worldX = 27 * gp.tileSize;
        gp.obj[2].worldY = 41 * gp.tileSize;

        gp.obj[3] = new OBJ_Door(gp);
        gp.obj[3].worldX = 34 * gp.tileSize;
        gp.obj[3].worldY = 24 * gp.tileSize;

        gp.obj[4] = new OBJ_Door(gp);
        gp.obj[4].worldX = 32 * gp.tileSize;
        gp.obj[4].worldY = 41 * gp.tileSize;

        gp.obj[5] = new OBJ_Door(gp);
        gp.obj[5].worldX = 36 * gp.tileSize;
        gp.obj[5].worldY = 35 * gp.tileSize;

        gp.obj[6] = new OBJ_Exit(gp);
        gp.obj[6].worldX = 34 * gp.tileSize;
        gp.obj[6].worldY = 14 * gp.tileSize;

        gp.obj[7] = new OBJ_Booster(gp);
        gp.obj[7].worldX = 58 * gp.tileSize;
        gp.obj[7].worldY = 54 * gp.tileSize;

        gp.obj[8] = new OBJ_Booster(gp);
        gp.obj[8].worldX = 23 * gp.tileSize;
        gp.obj[8].worldY = 30 * gp.tileSize;

        gp.obj[9] = new OBJ_Booster(gp);
        gp.obj[9].worldX = 40 * gp.tileSize;
        gp.obj[9].worldY = 41 * gp.tileSize;

        gp.obj[10] = new OBJ_Booster(gp);
        gp.obj[10].worldX = 84 * gp.tileSize;
        gp.obj[10].worldY = 24 * gp.tileSize;

        gp.obj[11] = new OBJ_Booster(gp);
        gp.obj[11].worldX = 68 * gp.tileSize;
        gp.obj[11].worldY = 47 * gp.tileSize;

        gp.obj[12] = new OBJ_Booster(gp);
        gp.obj[12].worldX = 61 * gp.tileSize;
        gp.obj[12].worldY = 23 * gp.tileSize;
    }

    public void setMonster (){
        gp.monster[0]= new MON_monster(gp);
        gp.monster[0].worldX = 38 * gp.tileSize;
        gp.monster[0].worldY = 47 * gp.tileSize;

        gp.monster[1]= new MON_monster(gp);
        gp.monster[1].worldX = 36 * gp.tileSize;
        gp.monster[1].worldY = 47 * gp.tileSize;

        gp.monster[2]= new MON_monster(gp);
        gp.monster[2].worldX = 62 * gp.tileSize;
        gp.monster[2].worldY = 25 * gp.tileSize;

        gp.monster[3]= new MON_monster(gp);
        gp.monster[3].worldX = 78 * gp.tileSize;
        gp.monster[3].worldY = 39 * gp.tileSize;
    }
}
