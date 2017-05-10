package org.samples.blassioli.reddit.features.details;

import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.samples.blassioli.reddit.features.details.api.DataChildrenItem;
import org.samples.blassioli.reddit.features.details.api.DataChildrenItemContent;
import org.samples.blassioli.reddit.features.details.api.DetailsApi;
import org.samples.blassioli.reddit.features.details.api.DetailsApiResponse;
import org.samples.blassioli.reddit.features.details.api.DetailsData;
import org.samples.blassioli.reddit.features.details.api.DetailsDataChildren;
import org.samples.blassioli.reddit.features.details.data.DetailsRemoteDataStore;
import org.samples.blassioli.reddit.utils.RandomData;

import java.util.ArrayList;

import io.reactivex.Single;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    public void testGetDetails() {
        // FakeResponse
        DetailsApiResponseBuilder builder = new DetailsApiResponseBuilder();
        for(int i=0; i<100; i++) {
            builder.withChild(new DataChildrenItemBuilder()
                    .withId("comment#" + i)
                    .withKind("t1")
                    .build());
        }
        DetailsApiResponse fakeResponse = builder.build();

        when(mockDetailsApi.getDetails(any(), any(), anyInt()))
                .thenReturn(Single.just(fakeResponse));

        String sub = "Android";
        String linkId = "id";
        detailsDataStore.getDetails(sub, linkId)
                .test()
                .assertNoErrors()
                .assertValue(detailsModel -> {
                    assertThat(detailsModel.getData(), is(notNullValue()));
                    assertThat(detailsModel.getData().size(), equalTo(fakeResponse.childWithT1.data.children.size()));
                    return true;
                });
        verify(mockDetailsApi).getDetails(eq(sub), eq(linkId), anyInt());
    }

    @Test
    public void testGetDetailsError() {
        when(mockDetailsApi.getDetails(any(), any(), anyInt()))
                .thenReturn(Single.error(new Exception("Single Error")));

        String sub = "Android";
        String linkId = "id";
        detailsDataStore.getDetails(sub, linkId)
                .test()
                .assertError(e -> e.getMessage().equals("Single Error"));
        verify(mockDetailsApi).getDetails(eq(sub), eq(linkId), anyInt());
    }

    private class DataChildrenItemBuilder {
        private DataChildrenItem current;

        public DataChildrenItemBuilder() {
            current = new DataChildrenItem();
            current.data = new DataChildrenItemContent();
            current.data.id = RandomData.randomString(36);
            current.data.author = RandomData.randomString(40);
            current.data.body = RandomData.randomString(2056);
        }

        public DataChildrenItemBuilder withId(String id) {
            current.data.id = id;
            return this;
        }

        public DataChildrenItemBuilder withAuthor(String author) {
            current.data.author = author;
            return this;
        }

        public DataChildrenItemBuilder withBody(String body) {
            current.data.body = body;
            return this;
        }

        public DataChildrenItemBuilder withKind(String kind) {
            current.kind = kind;
            return this;
        }

        public DataChildrenItem build() {
            return current;
        }

    }

    private class DetailsApiResponseBuilder {
        private DetailsApiResponse current;

        public DetailsApiResponseBuilder() {
            current = new DetailsApiResponse();
            current.childWithT1 = new DetailsData();
            current.childWithT1.data = new DetailsDataChildren();
            current.childWithT1.data.children = new ArrayList<>();
            current.childWithT3 = new DetailsData();
            current.childWithT3.data = new DetailsDataChildren();
            current.childWithT3.data.children = new ArrayList<>();
        }

        public DetailsApiResponseBuilder withChild(DataChildrenItem child) {
            if("t1".equals(child.kind)) {
                current.childWithT1.data.children.add(child);
            } else {
                current.childWithT3.data.children.add(child);
            }
            return this;
        }

        public DetailsApiResponse build() {
            return current;
        }
    }
}
