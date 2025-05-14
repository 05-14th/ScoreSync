package com.scoresync.scoresync2;

import static com.google.android.material.internal.ContextUtils.getActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.SportViewHolder> {

    private List<Sport> sportList;

    public SportAdapter(List<Sport> sportList) {
        this.sportList = sportList;
    }

    public void setFilteredList(List<Sport> filteredList) {
        this.sportList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sports, parent, false);
        return new SportViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return sportList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SportViewHolder holder, int position) {
        Sport sport = sportList.get(position);
        holder.sportName.setText(sport.getSportName());
        holder.sportIcon.setImageResource(sport.getImageResource());

        holder.openButton.setOnClickListener(view -> {
            Context context = view.getContext();
            String sportName = sport.getSportName().toLowerCase(Locale.ROOT);

            // Modern if-else chain that works on all Android versions
            if ("basketball".equals(sportName)) {
                Intent intent = new Intent(context, Basketball_Scoreboard.class);
                context.startActivity(intent);
            }
            else if ("badminton".equals(sportName)) {
                Intent intent = new Intent(context, Badminton_Scoreboard.class);
                context.startActivity(intent);
            }
            else if ("volleyball".equals(sportName)) {
                Intent intent = new Intent(context, volleyball_scoreboard.class);
                context.startActivity(intent);
            }
            else if ("chess".equals(sportName)) {
                Intent intent = new Intent(context, chess_scoreboard.class);
                context.startActivity(intent);
            }
            else if ("sepak takraw".equals(sportName)) {
                Intent intent = new Intent(context, SepakTakraw.class);
                context.startActivity(intent);
            }
            else {
                Toast.makeText(context,
                        sport.getSportName() + " feature is coming soon!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static class SportViewHolder extends RecyclerView.ViewHolder {
        TextView sportName;
        ImageView sportIcon;
        Button openButton;  // Add this

        public SportViewHolder(@NonNull View itemView) {
            super(itemView);
            sportName = itemView.findViewById(R.id.sport_name);
            sportIcon = itemView.findViewById(R.id.sport_icon);
            openButton = itemView.findViewById(R.id.btn_open_sesame);  // Initialize the button
        }
    }

}

