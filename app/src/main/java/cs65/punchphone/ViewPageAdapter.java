package cs65.punchphone;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;


import java.util.ArrayList;

//the class that is the adapter for the View pager (top toolbar)
public class ViewPageAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    public ViewPageAdapter(FragmentManager fm, ArrayList<Fragment> fragments){
        super(fm);
        this.fragments = fragments;
    }

    public Fragment getItem(int pos){
        return fragments.get(pos);
    }

    public int getCount(){
        return fragments.size();
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Account";
            case 1:
                return "Punch";
            case 2:
                return "History";
            case 3:
                return "Earnings";
            case 4:
                return "Schedule";
            default:
                break;
        }
        return null;
    }
}
