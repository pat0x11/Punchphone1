package cs65.punchphone;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import cs65.punchphone.backend.registration.Registration;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by Patrick on 2/26/17.
 * Async Task that registers app with backend
 */

public class GCMRegAsyncTask extends AsyncTask<Void, Void, String> {
    private static Registration registrationService = null;
    private GoogleCloudMessaging googleCloudMessaging;
    private Context context;

    private static final String SENDER_ID = "654592596630";

    public GCMRegAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (registrationService == null) {
            Registration.Builder builder =
                    new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                            new AndroidJsonFactory(), null)
                            // Need setRootUrl and setGoogleClientRequestInitializer only for local testing,
                            // otherwise they can be skipped
                            .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                                @Override
                                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                        throws IOException {
                                    abstractGoogleClientRequest.setDisableGZipContent(true);
                                }
                            });
            // end of optional local run code
            registrationService = builder.build();
        }

        String message = "";
        try {
            if (googleCloudMessaging == null) {
                googleCloudMessaging = GoogleCloudMessaging.getInstance(context);
            }
            String regId = googleCloudMessaging.register(SENDER_ID);
            message = "Device registered, registration ID=" + regId;
            MainActivity.registered = true;

            // You should send the registration ID to your server over HTTP,
            // so it can use GCM/HTTP or CCS to send messages to your app.
            // The request to your server should be authenticated if your app
            // is using accounts.
            registrationService.register(regId).execute();

        } catch (IOException ex) {
            ex.printStackTrace();
            message = "Error: " + ex.getMessage();
        }
        return message;
    }

    @Override
    protected void onPostExecute(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }
}