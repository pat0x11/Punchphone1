package cs65.punchphone;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EntryPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    protected DrawerLayout navDrawers;  //the drawer layout in navigation_menu.xml
    protected ListView navListView;     //the listView that will be populated with nav items
    protected ActionBarDrawerToggle mainActionBarToggle;    //the toggle used to create the nav menu
    protected NavigationView currentNav;                    //the navigation view on the main page
    protected DrawerLayout mDrawerLayout;                   //drawer layout for the class
    public Button punchIn;                                  //punch in button
    public Button punchOut;                                 //punch out button
    public boolean punchStatus;                             //true=punched in, false=punched out
    public TextView punchMessage;                           //the message displaying the punch status
    public TextView dateText;                               //the text view containing the date
    public TextClock timeText;                              //the text view containing the time

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

        //setup the date and time
        timeText=(TextClock)findViewById(R.id.timeClock);
        dateText=(TextView)findViewById(R.id.dateEditText);
        setupDateTime();

        //setup the buttons
        punchIn=(Button)findViewById(R.id.punchIn);
        punchOut=(Button)findViewById(R.id.punchOut);
        punchMessage=(TextView)findViewById(R.id.statusField);
        getInitialPunchStatus();

        new GCMRegAsyncTask(this).execute();
    }

    //handles when the user selects a navigation view item
    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        //get the option that was selected
        String toCompare=item.getTitle().toString();
        Log.d("NavItemSelected",toCompare);
        mDrawerLayout.closeDrawers();

        int id = item.getItemId();
        if(id == R.id.mainTab){
            Intent intent = new Intent(this, EntryPageActivity.class);
            startActivity(intent);
        } else if(id == R.id.scheduleTab){
            startActivity(new Intent(this, ScheduleActivity.class));
        }else if(id == R.id.earningTab){
            startActivity(new Intent(this, EarningsActivity.class));
        } else if(id == R.id.historyTab){
            startActivity(new Intent(this, HistoryActivity.class));
        }
        return true;
    }

    // a helper method that determines what the punch status is
    private boolean getInitialPunchStatus(){

        String status=punchMessage.getText().toString();

        //punched in
        if (status.compareTo("Punched In")==0){
            punchStatus=true;
            punchIn.setEnabled(false);
            punchOut.setEnabled(true);
        }
        else{
            punchStatus=false;
            punchIn.setEnabled(true);
            punchOut.setEnabled(false);
        }
        return punchStatus;
    }

    //a helper method that handles punching in
    public void handlePunchIn(View v){
        //proceed if the user is not punched in
        if (!punchStatus){
            //disable the punch in button
            punchIn.setEnabled(false);
            //enable the punch out button
            punchOut.setEnabled(true);

            //update the text field
            punchMessage.setText("Punched In");
            punchMessage.setTextColor(getResources().getColor(R.color.greenStatus));
            punchStatus=true;
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), R.string.punchError, Toast.LENGTH_SHORT);
        }
    }

    // a helper method that handles punching out
    //a helper method that handles punching in
    public void handlePunchOut(View v){
        //proceed if the user is not punched in
        if (punchStatus){
            //disable the punch in button
            punchIn.setEnabled(true);
            //enable the punch out button
            punchOut.setEnabled(false);

            //update the text field
            punchMessage.setText("Punched Out");
            punchMessage.setTextColor(getResources().getColor(R.color.red));
            punchStatus=false;
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), R.string.punchError, Toast.LENGTH_SHORT);
        }
    }

    //a helper method that handles setting up the date and the time on the UI
    private void setupDateTime(){
        //first get the date
        Calendar current= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            current = Calendar.getInstance();
        }
        Date dateInstance=null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dateInstance=current.getTime();
        }

        SimpleDateFormat dateFormat=new SimpleDateFormat("EEE, MMMM dd yyyy");
        String dateString=dateFormat.format(dateInstance);

        //set the text field to this string
        dateText.setText(dateString);

    }
}
