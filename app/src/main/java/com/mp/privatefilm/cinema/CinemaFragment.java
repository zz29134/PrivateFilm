package com.mp.privatefilm.cinema;

import android.os.Bundle;
import android.view.View;

import com.mp.commonsdk.fragment.BaseFragment;
import com.mp.privatefilm.R;

/**
 * Created by eE on 2016/4/24.
 */
public class CinemaFragment extends BaseFragment {

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.cinemafragment);
    }

    @Override
    protected void onEmptyButtonClick(View v) {

    }
}
