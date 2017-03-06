package cs65.punchphone.backend;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs65.punchphone.backend.data.PunchDataStore;

/**
 * Created by Patrick on 3/6/17.
 */

public class DeleteServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String punchid = request.getParameter("punchid");
        String userid1 = request.getParameter("userid1");
        String username = request.getParameter("username");
        PunchDataStore.delete(punchid);
        response.sendRedirect("/history.do?username="+username+"&userid="+userid1);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
