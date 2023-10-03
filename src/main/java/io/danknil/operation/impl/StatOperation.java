package io.danknil.operation.impl;

import io.danknil.operation.Operation;

import java.util.ArrayList;

public class StatOperation extends Operation {
    public StatOperation() {
        super();
    }
    @Override
    public String getType() {
        return "stat";
    }
    @Override
    public String getJSON(String input, String output) {
        return null;
    }
}
