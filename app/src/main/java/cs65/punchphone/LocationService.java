package cs65.punchphone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


/**
 * Created by CarterJacobsen on 3/1/17.
 */

public class LocationService extends Service {

    //The handler for messages from self
    private Messenger messenger = new Messenger(new MessageHandler());

    //The handler for messages from activity
    private Messenger client = new Messenger(new MessageHandler());

    //Whether there is an activity connected to this
    private boolean clientRegistered = false;

    //To deal with getting locations all the time
    private LocationManager locationManager;

    //The type of accuracy necessary for the locations
    public static String locationProvider;

    private static Location latestLocation;

    public boolean isRunning = false; //Whether it is running

    public static final int MSG_NEW_DATA = 1;
    public static final int MSG_REGISTER_ACTIVITY = 2;
    public static final int MSG_UNREGISTER_ACTIVITY = 3;

    @Override
    public int onStartCommand(Intent intent, int flags, int startdId) {
        super.onStartCommand(intent, flags, startdId);
        Bundle data = intent.getExtras();
//        Double latitude = data.getDouble("lat");
//        Double longitude = data.getDouble("long");
//        jobsite = new LatLng(latitude, longitude);
//        radius = data.getInt("radius");
        //Set the location manager
        locationManager = (LocationManager) getSystemService
                (Context.LOCATION_SERVICE);

        //Set the type of location gathering that we would like to use
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(true);
        criteria.setSpeedRequired(true);
        locationProvider = locationManager.getBestProvider(criteria,
                true);

        //Get the locations only if the user has allowed it
        if (ActivityCompat.checkSelfPermission(this, android.Manifest
                .permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Set up the listener to gather location data
            Log.d("Location Service","Permission Granted");
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latestLocation = location;
//                    Location jobsiteCenter = new Location(locationProvider);
//                    jobsiteCenter.setLatitude(jobsite.latitude);
//                    jobsiteCenter.setLongitude(jobsite.longitude);
//                    if (jobsiteCenter.distanceTo(location) > radius) {
                    if (clientRegistered) {
                        Message message = Message.obtain(null, MSG_NEW_DATA,
                                0, 0);
                        try {
                            client.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        //}
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            //How often is the shortest to grab location updates
            locationManager.requestLocationUpdates(locationProvider, 100, 1,
                    locationListener);
        }
        else{
            Log.d("Location Service","Permission Denied");
        }
        return START_NOT_STICKY;
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public static Location getLatestLocation(){
        if(latestLocation!= null) {
            return latestLocation;
        }else{
            return null;
        }
    }
    //Handle all the messages that come in to the service
    //Based on handler in BindDemo
    private class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            //If the message is about registering a client
            if (message.what == LocationService.MSG_REGISTER_ACTIVITY) {

                client = message.replyTo; //Define who the client is
                clientRegistered = true; //A client is registered

            }else if (message.what == LocationService.MSG_UNREGISTER_ACTIVITY){
                client=null; //no longer a client
                clientRegistered = false;
            } else{
                super.handleMessage(message);
            }
        }
    }
}