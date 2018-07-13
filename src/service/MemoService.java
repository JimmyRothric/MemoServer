package service;

import entity.Memo;

import java.util.ArrayList;
import java.util.Date;

public interface MemoService {
    //public boolean addMemo(Memo memo);
    public boolean delMemo(String id);
    //public boolean updateMemo(Memo memo);
    //public boolean setNotificationDate(String id, Date date);
    public String getSingleMemo(String id);
    public String getAllMyMemos(String accId);
    public String getAllDiscardedMemos(String accId);
    public boolean synchronizeMemos(ArrayList<Memo> list);

}
