package cs65.punchphone;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

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
        Log.d("IntentService", extras.toString());
        if (extras != null && !extras.isEmpty()) {  //
            String Stringgcm="gcm";
            // see what the message type was and try to get its information
            if (Stringgcm.equals(messageType)) {

                String content=extras.getString("message");
                Log.d("Intent", content);
                org.json.simple.JSONArray allEmployers=null;

                //convert the string to a JSON Array
                try {
                    allEmployers=(org.json.simple.JSONArray)new JSONParser().parse
                            (content);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //go through all JSON Objects in the JSON Array
                for (int i=0;i<allEmployers.size();i++){
                    //convert it into a JSON object
                    JSONObject currentObj=null;
                    currentObj=(JSONObject)allEmployers.get(i);


                    //only continue if there is a valid JSON Object (non-null)
                    if (currentObj!=null){
                        //get the relevant fields from the object
                        String company=currentObj.get("company").toString();
                        String radius=currentObj.get("radius").toString();
                        String street=currentObj.get("street").toString();
                        String city=currentObj.get("city").toString();
                        String state=currentObj.get("state").toString();
                        String zip=currentObj.get("zip").toString();
                        String normalHours=currentObj.get("normalHrs").toString();
                        String overtimeHours=currentObj.get("overtimeHrs").toString();

                        FrontEndEmployer toAddEmployer=null;
                        //create a new front end employer object
                        try {
                            toAddEmployer=new FrontEndEmployer(company,street,
                                    state,city,zip,radius,normalHours,overtimeHours,getApplicationContext());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //check to make sure that there are no errors
                        if (toAddEmployer!=null){
                            //add it to a list of these objects

                            //ADD HERE
                            MainActivity.employers.add(toAddEmployer);
                        }

                    }

                    Log.d("Intent", "Getting data");
                }

            }
        }
        //end the intent
        ExtendedBroadcastReceiver.completeWakefulIntent(intent);
    }

}