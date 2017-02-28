package cs65.punchphone.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Patrick on 2/26/17.
 */

public class WelcomeServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        //setup the logger for debugging purposes
       Logger logger = Logger.getLogger (WelcomeServlet.class.getName());

        //get the username and password from the request. Should have been passed in on the previous page
        String employerName=request.getParameter("employername");
        String password=request.getParameter("password");
        logger.log(Level.INFO,employerName);
        logger.log(Level.INFO,password);

        //check to see if the username and password are empty, if they are return to the login page
        //with an error
        if (employerName==null || password==null||
                employerName.length()==0 || password.length()==0){
            response.sendRedirect("loginError.html");
        }



        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.setAttribute("employername",employerName);
        out.write("<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n" +
                "<title>PunchPhone: Employer Home</title>\n" +
                "<link rel=\"stylesheet\" href=\"//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css\">\n" +
                "    <link rel=\"stylesheet\"\n" +
                "          href=\"//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css\">"+
                "</head>\n" +
                "<center>\n" +
                "<h1><font color=green>Welcome to Your PunchPhone Employer Page</font></h1>\n" +
                "</center><br>" +
                "<body role=\"document\" style=\"padding: 40px;\">"+
                "<h2><font color=green>Employer: "+employerName+"</font></h2>\n<center>"+
                "<h3>Main Menu</h3>\n"+
                "<a href=\"history.do?username="+employerName+"\">Punch History</a><br>" +
                "<a href=\"settings.do?username="+employerName+"\">Employer Settings</a></br>\n"+
                "<a href=\"login.html\">Logout</a>" +
                "</center></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        doGet(request, response);
    }
}
