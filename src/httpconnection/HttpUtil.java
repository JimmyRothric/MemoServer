package httpconnection;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class HttpUtil {
    private static HttpUtil instance;

    private HttpUtil() {
    }

    public static HttpUtil getInstance() {
        if (instance == null) {
            return new HttpUtil();
        }
        return instance;
    }

    public String get(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //打开连接
            conn.setRequestMethod("GET");//请求方式
            conn.setConnectTimeout(15000);  //设置连接超时 (可选)
            conn.setReadTimeout(10000);  //设置读取超时  (可选)
            conn.connect();//建立连接

            InputStream in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));  //得到连接服务器的缓冲流
            String line;
            StringBuilder result = new StringBuilder();
            while((line = br.readLine()) != null){    //从服务中读取请求返回的数据
                result.append(line);
            }

            return result.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String post(String path, Map<String, String> map) {
        try {
            URL url = new URL(path);//POST方式url不变
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //打开连接
            conn.setRequestMethod("POST");//请求方式
            conn.setDoOutput(true);
            conn.setConnectTimeout(15000);  //设置连接超时
            conn.setReadTimeout(10000);  //设置读取超时
            conn.connect();//建立连接

            OutputStream out = conn.getOutputStream();
            PrintWriter pr = new PrintWriter(out);
            StringBuilder request = new StringBuilder();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String)entry.getKey();
                String value = (String)entry.getValue();
                request.append(key).append("=").append(value).append("&");
            }
            request.deleteCharAt(request.length() - 1);
            pr.print(request);
            pr.flush();

            InputStream in = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));  //得到连接服务器的缓冲流
            String line;
            StringBuilder result = new StringBuilder();
            while((line = br.readLine()) != null) {    //从服务中读取请求返回的数据
                result.append(line);
            }

            return result.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
