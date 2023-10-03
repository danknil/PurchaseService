package io.danknil.database.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import io.danknil.database.Model;

public class Customer extends Model {
    @Expose private final String firstName;
    @Expose private final String secondName;

    public Customer(int id, String firstName, String secondName) {
        super(id);
        this.firstName = firstName;
        this.secondName = secondName;
    }

    @Override
    public String toJSON() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }
}