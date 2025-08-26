package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Booster extends Entity {
    public OBJ_Booster(GamePanel gp){
        super(gp);
        name = "Booster";
        down1 = setup("/objects/booster", gp.tileSize, gp.tileSize);


    }
}
