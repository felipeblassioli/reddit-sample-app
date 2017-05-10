package org.samples.blassioli.reddit.features.posts;

import android.support.annotation.NonNull;

import org.samples.blassioli.reddit.PaginatedModel;

import java.util.ArrayList;
import java.util.List;

public class PostListModel extends PaginatedModel<Post> {
    public final List<Post> data;

    public PostListModel(@NonNull List<Post> data, String after, String limit) {
        super(after);
        this.data = data;
        this.limit = limit;
    }

    public static PostListModel getEmpty() {
        return new PostListModel(new ArrayList<>(), "", "");
    }

    @Override
    public List<Post> getData() {
        return data;
    }
}
