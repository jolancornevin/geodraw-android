package ot3.insa.fr.geodraw.communication.message;

import java.util.Collection;

import ot3.insa.fr.geodraw.model.GameInfo;

public class GameList extends Message {

	private final List<GameInfo> games;
	

	public GameList(List<GameInfo> games) 
	{
		super(Type.GAMELIST);
		this.games = games;
	}

	public List<GameInfo> getGames() {
		return games;
	}
}
