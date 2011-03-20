package com.mattstine.vendingmachine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mstine
 * Date: Mar 19, 2011
 * Time: 11:41:43 AM
 * To change this template use File | Settings | File Templates.
 */
public enum Money {
    DOLLAR(100), Q(25), D(10), N(5);

    private int amount;

    Money(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public static List<Money> changeBackRange() {
        List<Money> changeBack = new ArrayList<Money>();
        changeBack.add(Q);
        changeBack.add(D);
        changeBack.add(N);
        return changeBack;
    }
}
