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
            {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        UserProfile newUser = new UserProfile(login, password);
        try {
            dbService.addUser(login, password);
        } catch (DBException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
