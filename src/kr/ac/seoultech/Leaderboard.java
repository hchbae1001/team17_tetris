package kr.ac.seoultech;

import java.io.*;
import java.util.ArrayList;

public class Leaderboard {


    public static String filePath = new File("").getAbsolutePath();
    public static String fileName = "Scores";

    public static ArrayList<Integer> topScores = new ArrayList<Integer>();
    public static ArrayList<String> topUser = new ArrayList<String>();
    public static ArrayList<Integer> topScores_EASY = new ArrayList<Integer>();
    public static ArrayList<String> topUser_EASY = new ArrayList<String>();
    public static ArrayList<Integer> topScores_HARD = new ArrayList<Integer>();
    public static ArrayList<String> topUser_HARD = new ArrayList<String>();
    public static ArrayList<Integer> topScores_ITEM = new ArrayList<Integer>();
    public static ArrayList<String> topUser_ITEM = new ArrayList<String>();
    public static ArrayList<Integer> topScores_ITEM_EASY = new ArrayList<Integer>();
    public static ArrayList<String> topUser_ITEM_EASY = new ArrayList<String>();
    public static ArrayList<Integer> topScores_ITEM_HARD = new ArrayList<Integer>();
    public static ArrayList<String> topUser_ITEM_HARD = new ArrayList<String>();


    public static void addScore(int score, String name, int difficulty){
        switch (difficulty)
        {
            case 0:
                for(int i=0;i<topScores_EASY.size();i++)
                {
                    if(score >=topScores_EASY.get(i)){
                        topScores_EASY.add(i, score);
                        topUser_EASY.add(i, name);
                        topScores_EASY.remove(topScores_EASY.size()-1);
                        topUser_EASY.remove(topUser_EASY.size()-1);
                        break;
                    }
                }
                for (int i = 9; i >= 0; i--) {
                    if (topScores_EASY.get(i) == score && topUser_EASY.get(i) == name) {
                        //LeaderBoard_menu.RankingColor = i;
                        break;
                    }
                }
                break;
            case 1:
                for(int i=0;i<topScores.size();i++)
                {
                    if(score >=topScores.get(i)){
                        topScores.add(i, score);
                        topUser.add(i, name);
                        topScores.remove(topScores.size()-1);
                        topUser.remove(topUser.size()-1);
                        break;
                    }
                }
                for (int i = 9; i >= 0; i--) {
                    if (topScores.get(i) == score && topUser.get(i) == name) {
                        //LeaderBoard_menu.RankingColor = i;
                        break;
                    }
                }
                break;
            case 2:
                for(int i=0;i<topScores_HARD.size();i++)
                {
                    if(score >=topScores_HARD.get(i)){
                        topScores_HARD.add(i, score);
                        topUser_HARD.add(i, name);
                        topScores_HARD.remove(topScores_HARD.size()-1);
                        topUser_HARD.remove(topUser_HARD.size()-1);
                        break;
                    }
                }
                for (int i = 9; i >= 0; i--) {
                    if (topScores_HARD.get(i) == score && topUser_HARD.get(i) == name) {
                        //LeaderBoard_menu.RankingColor = i;
                        break;
                    }
                }
                break;
            case 3:
                for(int i=0;i<topScores_ITEM_EASY.size();i++)
                {
                    if(score >=topScores_ITEM_EASY.get(i)){
                        topScores_ITEM_EASY.add(i, score);
                        topUser_ITEM_EASY.add(i, name);
                        topScores_ITEM_EASY.remove(topScores_ITEM_EASY.size()-1);
                        topUser_ITEM_EASY.remove(topUser_ITEM_EASY.size()-1);
                        break;
                    }
                }
                for (int i = 9; i >= 0; i--) {
                    if (topScores_ITEM_EASY.get(i) == score && topUser_ITEM_EASY.get(i) == name) {
                        //LeaderBoard_menu.RankingColor = i;
                        break;
                    }
                }
                break;
            case 4:
                for(int i=0;i<topScores_ITEM.size();i++)
                {
                    if(score >=topScores_ITEM.get(i)){
                        topScores_ITEM.add(i, score);
                        topUser_ITEM.add(i, name);
                        topScores_ITEM.remove(topScores_ITEM.size()-1);
                        topUser_ITEM.remove(topUser_ITEM.size()-1);
                        break;
                    }
                }
                for (int i = 9; i >= 0; i--) {
                    if (topScores_ITEM.get(i) == score && topUser_ITEM.get(i) == name) {
                        //LeaderBoard_menu.RankingColor = i;
                        break;
                    }
                }
                break;
            case 5:
                for(int i=0;i<topScores_ITEM_HARD.size();i++)
                {
                    if(score >=topScores_ITEM_HARD.get(i)){
                        topScores_ITEM_HARD.add(i, score);
                        topUser_ITEM_HARD.add(i, name);
                        topScores_ITEM_HARD.remove(topScores_ITEM_HARD.size()-1);
                        topUser_ITEM_HARD.remove(topUser_HARD.size()-1);
                        break;
                    }
                }
                for (int i = 9; i >= 0; i--) {
                    if (topScores_ITEM_HARD.get(i) == score && topUser_ITEM_HARD.get(i) == name) {
                        //LeaderBoard_menu.RankingColor = i;
                        break;
                    }
                }
                break;
        }

    }

    public static void loadScores(String filename){
        try{
            File f = new File(filePath, filename);
            if(!f.isFile()){
                createSaveData(filename);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

            topScores_EASY.clear();
            topUser_EASY.clear();
            topScores.clear();
            topUser.clear();
            topScores_HARD.clear();
            topUser_HARD.clear();
            topScores_ITEM_EASY.clear();
            topUser_ITEM_EASY.clear();
            topScores_ITEM.clear();
            topUser_ITEM.clear();
            topScores_ITEM_HARD.clear();
            topUser_ITEM_HARD.clear();

            String[] scores_E = reader.readLine().split("-");
            String[] name_E = reader.readLine().split("-");
            String[] scores = reader.readLine().split("-");
            String[] name = reader.readLine().split("-");
            String[] scores_H = reader.readLine().split("-");
            String[] name_H = reader.readLine().split("-");
            String[] scores_I_E = reader.readLine().split("-");
            String[] name_I_E = reader.readLine().split("-");
            String[] scores_I = reader.readLine().split("-");
            String[] name_I = reader.readLine().split("-");
            String[] scores_I_H = reader.readLine().split("-");
            String[] name_I_H = reader.readLine().split("-");

            for(int i = 0; i<scores_E.length;i++)
            {
                topScores_EASY.add(Integer.parseInt(scores_E[i]));
                topUser_EASY.add(name_E[i]);
            }

            for(int i = 0; i<scores.length;i++)
            {
                topScores.add(Integer.parseInt(scores[i]));
                topUser.add(name[i]);
            }

            for(int i = 0; i<scores_H.length;i++)
            {
                topScores_HARD.add(Integer.parseInt(scores_H[i]));
                topUser_HARD.add(name_H[i]);
            }

            for(int i = 0; i<scores_I_E.length;i++)
            {
                topScores_ITEM_EASY.add(Integer.parseInt(scores_I_E[i]));
                topUser_ITEM_EASY.add(name_I_E[i]);
            }

            for(int i = 0; i<scores_I.length;i++)
            {
                topScores_ITEM.add(Integer.parseInt(scores_I[i]));
                topUser_ITEM.add(name_I[i]);
            }

            for(int i = 0; i<scores_I_H.length;i++)
            {
                topScores_ITEM_HARD.add(Integer.parseInt(scores_I_H[i]));
                topUser_ITEM_HARD.add(name_I_H[i]);
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

            writer.write(topScores_EASY.get(0) + "-" + topScores_EASY.get(1) + "-" + topScores_EASY.get(2) + "-" + topScores_EASY.get(3) + "-" + topScores_EASY.get(4)+ "-" + topScores_EASY.get(5)+ "-" + topScores_EASY.get(6)+ "-" + topScores_EASY.get(7)+ "-" + topScores_EASY.get(8)+ "-" + topScores_EASY.get(9));
            writer.newLine();
            writer.write(topUser_EASY.get(0) + "-" + topUser_EASY.get(1) + "-" + topUser_EASY.get(2) + "-" + topUser_EASY.get(3) + "-" + topUser_EASY.get(4)+ "-" + topUser_EASY.get(5)+ "-" + topUser_EASY.get(6)+ "-" + topUser_EASY.get(7)+ "-" + topUser_EASY.get(8)+ "-" + topUser_EASY.get(9));
            writer.newLine();
            writer.write(topScores.get(0) + "-" + topScores.get(1) + "-" + topScores.get(2) + "-" + topScores.get(3) + "-" + topScores.get(4)+ "-" + topScores.get(5)+ "-" + topScores.get(6)+ "-" + topScores.get(7)+ "-" + topScores.get(8)+ "-" + topScores.get(9));
            writer.newLine();
            writer.write(topUser.get(0) + "-" + topUser.get(1) + "-" + topUser.get(2) + "-" + topUser.get(3) + "-" + topUser.get(4)+ "-" + topUser.get(5)+ "-" + topUser.get(6)+ "-" + topUser.get(7)+ "-" + topUser.get(8)+ "-" + topUser.get(9));
            writer.newLine();
            writer.write(topScores_HARD.get(0) + "-" + topScores_HARD.get(1) + "-" + topScores_HARD.get(2) + "-" + topScores_HARD.get(3) + "-" + topScores_HARD.get(4)+ "-" + topScores_HARD.get(5)+ "-" + topScores_HARD.get(6)+ "-" + topScores_HARD.get(7)+ "-" + topScores_HARD.get(8)+ "-" + topScores_HARD.get(9));
            writer.newLine();
            writer.write(topUser_HARD.get(0) + "-" + topUser_HARD.get(1) + "-" + topUser_HARD.get(2) + "-" + topUser_HARD.get(3) + "-" + topUser_HARD.get(4)+ "-" + topUser_HARD.get(5)+ "-" + topUser_HARD.get(6)+ "-" + topUser_HARD.get(7)+ "-" + topUser_HARD.get(8)+ "-" + topUser_HARD.get(9));
            writer.newLine();
            writer.write(topScores_ITEM_EASY.get(0) + "-" + topScores_ITEM_EASY.get(1) + "-" + topScores_ITEM_EASY.get(2) + "-" + topScores_ITEM_EASY.get(3) + "-" + topScores_ITEM_EASY.get(4)+ "-" + topScores_ITEM_EASY.get(5)+ "-" + topScores_ITEM_EASY.get(6)+ "-" + topScores_ITEM_EASY.get(7)+ "-" + topScores_ITEM_EASY.get(8)+ "-" + topScores_ITEM_EASY.get(9));
            writer.newLine();
            writer.write(topUser_ITEM_EASY.get(0) + "-" + topUser_ITEM_EASY.get(1) + "-" + topUser_ITEM_EASY.get(2) + "-" + topUser_ITEM_EASY.get(3) + "-" + topUser_ITEM_EASY.get(4)+ "-" + topUser_ITEM_EASY.get(5)+ "-" + topUser_ITEM_EASY.get(6)+ "-" + topUser_ITEM_EASY.get(7)+ "-" + topUser_ITEM_EASY.get(8)+ "-" + topUser_ITEM_EASY.get(9));
            writer.newLine();
            writer.write(topScores_ITEM.get(0) + "-" + topScores_ITEM.get(1) + "-" + topScores_ITEM.get(2) + "-" + topScores_ITEM.get(3) + "-" + topScores_ITEM.get(4)+ "-" + topScores_ITEM.get(5)+ "-" + topScores_ITEM.get(6)+ "-" + topScores_ITEM.get(7)+ "-" + topScores_ITEM.get(8)+ "-" + topScores_ITEM.get(9));
            writer.newLine();
            writer.write(topUser_ITEM.get(0) + "-" + topUser_ITEM.get(1) + "-" + topUser_ITEM.get(2) + "-" + topUser_ITEM.get(3) + "-" + topUser_ITEM.get(4)+ "-" + topUser_ITEM.get(5)+ "-" + topUser_ITEM.get(6)+ "-" + topUser_ITEM.get(7)+ "-" + topUser_ITEM.get(8)+ "-" + topUser_ITEM.get(9));
            writer.newLine();
            writer.write(topScores_ITEM_HARD.get(0) + "-" + topScores_ITEM_HARD.get(1) + "-" + topScores_ITEM_HARD.get(2) + "-" + topScores_ITEM_HARD.get(3) + "-" + topScores_ITEM_HARD.get(4)+ "-" + topScores_ITEM_HARD.get(5)+ "-" + topScores_ITEM_HARD.get(6)+ "-" + topScores_ITEM_HARD.get(7)+ "-" + topScores_ITEM_HARD.get(8)+ "-" + topScores_ITEM_HARD.get(9));
            writer.newLine();
            writer.write(topUser_ITEM_HARD.get(0) + "-" + topUser_ITEM_HARD.get(1) + "-" + topUser_ITEM_HARD.get(2) + "-" + topUser_ITEM_HARD.get(3) + "-" + topUser_ITEM_HARD.get(4)+ "-" + topUser_ITEM_HARD.get(5)+ "-" + topUser_ITEM_HARD.get(6)+ "-" + topUser_ITEM_HARD.get(7)+ "-" + topUser_ITEM_HARD.get(8)+ "-" + topUser_ITEM_HARD.get(9));

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

            writer.write("0-0-0-0-0-0-0-0-0-0");
            writer.newLine();
            writer.write("___-___-___-___-___-___-___-___-___-___");
            writer.newLine();
            writer.write("0-0-0-0-0-0-0-0-0-0");
            writer.newLine();
            writer.write("___-___-___-___-___-___-___-___-___-___");
            writer.newLine();
            writer.write("0-0-0-0-0-0-0-0-0-0");
            writer.newLine();
            writer.write("___-___-___-___-___-___-___-___-___-___");
            writer.newLine();
            writer.write("0-0-0-0-0-0-0-0-0-0");
            writer.newLine();
            writer.write("___-___-___-___-___-___-___-___-___-___");
            writer.newLine();
            writer.write("0-0-0-0-0-0-0-0-0-0");
            writer.newLine();
            writer.write("___-___-___-___-___-___-___-___-___-___");
            writer.newLine();
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

