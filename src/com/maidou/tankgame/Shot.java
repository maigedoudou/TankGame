package com.maidou.tankgame;

/**
 * 射击子弹
 */
public class Shot implements Runnable {
    int x; //bullet X coordinate
    int y; //bullet y coordinate
    int direct = 0; //bullet direction
    int speed = 2; //bullet speed
    boolean isLive = true; //bullet is alive or not

    //constructor
    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {//shot
        while (true) {

            //sleep 50millis
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //change x,y coordinate according to direction
            switch (direct) {
                case 0://up
                    y -= speed;
                    break;
                case 1://right
                    x += speed;
                    break;
                case 2://down
                    y += speed;
                    break;
                case 3://left
                    x -= speed;
                    break;
            }


            //When the bullet moves to the boundary of the panel, it should be destroyed (destroy the thread of the started bullet)
            //The thread should also end when the bullet hits the enemy tank
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)) {
                System.out.println("Bullet thread exit");
                isLive = false;
                break;
            }

        }
    }
}
