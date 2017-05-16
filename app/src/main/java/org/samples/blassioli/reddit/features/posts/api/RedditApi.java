package org.samples.blassioli.reddit.features.posts.api;

import org.samples.blassioli.reddit.api.posts.ListingOfLinks;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RedditApi {
    @GET("/r/{subreddit}/{label}/.json")
    Single<ListingOfLinks> getSubRedditPosts(@Path("subreddit") String subreddit,
                                             @Path("label") String label,
                                             @Query("after") String after,
                                             @Query("limit") String limit);
}
