package com.scoresync.scoresync2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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
            if (team1Points >= sepakPointsPerSet) {
                team1Sets++;
                team1Points = 0;
                team2Points = 0;
                updateRoundCounter();
                if (isMatchOver()) {
                    String winner;
                    if (team1Sets > team2Sets) {
                        winner = "Team 1";
                    } else if (team2Sets > team1Sets) {
                        winner = "Team 2";
                    } else {
                        winner = "Draw";
                    }
                    Toast.makeText(this, winner + " Wins!", Toast.LENGTH_LONG).show();
                    resetScoreboard(team1Score, team2Score, team1SetsView, team2SetsView);
                    return;
                }
            }
            team1Score.setText(String.valueOf(team1Points));
            team2Score.setText(String.valueOf(team2Points));
            team1SetsView.setText(String.valueOf(team1Sets));
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
            if (team2Points >= sepakPointsPerSet) {
                team2Sets++;
                team1Points = 0;
                team2Points = 0;
                updateRoundCounter();
                if (isMatchOver()) {
                    String winner;
                    if (team1Sets > team2Sets) {
                        winner = "Team 1";
                    } else if (team2Sets > team1Sets) {
                        winner = "Team 2";
                    } else {
                        winner = "Draw";
                    }
                    Toast.makeText(this, winner + " Wins!", Toast.LENGTH_LONG).show();
                    resetScoreboard(team1Score, team2Score, team1SetsView, team2SetsView);
                    return;
                }
            }
            team1Score.setText(String.valueOf(team1Points));
            team2Score.setText(String.valueOf(team2Points));
            team2SetsView.setText(String.valueOf(team2Sets));
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