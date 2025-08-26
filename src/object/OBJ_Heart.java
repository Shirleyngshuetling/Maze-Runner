package object;

import javax.imageio.ImageIO;
import java.io.IOException;
import main.GamePanel;
import entity.Entity;


public class OBJ_Heart extends Entity{
    public OBJ_Heart(GamePanel gp){
        super(gp);

        name = "Heart";
        image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/heart_blank", gp.tileSize, gp.tileSize);

    }

}

