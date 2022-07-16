package com.maidou.tankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;


public class MyTankGame extends JFrame {

    //define MyPanel
    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)  {

        MyTankGame TankGame = new MyTankGame();
   }


    public MyTankGame() {
        System.out.println("Please enter your choice 1: new game 2: Continue the previous game");
        String key = scanner.next();
        mp = new MyPanel(key);
        //put mp into Thread ,and start
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//

        this.setSize(1300, 950);
        this.addKeyListener(mp);//Let JFrame monitor MP's keyboard events
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

         //Add the processing of closing the corresponding window in JFrame
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}