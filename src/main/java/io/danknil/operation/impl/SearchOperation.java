package io.danknil.operation.impl;

import io.danknil.operation.Operation;

import java.sql.Connection;

public class SearchOperation extends Operation {
    public SearchOperation(Connection conn) {
        super(conn);
    }
    @Override
    public String getType() {
        return "search";
    }

    @Override
    public String getJSON(String input) {
        return null;
    }
}
