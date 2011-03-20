package com.mattstine.vendingmachine;

/**
 * Created by IntelliJ IDEA.
 * User: mstine
 * Date: Mar 19, 2011
 * Time: 12:56:54 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Item {
    A(65), B(100), C(150);

    private int price;

    Item(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
