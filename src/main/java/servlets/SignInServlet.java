package servlets;

import dbService.DBService;
import entity.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class SignInServlet extends HttpServlet {

    private DBService dbService;

    public SignInServlet(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        try {
            if (dbService.getUser(login).getPassword().equals(password)) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().println("Authorized: " + login);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println("Unauthorized");
            }
        } catch (DBException e) {
            LOGGER.error("Error in SignInServlet", e);
        }
    }
}
