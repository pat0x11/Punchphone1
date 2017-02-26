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

        //setup the toolbar
        Toolbar myToolbar=(Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        //setup the navigation drawer
        DrawerLayout mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        ListView mListDrawer=(ListView)findViewById(R.id.left_drawer);
        //get the array of menu items
        String[] menuItems=getResources().getStringArray(R.array.navItems);

        //setup the adapter for the listView
        mListDrawer.setAdapter(new ArrayAdapter<String>(this,
                R.layout.nav_item_layout,menuItems));
        //setup the listener for the adapter
    }

}
