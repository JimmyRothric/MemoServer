package servlet;

import com.google.gson.Gson;
import entity.Account;
import service.AccountService;
import service.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AccountServlet")
public class AccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        String function = request.getParameter("function");

        AccountService accountService = new AccountServiceImpl();

        if (function == null || function == "") {
            response.getWriter().write("test");
            return;
        }

        if (function.equals("login")) {
            String id = request.getParameter("id");
            String password = request.getParameter("password");
            String result = accountService.login(id, password);
            response.getWriter().write(result);
        }
        if (function.equals("register")) {
            String obj = request.getParameter("obj");
            boolean success = accountService.register(gson.fromJson(obj, Account.class));
            response.getWriter().write(String.valueOf(success));
        }
        if (function.equals("setName")) {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            boolean success = accountService.setName(id, name);
            response.getWriter().write(String.valueOf(success));
        }
        if (function.equals("setPassword")) {
            String id = request.getParameter("id");
            String password = request.getParameter("password");
            boolean success = accountService.setPassword(id, password);
            response.getWriter().write(String.valueOf(success));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
