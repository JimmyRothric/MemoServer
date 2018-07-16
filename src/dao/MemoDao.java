package dao;

import entity.Memo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class MemoDao extends BaseDao {
    private Connection conn;

    public MemoDao() {
        try {
            conn = super.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addMemo(Memo memo) {
        String sql = "insert into Memo values(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, memo.getId());
            stmt.setString(2, memo.getAccID());
            stmt.setString(3, memo.getTitle());
            stmt.setString(4, memo.getContent());
            stmt.setTimestamp(5, new Timestamp(memo.getCreateDate().getTime()));
            stmt.setTimestamp(6, new Timestamp(memo.getLastModifyDate().getTime()));
            stmt.setTimestamp(7, new Timestamp(memo.getNotificationDate().getTime()));
            stmt.setInt(8, memo.getState());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isExistedMemo(String id) {
        String sql = "select * from Memo where id = ?";
        boolean isExisted = false;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                isExisted = true;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExisted;
    }

    public boolean delMemo(String id) {
        String sql = "update Memo set state = 0 where id = ?";
        boolean success = false;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.executeUpdate();
            stmt.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean updateMemo(Memo memo) {
        String sql = "update Memo set title = ?, content = ?, " +
                "lastModifyDate = ?, notificationDate = ? where id = ?";
        boolean success = false;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, memo.getTitle());
            stmt.setString(2, memo.getContent());
            stmt.setTimestamp(3, new Timestamp(memo.getLastModifyDate().getTime()));
            stmt.setTimestamp(4, new Timestamp(memo.getNotificationDate().getTime()));
            stmt.setString(5, memo.getId());
            stmt.executeUpdate();
            stmt.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    /*  DISCARD
    public boolean setNotificationDate(String id, Date date) {
        String sql = "update Memo set notificationDate = ? where id = ?";
        boolean success = false;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, new Timestamp(date.getTime()));
            stmt.setString(2, id);
            stmt.executeUpdate();
            stmt.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
    */

    public Memo getSingleMemo(String id) {
        Memo memo = null;
        String sql = "select * from Memo where id = ? and state = 1";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String accId = rs.getString(2);
                String title = rs.getString(3);
                String content = rs.getString(4);
                Date createDate = new Date(rs.getTimestamp(5).getTime());
                Date lastModifyDate = new Date(rs.getTimestamp(6).getTime());
                Date notificationDate = new Date(rs.getTimestamp(7).getTime());
                int state = rs.getInt(8);
                memo = new Memo(id, accId, title, content, createDate, lastModifyDate, notificationDate, state);
            }
            stmt.close();
            return memo;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memo;
    }

    public ArrayList<Memo> getAllMyMemos(String accId) {
        ArrayList<Memo> list = new ArrayList<Memo>();
        String sql = "select * from Memo where accid = ? and state = 1";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString(1);
                String title = rs.getString(3);
                String content = rs.getString(4);
                Date createDate = new Date(rs.getTimestamp(5).getTime());
                Date lastModifyDate = new Date(rs.getTimestamp(6).getTime());
                Date notificationDate = new Date(rs.getTimestamp(7).getTime());
                int state = rs.getInt(8);
                list.add(new Memo(id, accId, title, content, createDate, lastModifyDate, notificationDate, state));
            }
            stmt.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Memo> getAllDiscardedMemos(String accId) {
        ArrayList<Memo> list = new ArrayList<Memo>();
        String sql = "select * from Memo where accid = ? and state = 0";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String id = rs.getString(1);
                String title = rs.getString(3);
                String content = rs.getString(4);
                Date createDate = new Date(rs.getTimestamp(5).getTime());
                Date lastModifyDate = new Date(rs.getTimestamp(6).getTime());
                Date notificationDate = new Date(rs.getTimestamp(7).getTime());
                int state = rs.getInt(8);
                list.add(new Memo(id, accId, title, content, createDate, lastModifyDate, notificationDate, state));
            }
            stmt.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countMemo(String accId) {
        int cnt = 0;
        String sql = "select count(*) from Memo";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt(1);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cnt;
    }
}
