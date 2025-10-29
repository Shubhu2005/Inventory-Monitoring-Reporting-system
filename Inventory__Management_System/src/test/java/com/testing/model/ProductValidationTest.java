package com.testing.model;

import com.inventory.model.Product;
import org.junit.Assert;
import org.junit.Test;

public class ProductValidationTest {

    @Test
    public void testProduct() {
        Product p = new Product(1, "KeyBoard", "Electronics", 10, 500);

        Assert.assertEquals(1, p.getId());
        Assert.assertEquals("KeyBoard", p.getName());
    }

    @Test
    public void testInvalidQuantity() {
        Product p = new Product(2, "Monitor", "Electronics", -5, 10000);
        Assert.assertTrue("Quantity should not be negative", p.getQuantity() < 0);
    }

}
