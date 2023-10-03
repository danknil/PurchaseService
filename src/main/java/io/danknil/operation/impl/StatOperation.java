package io.danknil.operation.impl;

import io.danknil.operation.Operation;

import java.sql.Connection;
import java.util.ArrayList;

public class StatOperation extends Operation {
    public StatOperation(Connection conn) {
        super(conn);
    }
    @Override
    public String getType() {
        return "stat";
    }

    @Override
    public String getJSON(String input) {
        return null;
    }
}
