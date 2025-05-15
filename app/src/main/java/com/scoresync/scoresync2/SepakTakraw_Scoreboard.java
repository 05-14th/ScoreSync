package com.scoresync.scoresync2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.scoresync.scoresync2.model.GameHistory;

import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.lang.reflect.Type;

public class SepakTakraw_Scoreboard extends AppCompatActivity {

    private int sepakPointsPerSet = 10;
    private int team1Points = 0, team2Points = 0;
    private int team1Sets = 0, team2Sets = 0;
    private TextView roundCounter;
    private int totalSets = 5;
    private int setsToWin = 3;
    private TextView bestOfCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sepak_takraw_scoreboard);

        roundCounter = findViewById(R.id.round_counter);
        bestOfCounter = findViewById(R.id.best_of_counter);

        totalSets = getSharedPreferences("ScoreSyncPrefs", MODE_PRIVATE)
                .getInt("SEPAK_TOTAL_SETS", 5);
        setsToWin = (totalSets / 2) + 1;
        bestOfCounter.setText(String.valueOf(totalSets));

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sepakPointsPerSet = getSharedPreferences("ScoreSyncPrefs", MODE_PRIVATE)
                .getInt("SEPAK_POINTS_PER_SET", 10);

        TextView team1Score = findViewById(R.id.team1_score);
        TextView team2Score = findViewById(R.id.team2_score);
        TextView team1SetsView = findViewById(R.id.team1_sets);
        TextView team2SetsView = findViewById(R.id.team2_sets);

        Button plusTeam1 = findViewById(R.id.plus_team1);
        Button minusTeam1 = findViewById(R.id.minus_team1);
        Button plusTeam2 = findViewById(R.id.plus_team2);
        Button minusTeam2 = findViewById(R.id.minus_team2);

        plusTeam1.setOnClickListener(v -> {
            team1Points++;
            team1Score.setText(String.valueOf(team1Points));
            if (team1Points >= sepakPointsPerSet) {
                team1Sets++;
                team1SetsView.setText(String.valueOf(team1Sets));
                updateRoundCounter();
                team1Points = 0;
                team2Points = 0;
                team1Score.setText("0");
                team2Score.setText("0");
                if (team1Sets == setsToWin) {
                    String team1Name = ((TextView) findViewById(R.id.team1_name)).getText().toString().trim();
                    String team2Name = ((TextView) findViewById(R.id.team2_name)).getText().toString().trim();
                    if (team1Name.isEmpty()) team1Name = "Team 1";
                    if (team2Name.isEmpty()) team2Name = "Team 2";
                    String winner = team1Name;

                    GameHistory history = new GameHistory();
                    history.setTeam1Name(team1Name);
                    history.setTeam2Name(team2Name);
                    history.setTeam1Score(team1Sets);
                    history.setTeam2Score(team2Sets);
                    history.setTeam1Sets(team1Sets);
                    history.setTeam2Sets(team2Sets);
                    history.setSportType("Sepak Takraw");
                    history.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date()));
                    history.setWinner(winner);

                    SharedPreferences prefs = getSharedPreferences("ScoreSyncPrefs", MODE_PRIVATE);
                    Gson gson = new Gson();
                    String json = prefs.getString("GAME_HISTORY_LIST", "[]");
                    Type type = new com.google.gson.reflect.TypeToken<ArrayList<GameHistory>>(){}.getType();
                    ArrayList<GameHistory> historyList = gson.fromJson(json, type);
                    if (historyList == null) historyList = new ArrayList<>();
                    historyList.add(history);
                    prefs.edit().putString("GAME_HISTORY_LIST", gson.toJson(historyList)).apply();

                    Toast.makeText(this, winner + " wins the match!", Toast.LENGTH_LONG).show();
                    resetScoreboard(team1Score, team2Score, team1SetsView, team2SetsView);
                }
            }
        });

        minusTeam1.setOnClickListener(v -> {
            if (team1Points > 0) {
                team1Points--;
                team1Score.setText(String.valueOf(team1Points));
            }
        });

        plusTeam2.setOnClickListener(v -> {
            team2Points++;
            team2Score.setText(String.valueOf(team2Points));
            if (team2Points >= sepakPointsPerSet) {
                team2Sets++;
                team2SetsView.setText(String.valueOf(team2Sets));
                updateRoundCounter();
                team1Points = 0;
                team2Points = 0;
                team1Score.setText("0");
                team2Score.setText("0");
                if (team2Sets == setsToWin) {
                    String team1Name = ((TextView) findViewById(R.id.team1_name)).getText().toString().trim();
                    String team2Name = ((TextView) findViewById(R.id.team2_name)).getText().toString().trim();
                    if (team1Name.isEmpty()) team1Name = "Team 1";
                    if (team2Name.isEmpty()) team2Name = "Team 2";
                    String winner = team2Name;

                    GameHistory history = new GameHistory();
                    history.setTeam1Name(team1Name);
                    history.setTeam2Name(team2Name);
                    history.setTeam1Score(team1Sets);
                    history.setTeam2Score(team2Sets);
                    history.setTeam1Sets(team1Sets);
                    history.setTeam2Sets(team2Sets);
                    history.setSportType("Sepak Takraw");
                    history.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date()));
                    history.setWinner(winner);

                    SharedPreferences prefs = getSharedPreferences("ScoreSyncPrefs", MODE_PRIVATE);
                    Gson gson = new Gson();
                    String json = prefs.getString("GAME_HISTORY_LIST", "[]");
                    Type type = new com.google.gson.reflect.TypeToken<ArrayList<GameHistory>>(){}.getType();
                    ArrayList<GameHistory> historyList = gson.fromJson(json, type);
                    if (historyList == null) historyList = new ArrayList<>();
                    historyList.add(history);
                    prefs.edit().putString("GAME_HISTORY_LIST", gson.toJson(historyList)).apply();

                    Toast.makeText(this, winner + " wins the match!", Toast.LENGTH_LONG).show();
                    resetScoreboard(team1Score, team2Score, team1SetsView, team2SetsView);
                }
            }
        });

        minusTeam2.setOnClickListener(v -> {
            if (team2Points > 0) {
                team2Points--;
                team2Score.setText(String.valueOf(team2Points));
            }
        });
    }

    private void updateRoundCounter() {
        int round = team1Sets + team2Sets + 1;
        roundCounter.setText(String.valueOf(round));
    }

    private void resetScoreboard(TextView team1Score, TextView team2Score, TextView team1SetsView, TextView team2SetsView) {
        team1Points = 0;
        team2Points = 0;
        team1Sets = 0;
        team2Sets = 0;
        team1Score.setText("0");
        team2Score.setText("0");
        team1SetsView.setText("0");
        team2SetsView.setText("0");
        updateRoundCounter();
    }
}