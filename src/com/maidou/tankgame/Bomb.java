package com.maidou.tankgame;

/**
 * Bomb
 */
public class Bomb {
    int x, y; //Coordinates of the bomb
    int life = 9; //bomb's life
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //left down
    public void lifeDown() { //Cooperate with the explosion effect of the images
        if(life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }
}
