package cs65.punchphone;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;

import cs65.punchphone.data.PunchEntry;
import cs65.punchphone.data.PunchEntryDbHelper;

// comment
// we changed this 
public class EarningsFragment extends Fragment {

    GraphView graphView;
    Context context;
    private ArrayList<PunchEntry> values;
    private DataPoint[] points;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_earnings, container, false);
        graphView = (GraphView) view.findViewById(R.id.graph);
        context = getActivity().getApplicationContext();
        Log.d("Earnings", "loading data next");
        PunchEntryDbHelper punchEntryDbHelper = new
                PunchEntryDbHelper(getActivity());
        values = punchEntryDbHelper.fetchEntries();
        Log.d("Earnings", "Number of values " + values.size());
        points = new DataPoint[values.size()];
        String[] labels = new String[values.size()];
        for (int i = 0; i < values.size(); i++) {
            points[i] = new DataPoint(i + 1, values.get(i).getEarnings());
            labels[i] = dateTimeToString(values.get(i).getInDateTimeMillis());
            Log.d("day", "" +values.get(i).getInDateTimeMillis());
        }

        drawChart(labels);
        return view;
    }

    public void drawChart(String[] labels) {

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        if(labels.length >2) {
            StaticLabelsFormatter staticLabelsFormatter = new
                    StaticLabelsFormatter(graphView);
            staticLabelsFormatter.setHorizontalLabels(labels);
            graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        }
        graphView.addSeries(series);
        graphView.getViewport().setScalable(true);
        graphView.setTitle("Earnings by Date");
        graphView.setTitleTextSize(100);
        GridLabelRenderer labelRenderer = graphView.getGridLabelRenderer();
        labelRenderer.setHorizontalAxisTitle("Dates");
        labelRenderer.setVerticalAxisTitle("Earnings in $");
    }

    public String dateTimeToString(Long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        String result = "";
        result += (calendar.get(Calendar.MONTH)+1) +  "-" + calendar.get
                (Calendar
                .DAY_OF_MONTH);
        return result;
    }

}