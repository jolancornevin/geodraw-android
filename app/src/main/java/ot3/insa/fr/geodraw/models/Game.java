package ot3.insa.fr.geodraw.models;

import java.util.Date;

/**
 * Created by Djowood on 27/09/2016.
 */

public class Game {
    private String name;
    private int currentNbPlayer;
    private int maxNbPlayer;
    private Date currentTimeLeft;
    private Date totalTime;
    private String theme;

    public Game() {

    }

    public Game(String name, int currentNbPlayer, int maxNbPlayer, Date currentTimeLeft, Date totalTime, String theme) {
        this.name = name;
        this.currentNbPlayer = currentNbPlayer;
        this.maxNbPlayer = maxNbPlayer;
        this.currentTimeLeft = currentTimeLeft;
        this.totalTime = totalTime;
        this.theme = theme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentNbPlayer() {
        return currentNbPlayer;
    }

    public void setCurrentNbPlayer(int currentNbPlayer) {
        this.currentNbPlayer = currentNbPlayer;
    }

    public int getMaxNbPlayer() {
        return maxNbPlayer;
    }

    public void setMaxNbPlayer(int maxNbPlayer) {
        this.maxNbPlayer = maxNbPlayer;
    }

    public Date getCurrentTimeLeft() {
        return currentTimeLeft;
    }

    public void setCurrentTimeLeft(Date currentTimeLeft) {
        this.currentTimeLeft = currentTimeLeft;
    }

    public Date getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Date totalTime) {
        this.totalTime = totalTime;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
