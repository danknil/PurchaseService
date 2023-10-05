package io.danknil.database.model;

import com.google.gson.annotations.Expose;
import io.danknil.database.Model;

public class Customer extends Model {
    @Expose private final String firstName;
    @Expose private final String lastName;

    public Customer(int id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
