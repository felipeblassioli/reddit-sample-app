package org.samples.blassioli.reddit;

import java.util.List;

public abstract class PaginatedModel<T> {
    public String limit;

    public String after;

    public PaginatedModel(String after) {
        this.after = after;
    }

    public abstract List<T> getData();
}
