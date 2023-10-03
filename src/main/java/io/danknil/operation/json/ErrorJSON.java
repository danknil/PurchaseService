package io.danknil.operation.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class ErrorJSON {
    private final String type = "error";
    private final String message;

    public ErrorJSON(String message) {
        this.message = message;
    }

    /**
     * write json to output
     * @param output path to output file
     * @return JSON string
     */
    public String writeJSON(String output) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String thisJSON = gson.toJson(this);

        try {
            File file = new File(output);
            file.createNewFile();
            try (FileWriter out = new FileWriter(file)) {
                out.write(thisJSON);
            }
        } catch (IOException e) {
            System.err.println("Невозможно создать файл(папка, где создается файл отсутствует");
        }

        return thisJSON;
    }
}
