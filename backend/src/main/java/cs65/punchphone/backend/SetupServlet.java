package cs65.punchphone.backend;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        mLogger.log(Level.INFO,username);
        mLogger.log(Level.INFO,password);
        mLogger.log(Level.INFO,company);
        mLogger.log(Level.INFO,street);
        mLogger.log(Level.INFO,city);
        mLogger.log(Level.INFO,state);
        mLogger.log(Level.INFO,zipcode);
        mLogger.log(Level.INFO,range);

        //make sure none of the fields are null, if they are...send the user back to the setup page
        if (!checkParameters(username,password,company,street,city,state,zipcode,range)){
            response.sendRedirect("setupError.html");
        }
        else{
            mLogger.log(Level.INFO,"Successful Login");
        }
    }

    //the get method for this class, should be the same as post
    public void doGet(HttpServletRequest request, HttpServletResponse response){

    }

    //a helper method that checks to see if the parameters are null
    public boolean checkParameters(String username,String password, String company, String street, String city,
                           String state, String zipcode, String radius){
        //first check if they are null
        if (username==null||password==null||company==null||street==null||city==null||state==null||
                zipcode==null||radius==null){
            return false;
        }
        if (username.length()==0||password.length()==0||company.length()==0||street.length()==0||
                city.length()==0||state.length()==0||zipcode.length()==0||radius.length()==0){
            return false;
        }
        //at this point the inputs are valid
        return true;
    }
}


