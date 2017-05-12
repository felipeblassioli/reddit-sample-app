package org.samples.blassioli.reddit.features.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import org.samples.blassioli.reddit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

public class DetailsActivity extends AppCompatActivity {

    public static final String FRAGMENT_TAG_DETAILS = "detailsFragmentTag";

    protected static final String P_LINK_ID = "t3LinkId";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @BindView(R.id.contentLayout)
    ViewGroup contentPanel;

    private String currentLinkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        currentLinkId = getIntent().getExtras().getString(P_LINK_ID);
        checkNotNull(currentLinkId);

        initToolbar();
        showDetails();
    }

    protected void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    protected void showDetails() {
        DetailsFragment fragment = DetailsFragment.newInstance(currentLinkId);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentLayout, fragment, FRAGMENT_TAG_DETAILS)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.no_change);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static Intent createIntent(Context context, String linkId) {
        Intent intent = new Intent(context, DetailsActivity.class);
        Bundle b = new Bundle();
        b.putString(DetailsActivity.P_LINK_ID, linkId);
        intent.putExtras(b);
        return intent;
    }
}
