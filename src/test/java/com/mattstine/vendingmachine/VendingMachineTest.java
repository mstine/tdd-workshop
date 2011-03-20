package com.mattstine.vendingmachine;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class VendingMachineTest {
    private VendingMachine machine;

    @Test
    public void new_machine_should_be_empty() {
        assertTrue(machine.isEmpty());
        assertTrue(machine.isEmptyBank());
    }

    @Test
    public void serviced_machine_should_have_items_and_money() {
        machine.service();

        Map<Item, Integer> items = new HashMap<Item, Integer>();
        items.put(Item.A, 10);
        items.put(Item.B, 10);
        items.put(Item.C, 10);

        assertFalse(machine.isEmpty());
        assertEquals(items, machine.getItems());

        Map<Money, Integer> bank = new HashMap<Money, Integer>();
        bank.put(Money.DOLLAR, 0);
        bank.put(Money.Q, 50);
        bank.put(Money.D, 50);
        bank.put(Money.N, 50);

        assertFalse(machine.isEmptyBank());
        assertEquals(bank, machine.getBank());
    }

    @Test
    public void should_display_25cents_when_quarter_inserted() {
        machine.insertMoney(Money.Q);

        assertEquals(25, machine.getTotalAmountInserted());
    }

    @Test
    public void should_display_5cents_when_nickle_inserted() {
        machine.insertMoney(Money.N);

        assertEquals(5, machine.getTotalAmountInserted());
    }

    @Test
    public void should_display_10cents_when_nickle_inserted() {
        machine.insertMoney(Money.D);

        assertEquals(10, machine.getTotalAmountInserted());
    }

    @Test
    public void should_display_50cents_when_two_quarters_inserted() {
        machine.insertMoney(Money.Q);
        machine.insertMoney(Money.Q);

        assertEquals(50, machine.getTotalAmountInserted());
    }

    @Test
    public void should_display_35cents_when_quarter_and_dime_inserted() {
        machine.insertMoney(Money.Q);
        machine.insertMoney(Money.D);

        assertEquals(35, machine.getTotalAmountInserted());
    }

    @Test
    public void should_return_quarter_if_quarter_inserted_and_coin_return_pressed() {
        assertEquals(0, machine.getTotalAmountInserted());
        machine.insertMoney(Money.Q);
        assertEquals(25, machine.getTotalAmountInserted());

        HashMap<Money, Integer> coins = new HashMap<Money, Integer>();
        coins.put(Money.Q, 1);

        assertEquals(coins, machine.coinReturn());
        assertEquals(0, machine.getTotalAmountInserted());
    }

    @Test
    public void should_return_same_coins_as_inserted_when_coin_return_pressed() {
        machine.insertMoney(Money.Q);
        machine.insertMoney(Money.Q);
        machine.insertMoney(Money.D);
        machine.insertMoney(Money.N);
        machine.insertMoney(Money.D);

        HashMap<Money, Integer> expectedChange = new HashMap<Money, Integer>();
        expectedChange.put(Money.Q, 2);
        expectedChange.put(Money.D, 2);
        expectedChange.put(Money.N, 1);

        assertEquals(expectedChange, machine.coinReturn());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_not_be_able_to_add_items_to_machine_outside_of_service() {
        Map<Item, Integer> items = machine.getItems();
        items.put(Item.A, 500);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void should_not_be_able_to_add_money_to_bank_outside_of_service() {
        Map<Money, Integer> items = machine.getBank();
        items.put(Money.Q, 500);
    }

    @Test
    public void should_vend_A_if_correct_amount_inserted_and_A_pressed() {
        machine.service();
        machine.insertMoney(Money.Q);
        machine.insertMoney(Money.Q);
        machine.insertMoney(Money.D);
        machine.insertMoney(Money.N);

        ItemAndChange expected = new ItemAndChange(Item.A, new HashMap<Money, Integer>());
        assertEquals(expected, machine.buy(Item.A));
        assertEquals(9, (Object) machine.getItems().get(Item.A));
        assertEquals(52, (Object) machine.getBank().get(Money.Q));
        assertEquals(51, (Object) machine.getBank().get(Money.D));
        assertEquals(51, (Object) machine.getBank().get(Money.N));
        assertEquals(0, machine.getTotalAmountInserted());
    }

    @Test(expected = RuntimeException.class)
    public void should_not_vend_A_if_correct_amount_inserted_and_A_pressed_and_no_A_available() {
        machine.insertMoney(Money.Q);
        machine.insertMoney(Money.Q);
        machine.insertMoney(Money.D);
        machine.insertMoney(Money.N);

        machine.buy(Item.A);
    }

    @Test(expected = RuntimeException.class)
    public void should_not_vend_A_if_too_little_money_inserted_and_A_pressed() {
        machine.insertMoney(Money.Q);
        machine.insertMoney(Money.Q);
        machine.insertMoney(Money.D);

        machine.buy(Item.A);
    }
    
    @Test
    public void buy_A_with_one_dollar_returns_quarter_and_dime() {
        machine.service();
        machine.insertMoney(Money.DOLLAR);

        HashMap<Money, Integer> expectedChange = new HashMap<Money, Integer>();
        expectedChange.put(Money.Q, 1);
        expectedChange.put(Money.D, 1);

        ItemAndChange expected = new ItemAndChange(Item.A, expectedChange);

        assertEquals(expected, machine.buy(Item.A));
        assertEquals(9, (Object) machine.getItems().get(Item.A));
        assertEquals(1, (Object) machine.getBank().get(Money.DOLLAR));
        assertEquals(49, (Object) machine.getBank().get(Money.Q));
        assertEquals(49, (Object) machine.getBank().get(Money.D));
        assertEquals(0, machine.getTotalAmountInserted());
    }

    @Test
    public void buy_B_with_two_dollars_returns_4_quarters() {
        machine.service();
        machine.insertMoney(Money.DOLLAR);
        machine.insertMoney(Money.DOLLAR);

        HashMap<Money, Integer> expectedChange = new HashMap<Money, Integer>();
        expectedChange.put(Money.Q, 4);

        ItemAndChange expected = new ItemAndChange(Item.B, expectedChange);

        assertEquals(expected, machine.buy(Item.B));
        assertEquals(9, (Object) machine.getItems().get(Item.B));        
    }

    @Before
    public void setUp() throws Exception {
        machine = new VendingMachine();
    }
}
