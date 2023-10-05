package io.danknil.operation.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import io.danknil.operation.Operation;
import io.danknil.operation.json.ErrorJSON;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.stream.Collectors;

public class StatOperation extends Operation {
    public StatOperation(Connection conn) {
        super(conn);
    }
    @Override
    public String getType() {
        return "stat";
    }

    @Override
    public String getJSON(String input, String output) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try (FileReader reader = new FileReader(input)) {
            return null;
        } catch (IOException e) {
            ErrorJSON errorJSON = new ErrorJSON("Файл ввода не найден");
            return errorJSON.writeJSON(output);
        }
    }
}
