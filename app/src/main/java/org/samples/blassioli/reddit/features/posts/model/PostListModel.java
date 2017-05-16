package org.samples.blassioli.reddit.features.posts.model;

import android.support.annotation.NonNull;

import org.parceler.Parcel;
import org.samples.blassioli.reddit.PaginatedModel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class PostListModel extends PaginatedModel<Post> {
    public List<Post> data;

    public PostListModel() {
        super("");
    }

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

    public void setData(List<Post> data) {
        this.data = data;
    }
}
