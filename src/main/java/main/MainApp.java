package main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import dbService.DBService;
import servlets.SignInServlet;
import servlets.SignUpServlet;

public class MainApp {

    public static void main(String[] args) throws Exception {
        DBService dbService = new DBService();
        dbService.printConnectInfo();

        SignInServlet signInServlet = new SignInServlet(dbService);
        SignUpServlet signUpServlet = new SignUpServlet(dbService);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(signInServlet), "/signin");
        contextHandler.addServlet(new ServletHolder(signUpServlet), "/signup");

        Server server = new Server(8080);
        server.setHandler(contextHandler);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}
