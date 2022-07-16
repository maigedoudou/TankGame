package com.maidou.tankgame;

import java.io.*;
import java.util.Vector;

/**
 *
 * This class is used to record relevant information Interact with files
 */
@SuppressWarnings({"all"})
public class Recorder {

   //Define variables to record the number of enemy tanks destroyed by our side
    private static int allEnemyTankNum = 0;
   //Define the IO object and prepare to write data to the file
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;

    //Save the record file to Src
    private static String recordFile = "src\\myRecord.txt";

    //define Vector (enemyTanks)
    private static Vector<EnemyTank> enemyTanks = null;

    //define Vector of Node(to save enemy's information)
    private static Vector<Node> nodes = new Vector<>();

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //Returns the directory of the record file
    public static String getRecordFile() {
        return recordFile;
    }


    //Add a method to read recordfile and recover relevant information
    //This method can be called when continuing the previous game
    public static Vector<Node> getNodesAndEnemyTankRec() {

        try {

            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());

            //Read the file circularly and generate the nodes set
            String line = "";//255 40 0
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]),
                        Integer.parseInt(xyd[2]));
                nodes.add(node);//add nodes Vector
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return nodes;
    }


    //Add a method. When exits game, we save allenemytanknum to recordfile
    //upgrade keepRecord, save enemytank's Coordinates and directions

    public static void keepRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\r\n");
            //traverse enemyTanks' Vector ,save according to the condition

            //OOP, define an attribute, and then get the vector of the enemy tank through setXXX
            for (int i = 0; i < enemyTanks.size(); i++) {
                //get enemyTank
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) {
                    //save enemyTank's information
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    //write to file
                    bw.write(record + "\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //When our tank destroys an enemy tank, allenemytanknum++
    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }
}
