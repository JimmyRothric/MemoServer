package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.MemoDao;
import entity.Memo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MemoServiceImpl implements MemoService {
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    public boolean addMemo(Memo memo) {
        boolean success = false;
        MemoDao dao = new MemoDao();

        //set default notification date
        if (memo.getNotificationDate() == null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = df.parse("2000-01-01 00:00:00");
                memo.setNotificationDate(date);

            } catch (ParseException e) {
                e.printStackTrace();
                dao.close();
                return false;
            }
        }

        success = dao.addMemo(memo);
        dao.close();
        return success;
    }

    @Override
    public boolean delMemo(String id) {
        MemoDao dao = new MemoDao();
        boolean success = dao.delMemo(id);
        dao.close();
        return success;
    }

    /*  DISCARD
    @Override
    public boolean updateMemo(Memo memo) {
        MemoDao dao = new MemoDao();
        boolean success = dao.updateMemo(memo);
        dao.close();
        return success;
    }
    */

    /*  DISCARD
    @Override
    public boolean setNotificationDate(String id, Date date) {
        MemoDao dao = new MemoDao();
        boolean success = dao.setNotificationDate(id, date);
        dao.close();
        return success;
    }
    */

    @Override
    public String getSingleMemo(String id) {
        String result = "";
        MemoDao dao = new MemoDao();
        Memo memo = null;
        memo = dao.getSingleMemo(id);
        if (memo != null) {
            result = gson.toJson(memo);
        }
        dao.close();
        return result;
    }

    @Override
    public String getAllMyMemos(String accId) {
        String result = "";
        MemoDao dao = new MemoDao();
        ArrayList<Memo> list = new ArrayList<Memo>();
        list = dao.getAllMyMemos(accId);
        if (!list.isEmpty()) {
            result = gson.toJson(list);
        }
        dao.close();
        return result;
    }

    @Override
    public String getAllDiscardedMemos(String accId) {
        String result = "";
        MemoDao dao = new MemoDao();
        ArrayList<Memo> list = new ArrayList<Memo>();
        list = dao.getAllDiscardedMemos(accId);
        if (!list.isEmpty()) {
            result = gson.toJson(list);
        }
        dao.close();
        return result;
    }

    @Override
    public boolean synchronizeMemos(ArrayList<Memo> list) {
        MemoDao dao = new MemoDao();
        boolean update_completed = true;
        boolean delete_completed = true;
        boolean add_completed = true;
        boolean isExisted = false;
        for (Memo memo : list) {
            String id = memo.getId();
            isExisted = dao.isExistedMemo(id);
            if (isExisted && memo.getState() != 0) {
                //update
                update_completed &= dao.updateMemo(memo);
            } else if (isExisted && memo.getState() == 0) {
                //delete
                delete_completed &= dao.delMemo(memo.getId());
            } else if (!isExisted) {
                //add
                add_completed &= addMemo(memo);
            }
        }
        dao.close();
        return (update_completed && delete_completed && add_completed);
    }
}
