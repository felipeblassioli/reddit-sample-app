package org.samples.blassioli.reddit.features.details.data;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.samples.blassioli.reddit.api.posts.LinkData;
import org.samples.blassioli.reddit.api.posts.PostsModelMapper;
import org.samples.blassioli.reddit.features.details.api.Comment;
import org.samples.blassioli.reddit.features.details.api.CommentData;
import org.samples.blassioli.reddit.features.details.api.DetailsApi;
import org.samples.blassioli.reddit.features.details.api.DetailsApiResponse;
import org.samples.blassioli.reddit.features.details.model.CommentModel;
import org.samples.blassioli.reddit.features.details.model.DetailsModel;
import org.samples.blassioli.reddit.features.posts.Post;
import org.samples.blassioli.reddit.features.posts.PostListModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

import static com.google.common.base.Preconditions.checkNotNull;

public class DetailsRemoteDataStore implements DetailsDataStore {
    private final DetailsApi api;

    private final PostsModelMapper postsModelMapper;

    @Inject
    public DetailsRemoteDataStore(DetailsApi api) {
        this.api = api;
        this.postsModelMapper = new PostsModelMapper();
    }

    private CommentModel toDetailsItem(Comment itemJson) {
        checkNotNull(itemJson.kind);
        //checkArgument(itemJson.kind.equals("t1"));

        CommentData data = itemJson.data;

        CommentModel response = new CommentModel();
        response.kind = itemJson.kind;
        response.id = data.id;
        response.author = data.author;
        response.body = data.body;
        response.ups = data.ups;
        response.created = data.created;
        response.createdUtc = data.created_utc;
        response.depth = data.depth;
        return response;
    }

    private CommentModel toDetailsItem(Map<String, Object> itemJson) {
        Moshi moshi = new Moshi.Builder().build();

        Type type = Types.newParameterizedType(Map.class, String.class, Object.class);
        JsonAdapter<Map<String, Object>> jsonAdapter2 = moshi.adapter(type);
        String itemJsonStr = jsonAdapter2.toJson(itemJson);

        JsonAdapter<Comment> jsonAdapter = moshi.adapter(Comment.class);
        Comment response = null;
        try {
            response = jsonAdapter.fromJson(itemJsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toDetailsItem(response);
    }

    private List<CommentModel> toListOfDetailsModel(List<Comment> items) {
        List<CommentModel> result = new ArrayList<>();
        CommentModel current;
        List<Map<String, Object>> children;
        for (Comment item : items) {
            current = toDetailsItem(item);
            result.add(current);
            children = PreOrderCommentsIterator.getPreOrder(item.data.replies);
            if (children != null) {
                result.addAll(Lists.transform(children, c -> toDetailsItem(c)));
            }
        }
        return result;
    }

    private DetailsModel toDetailsModel(DetailsApiResponse response) {
        List<CommentModel> items =
                Optional.fromNullable(response)
                        .transform(r -> r.childWithT1)
                        .transform(child -> child.data)
                        .transform(data -> data.children)
                        .transform(children -> toListOfDetailsModel(children))
                        .or(new ArrayList<>());
        Post post =
                Optional.fromNullable(response.childWithT3)
                        .transform(t3 -> postsModelMapper.from(t3, null))
                        .transform(model -> model.data)
                        .transform(data -> data.size() > 0? data.get(0) : new Post())
                        .orNull();
        return new DetailsModel(
                items,
                post,
                ""
        );
    }

    private Single<DetailsModel> getDetails(String subreddit, String id, int repliesDepth, int limit) {
        return api.getDetails(subreddit, id, repliesDepth, limit)
                .map(r -> toDetailsModel(r))
                .onErrorResumeNext(e -> {
                    if (repliesDepth > 1 && limit > 5) {
                        return getDetails(subreddit, id, repliesDepth - 1, limit - 5);
                    } else {
                        return Single.error(e);
                    }
                });
    }

    @Override
    public Observable<DetailsModel> getDetails(String subreddit, String id) {
        final int repliesDepth = 4;
        final int limit = 20;
        return getDetails(subreddit, id, repliesDepth, limit)
                .toObservable();
    }
}
