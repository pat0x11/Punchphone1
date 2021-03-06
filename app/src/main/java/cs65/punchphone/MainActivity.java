package cs65.punchphone;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import cs65.punchphone.view.SlidingTabLayout;

public class MainActivity extends AppCompatActivity implements ServiceConnection {
    private SlidingTabLayout slidingTabLayout;

    private ViewPager viewPager;

    private ArrayList<Fragment> fragments;                  //used for the fragments, contains all fragments
    public static ViewPageAdapter myRunsViewPagerAdapter;   //view pager for the top tab
    private EntryFragment mEntryFragment;
    private HistoryFragment mHistoryFragment;
    private SettingsFragment mSettingsFragment;
    private EarningsFragment mEarningsFragment;
    private ScheduleFragment mScheduleFragment;
    public static boolean registered;                       //keeps track of registration with backend
    private boolean bound=false;                            //bound with local service?


    //all of the employers received from front end
    public static ArrayList<FrontEndEmployer> employers = new ArrayList<FrontEndEmployer>();

    private final Messenger mMessenger = new Messenger(new
            MessageHandler()); //The handler to get message from the service

    public static boolean dataReceived; //Whether there is company data
    public static Location currentLocation; //Current location of device
    public static FrontEndEmployer frontEndEmployer; //the current company
    private ServiceConnection serviceConnection=this; //Connection to
    // intentservice
    public BroadcastReceiver mBr; //The broadcast receiver

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //setup and register the broadcast receiver
        mBr=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                EntryFragment.setupSpinner();
            }
        };
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("Notify Service Action");
        this.registerReceiver(mBr,intentFilter);

        //setup the top tap, containing all of the fragments
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
        fragments.add(mSettingsFragment);
        fragments.add(mEntryFragment);
        fragments.add(mHistoryFragment);
        fragments.add(mEarningsFragment);
        fragments.add(mScheduleFragment);

        //sets adapter to the top tab
        myRunsViewPagerAdapter = new ViewPageAdapter(getFragmentManager(),
                fragments);
        viewPager.setAdapter(myRunsViewPagerAdapter);

        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);

        dataReceived = false;
        registered = false;
        frontEndEmployer =null;

        new GCMRegAsyncTask(getApplicationContext()).execute();
        getData();

        doBindService();
        checkPermissions();
    }

    //Class to get messages from the service
    private class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            if (message.what == LocationService.MSG_NEW_DATA) { //the service has
                Log.d("message handler",message.getData().toString());
                currentLocation = LocationService.getLatestLocation();
                if (currentLocation != null && mEntryFragment.punchStatus) {
                    Location jobsiteCenter = new Location(LocationService.locationProvider);
                    jobsiteCenter.setLatitude(frontEndEmployer.getLat());
                    jobsiteCenter.setLongitude(frontEndEmployer.getLong());
                    if (jobsiteCenter.distanceTo(currentLocation) > frontEndEmployer.getRadius()) {
                        mEntryFragment.handlePunchOut(null);
                        Toast.makeText(getApplicationContext(),
                                "Punched Out: You left the work location", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                super.handleMessage(message);
            }
        }
    }

    //Start the location service
    public void startService() {
        Intent serviceIntent = new Intent(this, LocationService.class);
        startService(serviceIntent);
        Log.d("MainActivity", "Service Started");

    }

    //Start the binding to the locaiton service
    public void doBindService(){
        bindService(new Intent(this,LocationService.class),serviceConnection, Context.BIND_AUTO_CREATE);
        bound=true;

    }

    //Register the activity with the service
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

    //gets the sends the data to the backend
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
        Log.d("Main", "Starting Service");
        startService();
    }

    //Next bunch of methods allow the user to change the hours worked on each
    // day
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