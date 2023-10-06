package io.danknil.operation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import io.danknil.operation.json.ErrorJSON;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;

public abstract class Operation {
    protected final Connection conn;
    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    protected Operation(Connection conn) {
        this.conn = conn;
    }

    /**
     * @return type of operation
     */
    public abstract String getType();

    protected Gson getGson() {
        return gson;
    }

    /**
     * write json to output file
     *
     * @param input  input file path
     * @param output output file path
     * @return json string
     */
    public abstract String writeJSON(String input, String output);


    /**
     * write json to output file
     *
     * @param output output file path
     * @param json json element to write
     * @return json string
     */
    protected String writeJSON(String output, JsonElement json) {
        try (FileWriter writer = new FileWriter(output)) {
            String jsonResult = gson.toJson(json);

            writer.write(jsonResult);

            return jsonResult;
        } catch (IOException e) {
            ErrorJSON errorJSON = new ErrorJSON("Файл вывода не найден");
            return errorJSON.writeJSON(output);
        }
    }
}
