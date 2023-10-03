package io.danknil.database;

import java.sql.Connection;

public abstract class Model {
    protected final int id;
    protected Model(int id) {
        this.id = id;
    }
}
