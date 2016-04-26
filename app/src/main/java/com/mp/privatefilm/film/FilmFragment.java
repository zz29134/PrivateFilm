package com.mp.privatefilm.film;

import android.os.Bundle;
import android.view.View;

import com.mp.commonsdk.fragment.BaseFragment;
import com.mp.privatefilm.R;

/**
 * Created by eE on 2016/4/24.
 */
public class FilmFragment extends BaseFragment {

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.filmfragment);
    }

    @Override
    protected void onEmptyButtonClick(View v) {

    }

}
