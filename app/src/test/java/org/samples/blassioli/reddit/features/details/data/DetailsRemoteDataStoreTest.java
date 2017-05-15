package org.samples.blassioli.reddit.features.details.data;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.samples.blassioli.reddit.api.posts.ListingData;
import org.samples.blassioli.reddit.api.posts.ListingOfLinks;
import org.samples.blassioli.reddit.features.details.api.Comment;
import org.samples.blassioli.reddit.features.details.api.CommentData;
import org.samples.blassioli.reddit.features.details.api.DetailsApi;
import org.samples.blassioli.reddit.features.details.api.DetailsApiResponse;
import org.samples.blassioli.reddit.features.details.api.ListingOfComments;
import org.samples.blassioli.reddit.features.details.model.DetailsModel;
import org.samples.blassioli.reddit.utils.RandomData;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.samples.blassioli.reddit.utils.PredicateUtils.check;

public class DetailsRemoteDataStoreTest {
    @Mock
    DetailsApi mockDetailsApi;

    private DetailsRemoteDataStore detailsDataStore;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        detailsDataStore = new DetailsRemoteDataStore(mockDetailsApi);
    }

    @Test
    public void testGetDetails_shouldCallApi_getDetails_withSameParameters() {
        String sub = RandomData.randomString(256);
        String linkId = RandomData.randomString(36);
        detailsDataStore.getDetails(sub, linkId);
        verify(mockDetailsApi).getDetails(eq(sub), eq(linkId), anyInt(), anyInt());
    }

    @Test
    public void testGetDetails_shouldCreateDetailsModel_withSameSize_asApiResponseCommentsChildren() {
        // FakeResponse
        DetailsApiResponseBuilder builder = new DetailsApiResponseBuilder();
        for (int i = 0; i < 100; i++) {
            builder.withChild(new CommentBuilder()
                    .withId("comment#" + i)
                    .withKind("t1")
                    .build());
        }
        DetailsApiResponse fakeResponse = builder.build();

        when(mockDetailsApi.getDetails(any(), any(), anyInt(), anyInt()))
                .thenReturn(Single.just(fakeResponse));
        callGetDetailsWithRandomParameters()
                .test()
                .assertNoErrors()
                .assertValue(check(
                        detailsModel -> {
                            assertThat(detailsModel).isNotNull();
                            assertThat(detailsModel.getData()).isNotNull();
                            assertThat(detailsModel.getData().size())
                                    .isEqualTo(fakeResponse.childWithT1.data.children.size());
                        }
                ));
    }

    @Test
    public void testGetDetails_shouldPropagateApiErrors() {
        when(mockDetailsApi.getDetails(any(), any(), anyInt(), anyInt()))
                .thenReturn(Single.error(new Exception("Single Error")));

        callGetDetailsWithRandomParameters()
                .test()
                .assertError(e -> e.getMessage().equals("Single Error"));
    }

    private Observable<DetailsModel> callGetDetailsWithRandomParameters() {
        String sub = RandomData.randomString(256);
        String linkId = RandomData.randomString(36);
        return detailsDataStore.getDetails(sub, linkId);
    }

    private class CommentBuilder {
        private Comment current;

        public CommentBuilder() {
            current = new Comment();
            current.data = new CommentData();
            current.data.id = RandomData.randomString(36);
            current.data.author = RandomData.randomString(40);
            current.data.body = RandomData.randomString(2056);
        }

        public CommentBuilder withId(String id) {
            current.data.id = id;
            return this;
        }

        public CommentBuilder withKind(String kind) {
            current.kind = kind;
            return this;
        }

        public Comment build() {
            return current;
        }

    }

    private class DetailsApiResponseBuilder {
        private DetailsApiResponse current;

        public DetailsApiResponseBuilder() {
            current = new DetailsApiResponse();
            current.childWithT1 = new ListingOfComments();
            current.childWithT1.data = new ListingData<>();
            current.childWithT1.data.children = new ArrayList<>();
            current.childWithT3 = new ListingOfLinks();
            current.childWithT3.data = new ListingData<>();
            current.childWithT3.data.children = new ArrayList<>();
        }

        public DetailsApiResponseBuilder withChild(Comment child) {
            current.childWithT1.data.children.add(child);
            return this;
        }

        public DetailsApiResponse build() {
            return current;
        }
    }
}
