package com.inventory.util;

import com.inventory.exceptions.EmptyFieldException;
import com.inventory.exceptions.InvalidProductDataException;

public class InputValidator {
    public static void validateString(String field, String value) throws EmptyFieldException {
        if (value == null || value.trim().isEmpty())
            throw new EmptyFieldException(field + " cannot be empty!");
    }

    public static void validateQuantity(int qty) throws InvalidProductDataException {
        if (qty < 0) throw new InvalidProductDataException("Quantity cannot be negative!");
    }

    public static void validatePrice(double price) throws InvalidProductDataException {
        if (price < 0) throw new InvalidProductDataException("Price cannot be negative!");
    }

    public static void validateId(int id) throws InvalidProductDataException {
        if (id <= 0) throw new InvalidProductDataException("ID must be positive!");
    }
}
