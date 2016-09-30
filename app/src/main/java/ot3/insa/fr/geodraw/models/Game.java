package ot3.insa.fr.geodraw.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Djowood on 27/09/2016.
 */

public class Game {
    private int id;
    private String name;
    private Boolean lock;
    private int currentNbPlayer;
    private int maxNbPlayer;
    private Date startDate;
    private Date endDate;
    private String theme;

    public Game(String name, Boolean lock, int currentNbPlayer, int maxNbPlayer, Date startDate, Date endDate, String theme) {
        this.name = name;
        this.lock = lock;
        this.currentNbPlayer = currentNbPlayer;
        this.maxNbPlayer = maxNbPlayer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.theme = theme;
    }

    public static ArrayList<Game> getMockGames() {
        ArrayList<Game> listGames = new ArrayList<>();

        listGames.add(new Game("Partie 1", false, 10, 20, new Date(2016, 01, 27, 00, 18, 00),
                new Date(2016, 01, 27, 01, 00, 00), "Avion"));
        listGames.add(new Game("Partie 2", false, 5, 5, new Date(2016, 01, 27, 5, 32, 00),
                new Date(2016, 01, 27, 24, 00, 00), "Voiture"));
        listGames.add(new Game("Partie 3", true, 5, 10, new Date(2016, 01, 27, 5, 32, 00),
                new Date(2016, 01, 27, 24, 00, 00), ""));
        listGames.add(new Game("Partie 4", true, 8, 20, new Date(2016, 01, 27, 5, 32, 00),
                new Date(2016, 01, 27, 24, 00, 00), "Guerre"));

        return listGames;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
