package ot3.insa.fr.geodraw;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;


import android.location.LocationListener;
import android.widget.Button;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.m5c.safesockets.SafeSocket;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import ot3.insa.fr.geodraw.communication.Client;
import ot3.insa.fr.geodraw.communication.ClientListener;
import ot3.insa.fr.geodraw.communication.message.AddLatLng;
import ot3.insa.fr.geodraw.communication.message.GameList;
import ot3.insa.fr.geodraw.communication.message.GameUpdate;
import ot3.insa.fr.geodraw.communication.message.JoinedGame;
import ot3.insa.fr.geodraw.communication.message.TraceMessage;
import ot3.insa.fr.geodraw.communication.message.Vote;
import ot3.insa.fr.geodraw.model.Segment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //Google Maps settings
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location lastLocation;
    private LocationListener gpsLocationListener;
    //TODO : Add random user color

    // GPS Location settings
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0.1f; // 1 meter
    private static final long MIN_TIME_BW_UPDATES = 1000*3; // 3 seconds

    //User personal settings
    private UserDrawing thisUser;
    private HashMap<String,UserDrawing> drawingList = new HashMap<String,UserDrawing>();
    private ClientListener thisClient;
    private Handler clientHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) { promptGPS();}

    }

    //GPS and permission functions
    private void promptGPS() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }
    private void getGPSPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                1);
    }

    //Initialize location and drawing
    private void getInitLocation(){
        if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            getGPSPermission();
        }
        else{ // Permission exists, get the starting coordonate if possible

            //Puts marker on the map, enables blue dot
            mMap.setMyLocationEnabled(true);

            //Add GPS Listener to LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, gpsLocationListener);

            //Get last known location
            lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            //Add location as the starting point of drawing, if exists
            List<LatLng> points = thisUser.getSelfDrawing().getPoints();
            if(lastLocation != null) {
                points.add(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lastLocation.getLatitude(),
                        lastLocation.getLongitude())));

            }
            else{
                promptGPS();
            }
            thisUser.setDrawingPoints(points);

        }
    }
    private void initSelf(){
        //Init drawing
        //TODO : Select color randomly
        thisUser = new UserDrawing(5,Color.RED, "jbdshj");
        PolylineOptions lineOptions = new PolylineOptions().width(thisUser.getSelfWidth())
                .color(thisUser.getSelfColor());
        thisUser.setSelfDrawing(mMap.addPolyline(lineOptions));
        drawingList.put(thisUser.getNickname(),thisUser);

        thisClient = new ClientListener() {

            void HandleTraceMessage(TraceMessage m, SafeSocket sender) {

                int gameID = m.getGameID();
                if(gameID != thisUser.getCurrentGame()) {
                    return;
                }

                //Creation of new users for maps
                String username = m.getPlayerID();
                UserDrawing usr;
                if(drawingList.containsKey(username)){
                    //
                    System.out.println("Added new stuff for existing user");
                    usr = drawingList.get(username);
                }
                else{
                    // Get user statistics
                    //TODO : Keep colors?
                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    usr = new UserDrawing(7,color,username);
                    addUser(usr);
                    System.out.println("Added new stuff for new user");
                }

                //Add existing drawings to maps
                List<Segment> k = m.getTrace().getSegments();
                for(Segment seg : k){ // Foreach polyline of a user
                    List<LatLng> newPolyline = new ArrayList<>();
                    System.out.println("hee");
                    for(ot3.insa.fr.geodraw.model.LatLng ltln : seg.getSegment()){
                        newPolyline.add(new LatLng(ltln.getLat(),ltln.getLng()));
                    }
                    addUserSegment(username,newPolyline);
                }

                final UserDrawing tempUsr = usr;
                //Add the polyline that the current user will start to draw
                if(m.getPlayerID().equals(thisUser.getNickname())) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run()
                        {
                            PolylineOptions lineOptions = new PolylineOptions()
                                    .width(thisUser.getSelfWidth())
                                    .color(thisUser.getSelfColor());
                            Polyline newSegment = mMap.addPolyline(lineOptions);
                            tempUsr.setSelfDrawing(newSegment);
                            drawingList.put(thisUser.getNickname(),tempUsr);
                        }
                    });

                }

            }

            // Get new coordinates of other users and add them
            void HandleAddLatLng(AddLatLng m, SafeSocket sender) {

                if(m.getGameID() != thisUser.getCurrentGame() || m.getUserID() == thisUser.getNickname()) {
                    return;
                }
                String username = m.getUserID();
                UserDrawing usr;
                if(!drawingList.containsKey(username)) {
                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    usr = new UserDrawing(7,color,username);
                    addUser(usr);
                } else {
                    usr = drawingList.get(username);
                }

                if(m.isDrawing()) {
                    Polyline currentSegment = usr.getSelfDrawing();

                    if (currentSegment == null) { //
                        initUserSegment(usr.getNickname());
                    }

                    LatLng ltln = new LatLng(m.getLatLng().getLat(), m.getLatLng().getLng());
                    updateUserDrawing(username, ltln);
                }
            }


            void HandleGameUpdate(GameUpdate m, SafeSocket sender) {
                // TODO Auto-generated method stub

            }

            void HandleVote(Vote m, SafeSocket sender) {
                // TODO Auto-generated method stub

            }
        };

        Client.theClient.addListener(thisClient);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //Multiplayer user functions
    private void addUser(UserDrawing drawing){
        /**
         * Add new user to userlist
         */
        drawingList.put(drawing.getNickname(),drawing);
    }
    private void addUserToGame() {
        /**
         * Add new user to game
         */
    }
    private void addUserSegment(final String userName,final List<LatLng> points)  {
        /**
         * Add existing user drawings into maps
         */
        runOnUiThread(new Runnable() {

            @Override
            public void run()
            {
                UserDrawing usr = drawingList.get(userName);

                PolylineOptions lineOptions = new PolylineOptions()
                        .width(usr.getSelfWidth())
                        .color(usr.getSelfColor());
                Polyline newSegment = mMap.addPolyline(lineOptions);
                newSegment.setPoints(points);
                usr.setSelfDrawing(newSegment);
                drawingList.put(userName,usr);
            }
        });

    }
    private void initUserSegment(final String userName)  {
        /**
         * Add existing user drawings into maps
         */
        runOnUiThread(new Runnable() {

            @Override
            public void run()
            {
                UserDrawing usr = drawingList.get(userName);

                PolylineOptions lineOptions = new PolylineOptions()
                        .width(usr.getSelfWidth())
                        .color(usr.getSelfColor());
                Polyline newSegment = mMap.addPolyline(lineOptions);
                usr.setSelfDrawing(newSegment);
                drawingList.put(userName, usr);
            }
        });

    }
    private void updateUserDrawing(final String userName, final LatLng p)  {
        /**
         * Update current drawing of the user
         */
        runOnUiThread(new Runnable() {

            @Override
            public void run()
            {
                UserDrawing k = drawingList.get(userName);

                List<LatLng> oldPoints = k.getSelfDrawing().getPoints();
                oldPoints.add(p);
                k.setDrawingPoints(oldPoints);
                drawingList.put(userName,k);
            }
        });

    }

    private void removeUser(String userName){
        UserDrawing l = drawingList.get(userName);
        for(Polyline p : l.getSelfDrawings()){
            p.remove();
        }
        drawingList.remove(userName);
    }

    //UI functions
    public void drawPressed(View view){

        String buttonText="RESUME DRAWING";
        thisUser.setIsDrawing(!thisUser.isDrawing());
        if(thisUser.isDrawing()){
            PolylineOptions lineOptions;
            try{
                lineOptions = new PolylineOptions()
                        .width(thisUser.getSelfWidth())
                        .add(new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude()))
                        .color(thisUser.getSelfColor());
                thisUser.setSelfDrawing(mMap.addPolyline(lineOptions));
            } catch(Exception e) {

            }

            buttonText="PAUSE DRAWING";
        }
        Button b = (Button)view;
        b.setText(buttonText);
        //TODO : Send new status to server
    }
    public void hidePressed(View view){
        boolean locEnable = !mMap.isMyLocationEnabled();
        if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            getGPSPermission();
        } else{
            mMap.setMyLocationEnabled(locEnable);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //IP and port of the server goes here
        initSelf();

        //Zoom camera
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(mMap.getMaxZoomLevel()-3);
        mMap.animateCamera(zoom);

        // -------------------TEST-------------------------------
        UserDrawing testUser= new UserDrawing(5,Color.GREEN,"test");
        addUser(testUser);

        //-------------------------------------------------------

        gpsLocationListener =new LocationListener(){

            public void onStatusChanged(String provider, int status, Bundle extras) {
                switch (status) {
                    case LocationProvider.AVAILABLE:
                        break;
                    case LocationProvider.OUT_OF_SERVICE:
                        break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        break;
                }
            }


            public void onProviderEnabled(String provider) {

            }

            public void onProviderDisabled(String provider) {
                promptGPS();
            }

            public void onLocationChanged(Location location) {
                if(thisUser.isDrawing()){
                    List<LatLng> points = thisUser.getSelfDrawing().getPoints();
                    points.add(new LatLng(location.getLatitude(),location.getLongitude()));
                    thisUser.setDrawingPoints(points);
                }

                // -------------------TEST-------------------------------
                List<LatLng> lk = new ArrayList<>();
                lk.add(new LatLng(location.getLatitude()+0.5,location.getLongitude()+0.5));
                //updateDrawing("test",lk);

                //-------------------------------------------------------

                lastLocation = location;
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lastLocation.getLatitude(),
                //        lastLocation.getLongitude())));

                //TODO : Sending user location to server
                System.out.println("Sending : ");
                Client.theClient.sendMessage(new AddLatLng(thisUser.getNickname(),
                        thisUser.getCurrentGame(),
                        new ot3.insa.fr.geodraw.model.LatLng(lastLocation.getLatitude(),lastLocation.getLongitude()),
                        thisUser.isDrawing()));
            }
        };
        getInitLocation();

        // Start to listen server for coordonate updates of other players

    }

    /**
     * To remove a user from the map (its line), simply do line.remove
     */

    public class UserDrawing{

        private String nickname;
        private int selfColor ;
        private int selfWidth;
        private List<Polyline> selfDrawings; //Because more than 1 polyline
        private Polyline selfDrawing; //Current polyline that the user is drawing
        private boolean isDrawing;
        private int currentGameID;

        public UserDrawing(int width,int color, String nickname ){
            this.nickname = nickname;
            this.selfColor = color;
            this.selfWidth = width;
            this.selfDrawings = new ArrayList<>();
            this.isDrawing = true;
            this.currentGameID = 0;
        }

        public int getSelfColor() {
            return selfColor;
        }

        public void setSelfColor(int selfColor) {
            this.selfColor = selfColor;
        }

        public int getSelfWidth() {
            return selfWidth;
        }

        public void setSelfWidth(int selfWidth) {
            this.selfWidth = selfWidth;
        }

        public Polyline getSelfDrawing() {
            return selfDrawing;
        }

        public void setSelfDrawing (Polyline selfDrawing) {
            this.selfDrawing =  selfDrawing;
            this.selfDrawings.add(selfDrawing);
        }

        public void setDrawingPoints(List<LatLng> points) {
            this.selfDrawing.setPoints(points);
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public boolean isDrawing() {
            return isDrawing;
        }

        public void setIsDrawing(boolean isDrawing) {
            this.isDrawing = isDrawing;
        }

        public List<Polyline> getSelfDrawings() {
            return selfDrawings;
        }

        public int getCurrentGame() {
            return currentGameID;
        }

        public void setCurrentGame(int currentGameID) {
            this.currentGameID = currentGameID;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        getInitLocation();
                    } else {
                        getGPSPermission();
                    }
                }
            }
        }
    }
}
