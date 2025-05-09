package com.scoresync.scoresync2.model;

public class Player {
    private String name;
    private String team;
    private String coach;
    private int fouls;

    // Required no-argument constructor for Firestore
    public Player() {
        // Empty constructor needed for Firestore
    }

    // Constructor for creating players without coach (keeping your existing constructor)
    public Player(String name, String team, int fouls) {
        this.name = name;
        this.team = team;
        this.fouls = fouls;
    }

    // Constructor with all fields (keeping your existing constructor)
    public Player(String name, String team, String coach, int fouls) {
        this.name = name;
        this.team = team;
        this.coach = coach;
        this.fouls = fouls;
    }

    // Getters (keeping all your existing getters)
    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public String getCoach() {
        return coach;
    }

    public int getFouls() {
        return fouls;
    }

    // Setters (keeping all your existing setters)
    public void setName(String name) {
        this.name = name;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public void setFouls(int fouls) {
        this.fouls = fouls;
    }
}