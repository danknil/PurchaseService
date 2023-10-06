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
import java.io.FileWriter;
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
    public String writeJSON(String input, String output) {
        try (FileReader reader = new FileReader(input)) {
            CriteriaJSON[] criterias = getGson().fromJson(getGson().fromJson(reader, JsonObject.class).getAsJsonArray("criterias").toString(), CriteriaJSON[].class);

            JsonObject searchOperation = new JsonObject();
            searchOperation.addProperty("type", "search");

            JsonArray jsonArray = new JsonArray();

            for (CriteriaJSON criteria : criterias) {
                List<Customer> results = criteria.getCustomers(this.conn);
                JsonObject result = new JsonObject();

                result.addProperty("criteria", getGson().toJson(criteria));
                result.addProperty("results", getGson().toJson(results));

                jsonArray.add(result);
            }

            searchOperation.add("results", jsonArray);

            return writeJSON(output, searchOperation);
        } catch (IOException e) {
            ErrorJSON errorJSON = new ErrorJSON("Файл ввода не найден");
            return errorJSON.writeJSON(output);
        } catch (SQLException e) {
            ErrorJSON errorJSON = new ErrorJSON("Ошибка к доступу к датабазе, проверьте параметры database.properties");
            return errorJSON.writeJSON(output);
        }
    }
}
