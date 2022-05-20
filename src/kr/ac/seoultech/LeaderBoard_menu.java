package kr.ac.seoultech;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class LeaderBoard_menu extends Application {

    public static final int DEADLINEGAP = 4;
    public static final int SIZE = 25;
    public static final int XMAX = SIZE * 10;
    public static final int YMAX = SIZE * (20 + DEADLINEGAP);
    final public static Pane group = new Pane();
    public static Scene scene = new Scene(group, XMAX + 150, YMAX - SIZE);

    public static Text Title = new Text("Leaderboard");

    public static Text[] Ranking_score = new Text[10];
    public static Text[] Ranking_user = new Text[10];

    public static Stage window;
    public static String mode, difficulty;

    public enum difficulty_enum {EASY, NORMAL, HARD}
    public enum mode_enum {STANDARD, ITEM}

    public static void LeaderBoardPress(){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.BACK_SPACE)
                {
                    group.getChildren().removeAll(Ranking_score[0],Ranking_score[1],Ranking_score[2],Ranking_score[3],Ranking_score[4],Ranking_score[5],Ranking_score[6],Ranking_score[7],Ranking_score[8],Ranking_score[9],
                            Ranking_user[0],Ranking_user[1],Ranking_user[2],Ranking_user[3],Ranking_user[4],Ranking_user[5],Ranking_user[6],Ranking_user[7],Ranking_user[8],Ranking_user[9],
                            Title);
                    if(!StartMenu.isBoardSelectOn){
                        try{
                            LeaderBoard_Select leaderBoard_select = new LeaderBoard_Select();
                            leaderBoard_select.start(window);
                            StartMenu.isBoardSelectOn = true;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        window.setScene(LeaderBoard_Select.scene);
                    }
                }
            }
        });
    }

    public static void LeaderBoardColor(int i){
        if(i>=0&&i<10)
        {
            Ranking_user[i].setFill(Color.RED);
            Ranking_score[i].setFill(Color.RED);
        }
    }

    public static void RankingRefresh(int difficulty){
        Leaderboard.loadScores(Leaderboard.fileName);
        switch (difficulty)
        {
            case 0:
                for(int i=0;i<10;i++)
                {
                    Ranking_score[i]=new Text(Integer.toString(Leaderboard.topScores_EASY.get(i)));
                    Ranking_user[i]=new Text(Leaderboard.topUser_EASY.get(i));
                }
                break;
            case 1:
                for(int i=0;i<10;i++)
                {
                    Ranking_score[i]=new Text(Integer.toString(Leaderboard.topScores.get(i)));
                    Ranking_user[i]=new Text(Leaderboard.topUser.get(i));
                }
                break;
            case 2:
                for(int i=0;i<10;i++)
                {
                    Ranking_score[i]=new Text(Integer.toString(Leaderboard.topScores_HARD.get(i)));
                    Ranking_user[i]=new Text(Leaderboard.topUser_HARD.get(i));
                }
                break;
            case 3:
                for(int i=0;i<10;i++)
                {
                    Ranking_score[i]=new Text(Integer.toString(Leaderboard.topScores_ITEM_EASY.get(i)));
                    Ranking_user[i]=new Text(Leaderboard.topUser_ITEM_EASY.get(i));
                }
                break;
            case 4:
                for(int i=0;i<10;i++)
                {
                    Ranking_score[i]=new Text(Integer.toString(Leaderboard.topScores_ITEM.get(i)));
                    Ranking_user[i]=new Text(Leaderboard.topUser_ITEM.get(i));
                }
                break;
            case 5:
                for(int i=0;i<10;i++)
                {
                    Ranking_score[i]=new Text(Integer.toString(Leaderboard.topScores_ITEM_HARD.get(i)));
                    Ranking_user[i]=new Text(Leaderboard.topUser_ITEM_HARD.get(i));
                }
                break;
        }
    }

    public static void LeaderBoard(){

        RankingRefresh(difficulty_enum.valueOf(difficulty).ordinal()+mode_enum.valueOf(mode).ordinal()*3);
        System.out.println("RankingRefresh :"+(difficulty_enum.valueOf(difficulty).ordinal()+mode_enum.valueOf(mode).ordinal()*3));

        Title.setStyle("-fx-font: 40 arial");
        Title.setX(XMAX / 2 -25);
        Title.setY(YMAX / 2 -250);
        Title.setFill(Color.BLACK);

        for(int i=0;i<10;i++)
        {
            Ranking_score[i].setStyle("-fx-font: 20 arial");
            Ranking_score[i].setX(XMAX / 2 +100);
            Ranking_score[i].setY(YMAX / 2 -200+i*50);
            Ranking_score[i].setFill(Color.BLACK);
            Ranking_user[i].setStyle("-fx-font: 20 arial");
            Ranking_user[i].setX(XMAX / 2);
            Ranking_user[i].setY(YMAX / 2 -200+i*50);
            Ranking_user[i].setFill(Color.BLACK);
        }

        LeaderBoardColor(Leaderboard.RankingColor);
        Leaderboard.RankingColor = -1;

        group.getChildren().addAll(
                Ranking_score[0],Ranking_score[1],Ranking_score[2],Ranking_score[3],Ranking_score[4],Ranking_score[5],Ranking_score[6],Ranking_score[7],Ranking_score[8],Ranking_score[9],
                Ranking_user[0],Ranking_user[1],Ranking_user[2],Ranking_user[3],Ranking_user[4],Ranking_user[5],Ranking_user[6],Ranking_user[7],Ranking_user[8],Ranking_user[9],
                Title
        );

        LeaderBoardPress();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        LeaderBoard();

        primaryStage.setScene(scene);

        primaryStage.setTitle("T E T R I S");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}