package com.scoresync.scoresync2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<GameHistory> historyList;
    private OnHistoryClickListener listener;

    public interface OnHistoryClickListener {
        void onViewClick(GameHistory history);
    }

    public HistoryAdapter(List<GameHistory> historyList, OnHistoryClickListener listener) {
        this.historyList = historyList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        GameHistory history = historyList.get(position);

        holder.tvMatchup.setText(history.getTeam1() + " VS " + history.getTeam2());
        holder.tvSportType.setText(history.getSportType());
        holder.tvDate.setText("Date: " + history.getDate());
        holder.tvTeam1Score.setText(String.valueOf(history.getTeam1Score()));
        holder.tvTeam2Score.setText(String.valueOf(history.getTeam2Score()));

        holder.btnView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewClick(history);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvMatchup, tvSportType, tvDate, tvTeam1Score, tvTeam2Score;
        Button btnView;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMatchup = itemView.findViewById(R.id.tvMatchup);
            tvSportType = itemView.findViewById(R.id.tvSportType);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTeam1Score = itemView.findViewById(R.id.tvTeam1Score);
            tvTeam2Score = itemView.findViewById(R.id.tvTeam2Score);
            btnView = itemView.findViewById(R.id.btnView);
        }
    }
}