package cs65.punchphone.backend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs65.punchphone.backend.data.Punch;
import cs65.punchphone.backend.data.PunchDataStore;

/**
 * Created by Patrick on 3/6/17.
 */

//AddServlet adds punchout from front end to PunchDatastore
public class AddServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String userid = request.getParameter("name");
        String company = request.getParameter("company");
        String site = request.getParameter("site");
        String punchIn = request.getParameter("punchin");
        String punchOut = request.getParameter("punchout");
        //punch id is concatenation of userid and punchout time in milliseconds
        String punchid = userid + punchOut;

        Punch p = new Punch(punchid, userid, company, punchIn, punchOut, site);
        //add to datastore
        PunchDataStore.add(p);
        response.sendRedirect("/history.do");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}