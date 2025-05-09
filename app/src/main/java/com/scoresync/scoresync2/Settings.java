package com.scoresync.scoresync2;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    private int text1Color = Color.RED;
    private int text2Color = Color.BLACK;
    private int text3Color = Color.GREEN;
    private int bgColor = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView tvTime = findViewById(R.id.tvCurrentTime);
        tvTime.setText(java.text.DateFormat.getTimeInstance().format(new java.util.Date()));

        setupColorPicker(R.id.colorText1, R.id.btnPickColor1, text1Color, color -> {
            text1Color = color;
            findViewById(R.id.colorText1).setBackgroundColor(color);
        });

        setupColorPicker(R.id.colorText2, R.id.btnPickColor2, text2Color, color -> {
            text2Color = color;
            findViewById(R.id.colorText2).setBackgroundColor(color);
        });

        setupColorPicker(R.id.colorText3, R.id.btnPickColor3, text3Color, color -> {
            text3Color = color;
            findViewById(R.id.colorText3).setBackgroundColor(color);
        });

        setupColorPicker(R.id.colorBackground, R.id.btnPickBgColor, bgColor, color -> {
            bgColor = color;
            findViewById(R.id.colorBackground).setBackgroundColor(color);

            // Save to SharedPreferences
            getSharedPreferences("AppPrefs", MODE_PRIVATE)
                    .edit()
                    .putInt("bgColor", color)
                    .apply();
        });


        Spinner whistleSpinner = findViewById(R.id.spinnerWhistle);
        Spinner buzzerSpinner = findViewById(R.id.spinnerBuzzer);

        whistleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Settings.this, "Whistle: " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        buzzerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Settings.this, "Buzzer: " + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private interface ColorListener {
        void onColorPicked(int color);
    }

    private void setupColorPicker(int colorViewId, int buttonId, int defaultColor, ColorListener listener) {
        View colorView = findViewById(colorViewId);
        colorView.setBackgroundColor(defaultColor);

        ImageButton pickerButton = findViewById(buttonId);
        pickerButton.setOnClickListener(v -> showColorPicker(defaultColor, listener));
    }

    private void showColorPicker(int initialColor, ColorListener listener) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_color_picker, null);

        SeekBar redSeek = dialogView.findViewById(R.id.seekRed);
        SeekBar greenSeek = dialogView.findViewById(R.id.seekGreen);
        SeekBar blueSeek = dialogView.findViewById(R.id.seekBlue);
        SeekBar alphaSeek = dialogView.findViewById(R.id.seekAlpha);
        View preview = dialogView.findViewById(R.id.colorPreview);

        int r = Color.red(initialColor);
        int g = Color.green(initialColor);
        int b = Color.blue(initialColor);
        int a = Color.alpha(initialColor);

        redSeek.setProgress(r);
        greenSeek.setProgress(g);
        blueSeek.setProgress(b);
        alphaSeek.setProgress(a);
        preview.setBackgroundColor(initialColor);

        SeekBar.OnSeekBarChangeListener changeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int color = Color.argb(alphaSeek.getProgress(), redSeek.getProgress(), greenSeek.getProgress(), blueSeek.getProgress());
                preview.setBackgroundColor(color);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        };

        redSeek.setOnSeekBarChangeListener(changeListener);
        greenSeek.setOnSeekBarChangeListener(changeListener);
        blueSeek.setOnSeekBarChangeListener(changeListener);
        alphaSeek.setOnSeekBarChangeListener(changeListener);

        new AlertDialog.Builder(this)
                .setTitle("Choose Color")
                .setView(dialogView)
                .setPositiveButton("OK", (dialog, which) -> {
                    int color = Color.argb(alphaSeek.getProgress(), redSeek.getProgress(), greenSeek.getProgress(), blueSeek.getProgress());
                    listener.onColorPicked(color);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
