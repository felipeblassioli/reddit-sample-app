package org.samples.blassioli.reddit.features.details;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.samples.blassioli.reddit.utils.RandomData;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;

public class DetailsPresenterTest {

    @Mock
    DetailsInteractor mockInteractor;

    private DetailsPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new DetailsPresenter(mockInteractor);
    }

    private DetailsInteractor.Params eq(String id) {
        return argThat(new ParamsMatcher(id));
    }

    @Test
    public void testExecuteInteractorWithCorrectParameters() {
        boolean pullToRefresh = false;
        String id = RandomData.randomString(36);
        presenter.loadData(pullToRefresh, id);
        verify(mockInteractor).execute(any(), eq(id));
    }

    private static class ParamsMatcher extends ArgumentMatcher<DetailsInteractor.Params> {
        private final String id;

        public ParamsMatcher(String id) {
            this.id = id;
        }

        @Override
        public boolean matches(Object argument) {
            DetailsInteractor.Params actual = (DetailsInteractor.Params) argument;
            return actual.id.equals(id);
        }
    }
}
