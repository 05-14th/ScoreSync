package com.scoresync.scoresync2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity extends AppCompatActivity {



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int item_id = item.getItemId();

        if (item_id == R.id.android) {
            Toast.makeText(this, "This is android options item", Toast.LENGTH_SHORT).show();
        }else if (item_id == R.id.profile) {
            Toast.makeText(this, "This is profile item", Toast.LENGTH_SHORT).show();
        }else if (item_id == R.id.download) {
            Toast.makeText(this, "This is download item", Toast.LENGTH_SHORT).show();
        }else if (item_id == R.id.setting) {
            Toast.makeText(this, "This is setting item", Toast.LENGTH_SHORT).show();
        }else if (item_id == R.id.exit) {
            Toast.makeText(this, "This is exit item", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        // Find the Toolbar and set it as the app bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }



    // Start activities when buttons are clicked
    public void LoadSportClick(View v) {
       startActivity(new Intent(this, SportSelection.class));
    }

    public void LoadHistoryClick(View v) {
        // Replace with the correct class
        startActivity(new Intent(this, History.class));
    }

    public void LoadCustomClick(View v) {
        startActivity(new Intent(this, Custimization.class));
    }

    public void LoadBoardClick(View v) {
        startActivity(new Intent(this, SimpleScoreboard.class));
    }

    public void LoadSettingClick(View v) {
        startActivity(new Intent(this, Settings.class));
    }

}