package ot3.insa.fr.geodraw.communication;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.m5c.safesockets.SafeSocket;

import ot3.insa.fr.geodraw.MapsActivity;
import ot3.insa.fr.geodraw.communication.message.AddLatLng;
import ot3.insa.fr.geodraw.communication.message.GameList;
import ot3.insa.fr.geodraw.communication.message.GameListRequest;
import ot3.insa.fr.geodraw.communication.message.GameUpdate;
import ot3.insa.fr.geodraw.communication.message.JoinGame;
import ot3.insa.fr.geodraw.communication.message.JoinedGame;
import ot3.insa.fr.geodraw.communication.message.NewGame;
import ot3.insa.fr.geodraw.communication.message.NewUser;
import ot3.insa.fr.geodraw.communication.message.TraceMessage;
import ot3.insa.fr.geodraw.communication.message.Vote;
import ot3.insa.fr.geodraw.utils.Utils;

/**
 * Created by arda on 05/10/16.
 */

public abstract class ClientListener {
    /*
    private Handler mHandler;

    public ClientListener(){
        mHandler = new Handler(Looper.getMainLooper()) {

            @Override
            public void handleMessage(Message inputMessage) {
                //Depending on the type of message, call a method
            }
        };

    } */
    void HandleTraceMessage(TraceMessage m, SafeSocket sender) {

    }

    void HandleGameList(GameList m, SafeSocket sender) {
        // TODO Auto-generated method stub

    }


    void HandleJoinedGame(JoinedGame m, SafeSocket sender) {
        // TODO Auto-generated method stub

    }


    void HandleAddLatLng(AddLatLng m, SafeSocket sender) {
        // TODO Auto-generated method stub

    }


    void HandleGameUpdate(GameUpdate m, SafeSocket sender) {
        // TODO Auto-generated method stub

    }

    void HandleVote(Vote m, SafeSocket sender) {
        // TODO Auto-generated method stub

    }

    void HandleNewUser(NewUser m, SafeSocket sender){

    }


}
