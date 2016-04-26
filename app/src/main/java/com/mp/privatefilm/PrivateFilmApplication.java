package com.mp.privatefilm;

import com.mp.commonsdk.BaseApplication;
import com.mp.commonsdk.BaseTools;
import com.mp.privatefilm.utils.Constants;

/**
 * Created by eE on 2016/4/23.
 */
public class PrivateFilmApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        BaseTools.getInstance().InitTools(
                Constants.SHARED_PREFERENCE_NAME + "_" + getVersion(),
                Constants.DataPath, Constants.secretKey);
    }
}
