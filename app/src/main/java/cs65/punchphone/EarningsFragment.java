package cs65.punchphone;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

// comment
// we changed this 
public class EarningsFragment extends Fragment {

    GraphView graphView;
    Context context;
    TableLayout tableLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_earnings, container, false);
        graphView = (GraphView) view.findViewById(R.id.graph);
        context = getActivity().getApplicationContext();
        tableLayout = (TableLayout) view.findViewById(R.id.table_layout);
        drawChart();
        return view;
    }

    public void drawChart() {
        DataPoint[] points = new DataPoint[50];
        for (int i = 0; i < 50; i++) {
            points[i] = new DataPoint(i, Math.sin(i*0.5) * 20*(Math.random()*10+1));

        }
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(points);

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
