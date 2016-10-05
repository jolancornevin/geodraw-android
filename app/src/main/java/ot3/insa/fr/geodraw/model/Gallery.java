package ot3.insa.fr.geodraw.model;

import java.util.ArrayList;
import java.util.Date;
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

    /*
    Constructor
     */
    public Gallery(int id, String name, String theme, int like) {
        this.name = name;
        this.theme = theme;
        
        this.id = id;
        this.like = like;
    }

    /*
    Mock Functions
     */
    public static List<Gallery> getMockGalleries() {
        List<Gallery> listGallery = new ArrayList<>();

        listGallery.add(new Gallery(1, "Partie 100", "Chouette", 20));
        listGallery.add(new Gallery(1, "Partie 101", "Hiboux", 50));
        listGallery.add(new Gallery(1, "Partie 102", "Hulotte", 10));

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
}
