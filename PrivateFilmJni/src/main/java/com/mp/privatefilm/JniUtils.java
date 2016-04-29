package com.mp.privatefilm;

/**
 * Created by Zhangzhe on 2016/4/29.
 */
public class JniUtils {

    static {
        System.loadLibrary("PrivateFilmJni");
    }

    public static native String getJniUrl();
    public static native String getJniEncryptKey();
    public static native String getJniEncryptVector();

}
