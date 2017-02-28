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
        Logger logger = Logger.getLogger (WelcomeServlet.class.getName());

        String employerName=request.getParameter("employername");
        logger.log(Level.INFO,employerName);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.setAttribute("employername",employerName);
        out.write("<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n" +
                "<title>PunchPhone</title>\n" +
                "</head>\n" +
                "<center>\n" +
                "<h1>Welcome to Your PunchPhone Employer Page</h1>\n" +
                "</center><br>" +
                "<body>"+
                "<h2>Employer: "+employerName+"</h2>\n"+
                "<a href=\"history.do\">Punch History</a></br>" +
                "<br><a href=\"settings.do\">Employer Settings</a></br>\n"+
                "</body>\n \n" +
                "<footer><a href=\"login\">Back to Login</a>" +
                "</footer>"+
                "</html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        doGet(request, response);
    }
}
