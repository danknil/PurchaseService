package io.danknil;

import io.danknil.operation.Operation;
import io.danknil.operation.json.ErrorJSON;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        // stat, search
        String type, input, output;
        try {
            type = args[0];
            input = args[1];
            output = args[2];
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Параметры указываются по порядку:\n" +
                    "1. Тип вызываемой операции\n" +
                    "2. Входной файл\n" +
                    "3. Выходной файл");
            return;
        }

        // load properties
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream("database.properties")) {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String url = props.getProperty("url");

        // use connection to database
        try (Connection conn = DriverManager.getConnection(url, props)){
            switch (type) {
                case "stat":
                    // TODO: 10/4/23 add operation
                    break;
                case "search":
                    // TODO: 10/4/23 add operation
                    break;
                default:
                    new ErrorJSON("Тип операции указан неверно, возможные варианты: stat, search").writeJSON(output);
                    break;
            }
        } catch (SQLException e) {
            System.err.println("Подключение к датабазе провалено. Проверьте параметр url в database.properties");
        }
    }
}