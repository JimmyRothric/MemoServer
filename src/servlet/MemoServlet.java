package servlet;

import com.google.gson.*;
import entity.Memo;
import service.MemoService;
import service.MemoServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "MemoServlet")
public class MemoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String function = request.getParameter("function");

        MemoService memoService = new MemoServiceImpl();

        if (function == null || function == "") {
            response.getWriter().write("test");
            return;
        }
        if (function.equals("addMemo")) {
            String obj = request.getParameter("obj");
            boolean success = memoService.addMemo(gson.fromJson(obj, Memo.class));
            response.getWriter().write(String.valueOf(success));
        }
        if (function.equals("updateMemo")) {
            String obj = request.getParameter("obj");
            boolean success = memoService.updateMemo(gson.fromJson(obj, Memo.class));
            response.getWriter().write(String.valueOf(success));
        }
        if (function.equals("setNotificationDate")) {
            String id = request.getParameter("id");
            String date = request.getParameter("date");

            Date notificationDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                notificationDate = sdf.parse(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            boolean success = memoService.setNotificationDate(id, notificationDate);
            response.getWriter().write(String.valueOf(success));
        }
        if (function.equals("synchronizeMemos")) {
            String list = request.getParameter("list");
            JsonParser parser = new JsonParser();
            if (list == null || list.length() < 1) {
                list = "[]";
            }
            JsonArray jsonArray = parser.parse(list).getAsJsonArray();
            ArrayList<Memo> memoList = new ArrayList<Memo>();

            for (JsonElement element : jsonArray) {
                Memo memo = gson.fromJson(element, Memo.class);
                memoList.add(memo);
            }
            boolean success = memoService.synchronizeMemos(memoList);
            response.getWriter().write(String.valueOf(success));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String function = request.getParameter("function");

        MemoService memoService = new MemoServiceImpl();

        if (function == null || function == "") {
            response.getWriter().write("test");
            return;
        }
        if (function.equals("delMemo")) {
            String id = request.getParameter("id");
            boolean success = memoService.delMemo(id);
            response.getWriter().write(String.valueOf(success));
        }
        if (function.equals("getSingleMemo")) {
            String id = request.getParameter("id");
            String result = memoService.getSingleMemo(id);
            response.getWriter().write(result);
        }
        if (function.equals("getAllMyMemos")) {
            String accId = request.getParameter("accId");
            String result = memoService.getAllMyMemos(accId);
            response.getWriter().write(result);
        }
        if (function.equals("getAllDiscardedMemos")) {
            String accId = request.getParameter("accId");
            String result = memoService.getAllDiscardedMemos(accId);
            response.getWriter().write(result);
        }
    }
}
