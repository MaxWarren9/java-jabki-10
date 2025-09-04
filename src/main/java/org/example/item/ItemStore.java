package org.example.item;

import org.example.customException.ItemNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class ItemStore {
    private final Map<String, Item> items = new HashMap<>();

    public Item getItem(String code) throws ItemNotFoundException {
        if (!this.items.containsKey(code)) throw new ItemNotFoundException();
        return this.items.get(code);
    }

    public void addItem(String code, Item item) {
        this.items.put(code, item);
    }

    public Map<String, Item> getItems() {
        return this.items;
    }
}
