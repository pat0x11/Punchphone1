package cs65.punchphone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

//Carter Jacobsen 2/27/17
public class ScheduleActivity extends AppCompatActivity {

    public static final int MONDAY_ID = 0;
    public static final int TUESDAY_ID = 1;
    public static final int WEDNESDAY_ID = 2;
    public static final int THURSDAY_ID = 3;
    public static final int FRIDAY_ID = 4;
    public static final int SATURDAY_ID = 5;
    public static final int SUNDAY_ID = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
    }

    private void changeHours(int id, String day){
        DialogHandler handler = DialogHandler.newInstance(id, day);
        handler.show(getFragmentManager(), "punchphone");
    }

    public void printHours(int id, double hours){
        TextView view = null;
        if(id == MONDAY_ID){
            view = (TextView) findViewById(R.id.monday_hours);
        } else if(id == TUESDAY_ID) view = (TextView) findViewById(R.id.tuesday_hours);
        else if(id == WEDNESDAY_ID) view = (TextView) findViewById(R.id.wednesday_hours);
        else if(id == THURSDAY_ID) view = (TextView) findViewById(R.id.thursday_hours);
        else if(id == FRIDAY_ID) view = (TextView) findViewById(R.id.friday_hours);
        else if(id == SATURDAY_ID) view = (TextView) findViewById(R.id.saturday_hours);
        else if(id == SUNDAY_ID) view = (TextView) findViewById(R.id.sunday_hours);

        TextView total = (TextView) findViewById(R.id.total_hours);
        Double totalHours = 0.0;
        if(!total.getText().toString().equals("")){
            String stringHours = total.getText().toString();
            String[] hourArray = stringHours.split(" ");
            totalHours += Double.parseDouble(hourArray[0]);
        }
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
        if(currentTotal > 40){
            message = Double.toString
                    (currentTotal) + " You are receiving " + (currentTotal-40) +
                    " overtime hour";
            if(!((currentTotal-40) == 1)) message += "s";
        }
        total.setText(message);
        if(view != null) view.setText(Double.toString(hours));


    }

    public void mondayChange(View view){
        changeHours(MONDAY_ID, "Monday");
    }

    public void tuesdayChange(View view){
        changeHours(TUESDAY_ID, "Tuesday");
    }

    public void wednesdayChange(View view){
        changeHours(WEDNESDAY_ID, "Wednesday");
    }

    public void thursdayChange(View view){
        changeHours(THURSDAY_ID, "Thursday");
    }

    public void fridayChange(View view){
        changeHours(FRIDAY_ID, "Friday");
    }

    public void saturdayChange(View view){
        changeHours(SATURDAY_ID, "Saturday");
    }
    public void sundayChange(View view){
        changeHours(SUNDAY_ID, "Sunday");
    }
}
