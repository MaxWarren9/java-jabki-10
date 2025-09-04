package org.example.customException;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException() {
        super("Товара с данным кодом нет в списке");
    }

}
