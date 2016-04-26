package com.mp.privatefilm.home;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.gc.materialdesign.views.ButtonFlat;
import com.mp.commonsdk.fragment.BaseFragment;
import com.mp.privatefilm.R;

import butterknife.Bind;

/**
 * Created by eE on 2016/4/24.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.setting)
    protected ButtonFlat setting;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.home_fragment);
        setting.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
    }

    @Override
    protected void onEmptyButtonClick(View v) {

    }

}
