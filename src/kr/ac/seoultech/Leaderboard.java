package kr.ac.seoultech;

import java.io.*;
import java.util.ArrayList;

public class Leaderboard {


    public static String filePath = new File("").getAbsolutePath();
    public static String fileName = "Scores";


    public static ArrayList<Integer>[] topScores = (ArrayList<Integer>[])new ArrayList[6];
    public static ArrayList<String>[] topUser = (ArrayList<String>[])new ArrayList[6];

    public static void addScore(int score, String name, int difficulty){
        for(int i=0;i<topScores[difficulty].size();i++)
        {
            if(score >=topScores[difficulty].get(i)){
                topScores[difficulty].add(i, score);
                topUser[difficulty].add(i, name);
                topScores[difficulty].remove(topScores[difficulty].size()-1);
                topUser[difficulty].remove(topUser[difficulty].size()-1);
                return;
            }
        }
    }

    public static void loadScores(String filename){
        try{
            File f = new File(filePath, filename);
            if(!f.isFile()){
                createSaveData(filename);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

            for(int i=0;i<6;i++)
            {
                topScores[i].clear();
                topUser[i].clear();
            }


            for(int j=0;j<6;j++)
            {
                String[] scores = reader.readLine().split("-");
                String[] name = reader.readLine().split("-");

                for(int i = 0; i<scores.length;i++)
                {
                    topScores[j].add(Integer.parseInt(scores[i]));
                    topUser[j].add(name[i]);
                }
            }

            reader.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void saveScores(String filename){
        FileWriter output = null;

        try{
            File f = new File(filePath, filename);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);

            for(int i=0;i<6;i++)
            {
                writer.write(topScores[i].get(0) + "-" + topScores[i].get(1) + "-" + topScores[i].get(2) + "-" + topScores[i].get(3) + "-" + topScores[i].get(4)+ "-" + topScores[i].get(5)+ "-" + topScores[i].get(6)+ "-" + topScores[i].get(7)+ "-" + topScores[i].get(8)+ "-" + topScores[i].get(9));
                writer.newLine();
                writer.write(topUser[i].get(0) + "-" + topUser[i].get(1) + "-" + topUser[i].get(2) + "-" + topUser[i].get(3) + "-" + topUser[i].get(4)+ "-" + topUser[i].get(5)+ "-" + topUser[i].get(6)+ "-" + topUser[i].get(7)+ "-" + topUser[i].get(8)+ "-" + topUser[i].get(9));
                writer.newLine();
            }
            writer.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void createSaveData(String filename){
        FileWriter output = null;

        try{
            File f = new File(filePath, filename);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);

            for(int i=0; i<6; i++)
            {
                writer.write("0-0-0-0-0-0-0-0-0-0");
                writer.newLine();
                writer.write("___-___-___-___-___-___-___-___-___-___");
                writer.newLine();
            }
            writer.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}