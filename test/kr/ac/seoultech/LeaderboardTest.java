package kr.ac.seoultech;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardTest {

    @Test
    void addScore() {   //color change에서 오류 뜸 나중에 확인 필요!!!!
        Leaderboard.loadScores("TestScore");

        //Case 0 (EASY, STANDARD)
        Leaderboard.addScore(100,"AAA",0);
        Leaderboard.addScore(200,"BBB",0);
        Leaderboard.addScore(150,"CCC",0);

        assertEquals(200,Leaderboard.topScores_EASY.get(0));
        assertEquals(150,Leaderboard.topScores_EASY.get(1));
        assertEquals(100,Leaderboard.topScores_EASY.get(2));
        assertEquals("BBB",Leaderboard.topUser_EASY.get(0));
        assertEquals("CCC",Leaderboard.topUser_EASY.get(1));
        assertEquals("AAA",Leaderboard.topUser_EASY.get(2));

        //Case 1 (NORMAL, STANDARD)
        Leaderboard.addScore(100,"AAA",1);
        Leaderboard.addScore(200,"BBB",1);
        Leaderboard.addScore(150,"CCC",1);

        assertEquals(200,Leaderboard.topScores.get(0));
        assertEquals(150,Leaderboard.topScores.get(1));
        assertEquals(100,Leaderboard.topScores.get(2));
        assertEquals("BBB",Leaderboard.topUser.get(0));
        assertEquals("CCC",Leaderboard.topUser.get(1));
        assertEquals("AAA",Leaderboard.topUser.get(2));

        //Case 2 (HARD, STANDARD)
        Leaderboard.addScore(100,"AAA",2);
        Leaderboard.addScore(200,"BBB",2);
        Leaderboard.addScore(150,"CCC",2);

        assertEquals(200,Leaderboard.topScores_HARD.get(0));
        assertEquals(150,Leaderboard.topScores_HARD.get(1));
        assertEquals(100,Leaderboard.topScores_HARD.get(2));
        assertEquals("BBB",Leaderboard.topUser_HARD.get(0));
        assertEquals("CCC",Leaderboard.topUser_HARD.get(1));
        assertEquals("AAA",Leaderboard.topUser_HARD.get(2));

        //Case 3 (EASY, ITEM)
        Leaderboard.addScore(100,"AAA",3);
        Leaderboard.addScore(200,"BBB",3);
        Leaderboard.addScore(150,"CCC",3);

        assertEquals(200,Leaderboard.topScores_ITEM_EASY.get(0));
        assertEquals(150,Leaderboard.topScores_ITEM_EASY.get(1));
        assertEquals(100,Leaderboard.topScores_ITEM_EASY.get(2));
        assertEquals("BBB",Leaderboard.topUser_ITEM_EASY.get(0));
        assertEquals("CCC",Leaderboard.topUser_ITEM_EASY.get(1));
        assertEquals("AAA",Leaderboard.topUser_ITEM_EASY.get(2));

        //Case 4 (NORMAL, ITEM)
        Leaderboard.addScore(100,"AAA",4);
        Leaderboard.addScore(200,"BBB",4);
        Leaderboard.addScore(150,"CCC",4);

        assertEquals(200,Leaderboard.topScores_ITEM.get(0));
        assertEquals(150,Leaderboard.topScores_ITEM.get(1));
        assertEquals(100,Leaderboard.topScores_ITEM.get(2));
        assertEquals("BBB",Leaderboard.topUser_ITEM.get(0));
        assertEquals("CCC",Leaderboard.topUser_ITEM.get(1));
        assertEquals("AAA",Leaderboard.topUser_ITEM.get(2));

        //Case 5 (HARD, ITEM)
        Leaderboard.addScore(100,"AAA",5);
        Leaderboard.addScore(200,"BBB",5);
        Leaderboard.addScore(150,"CCC",5);

        assertEquals(200,Leaderboard.topScores_ITEM_HARD.get(0));
        assertEquals(150,Leaderboard.topScores_ITEM_HARD.get(1));
        assertEquals(100,Leaderboard.topScores_ITEM_HARD.get(2));
        assertEquals("BBB",Leaderboard.topUser_ITEM_HARD.get(0));
        assertEquals("CCC",Leaderboard.topUser_ITEM_HARD.get(1));
        assertEquals("AAA",Leaderboard.topUser_ITEM_HARD.get(2));

    }

    @Test
    void loadScores() {
        Leaderboard.loadScores("TestScore");

        File f = new File(Leaderboard.filePath,"TestScore");
        assertTrue(f.isFile());
    }

    @Test
    void saveScores() {
        Leaderboard.loadScores("TestScore");

        Leaderboard.saveScores("TestScore");
    }

    @Test
    void createSaveData() throws IOException {
        Leaderboard.createSaveData("TestScore");
        File f=new File(Leaderboard.filePath,"TestScore");
        assertTrue(f.isFile());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        String TestData = reader.readLine();
        assertEquals("0-0-0-0-0-0-0-0-0-0", TestData);
        TestData = reader.readLine();
        assertEquals("___-___-___-___-___-___-___-___-___-___", TestData);
        TestData = reader.readLine();
        assertEquals("0-0-0-0-0-0-0-0-0-0", TestData);
        TestData = reader.readLine();
        assertEquals("___-___-___-___-___-___-___-___-___-___", TestData);
        TestData = reader.readLine();
        assertEquals("0-0-0-0-0-0-0-0-0-0", TestData);
        TestData = reader.readLine();
        assertEquals("___-___-___-___-___-___-___-___-___-___", TestData);
        TestData = reader.readLine();
        assertEquals("0-0-0-0-0-0-0-0-0-0", TestData);
        TestData = reader.readLine();
        assertEquals("___-___-___-___-___-___-___-___-___-___", TestData);
        TestData = reader.readLine();
        assertEquals("0-0-0-0-0-0-0-0-0-0", TestData);
        TestData = reader.readLine();
        assertEquals("___-___-___-___-___-___-___-___-___-___", TestData);
        TestData = reader.readLine();
        assertEquals("0-0-0-0-0-0-0-0-0-0", TestData);
        TestData = reader.readLine();
        assertEquals("___-___-___-___-___-___-___-___-___-___", TestData);
    }
}