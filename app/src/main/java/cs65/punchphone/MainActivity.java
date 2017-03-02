package cs65.punchphone;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

    }

    //Class to get messages from the service
    private class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message message){
            if (message.what == LocationService.EMPLOYEE_PUNCHOUT){ //the service has
                
            } else {
                super.handleMessage(message);
            }
        }
    }
}
