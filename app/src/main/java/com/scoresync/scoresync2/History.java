package com.scoresync.scoresync2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    private RecyclerView rvHistory;
    private TextView tvEmptyHistory;
    private HistoryAdapter adapter;
    private ArrayList<GameHistory> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvHistory = findViewById(R.id.rvHistory);
        tvEmptyHistory = findViewById(R.id.tvEmptyHistory);

        // Setup RecyclerView
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter(historyList, history -> {
            // Handle view click
            // You can implement details viewing here
        });
        rvHistory.setAdapter(adapter);

        // Load saved games
        loadHistory();
    }

    /*private void loadHistory() {
        // Here you would load from SharedPreferences or database
        // This is just sample data
        if (historyList.isEmpty()) {
            tvEmptyHistory.setVisibility(View.VISIBLE);
            rvHistory.setVisibility(View.GONE);
        } else {
            tvEmptyHistory.setVisibility(View.GONE);
            rvHistory.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }*/

    private void loadHistory() {
        SharedPreferences prefs = getSharedPreferences("ScoreSyncPrefs", MODE_PRIVATE);
        String json = prefs.getString("GAME_HISTORY_LIST", "[]");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GameHistory>>(){}.getType();
        List<GameHistory> loadedList = gson.fromJson(json, type);
        historyList.clear();
        if (loadedList != null) historyList.addAll(loadedList);

        if (historyList.isEmpty()) {
            tvEmptyHistory.setVisibility(View.VISIBLE);
            rvHistory.setVisibility(View.GONE);
        } else {
            tvEmptyHistory.setVisibility(View.GONE);
            rvHistory.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }
}