package servlets;

import dbService.DBException;
import dbService.DBService;
import entity.UserProfile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUpServlet extends HttpServlet {

    private DBService dbService;

    public SignUpServlet(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {
                dbService.addUser(new UsersDataSet(login, password));
                response.getWriter().println("Signing up successfully");
        } catch (DBException e) {
            LOGGER.error("doPost error in SignUpServlet", e);
          }
    }
}
