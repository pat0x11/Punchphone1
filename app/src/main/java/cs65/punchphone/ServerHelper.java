package cs65.punchphone;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Jack on 3/2/17.
 */

public class ServerHelper {




    //handles POSTing to the server
    //that is takes an exercise entry and sends it to the database
    public static String getAllEmployers() throws IOException {

        //setup the url by parsing the first input
        URL url;
        try{
            url=new URL(Globals.backendURL+"connect.do");
        }
        catch (MalformedURLException urlE){
            throw new IllegalArgumentException("Please enter a valid url.");
        }

        //construct the message to be sent
        //convert the messageBuilder to bytes...doesn't matter what the message is
        String body="test";
        byte[] bytesArray=body.getBytes();

        //try to open the connection
        HttpURLConnection conn=null;
        try{
            conn=(HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytesArray.length);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");

            //use this request to send the request
            OutputStream toDB=conn.getOutputStream();
            toDB.write(bytesArray);
            toDB.close();

            //get the response
            int responseCode=conn.getResponseCode();
            Log.d("POST RESPONSE: ",Integer.toString(responseCode));
            if (responseCode!=200){
                throw new IOException("SERVERHELPER FAILED: CODE="+responseCode);
            }
            // Get Response
            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\n');
            }
            rd.close();
            return response.toString();
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
