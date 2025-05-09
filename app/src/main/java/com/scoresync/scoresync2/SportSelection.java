package com.scoresync.scoresync2;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SportSelection extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Sport> itemList;
    private SportAdapter sportAdapter;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sport_selection);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();

        // Sample data
        itemList.add(new Sport("Basketball", R.drawable.basket1));
        itemList.add(new Sport("Volleyball", R.drawable.ball));
        itemList.add(new Sport("Sepak Takraw", R.drawable.sepak6));
        itemList.add(new Sport("Badminton", R.drawable.badminton3));
        itemList.add(new Sport("Chess", R.drawable.chess2));

        sportAdapter = new SportAdapter(itemList);
        recyclerView.setAdapter(sportAdapter);
    }

    private void filterList(String newText) {
        List<Sport> filteredList = new ArrayList<>();
        for (Sport sport : itemList) {
            if (sport.getSportName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(sport);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No sports found", Toast.LENGTH_SHORT).show();
        } else {
            sportAdapter.setFilteredList(filteredList);
        }
    }

}
