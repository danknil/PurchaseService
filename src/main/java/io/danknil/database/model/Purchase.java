package io.danknil.database.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import io.danknil.database.Model;

import java.sql.*;

public class Purchase extends Model {
    @Expose private final int customerId;
    @Expose private final int productId;
    @Expose private final Date purchaseDate;

    public Purchase(int id, int customerId, int productId, Date purchaseDate) {
        super(id);
        this.customerId = customerId;
        this.productId = productId;
        this.purchaseDate = purchaseDate;
    }
}
