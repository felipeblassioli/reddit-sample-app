package org.samples.blassioli.reddit.features.posts;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.samples.blassioli.reddit.R;
import org.samples.blassioli.reddit.features.details.DetailsActivity;

import com.google.android.gms.plus.PlusOneButton;

/**
 * A fragment with a Google +1 button.
 */
public class PostsFragment extends Fragment {

    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    //private PlusOneButton mPlusOneButton;
    private Button button;

    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        //Find the +1 button
        //mPlusOneButton = (PlusOneButton) view.findViewById(R.id.plus_one_button);
        button = (Button) view.findViewById(R.id.bt_teste);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_info = new Intent(getActivity(), DetailsActivity.class);
                startActivity(intent_info);
                getActivity().overridePendingTransition(R.anim.slide_up, R.anim.no_change);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
        //mPlusOneButton.initialize(PLUS_ONE_URL, PLUS_ONE_REQUEST_CODE);
    }


    public static PostsFragment newInstance() {
        return new PostsFragment();
    }
}
