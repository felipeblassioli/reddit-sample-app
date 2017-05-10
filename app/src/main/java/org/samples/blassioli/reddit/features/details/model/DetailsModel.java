package org.samples.blassioli.reddit.features.details.model;

import org.samples.blassioli.reddit.PaginatedModel;

import java.util.List;

public class DetailsModel extends PaginatedModel<DetailsItem> {

    private final List<DetailsItem> data;

    public DetailsModel(List<DetailsItem> data, String after) {
        super(after);
        this.data = data;
    }

    @Override
    public List<DetailsItem> getData() {
        return data;
    }
}
