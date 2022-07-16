package com.maidou.tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

/**
  * Drawing area of tank war
 */


//In order to keep the panel redrawing bullets, need to make mypanel implement runnable and used as a thread
@SuppressWarnings({"all"})
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //define my tank
    Hero hero = null;
    //define enemytank，put into Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //define a Vector to store Node objects, used to restore the coordinates and direction of enemy tanks
    Vector<Node> nodes = new Vector<>();
    //define a Vector to store bombs
    //Note: when the bullet hits the tank, add a bomb object to the bombs
    Vector<Bomb> bombs = new Vector<>();
    int enemyTankSize = 3;

    //define three bomb images to show Explosion effect
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel(String key) {
         //First judge whether the recorded file exists
        //If the file exists, it will be executed normally. If the file does not exist,you can only start a new game, key = "1"
        File file = new File(Recorder.getRecordFile());
        if (file.exists()) {
            nodes = Recorder.getNodesAndEnemyTankRec();
        } else {
            System.out.println("The file does not exist. You can only start a new game");
            key = "1";
        }
        //set Recorder's enemyTanks MyPanel's enemyTanks
        Recorder.setEnemyTanks(enemyTanks);

        hero = new Hero(500, 100);//Initialize my tank

        switch (key) {
            case "1":
                //Initialize enemy tanks
                for (int i = 0; i < enemyTankSize; i++) {
                    //create an ememy tank
                    EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
                    //Set enemytanks to enemytank !!!
                    enemyTank.setEnemyTanks(enemyTanks);
                    //set direction
                    enemyTank.setDirect(2);
                    //Start the enemy tank thread and let it move
                    new Thread(enemyTank).start();
                    //Add a bullet to the enemytank
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //add Vector member of enemytank
                    enemyTank.shots.add(shot);
                    //Start shot object
                    new Thread(shot).start();
                    //add
                    enemyTanks.add(enemyTank);
                }
                break;
            case "2": //Continue the previous game
                //Initialize enemy tanks
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    //create an ememy tank
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    //set enemyTanks to enemyTank !!!
                    enemyTank.setEnemyTanks(enemyTanks);
                    //set direction
                    enemyTank.setDirect(node.getDirect());
                    //Start the enemy tank thread and let it move
                    new Thread(enemyTank).start();
                    //Add a bullet to the enemytank
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //add Vector member of enemytank
                    enemyTank.shots.add(shot);
                    //Start shot object
                    new Thread(shot).start();
                    //add
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("Your input is wrong...");
        }


        //Initialize image object
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));

        //Here, play the specified music
        new AePlayWave("src//111.wav").start();

    }

     //Write a method to display the information of our destroying enemy tanks
    public void showInfo(Graphics g) {

        //Draw the total score of the player
        g.setColor(Color.BLACK);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("You cumulatively destroyed enemy tanks", 1020, 30);
        drawTank(1020, 60, g, 0, 0);//Draw an enemy tank
        g.setColor(Color.BLACK);//It needs to be reset to black here
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 100);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//Fill rectangle, default black
        showInfo(g);
        if (hero != null && hero.isLive) {
            //Draw my tank - Encapsulate this method
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }

        //Draw the bullet that hero shot
        if (hero.shot != null && hero.shot.isLive == true) {
            g.draw3DRect(hero.shot.x, hero.shot.y, 1, 1, false);

        }

        //If there are objects in the bombs set, draw them
        for (int i = 0; i < bombs.size(); i++) {
            //get bomb
            Bomb bomb = bombs.get(i);
            //Draw the corresponding picture according to the life value of the current Bob object
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            //Reduce the life of this bomb
            bomb.lifeDown();
            //if bomb's life=0,delete this bomb from bombs
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

        //Draw the enemy's tank and traverse the vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            //get enemyTank from Vector
            EnemyTank enemyTank = enemyTanks.get(i);

            if (enemyTank.isLive) {//if enemyTank is alibe，draw it
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //draw all bullets of enemyTank
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //get bullet
                    Shot shot = enemyTank.shots.get(j);
                    //draw
                    if (shot.isLive) { //isLive == true
                        g.draw3DRect(shot.x, shot.y, 1, 1, false);
                    } else {
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }


    }

    //Write the method and draw the tank

    /**
     * @param x      X coordinate of the upper left corner of the tank
     * @param y      Y coordinate of the upper left corner of the tank
     * @param g      paint brush
     * @param direct tank direction（up down left right）
     * @param type   tank type
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {

        //Set different colors according to different types of tanks
        switch (type) {
            case 0: //enemyTank
                g.setColor(Color.cyan);
                break;
            case 1: //My tank
                g.setColor(Color.yellow);
                break;
        }

        //Draw the corresponding tank shape according to the tank direction
        //direct 表示方向(0: up 1 right 2 down 3 left )
        //
        switch (direct) {
            case 0: //Indicates up
                g.fill3DRect(x, y, 10, 60, false);//Draw the left wheel of the tank
                g.fill3DRect(x + 30, y, 10, 60, false);//Draw the right wheel of the tank
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//Draw the tank cover
                g.fillOval(x + 10, y + 20, 20, 20);//Draw the round cover
                g.drawLine(x + 20, y + 30, x + 20, y);//Draw the barrel
                break;
            case 1: //Indicates right
                g.fill3DRect(x, y, 60, 10, false);//Draw the upper wheel of the tank
                g.fill3DRect(x, y + 30, 60, 10, false);//Draw the lower wheels of the tank
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//Draw the tank cover
                g.fillOval(x + 20, y + 10, 20, 20);//Draw the round cover
                g.drawLine(x + 30, y + 20, x + 60, y + 20);//Draw the barrel
                break;
            case 2: //Indicates down
                g.fill3DRect(x, y, 10, 60, false);//Draw the left wheel of the tank
                g.fill3DRect(x + 30, y, 10, 60, false);//Draw the right wheel of the tank
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//Draw the tank cover
                g.fillOval(x + 10, y + 20, 20, 20);//Draw the round cover
                g.drawLine(x + 20, y + 30, x + 20, y + 60);//Draw the barrel
                break;
            case 3: //left
                g.fill3DRect(x, y, 60, 10, false);//Draw the upper wheel of the tank
                g.fill3DRect(x, y + 30, 60, 10, false);//Draw the lower wheels of the tank
                g.fill3DRect(x + 10, y + 10, 40, 20, false);//Draw the tank cover
                g.fillOval(x + 20, y + 10, 20, 20);//Draw the round cover
                g.drawLine(x + 30, y + 20, x, y + 20);//Draw the barrel
                break;
            default:
                System.out.println("Not handled yet");
        }

    }

    //If our tank can fire multiple bullets
    //When judging whether our bullets hit enemy tanks, we need to gather our bullets
    //All bullets were taken out and all enemy tanks were judged
    public void hitEnemyTank() {

        //Single bullet。
        if (hero.shot != null && hero.shot.isLive) {

                for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                hitTank(hero.shot, enemyTank);
            }

        }

    }

    //Write a method to judge whether the enemy tank hit my tank
    public void hitHero() {
           for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                 Shot shot = enemyTank.shots.get(j);
                 if (hero.isLive && shot.isLive) {
                    hitTank(shot, hero);
                }
            }
        }
    }




    //Write a method to judge whether my bullets hit the enemy tank
    //When will we judge whether our bullets hit enemy tanks? Run method

    public void hitTank(Shot s, Tank enemyTank) {

        switch (enemyTank.getDirect()) {
            case 0: //tank up
            case 2: //tank down
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //When my bullet hits the enemy tank, remove enemytank from the vector
                    enemyTanks.remove(enemyTank);
                    //When destroy an enemy tank, allenemytanknum++
                    //Interpretation: enemytank can be hero or enemytank
                    if (enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    //create Bomb object，add to bombs
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1: //tank right
            case 3: //tank left
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //hen my bullet hits the enemy tank, remove enemytank from the vector
                    enemyTanks.remove(enemyTank);
                    //Interpretation: enemytank can be hero or enemytank
                    if (enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    //create Bomb object，add to bombs
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //Handle the condition when we pree key "w""d""s""a"
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_W) {//press W
            //Change the direction of the tank
            hero.setDirect(0);//
            //Modify the coordinates of the tank y -= 1
            if (hero.getY() > 0) {
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {//D, towards the right
            hero.setDirect(1);
            if (hero.getX() + 60 < 1000) {
                hero.moveRight();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_S) {//S
            hero.setDirect(2);
            if (hero.getY() + 60 < 750) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//A
            hero.setDirect(3);
            if (hero.getX() > 0) {
                hero.moveLeft();
            }
        }

        //If the user presses J, it will shot
        if (e.getKeyCode() == KeyEvent.VK_J) {

            //Judge whether hero's bullet is destroyed and fire a bullet
            if (hero.shot == null || !hero.shot.isLive) {
                hero.shotEnemyTank();
            }

        }
        //Let the panel repaint
        this.repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() { //Every 100 milliseconds, repaint the area, refresh the drawing area, and the bullet moves

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Judge whether our bullet hit the enemy tank
            hitEnemyTank();
            //Judge whether the enemy tanks hit us
            hitHero();
            this.repaint();
        }

    }
}
