package cs65.punchphone;

import android.os.AsyncTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jack on 2/25/17.
 */

public class TimeUpdater extends AsyncTask {
    public static String asyncDate;
    public static String asyncTime;

    public TimeUpdater(){
        Calendar current=Calendar.getInstance();
        Date currentTime=current.getTime();

        //get the date aspect first
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
        String time = timeFormat.format(currentTime);

        //get the day component
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMMMM dd yyyy");
        String date=dateFormat.format(currentTime);

        //update the text views
        EntryPageActivity.dateView.setText(time);

        this.asyncDate=date;
        this.asyncTime=time;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        //continue so long as the app calling on it is in use
        while(!isCancelled()){
            Calendar current=Calendar.getInstance();
            Date currentTime=current.getTime();

            //get the date aspect first
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a");
            String time = timeFormat.format(currentTime);

            //get the day component
            SimpleDateFormat dateFormat=new SimpleDateFormat("MMMMM dd yyyy");
            String date=dateFormat.format(currentTime);

            asyncDate=date;
            asyncTime=time;

            //pause for 5 seconds
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
