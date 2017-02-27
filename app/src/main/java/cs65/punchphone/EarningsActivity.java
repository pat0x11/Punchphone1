package cs65.punchphone;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

public class EarningsActivity extends AppCompatActivity {

    LineChartView lineChartView;
    Context context;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings);
        lineChartView = (LineChartView) findViewById(R.id.chart);
        context = getApplicationContext();
        tableLayout = (TableLayout) findViewById(R.id.table_layout);
        drawChart();
    }

    public void drawChart() {

        List<PointValue> values = new ArrayList<PointValue>(); //the earnings
        BufferedReader bufferedReader; //reader for the file

        try{
            String filePath = context.getFilesDir().getPath(); //file path of data

            //Create way to read file
            FileReader fileReader = new FileReader(filePath);
            bufferedReader = new BufferedReader(fileReader);

            String line; //line being examined
            int lineNum = 1; //the index for the data

            //The layout style for the table
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);

            TableRow header = new TableRow(context); //The name of each column
            header.setLayoutParams(layoutParams);

            //Set up both text views
            TextView timeTitle = new TextView(context);
            timeTitle.setGravity(Gravity.CENTER);
            timeTitle.setPadding(10, 10, 10, 10);
            TextView stressTitle = new TextView(context);
            stressTitle.setGravity(Gravity.CENTER);
            stressTitle.setPadding(10, 10, 10, 10);

            //Title the text views
            timeTitle.setText("Time");
            stressTitle.setText("Earnings");

            //Add the text views to the row and the row to the table
            header.addView(timeTitle);
            header.addView(stressTitle);
            tableLayout.addView(header);

            while((line = bufferedReader.readLine()) != null){ //Get a line of data

                String[] split = line.split("\\|"); //Split the line at the defined break

                int temp = Integer.parseInt(split[1]); //get the earnings
                values.add(new PointValue(lineNum, temp)); //Create new point

                lineNum++; //Increment the index

                //Set up the table data similar to done with the header row but now with data
                TableRow currentRow = new TableRow(context);
                currentRow.setLayoutParams(layoutParams);

                TextView time = new TextView(context);
                time.setGravity(Gravity.CENTER);
                time.setPadding(10, 10, 10, 10);
                TextView stress = new TextView(context);
                stress.setGravity(Gravity.CENTER);
                stress.setPadding(10, 10, 10, 10);

                time.setText("" + split[0]); //getting from data file
                stress.setText("" + temp); //data file stress as used above
                currentRow.addView(time);
                currentRow.addView(stress);

                tableLayout.addView(currentRow);
            }
            bufferedReader.close(); //close the readers to prevent errors
            fileReader.close();
        } catch (IOException e){
            Log.d("Cj", "" + e);
        }

        //Set line from all the data points and add to list of lines
        Line line1 = new Line(values).setColor(Color.BLUE);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line1);

        //Put the lines in the chart data
        LineChartData lineChartData = new LineChartData();
        lineChartData.setLines(lines);

        //Get the axis values
        List<AxisValue> xAxisValues = new ArrayList<>();
        List<AxisValue> axisValuesForY = new ArrayList<>();
        AxisValue tempValue;

        //X axis is just the number of instances there are
        for(int i = 0; i <= values.size(); i++){
            tempValue = new AxisValue(i);
            tempValue.setLabel(""+i);
            xAxisValues.add(tempValue);
        }

        //Y axis is the number of stress levels which is 16
        for(int i = 0; i < 17; i++){
            tempValue = new AxisValue(i);
            tempValue.setLabel(""+i);
            axisValuesForY.add(tempValue);
        }

        //Name both axis and set their data
        Axis xAxis = new Axis(xAxisValues);
        xAxis.setName("Days");
        Axis yAxis = new Axis(axisValuesForY);
        yAxis.setName("Earnings");
        lineChartData.setAxisXBottom(xAxis);
        lineChartData.setAxisYLeft(yAxis);

        //apply the data to the chart
        lineChartView.setLineChartData(lineChartData);

    }

}
