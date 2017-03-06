package cs65.punchphone.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs65.punchphone.backend.data.EmployerDataStore;
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
        String username = request.getParameter("username");
        String companyname = EmployerDataStore.getCompanyName(username);
//        request.setAttribute("username", username);
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
        out.write("<form name=\"userid\" action=\"history.do\" method=\"get\">\n" +
                "User ID: <input type=\"text\" name=\"userid\">\n" +
                "<input type=\"hidden\" name=\"username\" value=\""+username+"\">\n" +
                "<input type=\"submit\" value=\"Search By User ID\">\n" +
                "</form>\n");
        out.write("<table>\n" +
                "<tr>\n" +
                "<th>Punch ID</th>\n" +
                "<th>User ID</th>\n" +
                "<th>Punch In</th>\n" +
                "<th>Punch Out</th>\n" +
                "<th>Company</th>\n" +
                "<th>Site</th>\n" +
                "<th>Delete</th>\n" +
                "</tr>\n");
        ArrayList<Punch> pList = PunchDataStore.query(null);
        ArrayList<Punch> pList1 = new ArrayList<>();
        String userid = request.getParameter("userid");

        for (Punch p: pList) {
            if (userid==null || userid.equals("")) {
                if (p.mCompany.equals(companyname)) {
                    pList1.add(p);
                }
            } else {
                if (p.mUserId.equals(userid) && p.mCompany.equals(companyname)) {
                    pList1.add(p);
                }
            }
        }
        for (Punch p: pList) {
            out.write("<tr>\n" +
                    "<td>" + p.mPunchId + "</td>\n" +
                    "<td>" + p.mUserId + "</td>\n" +
                    "<td>" + p.mPunchIn + "</td>\n" +
                    "<td>" + p.mPunchOut + "</td>\n" +
                    "<td>" + p.mCompany + "</td>\n" +
                    "<td>" + p.mSite + "</td>\n" +
                    "<td><input type=\"button\" onclick=\"location.href='/delete.do?punchid="
                    +p.mPunchId+"&username="+username+ "&userid1="+userid+
                    "'\" value=\"Delete\"></td>\n" +
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
