package service;

import entity.Account;

public interface AccountService {
    public String login(String id, String password);
    public boolean register(Account account);
    public boolean setName(String id, String name);
    public boolean setPassword(String id, String password);
}
