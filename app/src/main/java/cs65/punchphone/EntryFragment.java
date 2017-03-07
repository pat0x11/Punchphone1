package cs65.punchphone;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cs65.punchphone.data.PunchEntry;
import cs65.punchphone.data.PunchEntryDbHelper;

//Group built

public class EntryFragment extends Fragment {

    public static Button punchIn;                                  //punch in button
    public static Button punchOut;                                 //punch out
    // button
    public static boolean punchStatus;                             //true=punched
    // in, false=punched out
    public static TextView punchMessage;                           //the message
    // displaying the punch status
    public TextView dateText;                               //the text view containing the date
    public TextClock timeText;                              //the text view containing the time

    public static EmployerAdapter employerAdapter;
    public static Spinner spinner;

    private PunchEntry punchEntry;
    private PunchEntryDbHelper punchDbHelper;
    public static Context mContext;
    private final static String SERVER = Globals.backendURL;

    public static View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_entry, container, false);

        mContext = getActivity();
        //setup the date and time
        timeText = (TextClock) view.findViewById(R.id.timeClock);
        dateText = (TextView) view.findViewById(R.id.dateEditText);
        setupDateTime();

        //setup the buttons
        punchIn = (Button) view.findViewById(R.id.punchIn);
        punchIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handlePunchIn(v);
            }
        });
        punchOut = (Button) view.findViewById(R.id.punchOut);
        punchOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handlePunchOut(v);
            }
        });
        punchMessage = (TextView) view.findViewById(R.id.statusField);

        if (punchStatus == true) {
            punchMessage.setText("Punched In");
            punchMessage.setTextColor(getResources().getColor(R.color.greenStatus));
        }

        getInitialPunchStatus();

        // initialize
        punchEntry = new PunchEntry();
        punchDbHelper = new PunchEntryDbHelper(getActivity());

        //Adds the data to the spinner if available
        setupSpinner();

        return view;
    }

    //Adds the data to the spinner
    public static void setupSpinner() {

        //Log.d("EmployerssetupSpinner: ",Integer.toString(MainActivity.employers.size()));

        //Attach the adapter I created
        employerAdapter = new EmployerAdapter(mContext, MainActivity.employers);
        spinner = (Spinner) view.findViewById(R.id.spinner); //get spinner

        employerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(employerAdapter);

        //Set up what happens when the user selects an item from the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Log.d("Position: ", Integer.toString(position));
                //Get the position
                int spinnerVal = (int) parent.getItemIdAtPosition(position);
                //Log.d("Listener", "" + spinnerVal);
                //Find the emplyer object in the array
                MainActivity.frontEndEmployer = MainActivity.employers.get(spinnerVal);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // a helper method that determines what the punch status is
    public static boolean getInitialPunchStatus() {

        String status = punchMessage.getText().toString();

        if (MainActivity.dataReceived) { //Make sure the data is received
            //punched in
            if (status.compareTo("Punched In") == 0) {
                punchStatus = true;
                punchIn.setEnabled(false);
                punchOut.setEnabled(true);
            } else { //punched out
                punchStatus = false;
                punchIn.setEnabled(true);
                punchOut.setEnabled(false);
            }
        } else { //Set both to false
            punchIn.setEnabled(false);
            punchOut.setEnabled(false);
        }
        return punchStatus;
    }

    //a helper method that handles punching in
    public void handlePunchIn(View v) {
        //get employer
        FrontEndEmployer employer = MainActivity.frontEndEmployer;
        //proceed if the user is not punched in
        if (!punchStatus) {
            //Get the location of the employers site
            Location employerLocation = new Location(LocationService.locationProvider);
            employerLocation.setLatitude(employer.getLat());
            employerLocation.setLongitude(employer.getLong());


            if (MainActivity.currentLocation != null) {

//            if(MainActivity.currentLocation != null && MainActivity
//                    .currentLocation.distanceTo
//                    (employerLocation) < employer.getRadius()) {

                //disable the punch in button
                punchIn.setEnabled(false);
                //enable the punch out button
                punchOut.setEnabled(true);

                //update the text field
                punchMessage.setText("Punched In");
                punchMessage.setTextColor(getResources().getColor(R.color.greenStatus));
                punchStatus = true;

                //put data in the punch entry
                punchEntry.setInputType(0);
                SharedPreferences settings = PreferenceManager
                        .getDefaultSharedPreferences(getContext());

                String name = settings.getString(getContext().getString(R.string.ui_settings_name_key), "empty");
                punchEntry.setName(name);

                //Log.d("Array Size: ", Integer.toString(EmployerAdapter.employers.size()));

                // get company name and site as well as date in
                punchEntry.setCompany(employer.getName());
                punchEntry.setSite("office");
                punchEntry.setInDateTime(java.util.Calendar.getInstance());

            } else {
                Toast.makeText(getActivity().getApplicationContext(),
                        R.string.punchError, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(),
                    R.string.punchError, Toast.LENGTH_SHORT).show();
        }
    }

    // a helper method that handles punching out
    //a helper method that handles punching in
    public void handlePunchOut(View v) {
        //proceed if the user is punched in
        if (punchStatus) {
            //disable the punch in button
            punchIn.setEnabled(true);
            //enable the punch out button
            punchOut.setEnabled(false);

            //update the text field
            punchMessage.setText("Punched Out");
            punchMessage.setTextColor(getResources().getColor(R.color.red));
            punchStatus = false;

            punchEntry.setOutDateTime(java.util.Calendar.getInstance());
            int duration = createDuration();
            punchEntry.setDuration(duration);


            SharedPreferences settings = PreferenceManager
                    .getDefaultSharedPreferences(getContext());

            String sWage = settings.getString(getContext().getString(R.string.ui_settings_wage_key), "10.00");
            double wage = Double.parseDouble(sWage);
            punchEntry.setEarnings(wage * duration / 3600);
            Log.d("earnings", "earnings : " + punchEntry.getEarnings() + " wage " + wage);
            new SendPunchTask().execute(punchEntry);
            new InsertPunchTask().execute(punchEntry);
        } else {
            Toast.makeText(getActivity().getApplicationContext(),
                    R.string.punchError, Toast.LENGTH_SHORT).show();
        }
    }

    //Get the duration between punch out and in times in milliseconds
    private int createDuration() {
        int duration = (int) ((punchEntry.getOutDateTimeMillis() - punchEntry
                .getInDateTimeMillis())
                / 1000);
        return duration;
    }

    //a helper method that handles setting up the date and the time on the UI
    private void setupDateTime() {
        //first get the date
        Calendar current = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            current = Calendar.getInstance();
        }
        Date dateInstance = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dateInstance = current.getTime();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMMM dd yyyy");
        String dateString = dateFormat.format(dateInstance);

        //set the text field to this string
        dateText.setText(dateString);

    }

    //Will put the punch in the phone's database
    public class InsertPunchTask extends AsyncTask<PunchEntry, Void, String> {
        @Override
        protected String doInBackground(PunchEntry... exerciseEntries) {
            long id = punchDbHelper.insertEntry(exerciseEntries[0]);
            return "" + id;
        }
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getContext(), "Punch #" + result + " saved.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public class SendPunchTask extends AsyncTask<PunchEntry, Void, String> {
        @Override
        protected String doInBackground(PunchEntry... punchEntries) {
            String retString = "";
            Map<String, String> m = new HashMap<>();
            PunchEntry p = punchEntries[0];
            String name = p.getName();
            String company = p.getCompany();
            String site = p.getSite();
            String punchIn = Long.toString(p.getInDateTimeMillis());
            String punchOut = Long.toString(p.getOutDateTimeMillis());
            m.put("name", name);
            m.put("company", company);
            m.put("site", site);
            m.put("punchin", punchIn);
            m.put("punchout", punchOut);
            try {
                ServerUtilities.post(SERVER + "/add.do", m);
                retString = "Punched Out Successfully";
            } catch (IOException e) {
                e.printStackTrace();
                retString = e.getMessage();
            }
            return retString;
        }

        @Override
        protected void onPostExecute(String param) {
            Toast.makeText(getContext(), param, Toast.LENGTH_LONG).show();
        }
    }


}