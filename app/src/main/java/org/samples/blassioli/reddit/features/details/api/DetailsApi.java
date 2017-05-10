package org.samples.blassioli.reddit.features.details.api;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DetailsApi {
    @GET("/r/{subreddit}/comments/{id}/.json")
    Single<DetailsApiResponse> getDetails(@Path("subreddit") String sub,
                                         @Path("id") String id,
                                         @Query("depth") int depth);
}
