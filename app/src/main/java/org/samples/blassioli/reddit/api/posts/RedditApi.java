package org.samples.blassioli.reddit.api.posts;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RedditApi {
    @GET("/r/{subreddit}/{label}/.json")
    Single<RedditListingResponse> getSubRedditPosts(@Path("subreddit") String subreddit,
                                                    @Path("label") String label,
                                                    @Query("after") String after,
                                                    @Query("limit") String limit);
}
