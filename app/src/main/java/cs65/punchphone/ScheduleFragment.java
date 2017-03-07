package cs65.punchphone;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



//Carter Jacobsen 2/27/17
//The fragment to allow the user to check how their schedule matches with the
// employers hours guidelines
public class ScheduleFragment extends Fragment {

    //Ids for the days of the week
    public final int MONDAY_ID = 0;
    public final int TUESDAY_ID = 1;
    public final int WEDNESDAY_ID = 2;
    public final int THURSDAY_ID = 3;
    public final int FRIDAY_ID = 4;
    public final int SATURDAY_ID = 5;
    public final int SUNDAY_ID = 6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    //Changes the hours in one of the days of the week's view
    public void changeHours(int id, String day){
        DialogHandler handler = DialogHandler.newInstance(id, day);
        handler.show(getFragmentManager(), "punchphone");
    }

    //Puts the actual hours value in the text view and calculates how the
    // relates to the total and employers set hours
    public void printHours(int id, double hours){

        //Find the proper view
        TextView view = null;
        if(id == MONDAY_ID){
            view = (TextView) getView().findViewById(R.id.monday_hours);
        } else if(id == TUESDAY_ID) view = (TextView) getView().findViewById(R.id.tuesday_hours);
        else if(id == WEDNESDAY_ID) view = (TextView) getView().findViewById(R.id.wednesday_hours);
        else if(id == THURSDAY_ID) view = (TextView) getView().findViewById(R.id.thursday_hours);
        else if(id == FRIDAY_ID) view = (TextView) getView().findViewById(R.id.friday_hours);
        else if(id == SATURDAY_ID) view = (TextView) getView().findViewById(R.id.saturday_hours);
        else if(id == SUNDAY_ID) view = (TextView) getView().findViewById(R.id.sunday_hours);

        //Get the total text view
        TextView total = (TextView) getView().findViewById(R.id.total_hours);
        Double totalHours = 0.0;

        //Get the total hours currently
        if(!total.getText().toString().equals("")){
            String stringHours = total.getText().toString();
            String[] hourArray = stringHours.split(" ");
            totalHours += Double.parseDouble(hourArray[0]);
        }

        //
        if(view != null && !view.getText().toString().equals("") && !total
                .getText().toString().equals("")) {
            String currentHours = view.getText().toString();
            Log.d("cj", currentHours);
            Double doubleHours = Double.parseDouble(currentHours);
            totalHours -= doubleHours;
            total.setText(Double.toString(totalHours+hours));
        }

        Double currentTotal = totalHours + hours;
        String message = Double.toString(currentTotal);

        int employerNormalHours = MainActivity.frontEndEmployer.getNormalHrs()*7;
        int employerOvertimeHours = MainActivity.frontEndEmployer
                .getOvertimeHrs()*7;
        if(currentTotal > employerNormalHours){
            message = Double.toString
                    (currentTotal) + " You are receiving " +
                    (currentTotal-employerNormalHours) +
                    " overtime hour";
            if(!((currentTotal-employerNormalHours) == 1)) message += "s";
        } if(currentTotal >(employerNormalHours+employerOvertimeHours)){
            message += ", not getting paid for " +
                    (currentTotal-employerNormalHours-employerOvertimeHours);
        }
        total.setText(message);
        if(view != null) view.setText(Double.toString(hours));


    }
}
