package com.maidou.tankgame;

import java.util.Vector;

/**
 * enemyTank
 */
@SuppressWarnings({"all"})
public class EnemyTank extends Tank implements Runnable {
    //In the enemy tank class, use vector to save multiple shots
    Vector<Shot> shots = new Vector<>();
    Vector<EnemyTank> enemyTanks = new Vector<>();
    boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }



    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }


    //Write a method to judge whether the current enemy tank overlaps or collides with other tanks in enemytanks
    public boolean isTouchEnemyTank() {

        //Determine the direction of the current enemy tank (this)
        switch (this.getDirect()) {
            case 0: //up
                //Compare the current enemy tank with all other enemy tanks
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //Take an enemy tank from the vector
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //not compared to itself
                    if (enemyTank != this) {
                        //1. If the enemy tank is right/left   range of x [enemyTank.getX(), enemyTank.getX() + 40]
                        //                     range of y [enemyTank.getY(), enemyTank.getY() + 60]

                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //2. Coordinates of the upper left corner of the current tank [this.getX(), this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //3. Coordinates of the upper right corner of the current tank [this.getX() + 40, this.getY()]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //1. If the enemy tank is right/left  range of x [enemyTank.getX(), enemyTank.getX() + 60]
                        //                     range of y [enemyTank.getY(), enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2. Coordinates of the upper left corner of the current tank [this.getX(), this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //3. Coordinates of the upper right corner of the current tank [this.getX() + 40, this.getY()]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }

                }
                break;
            case 1: //right
                //Compare the current enemy tank with all other enemy tanks
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //Take an enemy tank from the vector
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //not compared to itself
                    if (enemyTank != this) {
                        //1. If the enemy tank is up / down  range of x [enemyTank.getX(), enemyTank.getX() + 40]
                        //                     range of y [enemyTank.getY(), enemyTank.getY() + 60]

                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //2. Coordinates of the upper right corner of the current tank [this.getX() + 60, this.getY()]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //3. Coordinates of the down right corner of the current tank [this.getX() + 60, this.getY() + 40]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //1. If the enemy tank is right / left  range of x [enemyTank.getX(), enemyTank.getX() + 60]
                        //                     range of y [enemyTank.getY(), enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2. Coordinates of the upper right corner of the current tank [this.getX() + 60, this.getY()]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //3. Coordinates of the lower right corner of the current tank [this.getX() + 60, this.getY() + 40]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2: //down
                //Compare the current enemy tank with all other enemy tanks
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //Take an enemy tank from the vector
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //not compared to itself
                    if (enemyTank != this) {
                         //1. If the enemy tank is up / down range of x [enemyTank.getX(), enemyTank.getX() + 40]
                        //                     range of y [enemyTank.getY(), enemyTank.getY() + 60]

                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //2. Coordinates of the lower left corner of the current tank [this.getX(), this.getY() + 60]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //3. Coordinates of the lower right corner of the current tank [this.getX() + 40, this.getY() + 60]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //1. If the enemy tank is right / left  range of x [enemyTank.getX(), enemyTank.getX() + 60]
                        //                     range of y [enemyTank.getY(), enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2. Coordinates of the lower left corner of the current tank[this.getX(), this.getY() + 60]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //3. Coordinates of the lower right corner of the current tank [this.getX() + 40, this.getY() + 60]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3: //left
                //Compare the current enemy tank with all other enemy tanks
                for (int i = 0; i < enemyTanks.size(); i++) {
                    //Take an enemy tank from the vector
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //not compare to itself
                    if (enemyTank != this) {
                         //1. If the enemy tank is up /down range of x [enemyTank.getX(), enemyTank.getX() + 40]
                        //                     range of y[enemyTank.getY(), enemyTank.getY() + 60]

                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //2. Coordinates of the upper left corner of the current tank [this.getX(), this.getY() ]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //3. Coordinates of the lower left corner of the current tank[this.getX(), this.getY() + 40]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //1. If the enemy tank is right / left  range of x [enemyTank.getX(), enemyTank.getX() + 60]
                        //                     range of y [enemyTank.getY(), enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //2. Coordinates of the upper left corner of the current tank [this.getX(), this.getY() ]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //3. Coordinates of the lower left corner of the current tank [this.getX(), this.getY() + 40]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return  false;
    }

    @Override
    public void run() {
        while (true) {


            //If shots size () = 0, create a bullet, put it into the shots collection, and start
            if (isLive && shots.size() < 1) {
                Shot s = null;
                //Determine the direction of the tank and create the corresponding bullet
                switch (getDirect()) {
                    case 0:
                        s = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        s = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2: //down
                        s = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3://left
                        s = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();

            }


            //Continue moving according to the direction of the tank
            switch (getDirect()) {
                case 0:  //up
                    //Keep the tank in one direction and take 30 steps
                    for (int i = 0; i < 30; i++) {
                        if (getY() > 0 && !isTouchEnemyTank()) {
                            moveUp();
                        }
                        //sleep 50 millis
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:  //right
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000 && !isTouchEnemyTank()) {
                            moveRight();
                        }
                        //sleep 50 millis
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:  //down
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750 && !isTouchEnemyTank()) {
                            moveDown();
                        }
                        //sleep 50 millis
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3:  //left
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0 && !isTouchEnemyTank()) {
                            moveLeft();
                        }
                        //sleep 50 millis
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }


            //randomly change the direction of the tank 0-3
            setDirect((int) (Math.random() * 4));
                if (!isLive) {
                break; //Exit thread.
            }

        }
    }
}
