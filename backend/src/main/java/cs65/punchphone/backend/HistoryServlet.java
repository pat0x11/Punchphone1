package cs65.punchphone.backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;

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

//HistoryServlet allows the employer to view employee punches and
public class HistoryServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        //get company name from username
        String companyname = EmployerDataStore.getCompanyName(username);

        //manually write out all of the html fields
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
                "</tr>\n");
        //get all pucnhes from database
        ArrayList<Punch> pList = PunchDataStore.query(null);
        ArrayList<Punch> pList1 = new ArrayList<>();
        //get userid from form data
        String userid = request.getParameter("userid");
        //filter by company
        for (Punch p: pList) {
            if (userid==null || userid.equals("")) {
                if (p.mCompany.equals(companyname)) {
                    pList1.add(p);
                }
            //filter by company and userid
            } else {
                if (p.mUserId.equals(userid) && p.mCompany.equals(companyname)) {
                    pList1.add(p);
                }
            }
        }
        //get calendar strings
        for (Punch p: pList1) {
            String calin;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.valueOf(p.mPunchIn));
            String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
            String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            String year = Integer.toString(calendar.get(Calendar.YEAR));
            String hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
            String minute = Integer.toString(calendar.get(Calendar.MINUTE));
            calin = month + "/" + day + "/" + year + " " + hour + ":" + minute;
            String calout;
            calendar.setTimeInMillis(Long.valueOf(p.mPunchOut));
            month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
            day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
            year = Integer.toString(calendar.get(Calendar.YEAR));
            hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
            minute = Integer.toString(calendar.get(Calendar.MINUTE));
            calout = month + "/" + day + "/" + year + " " + hour + ":" + minute;
            //write punches to table
            out.write("<tr>\n" +
                    "<td>" + p.mPunchId + "</td>\n" +
                    "<td>" + p.mUserId + "</td>\n" +
                    "<td>" + calin + "</td>\n" +
                    "<td>" + calout + "</td>\n" +
                    "<td>" + p.mCompany + "</td>\n" +
                    "<td>" + p.mSite + "</td>\n" +
                    "</tr>\n");
        }
        out.write("</table>\n");
        out.write("</body><br>" +
                "</html>");
    }
    //doPost is the same as doGet
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
