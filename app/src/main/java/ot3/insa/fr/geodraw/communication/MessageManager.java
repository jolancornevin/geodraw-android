package ot3.insa.fr.geodraw.communication;

import ot3.insa.fr.geodraw.communication.message.AddLatLng;
import ot3.insa.fr.geodraw.communication.message.GameList;
import ot3.insa.fr.geodraw.communication.message.GameListRequest;
import ot3.insa.fr.geodraw.communication.message.GameUpdate;
import ot3.insa.fr.geodraw.communication.message.JoinGame;
import ot3.insa.fr.geodraw.communication.message.JoinedGame;
import ot3.insa.fr.geodraw.communication.message.Message;
import ot3.insa.fr.geodraw.communication.message.NewGame;
import ot3.insa.fr.geodraw.communication.message.TraceMessage;
import ot3.insa.fr.geodraw.communication.message.Vote;

import com.m5c.safesockets.MessageObserver;
import com.m5c.safesockets.SafeSocket;

public class MessageManager implements MessageObserver
{
	private Side side;
	
	public MessageManager(Side s) 
	{
		side = s;
	}
	
	@Override
	public void notifyMessageObserver(SafeSocket sock, String message) 
	{
		Message m = Message.parseMessage(message);
		
		if(m instanceof TraceMessage)
			side.HandleTraceMessage((TraceMessage) m, sock);
		
		else if(m instanceof GameList)
			side.HandleGameList((GameList) m, sock);
		
		else if(m instanceof GameListRequest)
			side.HandleGameListRequest((GameListRequest) m, sock);
		
		else if(m instanceof JoinGame)
			side.HandleJoinGame((JoinGame) m, sock);
		
		else if(m instanceof NewGame)
			side.HandleNewGame((NewGame) m, sock);
		
		else if(m instanceof AddLatLng)
			side.HandleAddLatLng((AddLatLng) m, sock);
		
		else if(m instanceof GameUpdate)
			side.HandleGameUpdate((GameUpdate) m, sock);
		
		else if(m instanceof JoinedGame)
			side.HandleJoinedGame((JoinedGame) m, sock);

		else if(m instanceof Vote)
			side.HandleVote((Vote) m, sock);
		else
		{
			//if there was an error
		}
	}

}
