package ot3.insa.fr.geodraw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Djowood on 27/09/2016.
 */

public class GamesListActivity extends Fragment {
    public enum TypeList {
        ONGOING,
        PERSONNAL
    }

    private ListView mListView;
    private String[] mStrings = {
            "AAAAAAAA", "BBBBBBBB", "CCCCCCCC", "DDDDDDDD", "EEEEEEEE",
            "FFFFFFFF", "GGGGGGGG", "HHHHHHHH", "IIIIIIII", "JJJJJJJJ",
            "KKKKKKKK", "LLLLLLLL", "MMMMMMMM", "NNNNNNNN", "OOOOOOOO",
            "PPPPPPPP", "QQQQQQQQ", "RRRRRRRR", "SSSSSSSS", "TTTTTTTT",
            "UUUUUUUU", "VVVVVVVV", "WWWWWWWW", "XXXXXXXX", "YYYYYYYY",
            "ZZZZZZZZ"
    };
    private TypeList typeList;

    public GamesListActivity() {

    }
    public GamesListActivity(TypeList t) {
        this.typeList = t;
    }

    public static GamesListActivity newInstance(TypeList t) {
        return new GamesListActivity(t);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.on_going_games, container, false);

        mListView = (ListView) rootView.findViewById(R.id.listGames);

        //Création de l'adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_1, mStrings);

        mListView.setAdapter(adapter);

        return rootView;
    }
}
