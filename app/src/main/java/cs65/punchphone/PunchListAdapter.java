package cs65.punchphone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import cs65.punchphone.data.PunchEntry;

/**
 * Created by brian on 3/4/17.
 */

//used for the adapter on the history fragment
public class PunchListAdapter extends ArrayAdapter<PunchEntry> {

    public PunchListAdapter(Context context, List<PunchEntry> entries) {
        super(context, 0, entries);
    }

    // Returns the filled view with all entries
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PunchEntry entry = getItem(position);
        String input;
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_history, parent, false);

        // get input type
        if (entry.getInputType() == 0 ){
            input = "automatic";
        } else {
            input = "manual";
        }

        String calin;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(entry.getInDateTimeMillis());
        String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        String hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = Integer.toString(calendar.get(Calendar.MINUTE));
        calin = month + "/" + day + "/" + year + " " + hour + ":" + minute;
        String calout;
        calendar.setTimeInMillis(entry.getOutDateTimeMillis());
        month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
        day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        year = Integer.toString(calendar.get(Calendar.YEAR));
        hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        minute = Integer.toString(calendar.get(Calendar.MINUTE));
        calout = month + "/" + day + "/" + year + " " + hour + ":" + minute;
        int hours = entry.getDuration() / 3600;
        int minutes = entry.getDuration() / 60;
        int seconds = entry.getDuration() % 60;
        String first = Integer.toString(hours) + " hours, " + Integer.toString(minutes) +
                " minutes, " + Integer.toString(seconds) + " seconds for " + entry.getCompany()
                + " at " + entry.getSite();
        String second = "between " + calin + " and " + calout;

        TextView line1 = (TextView) convertView.findViewById(R.id.first_line);
        TextView line2 = (TextView) convertView.findViewById(R.id.second_line);
        line1.setText(first);
        line2.setText(second);

        return convertView;
    }
}
