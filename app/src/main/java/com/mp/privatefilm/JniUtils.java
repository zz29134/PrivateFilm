package com.mp.privatefilm;

import com.mp.privatefilm.utils.Constants;

/**
 * Created by Zhangzhe on 2016/4/29.
 */
public class JniUtils {

    static {
        System.loadLibrary(Constants.JniName);
    }

    public static native String getJniUrl();
    public static native String getJniEncryptKey();
    public static native String getJniEncryptVector();
}
