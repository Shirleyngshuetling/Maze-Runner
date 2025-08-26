package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_MainMenu extends Entity {
    public OBJ_MainMenu(GamePanel gp){
        super(gp);
        name = "tutorial";
        down1 = setup("/objects/main_menu", 1152, 864) ;
        image = down1;
    }
}
