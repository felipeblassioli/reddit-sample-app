package org.samples.blassioli.reddit.features.posts;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.samples.blassioli.reddit.api.posts.RedditApi;
import org.samples.blassioli.reddit.api.posts.RedditListingChildrenResponse;
import org.samples.blassioli.reddit.api.posts.RedditListingDataResponse;
import org.samples.blassioli.reddit.api.posts.RedditListingResponse;
import org.samples.blassioli.reddit.api.posts.RedditPostsDataResponse;
import org.samples.blassioli.reddit.utils.RandomData;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.samples.blassioli.reddit.utils.PredicateUtils.check;

public class PostsRemoteDataStoreTest {
    @Mock
    RedditApi mockRestApi;
    private PostsRemoteDataStore postsDataStore;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        postsDataStore = new PostsRemoteDataStore(mockRestApi);
    }

    @Test
    public void testGetPostsList_shouldCallApi_getSubRedditPosts_withSameParameters() {
        String sub = RandomData.randomString(256);
        String label = RandomData.randomString(256);
        String from = RandomData.randomString(256);
        String limit = String.valueOf(RandomData.randomInt(0, 1024));
        postsDataStore.getPostsList(sub, label, from, limit);
        verify(mockRestApi).getSubRedditPosts(eq(sub), eq(label), eq(from), eq(limit));
    }

    @Test
    public void testGetPostsList_shouldCreateDetailsModel_withSameSize_asApiResponseCommentsChildren() {
        RedditListingResponse fakeResponse = new RedditListingResponse();
        fakeResponse.data = new RedditListingDataResponse();
        fakeResponse.data.children = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            fakeResponse.data.children.add(randomChildWithId("post" + i));
        }
        when(mockRestApi.getSubRedditPosts(any(), any(), any(), any()))
                .thenReturn(Single.just(fakeResponse));

        callGetPostsListWithRandomParameters()
                .test()
                .assertNoErrors()
                .assertValue(check(
                        postsModel -> {
                            assertThat(postsModel).isNotNull();
                            assertThat(postsModel.getData()).isNotNull();
                            assertThat(postsModel.getData().size())
                                    .isEqualTo(fakeResponse.data.children.size());
                        }

                ));
    }

    @Test
    public void testGetPostsList_shouldPropagateApiErrors() {
        when(mockRestApi.getSubRedditPosts(any(), any(), any(), any()))
                .thenReturn(Single.error(new Exception("Single Error")));
        callGetPostsListWithRandomParameters()
                .test()
                .assertError(e -> e.getMessage().equals("Single Error"));
    }

    private Observable<PostListModel> callGetPostsListWithRandomParameters() {
        String sub = RandomData.randomString(256);
        String label = RandomData.randomString(256);
        String from = RandomData.randomString(256);
        String limit = String.valueOf(RandomData.randomInt(0, 1024));
        return postsDataStore.getPostsList(sub, label, from, limit);
    }


    private RedditListingChildrenResponse randomChildWithId(String id) {
        RedditListingChildrenResponse response = new RedditListingChildrenResponse();
        response.data = new RedditPostsDataResponse();
        response.data.id = id;
        response.data.author = RandomData.randomString(256);
        response.data.title = RandomData.randomString(256);
        response.data.num_comments = String.valueOf(RandomData.randomInt(0, 2056));
        response.data.thumbnail = RandomData.randomString(256);
        response.data.url = RandomData.randomString(256);
        response.data.ups = String.valueOf(RandomData.randomInt(0, 2056));
        response.data.permalink = RandomData.randomString(256);
        response.data.selftext = RandomData.randomString(256);
        return response;
    }
}
