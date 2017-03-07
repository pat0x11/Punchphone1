package cs65.punchphone;

import android.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import cs65.punchphone.view.SlidingTabLayout;

public class MainActivity extends AppCompatActivity {
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

    //all of the employers received from front end
    public static ArrayList<FrontEndEmployer> employers;

    private final Messenger mMessenger = new Messenger(new
            MessageHandler()); //The handler to get message from the service

    public static boolean dataReceived;
    public static double latitude;
    public static double longitude;
    public static int radius;
    public static Location currentLocation;
    public static FrontEndEmployer frontEndEmployer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

//      viewPager
        mHistoryFragment = new HistoryFragment();
        mEntryFragment = new EntryFragment();
        mScheduleFragment = new ScheduleFragment();
        mSettingsFragment = new SettingsFragment();
        mEarningsFragment = new EarningsFragment();

        //initialize the array list of employers that will be filled in when the app opens
        employers=new ArrayList<FrontEndEmployer>();

        fragments = new ArrayList<Fragment>();
        fragments.add(mEntryFragment);
        fragments.add(mHistoryFragment);
        fragments.add(mEarningsFragment);
        fragments.add(mScheduleFragment);
        fragments.add(mSettingsFragment);

        myRunsViewPagerAdapter =new ViewPageAdapter(getFragmentManager(),
                fragments);
        viewPager.setAdapter(myRunsViewPagerAdapter);

        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);

        dataReceived = false;
        latitude = 0;
        longitude =0;
        radius =0;
        registered = false;
        frontEndEmployer = null;

        new GCMRegAsyncTask(getApplicationContext()).execute();
        getData();
        startService();
    }

    //Class to get messages from the service
    private class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message message){
            if (message.what == LocationService.MSG_NEW_DATA){ //the service has
                currentLocation = LocationService.getLatestLocation();
                if(currentLocation!= null){
                    Location jobsiteCenter = new Location(LocationService
                            .locationProvider);
                    jobsiteCenter.setLatitude(latitude);
                    jobsiteCenter.setLongitude(longitude);
                    if (jobsiteCenter.distanceTo(currentLocation) > radius) {
                        mEntryFragment.handlePunchOut(null);
                    }
                }
            } else {
                super.handleMessage(message);
            }
        }
    }

    public void startService(){
        if(dataReceived) {
            Intent serviceIntent = new Intent(this, LocationService.class);
            startService(serviceIntent);
            Log.d("MainActivity", "Service Started");
        }
    }

    public void getData(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                while (!isCancelled()) {
                    if (registered) {
                        try {
                            ServerUtilities.post(Globals.backendURL+
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
}