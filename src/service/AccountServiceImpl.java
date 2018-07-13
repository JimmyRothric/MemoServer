package service;

import com.google.gson.Gson;
import dao.AccountDao;
import entity.Account;

public class AccountServiceImpl implements AccountService {

    @Override
    public String login(String id, String password) {
        Gson gson = new Gson();
        String result = "";
        AccountDao dao = new AccountDao();
        Account account = null;
        account = dao.isValid(id, password);
        if (account != null) {
            result = gson.toJson(account);
        }
        dao.close();
        return result;
    }

    @Override
    public boolean register(Account account) {
        boolean result = false;
        AccountDao dao = new AccountDao();
        boolean isExisted = dao.isExistedAccount(account.getId());
        if (!isExisted) {
            boolean success = dao.addAccount(account);
            if (success) {
                result = true;
            }
        }
        dao.close();
        return result;
    }

    @Override
    public boolean setName(String id, String name) {
        AccountDao dao = new AccountDao();
        boolean success = dao.setAccountName(id, name);
        dao.close();
        return success;
    }

    @Override
    public boolean setPassword(String id, String password) {
        AccountDao dao = new AccountDao();
        boolean success = dao.setAccountPassword(id, password);
        dao.close();
        return success;
    }
}
