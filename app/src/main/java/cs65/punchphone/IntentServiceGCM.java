package cs65.punchphone;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by Jack on 2/16/17.
 */

public class IntentServiceGCM extends IntentService {

    //the constructor method for this class
    public IntentServiceGCM(){
        super("IntentServiceGCM");
    }

    //handles the message from the GCMs
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {  //
            String Stringgcm="gcm";
            // see what the message type was and try to get its information
            if (Stringgcm.equals(messageType)) {

                String content=extras.getString("message");

                Log.d("message in receiver",content);

            }
        }
        //end the intent
        ExtendedBroadcastReceiver.completeWakefulIntent(intent);
    }

}
