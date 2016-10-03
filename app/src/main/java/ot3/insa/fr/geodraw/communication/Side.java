package ot3.insa.fr.geodraw.communication;

import java.util.Collection;
import java.util.LinkedList;

import ot3.insa.fr.geodraw.communication.message.AddLatLng;
import ot3.insa.fr.geodraw.communication.message.GameList;
import ot3.insa.fr.geodraw.communication.message.GameListRequest;
import ot3.insa.fr.geodraw.communication.message.GameUpdate;
import ot3.insa.fr.geodraw.communication.message.JoinGame;
import ot3.insa.fr.geodraw.communication.message.JoinedGame;
import ot3.insa.fr.geodraw.communication.message.NewGame;
import ot3.insa.fr.geodraw.communication.message.TraceMessage;
import ot3.insa.fr.geodraw.communication.message.Vote;

import com.m5c.safesockets.BreakdownObserver;
import com.m5c.safesockets.MessageObserver;
import com.m5c.safesockets.SafeSocket;


public abstract class Side 
{
	protected transient Collection<MessageObserver> mess;
	protected transient Collection<BreakdownObserver> breakdown;
	
	protected transient static int HEART_BEAT_RATE = 2000;
	protected transient static int TIMEOUT = 500;
	
	public Side()
	{
		mess = new LinkedList<MessageObserver>();
		breakdown = new LinkedList<BreakdownObserver>();
		mess.add(new MessageManager(this));
	}
	
	abstract void HandleTraceMessage(TraceMessage m, SafeSocket sender);
	
	abstract void HandleAddLatLng(AddLatLng m, SafeSocket sender);

	abstract void HandleGameList(GameList m, SafeSocket sender);

	abstract void HandleGameUpdate(GameUpdate m, SafeSocket sender);

	abstract void HandleGameListRequest(GameListRequest m, SafeSocket sender);

	abstract void HandleJoinGame(JoinGame m, SafeSocket sender);

	abstract void HandleJoinedGame(JoinedGame m, SafeSocket sender);
	
	abstract void HandleNewGame(NewGame m, SafeSocket sender);
	
	abstract void HandleVote(Vote m, SafeSocket sender);
	
}
