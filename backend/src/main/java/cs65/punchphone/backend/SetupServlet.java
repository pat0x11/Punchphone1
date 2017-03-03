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

public class SetupServlet extends HttpServlet {

    //the post method for this class
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //get a logger for debugging
        Logger mLogger= Logger.getLogger(SetupServlet.class.toString());

        //attempt to get all of the parameters
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String company=request.getParameter("company");
        String street=request.getParameter("street");
        String city=request.getParameter("city");
        String state=request.getParameter("state");
        String zipcode=request.getParameter("zipcode");
        String range=request.getParameter("radius");
        String normalHr=request.getParameter("normal");
        String overtime=request.getParameter("overtime");

        mLogger.log(Level.INFO,username);
        mLogger.log(Level.INFO,password);
        mLogger.log(Level.INFO,company);
        mLogger.log(Level.INFO,street);
        mLogger.log(Level.INFO,city);
        mLogger.log(Level.INFO,state);
        mLogger.log(Level.INFO,zipcode);
        mLogger.log(Level.INFO,range);
        mLogger.log(Level.INFO,normalHr);
        mLogger.log(Level.INFO,overtime);

        //make sure none of the fields are null, if they are...send the user back to the setup page
        if (!checkParameters(username,password,company,street,city,state,zipcode,range,normalHr,overtime)){
            response.sendRedirect("setupError.html");
        }
        else{

            //try to add to the datastore
            Employer newEmployer=new Employer(username,password,company,street,city,state,zipcode,range,
                    normalHr,overtime);
            EmployerDataStore.addNewUser(newEmployer);

            mLogger.log(Level.INFO,"Successful Addition");
            //redirect the employer to the welcome page
            response.sendRedirect("welcome.do?employername="+username+"&password="+password);
        }
    }

    //the get method for this class, should be the same as post
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }

    //a helper method that checks to see if the parameters are null
    public boolean checkParameters(String username,String password, String company, String street, String city,
                           String state, String zipcode, String radius, String normal, String overtime){
        //first check if they are null
        if (username==null||password==null||company==null||street==null||city==null||state==null||
                zipcode==null||radius==null || normal==null || overtime==null){
            return false;
        }
        if (username.length()==0||password.length()==0||company.length()==0||street.length()==0||
                city.length()==0||state.length()==0||zipcode.length()==0||radius.length()==0
                || normal.length()==0 || overtime.length()==0){
            return false;
        }
        //at this point the inputs are valid
        return true;
    }
}


