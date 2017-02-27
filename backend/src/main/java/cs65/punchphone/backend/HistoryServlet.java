package cs65.punchphone.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs65.punchphone.backend.data.Punch;
import cs65.punchphone.backend.data.PunchDataStore;

/**
 * Created by Patrick on 2/26/17.
 */

public class HistoryServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.write("<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n" +
                "<style>\n" +
                "table, th, td {\n" +
                "border: 1px solid black;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n");
        out.write("<table>\n" +
                "<tr>\n" +
                "<th>User ID</th>\n" +
                "<th>Punch In</th>\n" +
                "<th>Punch Out</th>\n" +
                "<th>Latitude</th>\n" +
                "<th>Longitude</th>\n" +
                "</tr>\n");
        ArrayList<Punch> pList = PunchDataStore.query(request.getParameter("id"));
        for (Punch p: pList) {
            out.write("<tr>\n" +
                    "<td>" + p.mUserId + "</td>\n" +
                    "<td>" + p.mPunchIn + "</td>\n" +
                    "<td>" + p.mPunchOut + "</td>\n" +
                    "<td>" + p.mLatitude + "</td>\n" +
                    "<td>" + p.mLongitude + "</td>\n" +
//                    "<td><input type=\"button\" onclick=\"location.href='/delete.do?id="+p.mUserId+
//                    "'\" value=\"Delete\"></td>\n" +
                    "</tr>\n");
        }
        out.write("</table>\n");
        out.write("</body>\n" +
                "</html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
