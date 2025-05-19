package com.scoresync.scoresync2;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class volleyball_scoreboard extends AppCompatActivity {

    // UI Elements
    private TextView team1ScoreDisplay, team2ScoreDisplay;
    private TextView roundCounter, team1Label, team2Label;
    private Button minusteam1, minusteam2, plusteam1, plusteam2;
    private ImageButton shuffleButton;

    // Score and round tracking
    private int team1Score = 0;
    private int team2Score = 0;
    private int round = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_volleyball_scoreboard);

        // Force landscape mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Link UI elements
        team1ScoreDisplay = findViewById(R.id.team1_score);
        team2ScoreDisplay = findViewById(R.id.team2_score);
        roundCounter = findViewById(R.id.round_counter);
        minusteam1 = findViewById(R.id.minus_team1v);
        minusteam2 = findViewById(R.id.minus_team2v);
        plusteam1 = findViewById(R.id.plus_team1);
        plusteam2 = findViewById(R.id.plus_team2);
        shuffleButton = findViewById(R.id.shuffle_button);
        team1Label = findViewById(R.id.team1_name);
        team2Label = findViewById(R.id.team2_name);

        // Initial UI update
        updateScores();
        updateRound();

        // Button listeners
        plusteam1.setOnClickListener(v -> {
            team1Score++;
            updateScores();
        });

        minusteam1.setOnClickListener(v -> {
            if (team1Score > 0) team1Score--;
            updateScores();
        });

        plusteam2.setOnClickListener(v -> {
            team2Score++;
            updateScores();
        });

        minusteam2.setOnClickListener(v -> {
            if (team2Score > 0) team2Score--;
            updateScores();
        });

        shuffleButton.setOnClickListener(v -> switchSides());
    }

    // Update score display
    private void updateScores() {
        team1ScoreDisplay.setText(String.valueOf(team1Score));
        team2ScoreDisplay.setText(String.valueOf(team2Score));
    }

    // Update round display
    private void updateRound() {
        roundCounter.setText(Integer.toString(round));
    }

    // Swap team sides and scores visually (not logically)
    private void switchSides() {
        // Swap scores visually
        int tempScore = team1Score;
        team1Score = team2Score;
        team2Score = tempScore;

        // Swap labels visually
        CharSequence tempLabel = team1Label.getText();
        team1Label.setText(team2Label.getText());
        team2Label.setText(tempLabel);

        updateScores();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Reset to portrait when exiting the activity
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
