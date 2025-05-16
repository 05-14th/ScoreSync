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
    private List<GameHistory> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rvHistory = findViewById(R.id.rvHistory);
        tvEmptyHistory = findViewById(R.id.tvEmptyHistory);

        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter(historyList, history -> {
            // Handle view click if needed
        });
        rvHistory.setAdapter(adapter);

        loadHistory();
    }

    private void loadHistory() {
        SharedPreferences prefs = getSharedPreferences("ScoreSyncPrefs", MODE_PRIVATE);
        String json = prefs.getString("GAME_HISTORY_LIST", "[]");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<GameHistory>>(){}.getType();
        List<GameHistory> loadedList = gson.fromJson(json, type);
        historyList.clear();
        if (loadedList != null) {
            // Ensure no null/empty team names
            for (GameHistory history : loadedList) {
                if (history.getTeam1() == null || history.getTeam1().trim().isEmpty()) {
                    history.setTeam1("Team 1");
                }
                if (history.getTeam2() == null || history.getTeam2().trim().isEmpty()) {
                    history.setTeam2("Team 2");
                }
            }
            historyList.addAll(loadedList);
        }

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