package org.samples.blassioli.reddit.features.details;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;
import org.samples.blassioli.reddit.AndroidApplication;
import org.samples.blassioli.reddit.BuildConfig;
import org.samples.blassioli.reddit.utils.RandomData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DetailsFragmentTest {

    @Test
    public void testNewInstance() {
        String subrreditId = RandomData.randomString(36);
        DetailsFragment fragment = DetailsFragment.newInstance(subrreditId, null);

        assertThat(fragment).isNotNull();
        assertThat(fragment.getArguments()).isNotNull();
        assertThat(fragment.getArguments().getString(DetailsFragment.P_SUBREDDIT_ID))
                .isEqualTo(subrreditId);
    }

    @Test
    @Config(application = AndroidApplication.class)
    public void testOnViewCreated_shouldCallLoadData() {
        String subrreditId = RandomData.randomString(36);
        DetailsFragment fragment = spy(DetailsFragment.newInstance(subrreditId, null));
        SupportFragmentTestUtil.startFragment(fragment);

        verify(fragment).loadData(anyBoolean());
    }

}
