package cs65.punchphone;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EntryPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    protected DrawerLayout navDrawers;  //the drawer layout in navigation_menu.xml
    protected ListView navListView;     //the listView that will be populated with nav items
    protected ActionBarDrawerToggle mainActionBarToggle;    //the toggle used to create the nav menu
    protected NavigationView currentNav;                    //the navigation view on the main page
    protected DrawerLayout mDrawerLayout;                   //drawer layout for the class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setup the content view for the page
        setContentView(R.layout.entry_page);

        //setup the toolbar
        Toolbar myToolbar=(Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        //setup the navigation drawer
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);

        //setup an adapter for the hamburger navigation menu
        //try to listen for when the user selects a tab
        currentNav=(NavigationView)findViewById(R.id.mainNavView);
        currentNav.setNavigationItemSelectedListener(this);


        //setup the hamburger icon for the toolbar
        //implement the "hamburger" menu
        mainActionBarToggle=new ActionBarDrawerToggle(this,mDrawerLayout,myToolbar,R.string.openInfo
                ,R.string.closeInfo);
        mainActionBarToggle.syncState();
    }

    //handles when the user selects a navigation view item
    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        //get the option that was selected
        String toCompare=item.getTitle().toString();
        Log.d("NavItemSelected",toCompare);
        mDrawerLayout.closeDrawers();
        return true;
    }
}
