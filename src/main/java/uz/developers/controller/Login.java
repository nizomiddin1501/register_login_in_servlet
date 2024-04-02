package uz.developers.controller;

import uz.developers.model.User;
import uz.developers.service.DbService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/login")
public class Login extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.html");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        PrintWriter printWriter = resp.getWriter();
        DbService dbService = new DbService();
        User user = dbService.login(username, password);
        if (user == null) {
            printWriter.write("<h1>Password or login error</h1>");
        } else {
            Cookie cookie = new Cookie("authApp",user.getUsername());
            cookie.setMaxAge(60*60);
            resp.addCookie(cookie);
            resp.sendRedirect("/cabinet");
        }
    }
}
