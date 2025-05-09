package com.scoresync.scoresync2;

import java.io.Serializable;

public class GameHistory implements Serializable {
    private String team1;
    private String team2;
    private String sportType;
    private String date;
    private int team1Score;
    private int team2Score;

    // Constructor
    public GameHistory(String team1, String team2, String sportType, String date,
                       int team1Score, int team2Score) {
        this.team1 = team1;
        this.team2 = team2;
        this.sportType = sportType;
        this.date = date;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
    }

    // Getters
    public String getTeam1() { return team1; }
    public String getTeam2() { return team2; }
    public String getSportType() { return sportType; }
    public String getDate() { return date; }
    public int getTeam1Score() { return team1Score; }
    public int getTeam2Score() { return team2Score; }
}