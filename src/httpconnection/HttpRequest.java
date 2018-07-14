package httpconnection;

import com.google.gson.*;
import entity.Account;
import entity.Memo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

//    private static final String ACCOUNT_SERVLET_URL_HEAD = "http://localhost:8080/AccountServlet";
//    private static final String MEMO_SERVLET_URL_HEAD = "http://localhost:8080/MemoServlet";
    private static final String ACCOUNT_SERVLET_URL_HEAD = "http://119.23.74.157:8080/AccountServlet";
    private static final String MEMO_SERVLET_URL_HEAD = "http://119.23.74.157:8080/MemoServlet";

    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
            .create();
    //params pairs
    private Map<String, String> map = new HashMap<String, String>();
    private StringBuilder url = null;

    /**
     * Login
     * @param id
     * @param password
     * @return
     */
    public String login(String id, String password) {
        map.put("function", "login");
        map.put("id", id);
        map.put("password", password);
        return HttpUtil.getInstance().post(ACCOUNT_SERVLET_URL_HEAD, map);
    }

    /**
     * Register
     * @param account
     * @return
     */
    public String register(Account account) {
        map.put("function", "register");
        map.put("obj", gson.toJson(account));
        return HttpUtil.getInstance().post(ACCOUNT_SERVLET_URL_HEAD, map);
    }

    /**
     * Set Name
     * @param id
     * @param name
     * @return
     */
    public String setName(String id, String name) {
        map.put("function", "setName");
        map.put("id", id);
        map.put("name", name);
        return HttpUtil.getInstance().post(ACCOUNT_SERVLET_URL_HEAD, map);
    }

    /**
     * Set Password
     * @param id
     * @param password
     * @return
     */
    public String setPassword(String id, String password) {
        map.put("function", "setPassword");
        map.put("id", id);
        map.put("password", password);
        return HttpUtil.getInstance().post(ACCOUNT_SERVLET_URL_HEAD, map);
    }

    /**
     * Synchronize Memos
     * @param list
     * @return
     */
    public String synchronizeMemos(ArrayList<Memo> list) {
        if (list == null) {
            list =  new ArrayList<Memo>();
        }
        map.put("function", "synchronizeMemos");
        map.put("list", gson.toJson(list));
        return HttpUtil.getInstance().post(MEMO_SERVLET_URL_HEAD, map);
    }

    /**
     * Delete Memo
     * @param id
     * @return
     */
    public String delMemo(String id) {
        url = new StringBuilder(MEMO_SERVLET_URL_HEAD);
        url.append("?function=delMemo&id=").append(id);
        return HttpUtil.getInstance().get(url.toString());
    }

    /**
     * Get All My Memos
     * @param accId
     * @return
     */
    public String getAllMyMemos(String accId) {
        url = new StringBuilder(MEMO_SERVLET_URL_HEAD);
        url.append("?function=getAllMyMemos&accId=").append(accId);
        return HttpUtil.getInstance().get(url.toString());
    }

    /**
     * Get All Discarded Memos
     * @param accId
     * @return
     */
    public String getAllDiscardedMemos(String accId) {
        url = new StringBuilder(MEMO_SERVLET_URL_HEAD);
        url.append("?function=getAllDiscardedMemos&accId=").append(accId);
        return HttpUtil.getInstance().get(url.toString());
    }


    /**
     * Parse Json Array
     * @param json
     * @return
     */
    public static ArrayList<Memo> parseJsonArray(String json) {
        JsonParser parser = new JsonParser();
        if (json == null || json.length() < 1) {
            json = "[]";
        }
        JsonArray jsonArray = parser.parse(json).getAsJsonArray();
        ArrayList<Memo> list = new ArrayList<Memo>();

        for (JsonElement memo : jsonArray) {
            Memo m = gson.fromJson(memo, Memo.class);
            list.add(m);
        }
        return list;
    }

}
