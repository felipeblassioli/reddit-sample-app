package org.samples.blassioli.reddit.features.details;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import org.samples.blassioli.reddit.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    public static final String FRAGMENT_TAG_DETAILS = "detailsFragmentTag";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @BindView(R.id.contentLayout)
    ViewGroup contentPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        showDetails();
    }

    private void showDetails() {
        DetailsFragment fragment = DetailsFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentLayout, fragment, FRAGMENT_TAG_DETAILS)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_out, R.anim.no_change);
    }
}
