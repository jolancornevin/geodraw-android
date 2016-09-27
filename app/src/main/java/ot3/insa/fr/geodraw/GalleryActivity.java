package ot3.insa.fr.geodraw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Djowood on 27/09/2016.
 */

public class GalleryActivity extends Fragment {

    public GalleryActivity() {

    }

    public static GalleryActivity newInstance() {
        return new GalleryActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gallery, container, false);

        return rootView;
    }
}