package io.danknil.database;

public abstract class Model {
    protected final int id;

    protected Model(int id) {
        this.id = id;
    }

    /**
     * @return model as json string
     */
    public abstract String toJSON();
}
