package io.danknil.operation.json.stat;

import com.sun.istack.internal.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PurchasesInfoJSON {
    private final String customer;
    private final ArrayList<PurchaseJSON> purchases = new ArrayList<>();
    private final Double totalExpenses;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public PurchasesInfoJSON(String customer, Double totalExpenses, List<PurchaseJSON> purchases) {
        this.customer = customer;
        this.purchases.addAll(purchases);
        this.totalExpenses = totalExpenses;
    }

    public static PurchasesInfoJSON getByCustomerId(Connection conn, Integer customerId, @NotNull Date startDate, @NotNull Date endDate) throws SQLException {
        Statement statement = conn.createStatement();

        ResultSet customerName = statement.executeQuery("SELECT firstname, lastname FROM customer WHERE customer.id=" + customerId);

        customerName.next();

        String customer = customerName.getString("firstname") + " " + customerName.getString("lastname");
        ArrayList<PurchaseJSON> purchases = new ArrayList<>();
        Double totalExpenses = 0.0;

        ResultSet productResult = statement.executeQuery("SELECT product.name, COUNT(purchase.productid)*product.price AS expense FROM {oj purchase left join product on purchase.productid=product.id} WHERE purchase.customerid=" + customerId + " && purchase.date BETWEEN " + dateFormat.format(startDate) + " AND " + dateFormat.format(endDate) + " GROUP BY purchase.productid");
        while(productResult.next()) {
            String name = productResult.getString("product.name");
            Double expense = productResult.getDouble("expense");
            purchases.add(new PurchaseJSON(name, expense));
            totalExpenses += expense;
        }

        return new PurchasesInfoJSON(customer, totalExpenses, purchases);
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }
}
