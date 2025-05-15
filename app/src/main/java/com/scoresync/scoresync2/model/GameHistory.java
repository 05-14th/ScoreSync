package com.scoresync.scoresync2.model;

public class GameHistory {
    private String team1Name;
    private String team2Name;
    private int team1Score;
    private int team2Score;
    private int team1Sets;
    private int team2Sets;
    private String sportType;
    private String date;
    private String winner;

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public int getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(int team1Score) {
        this.team1Score = team1Score;
    }

    public int getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(int team2Score) {
        this.team2Score = team2Score;
    }

    public int getTeam1Sets() {
        return team1Sets;
    }

    public void setTeam1Sets(int team1Sets) {
        this.team1Sets = team1Sets;
    }

    public int getTeam2Sets() {
        return team2Sets;
    }

    public void setTeam2Sets(int team2Sets) {
        this.team2Sets = team2Sets;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}