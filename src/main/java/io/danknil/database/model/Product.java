package io.danknil.database.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import io.danknil.database.Model;

import java.sql.Connection;

public class Product extends Model {
    @Expose private final String name;
    @Expose private final double price;

    public Product(int id, String name, double price) {
        super(id);
        this.name = name;
        this.price = price;
    }
}
