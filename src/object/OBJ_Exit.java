package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import entity.Entity;
import main.GamePanel;


public class OBJ_Exit extends Entity {
    public OBJ_Exit(GamePanel gp) {
        super(gp);
        name = "Exit";
        down1 = setup("/objects/exit", gp.tileSize, gp.tileSize);
    }
}
