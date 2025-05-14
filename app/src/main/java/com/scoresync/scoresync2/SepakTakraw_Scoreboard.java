package com.scoresync.scoresync2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.scoresync.scoresync2.model.GameHistory;
import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
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

        // Read total sets from SharedPreferences
        totalSets = getSharedPreferences("ScoreSyncPrefs", MODE_PRIVATE)
                .getInt("SEPAK_TOTAL_SETS", 5); // fallback default
        setsToWin = (totalSets / 2) + 1;
        bestOfCounter.setText(String.valueOf(totalSets));

        // Force landscape mode when entering the activity
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sepakPointsPerSet = getSharedPreferences("ScoreSyncPrefs", MODE_PRIVATE)
                .getInt("SEPAK_POINTS_PER_SET", 10); // 10 is the fallback default

        TextView team1Score = findViewById(R.id.team1_score);
        TextView team2Score = findViewById(R.id.team2_score);
        TextView team1SetsView = findViewById(R.id.team1_sets);
        TextView team2SetsView = findViewById(R.id.team2_sets);

        Button plusTeam1 = findViewById(R.id.plus_team1);
        Button minusTeam1 = findViewById(R.id.minus_team1);
        Button plusTeam2 = findViewById(R.id.plus_team2);
        Button minusTeam2 = findViewById(R.id.minus_team2);

        // for team 1
        plusTeam1.setOnClickListener(v -> {
            team1Points++;
            team1Score.setText(String.valueOf(team1Points));
            // Check if team1 wins the set/match
            if (team1Points >= sepakPointsPerSet) {
                team1Sets++;
                team1SetsView.setText(String.valueOf(team1Sets));
                updateRoundCounter();
                team1Points = 0;
                team2Points = 0;
                team1Score.setText("0");
                team2Score.setText("0");
                // Check if team1 wins the match
                if (team1Sets == setsToWin) {
                    String team1Name = ((TextView) findViewById(R.id.team1_name)).getText().toString().trim();
                    String team2Name = ((TextView) findViewById(R.id.team2_name)).getText().toString().trim();
                    if (team1Name.isEmpty()) team1Name = "Team 1";
                    if (team2Name.isEmpty()) team2Name = "Team 2";
                    String winner = team1Name;

                    GameHistory history = new GameHistory();
                    history.team1Name = team1Name;
                    history.team2Name = team2Name;
                    history.team1Score = team1Sets;
                    history.team2Score = team2Sets;
                    history.team1Sets = team1Sets;
                    history.team2Sets = team2Sets;
                    history.sportType = "Sepak Takraw";
                    history.date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
                    history.winner = winner;

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

        // for team 2
        plusTeam2.setOnClickListener(v -> {
            team2Points++;
            team2Score.setText(String.valueOf(team2Points));
            // Check if team2 wins the set/match
            if (team2Points >= sepakPointsPerSet) {
                team2Sets++;
                team2SetsView.setText(String.valueOf(team2Sets));
                updateRoundCounter();
                team1Points = 0;
                team2Points = 0;
                team1Score.setText("0");
                team2Score.setText("0");
                // Check if team2 wins the match
                if (team2Sets == setsToWin) {
                    String team1Name = ((TextView) findViewById(R.id.team1_name)).getText().toString().trim();
                    String team2Name = ((TextView) findViewById(R.id.team2_name)).getText().toString().trim();
                    if (team1Name.isEmpty()) team1Name = "Team 1";
                    if (team2Name.isEmpty()) team2Name = "Team 2";
                    String winner = team2Name;

                    GameHistory history = new GameHistory();
                    history.team1Name = team1Name;
                    history.team2Name = team2Name;
                    history.team1Score = team1Sets;
                    history.team2Score = team2Sets;
                    history.team1Sets = team1Sets;
                    history.team2Sets = team2Sets;
                    history.sportType = "Sepak Takraw";
                    history.date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
                    history.winner = winner;

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

        //updateRoundCounter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Reset to portrait when exiting the activity
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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

    private boolean isMatchOver() {
        // A team wins if it reaches setsToWin and the other team cannot catch up
        if (team1Sets >= setsToWin || team2Sets >= setsToWin) {
            return true;
        }
        // No one can catch up anymore
        if (team1Sets + team2Sets >= totalSets) {
            return true;
        }
        return false;
    }


}