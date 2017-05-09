package org.samples.blassioli.reddit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.samples.blassioli.reddit.api.RedditApi;
import org.samples.blassioli.reddit.api.RedditListingResponse;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl("https://www.reddit.com")
                        .addConverterFactory(MoshiConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

        final RedditApi api = retrofit.create(RedditApi.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View _view = view;
                api.getSubRedditPosts("Android", "new", "", "10")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<RedditListingResponse>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@NonNull RedditListingResponse redditListingResponse) {
                                Snackbar.make(_view, "Replace with your own action", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                e.printStackTrace();
                            }
                        });


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
