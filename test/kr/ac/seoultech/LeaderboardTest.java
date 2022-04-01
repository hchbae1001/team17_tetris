package kr.ac.seoultech;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class LeaderboardTest {
/*
    @Test
    void addScore() {

        Leaderboard.loadScores("TestFile");
        for(int i=0;i<9;i++)
        {
            Leaderboard.addScore(1500,"Second", );
        }
        Leaderboard.addScore(1000,"Third", );
        Leaderboard.addScore(2000,"First", );
        assertEquals(Leaderboard.topScores.get(0),2000);
        assertEquals(Leaderboard.topUser.get(0),"First");
        assertEquals(Leaderboard.topScores.get(1),1500);
        assertEquals(Leaderboard.topUser.get(1),"Second");
        assertEquals(Leaderboard.topScores.get(9), 1500);
        assertEquals(Leaderboard.topUser.get(9),"Second");
    }

    @Test
    void loadScores() throws IOException {
        File f = new File(Leaderboard.filePath, "TestFile");
        if(!f.isFile()){
            Leaderboard.createSaveData("TestFile");
        }

        ArrayList<Integer> test_score = new ArrayList<Integer>();
        ArrayList<String> test_user = new ArrayList<String>();

        for(int i = 0; i<10;i++)
        {
            test_score.add((10-i)*100);
            test_user.add("testName");
        }

        FileWriter output = new FileWriter(f);
        BufferedWriter writer = new BufferedWriter(output);

        writer.write(test_score.get(0) + "-" + test_score.get(1) + "-" + test_score.get(2) + "-" + test_score.get(3) + "-" + test_score.get(4)+ "-" + test_score.get(5)+ "-" + test_score.get(6)+ "-" + test_score.get(7)+ "-" + test_score.get(8)+ "-" + test_score.get(9));
        writer.newLine();
        writer.write(test_user.get(0) + "-" + test_user.get(1) + "-" + test_user.get(2) + "-" + test_user.get(3) + "-" + test_user.get(4)+ "-" + test_user.get(5)+ "-" + test_user.get(6)+ "-" + test_user.get(7)+ "-" + test_user.get(8)+ "-" + test_user.get(9));
        writer.close();

        Leaderboard.loadScores("TestFile");

        for(int i=0;i<10;i++)
        {
            assertEquals(test_score.get(i),Leaderboard.topScores.get(i));
            assertEquals(test_user.get(i),Leaderboard.topUser.get(i));
        }
    }

    @Test
    void saveScores() {
        Leaderboard.loadScores("TestFile");
        for(int i=0;i<10;i++)
        {
            Leaderboard.addScore(3000*i,"Test_name", );
        }

        ArrayList<Integer> test_score = Leaderboard.topScores;
        ArrayList<String> test_user = Leaderboard.topUser;

        Leaderboard.saveScores("TestFile");
        Leaderboard.topScores.clear();
        Leaderboard.topUser.clear();

        Leaderboard.loadScores("TestFile");

        for(int i=0;i<10;i++)
        {
            assertEquals(test_score.get(i),Leaderboard.topScores.get(i));
            assertEquals(test_user.get(i),Leaderboard.topUser.get(i));
        }

    }

 */
}