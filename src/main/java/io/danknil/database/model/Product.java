package io.danknil.database.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import io.danknil.database.Model;

public class Product extends Model {
    private final int id;
    @Expose private final String name;
    @Expose private final double price;

    public Product(int id, String name, double price) {
        super(id);
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toJSON() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }
}
