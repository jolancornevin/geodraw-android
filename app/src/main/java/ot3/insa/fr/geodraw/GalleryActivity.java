package ot3.insa.fr.geodraw;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ot3.insa.fr.geodraw.model.Gallery;

/**
 * Created by Djowood on 27/09/2016.
 */

public class GalleryActivity extends Fragment {

    private ArrayList<Gallery> listGallery;
    private GalleryAdapter galleryAdapter;

    public GalleryActivity() {

    }

    public static GalleryActivity newInstance() {
        return new GalleryActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_gallery, container, false);

        //Récupération de la liste des personnes
        //TODO à remplacer par les données de la bd
        listGallery = (ArrayList) Gallery.getMockGalleries();

        //Création et initialisation de l'Adapter pour les personnes
        galleryAdapter = new GalleryAdapter(this.getContext(), listGallery);

        //Récupération du composant ListView
        ListView list = (ListView) rootView.findViewById(R.id.listGallery);

        //Initialisation de la liste avec les données
        list.setAdapter(galleryAdapter);

        return rootView;
    }


    public class GalleryAdapter extends BaseAdapter {
        // Une liste de games
        private List<Gallery> mListGallery;
        //Le contexte dans lequel est présent notre gameAdapter
        private Context mContext;
        //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
        private LayoutInflater mInflater;

        public GalleryAdapter(Context context, List<Gallery> aListG) {
            mContext = context;
            mListGallery = aListG;
            mInflater = LayoutInflater.from(mContext);
        }

        public View getView(final int position, View convertView, final ViewGroup parentView) {
            LinearLayout layoutItem;

            //(1) : Réutilisation des layouts
            if (convertView == null) {
                //Initialisation de notre item à partir du  layout XML "personne_layout.xml"
                layoutItem = (LinearLayout) mInflater.inflate(R.layout.item_list_gallery, parentView, false);
            } else {
                layoutItem = (LinearLayout) convertView;
            }

            //(2) : Récupération des TextView de notre layout
            TextView game_name = (TextView) layoutItem.findViewById(R.id.galleryName);

            //(3) : Renseignement des valeurs
            Gallery gallery = mListGallery.get(position);

            game_name.setText(gallery.getName());

            //On retourne l'item créé.
            return layoutItem;
        }

        public int getCount() {
            return mListGallery.size();
        }

        public Object getItem(int position) {

            return mListGallery.get(position);
        }

        public long getItemId(int position) {
            return position;
        }
    }
}