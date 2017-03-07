package cs65.punchphone;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

import cs65.punchphone.view.SlidingTabLayout;

public class MainActivity extends AppCompatActivity implements ServiceConnection {
    private SlidingTabLayout slidingTabLayout;

    private ViewPager viewPager;

    private ArrayList<Fragment> fragments;
    private ViewPageAdapter myRunsViewPagerAdapter;
    private EntryFragment mEntryFragment;
    private HistoryFragment mHistoryFragment;
    private SettingsFragment mSettingsFragment;
    private EarningsFragment mEarningsFragment;
    private ScheduleFragment mScheduleFragment;
    public static boolean registered;
    private boolean bound=false;
    //all of the employers received from front end
    public static ArrayList<FrontEndEmployer> employers = new ArrayList<FrontEndEmployer>();

    private final Messenger mMessenger = new Messenger(new
            MessageHandler()); //The handler to get message from the service

    public static boolean dataReceived;
    public static double latitude;
    public static double longitude;
    public static int radius;
    public static Location currentLocation;
    public static FrontEndEmployer frontEndEmployer;
    private ServiceConnection serviceConnection=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

//      viewPager
        mHistoryFragment = new HistoryFragment();
        mEntryFragment = new EntryFragment();
        mScheduleFragment = new ScheduleFragment();
        mSettingsFragment = new SettingsFragment();
        mEarningsFragment = new EarningsFragment();

        //initialize the array list of employers that will be filled in when the app opens
        employers = new ArrayList<FrontEndEmployer>();

        fragments = new ArrayList<Fragment>();
        fragments.add(mEntryFragment);
        fragments.add(mHistoryFragment);
        fragments.add(mEarningsFragment);
        fragments.add(mScheduleFragment);
        fragments.add(mSettingsFragment);

        myRunsViewPagerAdapter = new ViewPageAdapter(getFragmentManager(),
                fragments);
        viewPager.setAdapter(myRunsViewPagerAdapter);

        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);

        dataReceived = false;
        latitude = 0;
        longitude = 0;
        radius = 0;
        registered = false;
        //frontEndEmployer =null;
        try {
            frontEndEmployer = new FrontEndEmployer("Dartmouth", "5545 Ne Penrith Rd", "Seattle", "WA", "98105", "399", "10", "6", getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        new GCMRegAsyncTask(getApplicationContext()).execute();
        getData();

        doBindService();
        startService();
    }

    //Class to get messages from the service
    private class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            if (message.what == LocationService.MSG_NEW_DATA) { //the service has
                Log.d("message handler",message.getData().toString());
                currentLocation = LocationService.getLatestLocation();
                if (currentLocation != null) {
                    Location jobsiteCenter = new Location(LocationService.locationProvider);
                    jobsiteCenter.setLatitude(frontEndEmployer.getLat());
                    jobsiteCenter.setLongitude(frontEndEmployer.getLong());
                    if (jobsiteCenter.distanceTo(currentLocation) > frontEndEmployer.getRadius()) {
                        mEntryFragment.handlePunchOut(null);
                    }
                }
            } else {
                super.handleMessage(message);
            }
        }
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, LocationService.class);
        startService(serviceIntent);
        Log.d("MainActivity", "Service Started");

    }

    public void doBindService(){
        bindService(new Intent(this,LocationService.class),serviceConnection, Context.BIND_AUTO_CREATE);
        bound=true;

    }
    @Override
    public void onServiceConnected(ComponentName name, IBinder service){
        Messenger messenger=new Messenger(service);
        Message message=Message.obtain(null,LocationService.MSG_REGISTER_ACTIVITY);
        message.replyTo=mMessenger;

        try{
            messenger.send(message);
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    public void getData() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                while (!isCancelled()) {
                    if (registered) {
                        try {
                            ServerUtilities.post(Globals.backendURL +
                                    "connect.do", null);
                            cancel(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return null;
            }
        }.execute();
    }

    /**
     * Code to check for runtime permissions.
     * Note: Function is directly taken from the CS65 course webpage, written by Professor Andrew Palmer
     * was given permission to use this in class
     */
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT < 23)
            return;

        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }
    }

    public void mondayChange(View view){
        mScheduleFragment.changeHours(mScheduleFragment.MONDAY_ID, "Monday");
    }

    public void tuesdayChange(View view){
        mScheduleFragment.changeHours(mScheduleFragment.TUESDAY_ID, "Tuesday");
    }

    public void wednesdayChange(View view){
        mScheduleFragment.changeHours(mScheduleFragment.WEDNESDAY_ID,
                "Wednesday");
    }

    public void thursdayChange(View view){
        mScheduleFragment.changeHours(mScheduleFragment.THURSDAY_ID,
                "Thursday");
    }

    public void fridayChange(View view){
        mScheduleFragment.changeHours(mScheduleFragment.FRIDAY_ID, "Friday");
    }

    public void saturdayChange(View view){
        mScheduleFragment.changeHours(mScheduleFragment.SATURDAY_ID,
                "Saturday");
    }
    public void sundayChange(View view){
        mScheduleFragment.changeHours(mScheduleFragment.SUNDAY_ID, "Sunday");
    }

    public void printHours(int id, double value){
        mScheduleFragment.printHours(id, value);
    }
}