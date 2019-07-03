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
        UserProfile user = null;
        try {
            user = dbService.getUser(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (Objects.isNull(user)) {
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else if (password.equals(user.getPassword())) {
            response.getWriter().print("Authorized: " + login);
            response.getWriter().print(HttpServletResponse.SC_OK);
        }
    }
}
