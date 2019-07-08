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
                accountService.addNewUser(new UserProfile(
                request.getParameter("login"),
                "password",
                "email"));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
