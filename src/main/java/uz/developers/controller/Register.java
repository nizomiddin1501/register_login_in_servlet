package uz.developers.controller;

import uz.developers.model.Result;
import uz.developers.model.User;
import uz.developers.service.DatabaseService;
import uz.developers.service.DbService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Register extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("register.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String username = req.getParameter("username");
        String phoneNumber = req.getParameter("phoneNumber");
        String password = req.getParameter("password");
        String prePassword = req.getParameter("prePassword");

        PrintWriter printWriter = resp.getWriter();

        if (password.equals(prePassword)){
            DbService dbService = new DbService();
            User user = new User(firstname,lastname,phoneNumber,username,password);
            Result result = dbService.registerUser(user);
            if (result.isSuccess()){
                printWriter.write("<h1 color='green'>"+result.getMessage()+"</h1>");
            }else {
                printWriter.write("<h1 color='red'>"+result.getMessage()+"</h1>");
            }
        }else {
            printWriter.write("<h1 color='red'>Passwords not equals</h1>");
        }


    }
}
