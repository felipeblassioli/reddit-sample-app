package org.samples.blassioli.reddit.features.posts;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.samples.blassioli.reddit.utils.RandomData;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

public class PostsPresenterTest {

    @Mock
    PostsInteractor mockInteractor;
    private PostsPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new PostsPresenter(mockInteractor);
    }

    private PostsInteractor.Params eq(String after, String limit) {
        return argThat(new ParamsMatcher(after, limit));
    }

    @Test
    public void testLoadData_shouldExecuteInteractorWithSameParameters() {
        boolean pullToRefresh = false;
        String after = RandomData.randomString(36);
        String limit = String.valueOf(RandomData.randomInt(1, 100));
        presenter.loadData(pullToRefresh, after, limit);
        verify(mockInteractor).execute(any(), eq(after, limit));
    }

    private static class ParamsMatcher extends ArgumentMatcher<PostsInteractor.Params> {
        private final String after;

        private final String limit;

        public ParamsMatcher(String after, String limit) {
            this.after = after;
            this.limit = limit;
        }

        @Override
        public boolean matches(Object argument) {
            PostsInteractor.Params actual = (PostsInteractor.Params) argument;
            return actual.after.equals(after) && actual.limit.equals(limit);
        }
    }
}
