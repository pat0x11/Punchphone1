package cs65.punchphone.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs65.punchphone.backend.data.Employer;
import cs65.punchphone.backend.data.EmployerDataStore;

/**
 * Created by Jack on 2/28/17.
 */

public class EmployerSettingsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Logger logger = Logger.getLogger (WelcomeServlet.class.getName());

        String username=request.getParameter("username");
        logger.log(Level.INFO,username);
        response.setContentType("text/html");

        //get the current Employer from the username
        Employer current= EmployerDataStore.getEmployerByUsername(username);

        //setup the print writer that will contain all of the settings
        PrintWriter out = response.getWriter();

        out.write("<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n" +
                "<title>PunchPhone: Employer Settings</title>\n" +
                "<link rel=\"stylesheet\" href=\"//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css\">\n" +
                "    <link rel=\"stylesheet\"\n" +
                "          href=\"//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css\">"+
                "</head>\n" +
                "<center>\n" +
                "<h1><font color=green>Employer Settings</font></h1>\n" +
                "</center><br>" +
                "<body>"+
                "<h2>Employer: "+current.mCompany+"</h2><center>\n"+
                "<legend><h3>Update Profile</h3></legend><br></center>\n" +
                "<p>Please use the following fields to update your desired fields. If you do not wish to " +
                "update a field, skip it.</p><br>"+
                "<form name=input action=update.do?oldusername="+username+" method=post>"+

                "Username:     <input type=text name=newUsername placeholder="+current.mUsername+"><br>"+
                "Password:     <input type=password name=newPassword placeholder="+current.mPassword+"><br>"+
                "Company Name: <input type=text name=newCompany placeholder="+current.mCompany+"><br>"+
                "Street:       <input type=text name=newStreet placeholder="+current.mStreet+"><br>"+
                "City:         <input type=text name=newCity placeholder="+current.mCity+"><br>"+
                "State:        <input type=text name=newState placeholder="+current.mState+"><br>"+
                "Zipcode:      <input type=text name=newZipcode placeholder="+current.mZip+"><br>"+
                "Radius:       <input type=text name=newRadius placeholder="+current.mRadius+"><br>" +
                "Normal Hours: <input type=text name=newNormal placeholder="+current.mNormal+"><br>"+
                "Overtime Hours: <input type=text name=newOvertime placeholder="+current.mOvertime+"><br>"+
                "</br>"+
                "<input type=submit value=\"Update Profile\" name=submitButton><br>"+
                "</form><footer><a href=\"welcome.do?employername="+username+"&password="
                +current.mPassword+"\">Back to Welcome Page</a>" +
                "</footer>"+
                "</html>");
    }

    //the do post method is the same as the doGet method in this case
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        doGet(request, response);
    }
}
