package io.danknil.operation.impl;

import io.danknil.operation.Operation;

public class SearchOperation extends Operation {
    @Override
    public String getType() {
        return "search";
    }

    @Override
    public String getJSON(String input, String output) {
        return "";
    }
}
