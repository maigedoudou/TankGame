package com.maidou.tankgame;

/**
 * my tank
 */
public class Hero extends Tank {
    //Define a shot object to represent a shot (thread)
    Shot shot = null;
    //Can fire multiple bullets
    //Vector<Shot> shots = new Vector<>();
    public Hero(int x, int y) {
        super(x, y);
    }

    //shot
    public void shotEnemyTank() {

         //create a shot according to the position and direction of the current hero object
        switch (getDirect()) {//Get the direction of the hero object
            case 0: //up
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1: //right
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2: //down
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3: //left
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }

          new Thread(shot).start();

    }

}
