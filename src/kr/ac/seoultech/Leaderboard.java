package kr.ac.seoultech;

import java.io.*;
import java.util.ArrayList;

public class Leaderboard {


    private static String filePath = new File("").getAbsolutePath();
    private static String fileName = "Scores";

    public static ArrayList<Integer> topScores = new ArrayList<Integer>();
    public static ArrayList<String> topUser = new ArrayList<String>();

    public static void addScore(int score, String name){
        for(int i=0;i<topScores.size();i++)
        {
            if(score >=topScores.get(i)){
                topScores.add(i, score);
                topUser.add(i, name);
                topScores.remove(topScores.size()-1);
                topUser.remove(topUser.size()-1);
                return;
            }
        }
    }

    public static void loadScores(){
        try{
            File f = new File(filePath, fileName);
            if(!f.isFile()){
                createSaveData();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

            topScores.clear();

            String[] scores = reader.readLine().split("-");
            String[] name = reader.readLine().split("-");

            for(int i = 0; i<scores.length;i++)
            {
                topScores.add(Integer.parseInt(scores[i]));
                topUser.add(name[i]);
            }
            reader.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void saveScores(){
        FileWriter output = null;

        try{
            File f = new File(filePath, fileName);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);

            writer.write(topScores.get(0) + "-" + topScores.get(1) + "-" + topScores.get(2) + "-" + topScores.get(3) + "-" + topScores.get(4)+ "-" + topScores.get(5)+ "-" + topScores.get(6)+ "-" + topScores.get(7)+ "-" + topScores.get(8)+ "-" + topScores.get(9));
            writer.newLine();
            writer.write(topUser.get(0) + "-" + topUser.get(1) + "-" + topUser.get(2) + "-" + topUser.get(3) + "-" + topUser.get(4)+ "-" + topUser.get(5)+ "-" + topUser.get(6)+ "-" + topUser.get(7)+ "-" + topUser.get(8)+ "-" + topUser.get(9));
            writer.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    private static void createSaveData(){
        FileWriter output = null;

        try{
            File f = new File(filePath, fileName);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);

            writer.write("0-0-0-0-0-0-0-0-0-0");
            writer.newLine();
            writer.write("___-___-___-___-___-___-___-___-___-___");
            writer.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}