package dao;

import entity.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDao extends BaseDao {
    private Connection conn;

    public AccountDao() {
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

    public boolean addAccount(Account acc) {
        String sql = "insert into Account values(?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, acc.getId());
            stmt.setString(2, acc.getName());
            stmt.setString(3, acc.getPassword());
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isExistedAccount(String accID) {
        String sql = "select * from Account where id = ?";
        boolean isExisted = false;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accID);
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

    public Account getSingleAccount(String accID) {
        String sql = "select * from Account where id = ?";
        Account acc = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                acc = new Account(rs.getString(1), rs.getString(2), rs.getString(3));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acc;
    }

    public Account isValid(String accID, String password) {
        String sql = "select * from Account where id = ? and password = ?";
        Account acc = null;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, accID);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                acc = new Account(rs.getString(1), rs.getString(2), rs.getString(3));
            }
            stmt.close();
            return acc;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setAccountName(String accID, String name) {
        String sql = "update Account set name = ? where id = ?";
        boolean success = false;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, accID);
            stmt.executeUpdate();
            stmt.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean setAccountPassword(String accID, String password) {
        String sql = "update Account set password = ? where id = ?";
        boolean success = false;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, password);
            stmt.setString(2, accID);
            stmt.executeUpdate();
            stmt.close();
            success = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}
