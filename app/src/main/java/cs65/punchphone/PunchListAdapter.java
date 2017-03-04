package cs65.punchphone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cs65.punchphone.data.PunchEntry;

/**
 * Created by brian on 3/4/17.
 */

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

        String first = entry.getDateTimeMillis() + " at " + entry.getSite();
        String second = input + ": for " + entry.getDuration() + " earning: $" + entry.getEarnings();

        TextView line1 = (TextView) convertView.findViewById(R.id.first_line);
        TextView line2 = (TextView) convertView.findViewById(R.id.second_line);
        line1.setText(first);
        line2.setText(second);

        return convertView;
    }
}
