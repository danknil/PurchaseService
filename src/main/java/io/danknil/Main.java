package io.danknil;

import io.danknil.operation.Operation;

import java.io.*;
import java.util.HashMap;

public class Main {
    public static HashMap<String, Operation> operations = new HashMap<>();
    public static void main(String[] args) {
        // stat, search
        String type, input, output;
        try {
            type = args[0];
            input = args[1];
            output = args[2];
        } catch (IndexOutOfBoundsException e) {
            usage();
            return;
        }
    }

    private static void usage() {
        System.out.println("Параметры указываются по порядку:");
        System.out.println("1. Тип вызываемой операции");
        System.out.println("2. Входной файл");
        System.out.println("3. Выходной файл");
    }
}