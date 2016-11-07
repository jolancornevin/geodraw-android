package ot3.insa.fr.geodraw;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ot3.insa.fr.geodraw.communication.Client;
import ot3.insa.fr.geodraw.communication.message.JoinGame;
import ot3.insa.fr.geodraw.model.Game;
import ot3.insa.fr.geodraw.model.GameInfo;


/**
 * Created by Djowood on 27/09/2016.
 */

public class GamesListActivity extends Fragment {
    public enum TypeList {
        ONGOING,
        PERSONNAL
    }

    private TypeList typeList;
    private ArrayList<GameInfo> listGames;
    private GameAdapter gameAdapter;

    public GamesListActivity() {

    }

    public GamesListActivity(TypeList t) {
        this.typeList = t;
    }

    public static GamesListActivity newInstance(TypeList t) {
        return new GamesListActivity(t);
    }

    public void addGameToList(GameInfo game) {
        listGames.add(game);
        gameAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_games_list, container, false);

        //Récupération de la liste des personnes
        //TODO à remplacer par les données de la bd
        if (this.typeList == TypeList.ONGOING) {
            listGames = (ArrayList) Game.getMockGamesOnGoing();
        } else if (this.typeList == TypeList.PERSONNAL) {
            listGames = (ArrayList) Game.getMockGamesPersonal();
        }

        //Création et initialisation de l'Adapter pour les personnes
        gameAdapter = new GameAdapter(this.getContext(), listGames);

        //Récupération du composant ListView
        ListView list = (ListView) rootView.findViewById(R.id.listGames);

        //Initialisation de la liste avec les données
        list.setAdapter(gameAdapter);

        return rootView;
    }

    public class GameAdapter extends BaseAdapter {
        // Une liste de games
        private List<GameInfo> mListG;
        // Une liste de games
        private Map<Integer, LinearLayout> mListLayout;
        //Le contexte dans lequel est présent notre gameAdapter
        private Context mContext;
        //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
        private LayoutInflater mInflater;
        //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
        private int _currentSelectedGame = -1;

        public GameAdapter(Context context, List<GameInfo> aListG) {
            mContext = context;
            mListG = aListG;
            mInflater = LayoutInflater.from(mContext);

            mListLayout = new HashMap<>();
        }

        public View getView(final int position, View convertView, final ViewGroup parentView) {
            String timeLeft, timeTotal;

            LinearLayout layoutItem;

            //(1) : Réutilisation des layouts
            if (convertView == null) {
                //Initialisation de notre item à partir du  layout XML "personne_layout.xml"
                layoutItem = (LinearLayout) mInflater.inflate(R.layout.item_list_game, parentView, false);
            } else {
                layoutItem = (LinearLayout) convertView;
            }

            mListLayout.put(position, layoutItem);

            //TODO remplacer par un meilleurs design
            layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LinearLayout _layoutItem;
                    FloatingActionButton btn_join, btn_edit, btn_like;
                    if (_currentSelectedGame != -1) {
                        _layoutItem = mListLayout.get(_currentSelectedGame);

                        btn_join = (FloatingActionButton) _layoutItem.findViewById(R.id.btn_join);
                        btn_edit = (FloatingActionButton) _layoutItem.findViewById(R.id.btn_edit);
                        btn_like = (FloatingActionButton) _layoutItem.findViewById(R.id.btn_like);

                        btn_join.setVisibility(View.GONE);
                        btn_edit.setVisibility(View.GONE);
                        btn_like.setVisibility(View.GONE);
                    }

                    _currentSelectedGame = position;

                    _layoutItem = mListLayout.get(_currentSelectedGame);

                    btn_join = (FloatingActionButton) _layoutItem.findViewById(R.id.btn_join);
                    btn_edit = (FloatingActionButton) _layoutItem.findViewById(R.id.btn_edit);
                    btn_like = (FloatingActionButton) _layoutItem.findViewById(R.id.btn_like);

                    btn_join.setVisibility(View.VISIBLE);
                    btn_edit.setVisibility(View.VISIBLE);
                    btn_like.setVisibility(View.VISIBLE);
                }
            });

            //(2) : Récupération des TextView de notre layout
            TextView game_name = (TextView) layoutItem.findViewById(R.id.gameName);
            TextView game_nb_players = (TextView) layoutItem.findViewById(R.id.gameNbPlayers);
            TextView game_time = (TextView) layoutItem.findViewById(R.id.gameTime);
            TextView game_theme = (TextView) layoutItem.findViewById(R.id.gameTheme);

            //(3) : Renseignement des valeurs
            GameInfo game = mListG.get(position);
            game_name.setText(game.getName());
            game_nb_players.setText(game.getCurrentNbPlayer() + " / " + game.getMaxNbPlayer());

            //TODO
            timeTotal = game.getMaxHours() + "h " + game.getMaxMinutes() + "m";
            timeLeft = game.getRemainingHours() + "h " + game.getRemainingMinutes() + "m";
            game_time.setText(timeLeft + " / " + timeTotal);
            game_theme.setText(game.getTheme());

            FloatingActionButton btn_join = (FloatingActionButton) layoutItem.findViewById(R.id.btn_join);
            FloatingActionButton btn_edit = (FloatingActionButton) layoutItem.findViewById(R.id.btn_edit);
            FloatingActionButton btn_like = (FloatingActionButton) layoutItem.findViewById(R.id.btn_like);

            btn_join.setImageResource(R.drawable.launch_white_24dp);
            btn_edit.setImageResource(R.drawable.edit_white_24dp);
            btn_like.setImageResource(R.drawable.star_white_24dp);
            //TODO : GAME ID ET PLAYER ID STATIQUE
            btn_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Client.theClient.sendMessage(new JoinGame("kaka", 0));
                    Intent intent = new Intent(parentView.getContext(), MapsActivity.class);
                    startActivity(intent);

                }
            });

            //On retourne l'item créé.
            return layoutItem;
        }

        public int getCount() {
            return mListG.size();
        }

        public Object getItem(int position) {

            return mListG.get(position);
        }

        public long getItemId(int position) {
            return position;
        }
    }
}
