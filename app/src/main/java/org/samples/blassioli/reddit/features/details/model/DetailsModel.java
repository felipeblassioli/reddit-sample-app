package org.samples.blassioli.reddit.features.details.model;

import org.samples.blassioli.reddit.PaginatedModel;
import org.samples.blassioli.reddit.api.posts.LinkData;
import org.samples.blassioli.reddit.features.posts.Post;

import java.util.List;

public class DetailsModel extends PaginatedModel<CommentModel> {

    private final Post postData;
    private final List<CommentModel> data;

    public DetailsModel(List<CommentModel> data, Post postData, String after) {
        super(after);
        this.data = data;
        this.postData = postData;
    }

    @Override
    public List<CommentModel> getData() {
        return data;
    }

    public Post getPostData() {
        return this.postData;
    }
}
