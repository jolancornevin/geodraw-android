package ot3.insa.fr.geodraw.communication.message;

import java.util.Collection;

import ot3.insa.fr.geodraw.model.Game;

public class GameList extends Message {

	private final Collection<Game> games;
	

	public GameList(Collection<Game> games) 
	{
		super(Type.GAMELIST);
		this.games = games;
	}

	public Collection<Game> getGames() {
		return games;
	}
}
