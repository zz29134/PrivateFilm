package com.mp.privatefilm.activity;

import android.os.Bundle;

import com.mp.commonsdk.activity.BaseActivity;
import com.mp.commonsdk.utils.SPTool;
import com.mp.privatefilm.R;
import com.mp.privatefilm.fragment.LaunchFragment;
import com.mp.privatefilm.fragment.LogoFragment;
import com.mp.privatefilm.utils.Constants;

/**
 * Created by Zhangzhe on 2016/4/25.
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame);
        SPTool.addSessionMap(Constants.keywords.Version, getMyApplication().getVersion() + "");
        SPTool.addSessionMap(Constants.keywords.Platform, Constants.Platform);
        SPTool.addSessionMap(Constants.keywords.Deviceid, getMyApplication().getDeviceid());
        if (SPTool.getSessionValue(Constants.keywords.Initialed, false)) {
            pushFragmentToBackStack(LogoFragment.class, null);
        } else {
            pushFragmentToBackStack(LaunchFragment.class, null);
        }
    }

}
