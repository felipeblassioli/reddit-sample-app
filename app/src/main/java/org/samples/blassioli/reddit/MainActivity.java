package org.samples.blassioli.reddit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import org.samples.blassioli.reddit.features.posts.PostsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    public static final String FRAGMENT_TAG_DETAILS = "detailsFragmentTag";
    public static final String FRAGMENT_TAG_POSTS = "postsFragmentTag";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.leftPane)
    ViewGroup leftPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        showPosts();
    }

    private void showPosts() {
        PostsFragment fragment = PostsFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.leftPane, fragment, FRAGMENT_TAG_DETAILS)
                .commit();
    }

}
