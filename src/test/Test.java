package test;

import entity.Account;
import entity.Memo;
import httpconnection.HttpRequest;

import java.util.ArrayList;
import java.util.Date;

public class Test {

    public static void main (String[] args) {

        HttpRequest httpRequest = new HttpRequest();

        //Account Servlet
        System.out.println("test login==== " + httpRequest.login("a0001", "admin"));
        System.out.println("test register====" + httpRequest.register(new Account("a123456", "Jimmy Roth", "12345")));
        System.out.println("test setName====" + httpRequest.setName("a123456", "alphabet"));
        System.out.println("test setPassword====" + httpRequest.setPassword("a123456", "qweqwe"));

        //Memo Servlet
        ArrayList<Memo> list = new ArrayList<Memo>();
        Date date = new Date();
        //Test add
        list.add(new Memo("a0001", "test add 2", "test message"));
        list.add(new Memo("a0001", "test add 3", "test message1"));

        System.out.println("test synchronizeMemos====" + httpRequest.synchronizeMemos(list));
        //Test delete
        list.get(0).setState(0);
        //Test update
        list.get(1).setNotificationDate(date);
        System.out.println("test synchronizeMemos====" + httpRequest.synchronizeMemos(list));

        System.out.println("test delMemo====" + httpRequest.delMemo("m0001"));
        System.out.println("test getAllMyMemos====" + httpRequest.getAllMyMemos("a0001"));
        System.out.println("test getAllDiscardedMemos====" + httpRequest.getAllDiscardedMemos("a0001"));

    }
}
