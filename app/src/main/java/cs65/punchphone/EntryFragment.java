package cs65.punchphone;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import cs65.punchphone.data.PunchEntry;
import cs65.punchphone.data.PunchEntryDbHelper;

//test comment
public class EntryFragment extends Fragment {
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
    private Long punchInTime;


    private PunchEntry punchEntry;
    private PunchEntryDbHelper punchDbHelper;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_entry, container, false);


        //setup the date and time
        timeText=(TextClock) view.findViewById(R.id.timeClock);
        dateText=(TextView) view.findViewById(R.id.dateEditText);
        setupDateTime();

        //setup the buttons
        punchIn=(Button)view.findViewById(R.id.punchIn);
        punchIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handlePunchIn(v);
            }
        });
        punchOut=(Button)view.findViewById(R.id.punchOut);
        punchOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handlePunchOut(v);
            }
        });
        punchMessage=(TextView)view.findViewById(R.id.statusField);
        getInitialPunchStatus();

        // initialize
        punchEntry = new PunchEntry();
        punchDbHelper = new PunchEntryDbHelper(getActivity());

        new GCMRegAsyncTask(getActivity().getApplicationContext()).execute();
        return view;
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


            punchEntry.setInputType(0);
            SharedPreferences settings = PreferenceManager
                    .getDefaultSharedPreferences(getContext());

            String name = settings.getString(getContext().getString(R.string.ui_settings_name_key), "no Name");
            punchEntry.setName(name);

            // How do we get company ....
            punchEntry.setCompany("my Company");
            punchEntry.setSite("new site");
            punchEntry.setDateTime(java.util.Calendar.getInstance());


        }
        else{
            //Toast toast = Toast.makeText(getApplicationContext(), R.string.punchError, Toast.LENGTH_SHORT);
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    R.string.punchError, Toast.LENGTH_SHORT);
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

            int duration = createDuration();
            punchEntry.setDuration(duration);

            SharedPreferences settings = PreferenceManager
                    .getDefaultSharedPreferences(getContext());

            String sWage = settings.getString(getContext().getString(R.string.ui_settings_wage_key), "10.00");
            double wage = Double.parseDouble(sWage);
            punchEntry.setEarnings(wage * duration/3600);
            Log.d("earnings", "earnings : " + punchEntry.getEarnings() + " wage " + wage);
            new InsertPunchTask().execute(punchEntry);
        }
        else{
            //Toast toast = Toast.makeText(getApplicationContext(), R.string.punchError, Toast.LENGTH_SHORT);
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    R.string.punchError, Toast.LENGTH_SHORT);
        }
    }

    private int createDuration(){
        int duration = (int) ((System.currentTimeMillis() - punchEntry.getDateTimeMillis()) / 1000);
        return duration;
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

    public class InsertPunchTask extends AsyncTask<PunchEntry, Void, String> {
        @Override
        protected String doInBackground(PunchEntry... exerciseEntries) {
            long id = punchDbHelper.insertEntry(exerciseEntries[0]);
            return ""+id;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getContext(), "Punch #" + result + " saved.", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
