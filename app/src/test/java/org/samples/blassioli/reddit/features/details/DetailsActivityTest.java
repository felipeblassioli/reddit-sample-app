package org.samples.blassioli.reddit.features.details;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.samples.blassioli.reddit.BuildConfig;
import org.samples.blassioli.reddit.utils.RandomData;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DetailsActivityTest {

    @Test(expected = NullPointerException.class)
    public void test_shouldThrowException_ifCurrendLinkIdNull() {
        Robolectric.setupActivity(DetailsActivity.class);
    }

    @Test
    public void testOnCreate_shouldCall_initToolbar_then_showDetails() {
        String currentLinkId = RandomData.randomString(36);
        Intent intent = DetailsActivity.createIntent(RuntimeEnvironment.application, currentLinkId, null);
        DetailsActivity activity = spy(Robolectric.buildActivity(DetailsActivity.class)
                .withIntent(intent)
                .get());

        activity.onCreate(null);
        verify(activity).initToolbar();
        verify(activity).showDetails();
    }

    @Test
    public void testOnBackPressed_shouldCall_overridePendingAnimation() {
        String currentLinkId = RandomData.randomString(36);
        Intent intent = DetailsActivity.createIntent(RuntimeEnvironment.application, currentLinkId, null);
        DetailsActivity activity = spy(Robolectric.buildActivity(DetailsActivity.class)
                .withIntent(intent)
                .create()
                .start()
                .visible()
                .get());

        activity.onBackPressed();
        verify(activity).overridePendingTransition(anyInt(), anyInt());
    }

    @Test
    public void testOnSupportNavigateUp_shouldCall_onBackPressed() {
        String currentLinkId = RandomData.randomString(36);
        Intent intent = DetailsActivity.createIntent(RuntimeEnvironment.application, currentLinkId, null);
        DetailsActivity activity = spy(Robolectric.buildActivity(DetailsActivity.class)
                .withIntent(intent)
                .create()
                .start()
                .visible()
                .get());

        activity.onSupportNavigateUp();
        verify(activity).onBackPressed();
    }

    @Test
    public void testCreateIntent() {
        String linkId = "linkId";
        Intent intent = DetailsActivity.createIntent(RuntimeEnvironment.application, linkId, null);
        assertThat(intent).isNotNull();
        assertThat(intent.getExtras()).isNotNull();
        assertThat(intent.getExtras().getString(DetailsActivity.P_LINK_ID)).isEqualTo(linkId);
    }

}