package main;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];
    long lastDamageTime = 0;

    int previousEventX, previousEventY;
    public EventHandler(GamePanel gp){
        this.gp = gp;

        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while(col<gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col==gp.maxWorldCol){
                col = 0;
                row++;
            }
        }

    }
    public void checkEvent(){
//        // event of lava pool
//        if(!eventRect[23][6].eventDone && hit(23,6,"any")){
//            long currentTime = System.currentTimeMillis();
//            if (currentTime - lastDamageTime >= 3000) { // every 3 seconds, a life will be taken if the user has not exited the lave after the initial entrance
//                damagePit(23, 6, gp.playState);
//                lastDamageTime = currentTime;
//            }
//        }
//        // event of lava wall
//        if(!eventRect[17][39].eventDone && hit(17,39,"any")){
//            long currentTime = System.currentTimeMillis();
//            if (currentTime - lastDamageTime >= 3000) { // every 3 seconds, a life will be taken if the user has not exited the lave after the initial entrance
//                damagePit(17, 39, gp.playState);
//                lastDamageTime = currentTime;
//            }
//        }
        long currentTime = System.currentTimeMillis();

        for (int col = 43; col <= 50; col++) {
            for (int row = 15; row <= 19; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 38; col <= 41; col++) {
            for (int row = 51; row <= 54; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }

        for (int col = 59; col <= 64; col++) {
            for (int row = 20; row <= 20; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 60; col <= 63; col++) {
            for (int row = 18; row <= 19; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }

        for (int col = 82; col <= 83; col++) {
            for (int row = 25; row <= 25; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 69; col <= 71; col++) {
            for (int row = 42; row <= 42; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }

        for (int col = 68; col <= 72; col++) {
            for (int row = 43; row <= 46; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 69; col <= 71; col++) {
            for (int row = 47; row <= 47; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 42; col <= 43; col++) {
            for (int row = 52; row <= 53; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 43; col <= 44; col++) {
            for (int row = 54; row <= 54; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 44; col <= 49; col++) {
            for (int row = 55; row <= 55; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 49; col <= 49  ; col++) {
            for (int row = 54; row <= 54; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 55; col <= 56; col++) {
            for (int row = 49; row <= 51; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 57; col <= 59; col++) {
            for (int row = 49; row <= 50; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 53; col <= 55; col++) {
            for (int row = 48; row <= 48; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 46; col <= 47; col++) {
            for (int row = 50; row <= 50; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 46; col <= 47; col++) {
            for (int row = 52; row <= 52; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 45; col <= 48; col++) {
            for (int row = 51; row <= 51; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 60; col <= 60; col++) {
            for (int row = 47; row <= 50; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 45; col <= 45; col++) {
            for (int row = 41; row <= 44; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 44; col <= 44; col++) {
            for (int row = 41; row <= 41; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 27; col <= 27; col++) {
            for (int row = 36; row <= 40; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 26; col <= 26; col++) {
            for (int row = 39; row <= 41; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }
        for (int col = 46; col <= 48; col++) {
            for (int row = 20; row <= 20; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }

        for (int col = 42; col <= 44; col++) {
            for (int row = 22; row <= 23; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }

        for (int col = 33; col <= 33; col++) {
            for (int row = 21; row <= 23; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }

        for (int col = 35; col <= 35; col++) {
            for (int row = 21; row <= 23; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }

        for (int col = 22; col <= 24; col++) {
            for (int row = 52; row <= 52; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }

        for (int col = 24; col <= 24; col++) {
            for (int row = 53; row <= 54; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }

        for (int col = 52; col <= 58; col++) {
            for (int row = 33; row <= 33; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }

        for (int col = 56; col <= 56; col++) {
            for (int row = 31; row <= 32; row++) {
                if (!eventRect[col][row].eventDone && hit(col, row, "any")) {
                    if (currentTime - lastDamageTime >= 3000) {
                        damagePit(col, row, gp.playState);
                        lastDamageTime = currentTime;
                    }
                }
            }
        }

    }

    public boolean hit(int col, int row, String reqDirection){
        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[col][row])){
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;

    }
    public void damagePit(int col, int row, int gameState){
        gp.gameState = gameState;
        gp.ui.showMessage("You fell into lava! Move Away!");
        gp.player.life -= 1;
    }
}

