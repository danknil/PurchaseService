package io.danknil.operation;

import io.danknil.Main;

public abstract class Operation {
    protected Operation() {
        Main.operations.put(getType(), this);
    }

    /**
     * @return type of operation
     */
    public abstract String getType();

    /**
     * write to output file as a side effect
     * @param input input file path
     * @param output output file path
     * @return json string
     */
    public abstract String getJSON(String input, String output);
}
