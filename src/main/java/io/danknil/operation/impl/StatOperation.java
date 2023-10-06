package io.danknil.operation.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.danknil.operation.Operation;
import io.danknil.operation.json.ErrorJSON;
import io.danknil.operation.json.stat.PurchasesInfoJSON;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StatOperation extends Operation {
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public StatOperation(Connection conn) {
        super(conn);
    }

    @Override
    public String getType() {
        return "stat";
    }

    @Override
    public String writeJSON(String input, String output) {
        try (FileReader reader = new FileReader(input)) {
            JsonObject jsonObject = getGson().fromJson(reader, JsonObject.class);

            java.util.Date startDate = dateFormat.parse(jsonObject.get("startDate").toString());
            java.util.Date endDate = dateFormat.parse(jsonObject.get("endDate").toString());

            JsonObject statOperation = new JsonObject();
            // get dates as millisecs from epoch, then convert millisecs into days
            long totalDays = (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24);
            Double totalExpenses = 0.0;
            Integer countExpenses = 0;
            JsonArray purchaseInfoArray = new JsonArray();

            Statement getCustomers = conn.createStatement();
            ResultSet customerIds = conn.createStatement().executeQuery("SELECT DISTINCT customer.id FROM {oj customer left join purchase on customer.id=purchase.customerid} WHERE purchase.date BETWEEN " + dateFormat.format(startDate) + " AND " + dateFormat.format(endDate));


            while (customerIds.next()) {
                PurchasesInfoJSON purchasesInfo = PurchasesInfoJSON.getByCustomerId(conn, customerIds.getInt("customer.id"), startDate, endDate);
                purchaseInfoArray.add(getGson().toJsonTree(purchasesInfo));
                totalExpenses += purchasesInfo.getTotalExpenses();
                countExpenses++;
            }

            statOperation.addProperty("type", getType());
            statOperation.addProperty("totalDays", totalDays);
            statOperation.add("purchases", purchaseInfoArray);
            statOperation.addProperty("totalExpenses", totalExpenses);
            statOperation.addProperty("avgExpenses", totalExpenses / countExpenses);

            return writeJSON(output, statOperation);
        } catch (IOException e) {
            ErrorJSON errorJSON = new ErrorJSON("Файл ввода не найден");
            return errorJSON.writeJSON(output);
        } catch (ParseException e) {
            ErrorJSON errorJSON = new ErrorJSON("Невозможно прочесть дату. Проверьте правильность ее написания");
            return errorJSON.writeJSON(output);
        } catch (SQLException e) {
            ErrorJSON errorJSON = new ErrorJSON("Ошибка к доступу к датабазе, проверьте параметры database.properties");
            return errorJSON.writeJSON(output);
        }
    }
}
