package com.scoresync.scoresync2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

    private void loadHistory() {
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
    }
}