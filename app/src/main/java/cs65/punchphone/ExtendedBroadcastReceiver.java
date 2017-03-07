package cs65.punchphone;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Jack on 2/16/17.
 */

public class ExtendedBroadcastReceiver extends WakefulBroadcastReceiver {

    //handles messages that come in from the broadcast receiver
    @Override
    public void onReceive(Context context, Intent intent) {
        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),
                IntentServiceGCM.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        MainActivity.dataReceived=true;
        EntryFragment.employerAdapter.clear();
        EntryFragment.employerAdapter.notifyDataSetChanged();
        EntryFragment.getInitialPunchStatus();
        setResultCode(Activity.RESULT_OK);
        Toast.makeText(context,"Loaded all employers" , Toast.LENGTH_SHORT).show();

    }
}
