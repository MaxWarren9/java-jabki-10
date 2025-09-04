package org.example.item;

import org.example.customException.InvalidRatingException;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private String itemName;
    private List<Integer> items = new ArrayList<>();

    public Item(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "ItemName = " + itemName;
    }

    public void rateProduct(String rating) throws InvalidRatingException, NumberFormatException {
        try {
            int ratingValue = Integer.parseInt(rating);

            if (ratingValue < 1 || ratingValue > 5) {
                throw new InvalidRatingException();
            }
            items.add(ratingValue);
            System.out.printf("Рейтинг %d добавлен для товара %s.", ratingValue, itemName);
        } catch (NumberFormatException e) {
            System.out.println("Введенное значение не является числом");
        }
    }

    public List<Integer> getItems() {
        return this.items;
    }
}
