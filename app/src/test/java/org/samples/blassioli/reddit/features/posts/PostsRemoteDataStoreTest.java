package org.samples.blassioli.reddit.features.posts;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.samples.blassioli.reddit.api.posts.RedditApi;
import org.samples.blassioli.reddit.api.posts.RedditListingResponse;

import io.reactivex.Single;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostsRemoteDataStoreTest {
    private PostsRemoteDataStore postsDataStore;

    @Mock
    RedditApi mockRestApi;

    @Mock
    RedditListingResponse mockResponse;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        postsDataStore = new PostsRemoteDataStore(mockRestApi);
    }

    @Test
    public void testGetPostsList() {
        when(mockRestApi.getSubRedditPosts(any(), any(), any(), any()))
                .thenReturn(Single.just(mockResponse));

        String sub = "Android";
        String label = "new";
        String from = "";
        String limit = "10";
        postsDataStore.getPostsList(sub, label, from, limit);
        verify(mockRestApi).getSubRedditPosts(sub, label, from, limit);
    }

}
