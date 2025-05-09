package com.scoresync.scoresync2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;



public class SimpleScoreboard extends AppCompatActivity {

    private int team1Score = 0;
    private int team2Score = 0;
    private TextView team1ScoreView;
    private TextView team2ScoreView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_simple_scoreboard);

        // Initialize views
        team1ScoreView = findViewById(R.id.team1Score);
        team2ScoreView = findViewById(R.id.team2Score);
        ImageButton menuButton = findViewById(R.id.menuButton);
        Button team1PlusButton = findViewById(R.id.team1PlusButton);
        Button team1MinusButton = findViewById(R.id.team1MinusButton);
        Button team2PlusButton = findViewById(R.id.team2PlusButton);
        Button team2MinusButton = findViewById(R.id.team2MinusButton);

        // Team 1 button click listeners
        team1PlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team1Score++;
                updateTeam1Score();
            }
        });

        team1MinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team1Score--;
                updateTeam1Score();
            }
        });

        // Team 2 button click listeners
        team2PlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team2Score++;
                updateTeam2Score();
            }
        });

        team2MinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                team2Score--;
                updateTeam2Score();
            }
        });

        // Menu button click listener
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your menu functionality here
                // Example: open a menu or show a Toast
                // Toast.makeText(MainActivity.this, "Menu clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTeam1Score() {
        team1ScoreView.setText(String.valueOf(team1Score));
    }

    private void updateTeam2Score() {
        team2ScoreView.setText(String.valueOf(team2Score));
    }
}