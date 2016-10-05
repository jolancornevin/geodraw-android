package ot3.insa.fr.geodraw.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Djowood on 27/09/2016.
 */

public class Game {
	
    private final int id;
    private final String name;
    private final boolean lock;
    private final int maxNbPlayer;
    private final Date startDate;
    private final Date endDate;
    private final String theme;

    private final Map<String, Integer> players;
    private final Map<String, Drawing> traces;
    
    private int currentNbPlayer;

    public Game(int id, String name, Boolean lock, int currentNbPlayer, int maxNbPlayer, Date startDate, Date endDate, String theme) {
        this.name = name;
        this.lock = lock;
        this.currentNbPlayer = currentNbPlayer;
        this.maxNbPlayer = maxNbPlayer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.theme = theme;
        
        this.id = id;
        
        players = new HashMap<String, Integer>();
        traces = new HashMap<String, Drawing>();
    }

    public int getId() {
        return id;
    }

    public Boolean getLock() {
        return lock;
    }

    public static ArrayList<Game> getMockGamesOnGoing() {
        ArrayList<Game> listGames = new ArrayList<>();

        listGames.add(new Game(0,"Partie 1", false, 10, 20, new Date(2016, 01, 27, 00, 18, 00),
                new Date(2016, 01, 27, 01, 00, 00), "Avion"));
        listGames.add(new Game(1,"Partie 2", false, 5, 5, new Date(2016, 01, 27, 5, 32, 00),
                new Date(2016, 01, 27, 24, 00, 00), "Voiture"));
        listGames.add(new Game(2,"Partie 3", true, 5, 10, new Date(2016, 01, 27, 5, 32, 00),
                new Date(2016, 01, 27, 24, 00, 00), ""));
        listGames.add(new Game(3,"Partie 4", true, 8, 20, new Date(2016, 01, 27, 5, 32, 00),
                new Date(2016, 01, 27, 24, 00, 00), "Guerre"));

        return listGames;
    }

    public static ArrayList<Game> getMockGamesPersonal() {
        ArrayList<Game> listGames = new ArrayList<>();

        listGames.add(new Game(0,"Partie 1", false, 10, 20, new Date(2016, 01, 27, 00, 18, 00),
                new Date(2016, 01, 27, 01, 00, 00), "Avion"));
        listGames.add(new Game(1,"Partie 2", false, 5, 5, new Date(2016, 01, 27, 5, 32, 00),
                new Date(2016, 01, 27, 24, 00, 00), "Voiture"));

        return listGames;
    }

    public String getName() {
        return name;
    }

    public int getCurrentNbPlayer() {
        return currentNbPlayer;
    }

    /**
     * Adds a player to the current game
     * @param playerID : The player's ID (pseudo)
     * @return true if the player has been successfully added, false otherwise
     */
    public boolean addPlayer(String playerID) {
        if(currentNbPlayer < maxNbPlayer)
        {
        	if(players.containsKey(playerID))
        		return true;
        	players.put(playerID, 0);
        	currentNbPlayer++;
        	return true;
        }
        return false;
    }
    
    public void removePlayer(String playerID) {
    	if(players.containsKey(playerID))
    	{
    		players.remove(playerID);
    		currentNbPlayer--;
    	}
    }

    public int getMaxNbPlayer() {
        return maxNbPlayer;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getTheme() {
        return theme;
    }
    
    public Drawing getTrace(String playerID)
    {
    	if(traces.containsKey(playerID))
    		return traces.get(playerID);
    	
    	return null;
    }
    
    public void updateTrace(String playerID, Drawing newTrace){
    	traces.put(playerID, newTrace);
    }
}
