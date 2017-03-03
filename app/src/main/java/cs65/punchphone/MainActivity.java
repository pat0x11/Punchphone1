package cs65.punchphone;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

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

    private final Messenger mMessenger = new Messenger(new
            MessageHandler()); //The handler to get message from the service

    public static boolean dataRecieved;
    public static double latitude;
    public static double longitude;
    public static int radius;

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

        dataRecieved = false;
        latitude = 0;
        longitude =0;
        radius =0;

        startService();
//        //get all employers from the database
//        new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    ServerHelper.getAllEmployers();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.run();

    }

    //Class to get messages from the service
    private class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message message){
            if (message.what == LocationService.EMPLOYEE_PUNCHOUT){ //the service has
                mEntryFragment.handlePunchOut(null);
            } else {
                super.handleMessage(message);
            }
        }
    }

    public void startService(){
        if(dataRecieved) {
            Intent serviceIntent = new Intent(this, LocationService.class);
            serviceIntent.putExtra("lat", latitude);
            serviceIntent.putExtra("long", longitude);
            serviceIntent.putExtra("radius", radius);
            startService(serviceIntent);
        }
    }
}
