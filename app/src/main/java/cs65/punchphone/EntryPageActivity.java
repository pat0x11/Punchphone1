package cs65.punchphone;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EntryPageActivity extends AppCompatActivity {
    protected DrawerLayout navDrawers;  //the drawer layout in navigation_menu.xml
    protected ListView navListView;     //the listView that will be populated with nav items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setup the content view for the page
        setContentView(R.layout.entry_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setup the navigation bar on the left side of the page
        String[] navItems=getResources().getStringArray(R.array.navItems);
        setContentView(R.layout.navigation_menu);
        navDrawers=(DrawerLayout)findViewById(R.id.drawer_layout);
        navListView=(ListView)findViewById(R.id.navDrawerList);

        setContentView(R.layout.nav_item_layout);
        //setup the adapter for the navigation drawers
        navListView.setAdapter(new ArrayAdapter<String>(this,R.layout.nav_item_layout,navItems));

        //setup the listener for the click

    }

}
