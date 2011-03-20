package com.mattstine.vendingmachine;

import com.google.common.base.Objects;

import java.util.Collections;
import java.util.Map;

public class ItemAndChange {

    private Item item;
    private Map<Money, Integer> change;

    public ItemAndChange(Item item, Map<Money, Integer> change) {
        this.item = item;
        this.change = change;
    }

    public Item getItem() {
        return item;
    }

    public Map<Money, Integer> getChange() {
        return Collections.unmodifiableMap(change);
    }

    @Override
    public String toString() {
        return "ItemAndChange{" +
                "item=" + item +
                ", change=" + change +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(item, change);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ItemAndChange) {
            ItemAndChange that = (ItemAndChange) o;
            return Objects.equal(this.item, that.item) &&
                    Objects.equal(this.change, that.change);
        }
        return false;
    }
}
