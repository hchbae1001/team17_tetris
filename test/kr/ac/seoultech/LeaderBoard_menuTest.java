package kr.ac.seoultech;

import javafx.embed.swing.JFXPanel;
import javafx.scene.paint.Color;
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
        Leaderboard.loadScores("Scores");
        Leaderboard.addScore(100,"AAA",0);
        Leaderboard.addScore(200,"BBB",1);
        Leaderboard.addScore(300,"CCC",2);
        Leaderboard.addScore(400,"DDD",3);
        Leaderboard.addScore(500,"EEE",4);
        Leaderboard.addScore(600,"FFF",5);
        Leaderboard.saveScores("Scores");
        //CASE 0
        LeaderBoard_menu.RankingRefresh(0);
        assertEquals("100",LeaderBoard_menu.Ranking_score[0].getText());
        assertEquals("AAA",LeaderBoard_menu.Ranking_user[0].getText());
        //CASE 1
        LeaderBoard_menu.RankingRefresh(1);
        assertEquals("200",LeaderBoard_menu.Ranking_score[0].getText());
        assertEquals("BBB",LeaderBoard_menu.Ranking_user[0].getText());
        //CASE 2
        LeaderBoard_menu.RankingRefresh(2);
        assertEquals("300",LeaderBoard_menu.Ranking_score[0].getText());
        assertEquals("CCC",LeaderBoard_menu.Ranking_user[0].getText());
        //CASE 3
        LeaderBoard_menu.RankingRefresh(3);
        assertEquals("400",LeaderBoard_menu.Ranking_score[0].getText());
        assertEquals("DDD",LeaderBoard_menu.Ranking_user[0].getText());
        //CASE 4
        LeaderBoard_menu.RankingRefresh(4);
        assertEquals("500",LeaderBoard_menu.Ranking_score[0].getText());
        assertEquals("EEE",LeaderBoard_menu.Ranking_user[0].getText());
        //CASE 5
        LeaderBoard_menu.RankingRefresh(5);
        assertEquals("600",LeaderBoard_menu.Ranking_score[0].getText());
        assertEquals("FFF",LeaderBoard_menu.Ranking_user[0].getText());
    }

    @Test
    void leaderBoard() {
        Leaderboard.loadScores("Scores");
        //EASE STANDARD
        LeaderBoard_menu.difficulty="EASY";
        LeaderBoard_menu.mode="STANDARD";
        LeaderBoard_menu.LeaderBoard();
    }

    @Test
    void start() {
    }
}