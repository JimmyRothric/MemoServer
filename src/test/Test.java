package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Memo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class Test {

//    private static final String ACCOUNTSERVLET_URL_HEAD = "http://localhost:8080/AccountServlet";
//    private static final String MEMOSERVLET_URL_HEAD = "http://localhost:8080/MemoServlet";
    private static final String ACCOUNTSERVLET_URL_HEAD = "http://119.23.74.157:8080/AccountServlet";
    private static final String MEMOSERVLET_URL_HEAD = "http://119.23.74.157:8080/MemoServlet";

    public static void main (String[] args) {
        Test_Get();
        Test_Post();


//        MemoService service = new MemoServiceImpl();
//        String str = service.getAllMyMemo("a0001");
//        System.out.println(str);
//        service.addMemo(new Memo("", "a0001", "test4", "test message", new Date(), new Date(), null,1));
//        service.addMemo(new Memo("", "a0001", "test2", "test message1", new Date(), new Date(), new Date(),1));

    }
    public static void Test_Get() {
        String function = "getAllMyMemos";

        try {
            //	URL url = new URL(URL_HEAD + "?function=" + function + "&subjectList=" + new Gson().toJson(subject));//Get方式参数加在url后面
            URL url = new URL(MEMOSERVLET_URL_HEAD
                    + "?function=" + function + "&accId=" + "a12345");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //打开连接
            conn.setRequestMethod("GET");//请求方式
            conn.setConnectTimeout(15000);  //设置连接超时 (可选)
            conn.setReadTimeout(10000);  //设置读取超时  (可选)
            conn.connect();//建立连接

            /*从服务器读数据*/
            InputStream in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));  //得到连接服务器的缓冲流
            String line;
            StringBuilder sb = new StringBuilder();
            while((line=br.readLine()) != null){    //从服务中读取请求返回的数据
                sb.append(line);
            }
            System.out.println(sb.toString());//获取的字符串数据存放在sb里

            /*通过gson将字符串数据转为实体类*/
            Gson gson = new Gson();
            ArrayList<Memo> list = gson.fromJson(sb.toString(), ArrayList.class);
           //System.out.println(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Test_Post() {

        String function = "addMemo";
        Memo memo = new Memo("a12345", "test1aa", "test1aa");
        try {

            URL url = new URL(MEMOSERVLET_URL_HEAD);//POST方式url不变
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //打开连接
            conn.setRequestMethod("POST");//请求方式
            conn.setDoOutput(true);
            conn.setConnectTimeout(15000);  //设置连接超时
            conn.setReadTimeout(10000);  //设置读取超时
            conn.connect();//建立连接

            /*传数据*/
            OutputStream out = conn.getOutputStream();
            PrintWriter pr = new PrintWriter(out);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            pr.print("function=" + function + "&obj=" + gson.toJson(memo));
            //pr.print("function=" + function + "&obj=" + "m0001");
            pr.flush();

            /*得数据*/
            InputStream in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));  //得到连接服务器的缓冲流
            String line;
            StringBuilder sb = new StringBuilder();
            while((line=br.readLine()) != null) {    //从服务中读取请求返回的数据
                sb.append(line);
            }

            /*通过gson将字符串数据转为实体类*/
            //Account acc = gson.fromJson(sb.toString(), Account.class);
            System.out.println(sb.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
