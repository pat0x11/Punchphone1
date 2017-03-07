package cs65.punchphone.backend;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs65.punchphone.backend.data.Employer;
import cs65.punchphone.backend.data.EmployerDataStore;

/**
 * Created by Jack on 3/2/17.
 */

//the servlet that handles the operation of sending all employers upon app opening
public class appOpenedServlet extends HttpServlet {
    public static final String NEW_CONNECTION="newConnection";
    private static Logger mLogger= Logger.getLogger(appOpenedServlet.class.toString());
    //the get method sends a message containing all known employers, their locations, and the radius
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        mLogger.log(Level.INFO,"Coming to appOpenedServlet");
        

        //at this point, we know that the user wants every thing from the employer datastore
        //get the array list of employer information from the datastore
        ArrayList<Employer> allEmployers= EmployerDataStore.getAllEmployersInDatastore();

        JSONArray employersArray=new JSONArray();

        //go through every employer in the datastore and get its company, location, and radius
        for (Employer current:allEmployers){
            //get the JSON Object from the string
            JSONObject toAdd=getJSONfromEmployer(current);
            employersArray.add(toAdd);
        }

        //after adding in all of the objects, convert the Json array to a string
        String jsonString=employersArray.toString();

        //use the google cloud messaging to alert the local database on the phone that something was deleted
        //alert the user on the device that the entries were updated
        MessagingEndpoint msg=new MessagingEndpoint();
        msg.sendMessage("allEmployers:"+jsonString);
    }


    //the doPost method should be identical to doGet, because it will never get called
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request,response);
    }

    //a helper method that will convert an employer object to a JSON Object
    private static JSONObject getJSONfromEmployer(Employer toConvert){
        //we only care about it's company, location, and radius
        JSONObject toReturn=new JSONObject();

        //COMPANY
        String company=toConvert.mCompany;
        toReturn.put("company",company);

        //RADIUS
        String radius=toConvert.mRadius;
        toReturn.put("radius",radius);

        //put in the address
        String street=toConvert.mStreet;
        String city=toConvert.mCity;
        String state=toConvert.mState;
        String zip=toConvert.mZip;
        String normalTime=toConvert.mNormal;
        String overtime=toConvert.mOvertime;

        //add them to the JSON Object
        toReturn.put("street",street);
        toReturn.put("city",city);
        toReturn.put("state",state);
        toReturn.put("zip",zip);
        toReturn.put("normalHrs",normalTime);
        toReturn.put("overtimeHrs",overtime);

        return toReturn;

    }
}


