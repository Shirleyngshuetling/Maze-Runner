package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[] [];

    public TileManager (GamePanel gp){
        this.gp = gp;

        tile = new Tile[10]; //creating ten types of tiles
        mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/world02.txt");

    }

    public void getTileImage (){
            setup(0,"path2", false);
            setup(1,"rock", true);
            setup(2,"obstacle", false);
            setup(3,"exit_path", true);
            setup(4,"rock", true);
            setup(5,"path2", false);
            setup(6,"obstacle", false);
    }

    public void setup(int index, String imagePath, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/" + imagePath + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){

        try{
            //use inputStream to input the text file storing the map
            InputStream is = getClass().getResourceAsStream(filePath);

            //use bufferedReader to read the content of this text file
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < gp.maxWorldRow; row++) {
                String line = br.readLine();
                String[] numbers = line.split(" ");

                for (int col = 0; col < gp.maxWorldCol; col++) {
                    // Force edges to be solid tiles
                    if (col == 0 || col == gp.maxWorldCol-1 ||
                            row == 0 || row == gp.maxWorldRow-1) {
                        mapTileNum[col][row] = 1; // Use your solid tile index
                    } else {
                        mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                    }
                }
            }
            br.close(); //close the buffered reader
        }catch(Exception e){

        }
    }
//    public void draw(Graphics2D g2){
//        int worldCol = 0;
//        int worldRow = 0;
//
//        while (worldCol<gp.maxWorldCol && worldRow<gp.maxWorldRow){
//
//            int tileNum = mapTileNum[worldCol][worldRow]; //extract a tile num from the 2d array
//
//            //worldX and worldY are positions of the first tile of the current row and column in the world map
//            int worldX = worldCol * gp.tileSize; //first we check the tile's worldX coordinate, if it's a [0][0] tile, worldX = 0, if it's a [1][0] tile, worldX = 48, etc.'
//            int worldY = worldRow * gp.tileSize;
//
//            //screenX and screenY are where we want to draw the tile on the screen
//            int screenX = worldX - gp.player.worldX + gp.player.screenX;
//            int screenY = worldY - gp.player.worldY + gp.player.screenY;
//
//            //check if the tile is within the screen boundaries
//            //as long as the tile is within the player's screen boundaries, we draw it
//            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
//                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
//                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
//                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
//                //draw tile based on the tileNum
//                g2.drawImage(tile[tileNum].image, screenX, screenY,null);
//            }
//            worldCol++;
//
//            if(worldCol == gp.maxWorldCol){
//                worldCol = 0;
//                worldRow++;
//            }
//        }
//    }
    public void draw(Graphics2D g2) {
        // Calculate camera boundaries
        int leftBoundary = gp.player.screenX - gp.player.worldX;
        int rightBoundary = leftBoundary + gp.worldWidth;
        int topBoundary = gp.player.screenY - gp.player.worldY;
        int bottomBoundary = topBoundary + gp.worldHeight;

        // Fill background with edge color (optional)
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Calculate visible tile range
        int startCol = Math.max(0, (gp.player.worldX - gp.player.screenX) / gp.tileSize);
        int endCol = Math.min(gp.maxWorldCol, startCol + (gp.screenWidth / gp.tileSize) + 1);
        int startRow = Math.max(0, (gp.player.worldY - gp.player.screenY) / gp.tileSize);
        int endRow = Math.min(gp.maxWorldRow, startRow + (gp.screenHeight / gp.tileSize) + 1);

        // Draw only visible tiles
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                int tileNum = mapTileNum[col][row];
                int worldX = col * gp.tileSize;
                int worldY = row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                // Final boundary check
                if (screenX > -gp.tileSize && screenX < gp.screenWidth &&
                        screenY > -gp.tileSize && screenY < gp.screenHeight) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }
            }
        }
    }

}
