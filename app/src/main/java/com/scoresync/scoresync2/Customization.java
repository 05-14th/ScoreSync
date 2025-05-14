package com.scoresync.scoresync2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Customization extends AppCompatActivity {

    // basketball settings
    private EditText basketballTime;
    private EditText basketballOvertime;
    private EditText basketballPeriods;

    // Volleyball settings
    private EditText volleyballPointsPerSet;
    private EditText volleyballTieBreaker;
    private EditText volleyballTotalSets;

    // Sepak Takraw settings
    private EditText SepakPointsPerSet;
    private EditText SepakTotalSets;

    // Badminton settings
    private EditText badmintonPointsPerSet;
    private EditText badmintonRoundsPerMatch;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customization);

        // Initialize all views
        initializeViews();

        // Set up all plus/minus button listeners
        setupButtonListeners();

        // Save button listener
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveSettings());


    }


    private void initializeViews() {
        // Basevolban settings
        basketballTime = findViewById(R.id.basketball_time);
        basketballOvertime = findViewById(R.id.basketball_overtime);
        basketballPeriods = findViewById(R.id.basketball_period);

        // Volleyball settings
        volleyballPointsPerSet = findViewById(R.id.volleyball_points_per_set);
        volleyballTieBreaker = findViewById(R.id.volleyball_tie_breaker);
        volleyballTotalSets = findViewById(R.id.volleyball_total_sets);

        // SepakTakraw settings
        SepakPointsPerSet = findViewById(R.id.sepak_points_per_set);
        SepakTotalSets = findViewById(R.id.sepak_total_sets);

        // Badminton settings
        badmintonPointsPerSet = findViewById(R.id.badminton_points_per_set);
        badmintonRoundsPerMatch = findViewById(R.id.badminton_rounds_per_match);
    }

    private void setupButtonListeners() {
        // Basevolban time
        setupPlusMinusButtons(
                findViewById(R.id.basketball_time_minus),
                findViewById(R.id.basketball_time_plus),
                basketballTime
        );

        // Basevolban overtime
        setupPlusMinusButtons(
                findViewById(R.id.basketball_overtime_minus),
                findViewById(R.id.basketball_overtime_plus),
                basketballOvertime
        );

        // Basevolban periods
        setupPlusMinusButtons(
                findViewById(R.id.basketball_periods_minus),
                findViewById(R.id.basketball_periods_plus),
                basketballPeriods
        );

        // Volleyball points per set
        setupPlusMinusButtons(
                findViewById(R.id.volleyball_points_minus),
                findViewById(R.id.volleyball_points_plus),
                volleyballPointsPerSet
        );

        // Volleyball tie breaker
        setupPlusMinusButtons(
                findViewById(R.id.volleyball_tie_breaker_minus),
                findViewById(R.id.volleyball_tie_breaker_plus),
                volleyballTieBreaker
        );

        // Volleyball total sets
        setupPlusMinusButtons(
                findViewById(R.id.volleyball_total_sets_minus),
                findViewById(R.id.volleyball_total_sets_plus),
                volleyballTotalSets
        );

        // Scripta points per set
        setupPlusMinusButtons(
                findViewById(R.id.sepak_points_minus),
                findViewById(R.id.sepak_points_plus),
                SepakPointsPerSet
        );

        // Sepak Takraw total sets
        setupPlusMinusButtons(
                findViewById(R.id.sepak_total_sets_minus),
                findViewById(R.id.sepak_total_sets_plus),
                SepakTotalSets
        );

        // Badminton points per set
        setupPlusMinusButtons(
                findViewById(R.id.badminton_points_minus),
                findViewById(R.id.badminton_points_plus),
                badmintonPointsPerSet
        );

        // Badminton rounds per match
        setupPlusMinusButtons(
                findViewById(R.id.badminton_rounds_minus),
                findViewById(R.id.badminton_rounds_plus),
                badmintonRoundsPerMatch
        );
    }

    private void setupPlusMinusButtons(Button minusButton, Button plusButton, EditText valueEditText) {
        minusButton.setOnClickListener(v -> {
            int currentValue = getCurrentValue(valueEditText);
            if (currentValue > 0) {
                valueEditText.setText(String.valueOf(currentValue - 1));
            }
        });

        plusButton.setOnClickListener(v -> {
            int currentValue = getCurrentValue(valueEditText);
            valueEditText.setText(String.valueOf(currentValue + 1));
        });
    }

    private int getCurrentValue(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void saveSettings() {
        // Get all values
        int basketballTimeValue = getCurrentValue(basketballTime);
        int basketballOvertimeValue = getCurrentValue(basketballOvertime);
        int basketballPeriodsValue = getCurrentValue(basketballPeriods);

        int volleyballPointsPerSetValue = getCurrentValue(volleyballPointsPerSet);
        int volleyballTieBreakerValue = getCurrentValue(volleyballTieBreaker);
        int volleyballTotalSetsValue = getCurrentValue(volleyballTotalSets);

        // Sepak Takraw settings
        int SepakPointsPerSetValue = getCurrentValue(SepakPointsPerSet);
        int SepakTotalSetsValue = getCurrentValue(SepakTotalSets);
        getSharedPreferences("ScoreSyncPrefs", MODE_PRIVATE)
                .edit()
                .putInt("SEPAK_POINTS_PER_SET", SepakPointsPerSetValue)
                .putInt("SEPAK_TOTAL_SETS", SepakTotalSetsValue)
                .apply();


        int badmintonPointsPerSetValue = getCurrentValue(badmintonPointsPerSet);
        int badmintonRoundsPerMatchValue = getCurrentValue(badmintonRoundsPerMatch);

        // Here you would typically save these values to SharedPreferences or database
        // For now we'll just show a toast
        String message = "Settings saved:\n" +
                "Basevolban - Time: " + basketballTimeValue +
                ", Overtime: " + basketballOvertimeValue +
                ", Periods: " + basketballPeriodsValue + "\n" +
                "Volleyball - Points: " + volleyballPointsPerSetValue +
                ", Tie Breaker: " + volleyballTieBreakerValue +
                ", Total Sets: " + volleyballTotalSetsValue + "\n" +
                "Sepak - Points Per Set: " + SepakPointsPerSetValue +
                ", Total Sets: " + SepakTotalSetsValue + "\n" +
                "Badminton - Points Per set: " + badmintonPointsPerSetValue +
                ", Rounds per match: " + badmintonRoundsPerMatchValue;




        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        // Close the activity after saving
        finish();
    }
}