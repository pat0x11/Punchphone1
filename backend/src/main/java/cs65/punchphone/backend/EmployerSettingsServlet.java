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
 * Created by Jack on 2/28/17.
 */

public class EmployerSettingsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Logger logger = Logger.getLogger (WelcomeServlet.class.getName());

        String username=request.getParameter("username");
        logger.log(Level.INFO,username);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.write("<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n" +
                "<title>PunchPhone: Employer Settings</title>\n" +
                "</head>\n" +
                "<center>\n" +
                "<h1>Employer Settings</h1>\n" +
                "</center><br>" +
                "<body>"+
                "<h2>Employer: "+username+"</h2>\n"+
                "<footer><a href=\"login\">Back to Login</a>" +
                "</footer>"+
                "</html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        doGet(request, response);
    }
}
