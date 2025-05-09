package com.scoresync.scoresync2;

public class Sport {

    private String sportName;
    private int imageResource;

    public Sport(String sportName, int imageResource) {
        this.sportName = sportName;
        this.imageResource = imageResource;
    }

    public String getSportName() {
        return sportName;
    }

    public int getImageResource() {
        return imageResource;
    }
}