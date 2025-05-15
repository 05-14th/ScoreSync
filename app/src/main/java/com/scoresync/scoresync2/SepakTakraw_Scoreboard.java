package com.scoresync.scoresync2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
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
    private boolean isSwapped = false;

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

        // Swap button logic
        ImageButton swapButton = findViewById(R.id.swap_button);
        swapButton.setOnClickListener(v -> {
            // Swap team names
            TextView team1NameView = findViewById(R.id.team1_name);
            TextView team2NameView = findViewById(R.id.team2_name);
            CharSequence tempName = team1NameView.getText();
            team1NameView.setText(team2NameView.getText());
            team2NameView.setText(tempName);

            // Swap scores
            CharSequence tempScore = team1Score.getText();
            team1Score.setText(team2Score.getText());
            team2Score.setText(tempScore);

            // Swap sets
            CharSequence tempSets = team1SetsView.getText();
            team1SetsView.setText(team2SetsView.getText());
            team2SetsView.setText(tempSets);

            // Swap internal variables
            int tempPoints = team1Points;
            team1Points = team2Points;
            team2Points = tempPoints;

            int tempSetsInt = team1Sets;
            team1Sets = team2Sets;
            team2Sets = tempSetsInt;

            isSwapped = !isSwapped;
        });

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
                    saveGameHistory(true);
                    Toast.makeText(this, getCurrentWinnerName(true) + " wins the match!", Toast.LENGTH_LONG).show();
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
                    saveGameHistory(false);
                    Toast.makeText(this, getCurrentWinnerName(false) + " wins the match!", Toast.LENGTH_LONG).show();
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

    // Helper to get the correct winner name based on swap state and which team won
    private String getCurrentWinnerName(boolean leftWon) {
        TextView team1NameView = findViewById(R.id.team1_name);
        TextView team2NameView = findViewById(R.id.team2_name);
        String leftName = team1NameView.getText().toString().trim();
        String rightName = team2NameView.getText().toString().trim();
        if (leftName.isEmpty()) leftName = "Team 1";
        if (rightName.isEmpty()) rightName = "Team 2";
        if (!isSwapped) {
            return leftWon ? leftName : rightName;
        } else {
            return leftWon ? rightName : leftName;
        }
    }

    // Save game history with correct team positions based on swap state
    private void saveGameHistory(boolean leftWon) {
        TextView team1NameView = findViewById(R.id.team1_name);
        TextView team2NameView = findViewById(R.id.team2_name);
        String leftName = team1NameView.getText().toString().trim();
        String rightName = team2NameView.getText().toString().trim();
        if (leftName.isEmpty()) leftName = "Team 1";
        if (rightName.isEmpty()) rightName = "Team 2";

        GameHistory history = new GameHistory();
        if (!isSwapped) {
            history.setTeam1Name(leftName);
            history.setTeam2Name(rightName);
            history.setTeam1Score(team1Sets);
            history.setTeam2Score(team2Sets);
            history.setTeam1Sets(team1Sets);
            history.setTeam2Sets(team2Sets);
            history.setWinner(leftWon ? leftName : rightName);
        } else {
            history.setTeam1Name(rightName);
            history.setTeam2Name(leftName);
            history.setTeam1Score(team2Sets);
            history.setTeam2Score(team1Sets);
            history.setTeam1Sets(team2Sets);
            history.setTeam2Sets(team1Sets);
            history.setWinner(leftWon ? rightName : leftName);
        }
        history.setSportType("Sepak Takraw");
        history.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date()));

        SharedPreferences prefs = getSharedPreferences("ScoreSyncPrefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString("GAME_HISTORY_LIST", "[]");
        Type type = new com.google.gson.reflect.TypeToken<ArrayList<GameHistory>>(){}.getType();
        ArrayList<GameHistory> historyList = gson.fromJson(json, type);
        if (historyList == null) historyList = new ArrayList<>();
        historyList.add(history);
        prefs.edit().putString("GAME_HISTORY_LIST", gson.toJson(historyList)).apply();
    }
}