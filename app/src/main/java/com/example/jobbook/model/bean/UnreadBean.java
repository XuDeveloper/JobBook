package com.example.jobbook.model.bean;

import org.litepal.crud.LitePalSupport;

/**
 * Created by Xu on 2017/1/23.
 */

public class UnreadBean extends LitePalSupport {

    private String account;
    private int num;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
