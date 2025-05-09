package com.scoresync.scoresync2.model;

public class Coach {
    private String coachID;
    private String coachName;

    public Coach() {}

    public Coach(String coachID, String coachName) {
        this.coachID = coachID;
        this.coachName = coachName;
    }

    public String getCoachID() { return coachID; }
    public void setCoachID(String coachID) { this.coachID = coachID; }

    public String getCoachName() { return coachName; }
    public void setCoachName(String coachName) { this.coachName = coachName; }
}
