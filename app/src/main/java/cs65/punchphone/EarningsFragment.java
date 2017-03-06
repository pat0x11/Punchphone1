package cs65.punchphone;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import cs65.punchphone.data.PunchEntry;
import cs65.punchphone.data.PunchEntryDbHelper;

// comment
// we changed this 
public class EarningsFragment extends Fragment {

    GraphView graphView;
    Context context;
    TableLayout tableLayout;
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

        for (int i = 0; i < values.size(); i++) {
            points[i] = new DataPoint(i + 1, values.get(i).getEarnings
                    ());

        }

        drawChart();
        return view;
    }

    public void drawChart() {
//        Log.d("Graphing", points[0].toString());

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        graphView.addSeries(series);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMaxX(4);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setScalable(true);
        graphView.setTitle("Earnings by Date");
        graphView.setTitleTextSize(100);
        graphView.setHorizontalScrollBarEnabled(true);
        GridLabelRenderer labelRenderer = graphView.getGridLabelRenderer();
        labelRenderer.setHorizontalAxisTitle("Dates");
        labelRenderer.setVerticalAxisTitle("Earnings in $");
    }


}