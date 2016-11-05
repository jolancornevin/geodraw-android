package ot3.insa.fr.geodraw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Djowood on 27/09/2016.
 */

public class Gallery {

    private final int id;
    private final String name;
    private final String theme;
    private final Integer like;

    private final Map<String, Drawing> traces;

    /*
    Constructor
     */
    public Gallery(int id, String name, String theme, int like) {
        this.name = name;
        this.theme = theme;

        this.id = id;
        this.like = like;

        traces = new HashMap<>();
    }

    /*
    Mock Functions
     */
    public static List<Gallery> getMockGalleries() {
        List<Gallery> listGallery = new ArrayList<>();
        Gallery gallery;
        Drawing drawing;

        gallery = new Gallery(1, "Partie 100", "Chouette", 20);
        drawing = new Drawing();
        drawing.addLatLng(new LatLng(45.777460,4.845140), true);
        drawing.addLatLng(new LatLng(45.772430,4.855100), true);
        drawing.addLatLng(new LatLng(45.785600,4.858020), true);
        drawing.addLatLng(new LatLng(45.777460,4.845140), true);
        gallery.addTrace("1", drawing);

        drawing = new Drawing();
        drawing.addLatLng(new LatLng(45.774800,4.849990), true);
        drawing.addLatLng(new LatLng(45.778030,4.855570), true);
        drawing.addLatLng(new LatLng(45.780180,4.849690), true);
        drawing.addLatLng(new LatLng(45.774800,4.849990), true);
        gallery.addTrace("2", drawing);

        listGallery.add(gallery);

        gallery = new Gallery(1, "Partie 101", "Hiboux", 50);
        drawing = new Drawing();
        drawing.addLatLng(new LatLng(45.777460,4.845140), true);
        drawing.addLatLng(new LatLng(45.772430,4.855100), true);
        drawing.addLatLng(new LatLng(45.785600,4.858020), true);
        drawing.addLatLng(new LatLng(45.777460,4.845140), true);
        gallery.addTrace("2", drawing);

        listGallery.add(gallery);

        gallery = new Gallery(1, "Partie 102", "Hulotte", 10);
        drawing = new Drawing();
        drawing.addLatLng(new LatLng(45.774800,4.849990), true);
        drawing.addLatLng(new LatLng(45.778030,4.855570), true);
        drawing.addLatLng(new LatLng(45.780180,4.849690), true);
        drawing.addLatLng(new LatLng(45.774800,4.849990), true);
        gallery.addTrace("3", drawing);

        listGallery.add(gallery);

        return listGallery;
    }

    /*
    Getters & setters
     */

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTheme() {
        return theme;
    }

    public Integer getLike() {
        return like;
    }

    public Map<String, Drawing> getTraces() {
        return traces;
    }

    public void addTrace(String idPlayer, Drawing drawing) {
        traces.put(idPlayer, drawing);
    }
}
