package com.scoresync.scoresync2.model;

public class Team {
    private String teamID;
    private String teamName;
    private String coachID;

    public Team() {}

    public Team(String teamID, String teamName, String coachID) {
        this.teamID = teamID;
        this.teamName = teamName;
        this.coachID = coachID;
    }

    public String getTeamID() { return teamID; }
    public void setTeamID(String teamID) { this.teamID = teamID; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public String getCoachID() { return coachID; }
    public void setCoachID(String coachID) { this.coachID = coachID; }
}
