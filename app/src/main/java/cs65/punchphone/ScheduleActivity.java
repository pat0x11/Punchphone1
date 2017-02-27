package cs65.punchphone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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
            view = (TextView) findViewById(R.id.monday_change);
        } else if(id == TUESDAY_ID) view = (TextView) findViewById(R.id.tuesday_change);
        else if(id == WEDNESDAY_ID) view = (TextView) findViewById(R.id.wednesday_change);
        else if(id == THURSDAY_ID) view = (TextView) findViewById(R.id.thursday_change);
        else if(id == FRIDAY_ID) view = (TextView) findViewById(R.id.friday_change);
        else if(id == SATURDAY_ID) view = (TextView) findViewById(R.id.saturday_change);
        else if(id == SUNDAY_ID) view = (TextView) findViewById(R.id.sunday_change);

        String currentHours = view.getText().toString();
        Double doubleHours = Double.parseDouble(currentHours);

        TextView total = (TextView) findViewById(R.id.total_hours);
        Double totalHours = Double.parseDouble(total.getText().toString());
        totalHours -= doubleHours;
        total.setText(Double.toString(totalHours+hours));

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
