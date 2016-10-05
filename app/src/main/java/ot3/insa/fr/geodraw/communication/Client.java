package ot3.insa.fr.geodraw.communication;

import com.m5c.safesockets.BreakdownObserver;
import com.m5c.safesockets.SafeSocket;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

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
import ot3.insa.fr.geodraw.utils.Utils;

public class Client extends Side
{
	private SafeSocket socket;
	private final int port;
	private final String ip;

	private List<ClientListener> listeners;
	private ConcurrentLinkedQueue<String> messagePool; 

	public static Client theClient;

	private Thread connector;
	private Thread msgSender;
	
	private boolean isStopped;

	static {
		theClient = new Client("localhost",8080);

	}


	public Client(final String ip, final int port)
	{
		super();
		this.port = port;
		this.ip = ip;

		this.isStopped = false;

		breakdown.add(new ClientBreak());
		
		messagePool = new ConcurrentLinkedQueue<String>();

		connect();

		msgSender = new Thread("Message sender") {
			
			@Override
			public void run() {
				String msg;
				boolean sent = true;
				
				while(!isStopped){
					
					if(sent){
						sent = false;
						msg = messagePool.poll();
					}
					
					if(msg == null)
					{
						sent = true;
						Thread.sleep(500);
						continue;
					}
					
					try {
						if(socket != null)
							if(socket.sendMessage(msg))
								sent = true;
					}
				}
			}
		};
		
		msgSender.start();
		
	}
	

	public void addListener(ClientListener cl){
		listeners.add(cl);
	}


	public void removeListener(ClientListener cl) {
		listeners.remove(cl);
	}

	public boolean sendMessage(Message m)
	{
		String jsonstr = Utils.gson.toJson(m);
		
		messagePool.offer(jsonstr);
		
		return true;
		/*if(socket == null)
			System.err.println("Error null socket!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return socket.sendMessage(jsonstr);*/
	}
	
	private boolean sendMsg(String msg)
	{
		return socket.sendMessage(msg);
	}
	
	public void disconnect()
	{
		isStopped = true;
		if(socket != null)
			socket.disconnect();
	}
	
	private void connect()
	{
		connector = new Thread("Connector") {
			public void run(){
				socket = null;
				while (socket == null && !isStopped)
					try {
						socket = new SafeSocket(ip, port, HEART_BEAT_RATE, TIMEOUT, mess, breakdown);
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		};
		connector.start();
	}
	
	private void reconnect()
	{
		connect();
	}
	
	class ClientBreak implements BreakdownObserver
	{

		@Override
		public void notifyBreakdownObserver(SafeSocket arg0, boolean intended) {
			if(!intended)
				reconnect();
		}
		
	}

	@Override
	void HandleTraceMessage(TraceMessage m, SafeSocket sender) {
		//System.out.println(Utils.gson.toJson(m.getTrace()));
		//Utils.gson.toJson(m.getTrace());

		for(ClientListener cl : listeners) {
			cl.HandleTraceMessage(m, sender);
		}
	}

	@Override
	void HandleGameList(GameList m, SafeSocket sender) {
		// TODO Auto-generated method stub
		for(ClientListener cl : listeners) {
			cl.HandleGameList(m, sender);
		}
	}


	/** Server method*/
	@Override
	void HandleGameListRequest(GameListRequest m, SafeSocket sender) {return;}


	/** Server method*/
	@Override
	void HandleJoinGame(JoinGame m, SafeSocket sender) {return;}


	@Override
	void HandleJoinedGame(JoinedGame m, SafeSocket sender) {
		// TODO Auto-generated method stub
		for(ClientListener cl : listeners) {
			cl.HandleJoinedGame(m, sender);
		}
	}

	/** Server method*/
	@Override
	void HandleNewGame(NewGame m, SafeSocket sender) {return;}



	@Override
	void HandleAddLatLng(AddLatLng m, SafeSocket sender) {
		// TODO Auto-generated method stub
		for(ClientListener cl : listeners) {
			cl.HandleAddLatLng(m, sender);
		}
	}



	@Override
	void HandleGameUpdate(GameUpdate m, SafeSocket sender) {
		// TODO Auto-generated method stub
		for(ClientListener cl : listeners) {
			cl.HandleGameUpdate(m, sender);
		}
	}



	@Override
	void HandleVote(Vote m, SafeSocket sender) {
		// TODO Auto-generated method stub
		for(ClientListener cl : listeners) {
			cl.HandleVote(m, sender);
		}
	}


	
}
