package io.danknil.operation;

import java.sql.Connection;

public abstract class Operation {
    protected final Connection conn;
    protected Operation(Connection conn) {
        this.conn = conn;
    }

    /**
     * @return type of operation
     */
    public abstract String getType();

    /**
     * write to output file as a side effect
     *
     * @param input  input file path
     * @param output
     * @return json string
     */
    public abstract String getJSON(String input, String output);
}
