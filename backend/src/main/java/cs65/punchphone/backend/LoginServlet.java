package cs65.punchphone.backend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Patrick on 2/26/17.
 */

public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.write("<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n" +
                "<title>PunchPhone</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<center>\n" +
                "<h1>Welcome to PunchPhone Server</h1>\n" +
                "</center><br>" +
                "<h2>Login</h2>"+
                "<p>"+
                "<b>Please enter your company name in the search box below in order to locate your " +
                "records.</b></p>"+
                "<center>"+
                "<form name=\"input\" action=\"welcome.do\" method=\"get\">"+
                "Employer Name: <input type=text name=employername>"+
                "<input type=submit name=submitbutton></form>\n"+
                "<br>"+
                "</center>\n" +
                "</body>\n" +
                "</html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
        doGet(request, response);
    }
}
