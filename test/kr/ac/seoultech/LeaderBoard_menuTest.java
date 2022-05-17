package kr.ac.seoultech;

import javafx.embed.swing.JFXPanel;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LeaderBoard_menuTest {
    private JFXPanel panel = new JFXPanel();

    @Test
    void leaderBoardPress() {
        LeaderBoard_menu.LeaderBoardPress();
    }

    @Test
    void leaderBoardColor() {
        LeaderBoard_menu.RankingRefresh(0);
        LeaderBoard_menu.LeaderBoardColor(0);
        assertEquals(Color.RED,LeaderBoard_menu.Ranking_score[0].getFill());
        assertEquals(Color.RED,LeaderBoard_menu.Ranking_user[0].getFill());
        assertEquals(Color.BLACK,LeaderBoard_menu.Ranking_score[1].getFill());
        assertEquals(Color.BLACK,LeaderBoard_menu.Ranking_user[1].getFill());
    }

    @Test
    void rankingRefresh() {
        //초기화
        Leaderboard.createSaveData("Scores");

        Leaderboard.loadScores("Scores");

        Leaderboard.addScore(10000000,"AAA",0);
        Leaderboard.addScore(20000000,"BBB",1);
        Leaderboard.addScore(30000000,"CCC",2);
        Leaderboard.addScore(40000000,"DDD",3);
        Leaderboard.addScore(50000000,"EEE",4);
        Leaderboard.addScore(60000000,"FFF",5);
        Leaderboard.saveScores("Scores");
        //CASE 0
        LeaderBoard_menu.RankingRefresh(0);
        assertEquals("10000000",LeaderBoard_menu.Ranking_score[0].getText());
        assertEquals("AAA",LeaderBoard_menu.Ranking_user[0].getText());
        //CASE 1
        LeaderBoard_menu.RankingRefresh(1);
        assertEquals("20000000",LeaderBoard_menu.Ranking_score[0].getText());
        assertEquals("BBB",LeaderBoard_menu.Ranking_user[0].getText());
        //CASE 2
        LeaderBoard_menu.RankingRefresh(2);
        assertEquals("30000000",LeaderBoard_menu.Ranking_score[0].getText());
        assertEquals("CCC",LeaderBoard_menu.Ranking_user[0].getText());
        //CASE 3
        LeaderBoard_menu.RankingRefresh(3);
        assertEquals("40000000",LeaderBoard_menu.Ranking_score[0].getText());
        assertEquals("DDD",LeaderBoard_menu.Ranking_user[0].getText());
        //CASE 4
        LeaderBoard_menu.RankingRefresh(4);
        assertEquals("50000000",LeaderBoard_menu.Ranking_score[0].getText());
        assertEquals("EEE",LeaderBoard_menu.Ranking_user[0].getText());
        //CASE 5
        LeaderBoard_menu.RankingRefresh(5);
        assertEquals("60000000",LeaderBoard_menu.Ranking_score[0].getText());
        assertEquals("FFF",LeaderBoard_menu.Ranking_user[0].getText());

        Leaderboard.createSaveData("Scores");
    }

    @Test
    void leaderBoard() {
        Leaderboard.loadScores("TestScore");
        //EASE STANDARD
        LeaderBoard_menu.difficulty="EASY";
        LeaderBoard_menu.mode="STANDARD";
        LeaderBoard_menu.LeaderBoard();
    }

    @Test
    void start() {
    }
}