package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_tutorial extends Entity {
    public OBJ_tutorial(GamePanel gp){
        super(gp);
        name = "tutorial";
        down1 = setup("/objects/newTutorial", 1000, 707) ;
        image = down1;
    }
}
