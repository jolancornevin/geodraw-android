package ot3.insa.fr.geodraw;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ot3.insa.fr.geodraw.model.Drawing;
import ot3.insa.fr.geodraw.model.Gallery;
import ot3.insa.fr.geodraw.model.Segment;
import ot3.insa.fr.geodraw.utils.lazylist.ImageLoader;
import ot3.insa.fr.geodraw.utils.lazylist.Utils;

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


    public class GalleryAdapter extends BaseAdapter implements OnMapReadyCallback {
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

            Gallery gallery = mListGallery.get(position);

            Point size = getScreenSize();
            int width = size.x;

            /*String url = "https://maps.googleapis.com/maps/api/staticmap?" +
                    "size=" + width + "x300" +
                    "&scale=1" +
                    "&path=color:red|weight:5|45.777460,4.845140|45.772430,4.855100|45.785600,4.858020|45.777460,4.845140" +
                    "&path=color:blue|weight:5|45.774800,4.849990|45.778030,4.855570|45.780180,4.849690|45.774800,4.849990" +
                    "&key=" + getResources().getString(R.string.google_maps_key_static);*/

            ImageView imageView = (ImageView) layoutItem.findViewById(R.id.mapImageView);
            ImageLoader imageLoader = new ImageLoader(mContext, (short) width, (short) 300);
            imageLoader.DisplayImage(constructGoogleMapStaticUrl(width, gallery), imageView);

            //(2) : Récupération des TextView de notre layout
            TextView gallery_name = (TextView) layoutItem.findViewById(R.id.gallery_name);
            TextView gallery_theme = (TextView) layoutItem.findViewById(R.id.gallery_theme);
            TextView gallery_like = (TextView) layoutItem.findViewById(R.id.gallery_like);

            //(3) : Renseignement des valeurs

            gallery_name.setText(gallery.getName());
            gallery_theme.setText(gallery.getTheme());
            gallery_like.setText(gallery.getLike().toString());

            FloatingActionButton btn_like = (FloatingActionButton) layoutItem.findViewById(R.id.btn_gallery_like);
            btn_like.setImageResource(R.drawable.thumb_up_white_24dp);

            //On retourne l'item créé.
            return layoutItem;
        }

        @NonNull
        String constructGoogleMapStaticUrl(int width, Gallery gallery) {
            String url = "https://maps.googleapis.com/maps/api/staticmap?" +
                    "size=" + width + "x300";

            for (Map.Entry<String, Drawing> entry : gallery.getTraces().entrySet()) {
                Drawing drawing = entry.getValue();

                //TODO changer les couleurs pour chaque drawing
                String couleur = "bleu";

                //On parcours tous les segment pour les ajouté
                for (Segment segment : drawing.getSegments()) {
                    url += "&path=color:" + couleur + "|weight:5|enc:";
                    url += ot3.insa.fr.geodraw.utils.Utils.encode(segment.getGoogleMapSegment());
                }
            }

            url += "&key=" + getResources().getString(R.string.google_maps_key_static);
            return url;
        }

        @NonNull
        private Point getScreenSize() {
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getRealSize(size);
            return size;
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

        @Override
        public void onMapReady(GoogleMap map) {
            // Add a marker in Sydney, Australia, and move the camera.
            LatLng sydney = new LatLng(-34, 151);
            map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }
}