package cs65.punchphone.backend;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs65.punchphone.backend.data.Employer;
import cs65.punchphone.backend.data.EmployerDataStore;

/**
 * Created by Jack on 2/28/17.
 */

public class updateInformation extends HttpServlet {
    //the logger for debugging purposes
    Logger mLogger=Logger.getLogger(updateInformation.class.toString());
    //doPost method
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //get all of the information including the old username
        String oldUsername=request.getParameter("oldusername");
        //get the Employer entry from the datastore that corresponds to this username
        Employer oldEntry= EmployerDataStore.getEmployerByUsername(oldUsername);

        //get the other parameters that correspond to updated information
        String newUsername=request.getParameter("newUsername");
        String newPassword=request.getParameter("newPassword");
        String newCompany=request.getParameter("newCompany");
        String newStreet=request.getParameter("newStreet");
        String newCity=request.getParameter("newCity");
        String newState=request.getParameter("newState");
        String newZip=request.getParameter("newZipcode");
        String newRadius=request.getParameter("newRadius");

        //setup a new Employer that will be used to update in the datastore
        //initially all values are equal to the old values
        String toAddUsername=oldUsername;
        String toAddPassword=oldEntry.mPassword;
        String toAddCompany=oldEntry.mCompany;
        String toAddStreet=oldEntry.mStreet;
        String toAddCity=oldEntry.mCity;
        String toAddState=oldEntry.mState;
        String toAddZip=oldEntry.mZip;
        String toAddRadius=oldEntry.mRadius;

        //check each of the new values to see if they are null
        //if they aren't, then replace the toAdd... values with the new value
        if (newUsername!=null &&newUsername.length()!=0){
            toAddUsername=newUsername;
        }
        if (newPassword!=null && newPassword.length()!=0){
            toAddPassword=newPassword;
        }
        if (newCompany!=null && newCompany.length()!=0){
            toAddCompany=newCompany;
        }
        if (newStreet!=null && newStreet.length()!=0){
            toAddStreet=newStreet;
        }
        if (newCity!=null && newCity.length()!=0){
            toAddCity=newCity;
        }
        if (newState!=null && newState.length()!=0){
            toAddState=newState;
        }
        if (newZip!=null && newZip.length()!=0){
            toAddZip=newZip;
        }
        if (newRadius!=null && newRadius.length()!=0){
            toAddRadius=newRadius;
        }

        //create a new employer object
        Employer toAdd=new Employer(toAddUsername,toAddPassword,toAddCompany,toAddStreet,toAddCity,toAddState,
                toAddZip,toAddRadius);

        //at this point, all values are checked, so update the employer in the datastore
        EmployerDataStore.updateEntry(oldUsername,toAdd);
        mLogger.log(Level.INFO,"Successfully updated entry");
        //redirect the user back to the welcome page
        response.sendRedirect("welcome.do?employername="+toAddUsername+"&password="+toAddPassword);
    }

    //doGet Method should be the same as doPost
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
}
