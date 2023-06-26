package com.valantic.sti.tutorial;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private String name;
    private double price;
    private int quantity;

    public Product() {
        name = "";
        price = 0.0;
        quantity = 0;
    }
}
