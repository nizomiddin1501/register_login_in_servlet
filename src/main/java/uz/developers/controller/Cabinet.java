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

@WebServlet(value = "/cabinet")
public class Cabinet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter = resp.getWriter();
        Cookie[] cookies = req.getCookies();
        String username = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("authApp")) {
                    username = cookie.getValue();
                    break;
                }
            }
        }
        DbService dbService = new DbService();
        User user = dbService.loadUserByCookies(username);
        if (user == null) {
            Cookie cookie = new Cookie("authApp", "");
            cookie.setMaxAge(0);
            resp.addCookie(cookie);
            resp.sendRedirect("/");
        }
        if (user != null) {
            printWriter.write("<h1>Welcome to system " + user.getFirstname() + " " + user.getLastname() + "</h1>");
            printWriter.write("<h1>Your phone number: " + user.getPhoneNumber() + "</h1>");
        }
    }
}
