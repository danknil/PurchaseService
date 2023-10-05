package io.danknil.operation.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import io.danknil.database.model.Customer;
import io.danknil.operation.Operation;
import io.danknil.operation.json.ErrorJSON;
import io.danknil.operation.json.search.CriteriaJSON;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SearchOperation extends Operation {
    public SearchOperation(Connection conn) {
        super(conn);
    }

    @Override
    public String getType() {
        return "search";
    }

    @Override
    public String getJSON(String input, String output) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try (FileReader reader = new FileReader(input)) {
            CriteriaJSON[] criterias = gson.fromJson(gson.fromJson(reader, JsonObject.class).getAsJsonArray("criterias").toString(), CriteriaJSON[].class);

            JsonObject searchOperation = new JsonObject();
            searchOperation.addProperty("type", "search");

            JsonArray jsonArray = new JsonArray();

            for (CriteriaJSON criteria :
                    criterias) {
                List<Customer> results = criteria.getCustomers(this.conn);
                JsonObject result = new JsonObject();

                result.addProperty("criteria", gson.toJson(criteria));
                result.addProperty("results", gson.toJson(results));

                jsonArray.add(result);
            }

            searchOperation.add("results", jsonArray);

            return gson.toJson(searchOperation);
        } catch (IOException e) {
            ErrorJSON errorJSON = new ErrorJSON("Файл ввода не найден");
            return errorJSON.writeJSON(output);
        } catch (SQLException e) {
            ErrorJSON errorJSON = new ErrorJSON("Что-то с датабазой");
            return errorJSON.writeJSON(output);
        }
    }
}
