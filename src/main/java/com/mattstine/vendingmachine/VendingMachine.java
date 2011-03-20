package com.mattstine.vendingmachine;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
    private Map<Item, Integer> items = new HashMap<Item, Integer>();
    private Map<Money, Integer> bank = new HashMap<Money, Integer>();
    private Map<Money, Integer> coinsInserted = new HashMap<Money, Integer>();

    public VendingMachine() {
        items.put(Item.A, 0);
        items.put(Item.B, 0);
        items.put(Item.C, 0);        
    }

    public boolean isEmpty() {
        int numOfItems = 0;

        for (Integer numOfItem : items.values()) {
            numOfItems += numOfItem;
        }

        return numOfItems == 0;
    }

    public boolean isEmptyBank() {
        return bank.isEmpty();
    }

    public void service() {
        items.put(Item.A, 10);
        items.put(Item.B, 10);
        items.put(Item.C, 10);

        bank.put(Money.DOLLAR, 0);
        bank.put(Money.Q, 50);
        bank.put(Money.D, 50);
        bank.put(Money.N, 50);
    }

    public Map<Item, Integer> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public Map<Money, Integer> getBank() {
        return Collections.unmodifiableMap(bank);
    }

    public void insertMoney(Money money) {
        Integer numOfCoins = coinsInserted.get(money);
        if (numOfCoins == null) {
            coinsInserted.put(money, 1);
        } else {
            Integer coinsSoFar = coinsInserted.get(money);
            coinsInserted.put(money, ++coinsSoFar);
        }
    }

    public int getTotalAmountInserted() {
        int myTotalAmount = 0;

        for (Money money : coinsInserted.keySet()) {
            myTotalAmount += money.getAmount() * coinsInserted.get(money);
        }

        return myTotalAmount;
    }

    public Map<Money, Integer> coinReturn() {
        Map<Money, Integer> returnedCoins = coinsInserted;
        coinsInserted = new HashMap<Money, Integer>();
        return returnedCoins;
    }

    public ItemAndChange buy(Item item) {
        if (items.get(item) > 0) {
            if (getTotalAmountInserted() == item.getPrice()) {
                decrementItem(item);
                incrementBank();
                return new ItemAndChange(item, new HashMap<Money, Integer>());
            } else if (getTotalAmountInserted() > item.getPrice()) {
                int overpay = getTotalAmountInserted() - item.getPrice();
                decrementItem(item);
                incrementBank();
                return new ItemAndChange(item, calculateChange(overpay));
            } else {
                throw new RuntimeException("You didn't insert enough money!");
            }
        } else {
            throw new RuntimeException("No item " + item + "is available.");
        }
    }

    private void incrementBank() {
        for (Money money : coinsInserted.keySet()) {
            bank.put(money, bank.get(money) + coinsInserted.get(money));
        }
        coinsInserted.clear();
    }

    private void decrementItem(Item item) {
        Integer itemCount = items.get(item);
        items.put(item, --itemCount);
    }

    private HashMap<Money, Integer> calculateChange(int overpay) {
        HashMap<Money, Integer> change = new HashMap<Money, Integer>();

        int remainder = overpay;

        for (Money money : Money.changeBackRange()) {
            while (remainder >= money.getAmount() && bank.get(money) > 0) {
                remainder = remainder - money.getAmount();
                Integer coinsInBank = bank.get(money);
                bank.put(money, --coinsInBank);

                if (change.get(money) == null) {
                    change.put(money, 1);
                } else {
                    Integer coins = change.get(money);
                    change.put(money, ++coins);
                }
            }
        }

        return change;
    }
}
