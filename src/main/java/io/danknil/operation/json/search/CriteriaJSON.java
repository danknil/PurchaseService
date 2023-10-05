package io.danknil.operation.json.search;

import com.google.gson.annotations.Expose;
import io.danknil.database.model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CriteriaJSON {
    @Expose
    private final String lastName;
    @Expose
    private final String productName;
    @Expose
    private final Integer minTimes;
    @Expose
    private final Integer minExpenses;
    @Expose
    private final Integer maxExpenses;
    @Expose
    private final Integer badCustomers;

    public CriteriaJSON(String lastName, String productName, Integer minTimes, Integer minExpenses, Integer maxExpenses, Integer badCustomers) {
        this.lastName = lastName;
        this.productName = productName;
        this.minTimes = minTimes;
        this.minExpenses = minExpenses;
        this.maxExpenses = maxExpenses;
        this.badCustomers = badCustomers;
    }

    public List<Customer> getCustomers(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();

        List<Customer> customers = null;
        if (lastName != null)
            customers = execCriteria(statement.executeQuery("SELECT * FROM customer WHERE lastName=" + this.lastName));
        else if (productName != null)
            customers = execCriteria(
                    statement.executeQuery(
                            "SELECT * FROM {oj product left join (SELECT productid, customerid, COUNT(id) as mintimes FROM purchase GROUP BY productid, customerid) left join customer on customer.id=customerid on product.id=productid} WHERE mintimes=" + this.minTimes + " && product.name=" + this.productName
                    )
            );
        else if (minExpenses != null)
            customers = execCriteria(
                    statement.executeQuery(
                            "SELECT * FROM {oj customer left join (SELECT customerid, SUM(product.price) as expense FROM {oj purchase left join product on purchase.productid=product.id} GROUP BY customerid) on customer.id=customerid} WHERE expense BETWEEN " + this.minExpenses + " AND " + this.maxExpenses
                    )
            );
        else if (badCustomers != null)
            customers = execCriteria(
                    statement.executeQuery(
                            "SELECT * FROM {oj customer left join (SELECT customerid, COUNT(productid) as purchases FROM purchase GROUP BY customerid) on customer.id=customerid} ORDER BY purchases ASC NULLS FIRST LIMIT " + this.badCustomers
                    )
            );

        statement.close();
        // will be null if criteria is invalid
        return customers;
    }

    private List<Customer> execCriteria(ResultSet result) throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();

        while (result.next()) {
            customers.add(new Customer(
                    result.getInt("customer.id"),
                    result.getString("customer.firstname"),
                    result.getString("customer.lastname")
            ));
        }

        result.close();
        return customers;
    }
}
