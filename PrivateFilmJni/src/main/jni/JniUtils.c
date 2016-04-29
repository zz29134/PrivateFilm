//
// Created by Zhangzhe on 2016/4/29.
//

#include "com_mp_privatefilm_JniUtils.h"

JNIEXPORT jstring JNICALL Java_com_mp_privatefilm_JniUtils_getJniUrl
        (JNIEnv * env, jclass obj){
    return (*env)->NewStringUTF(env, "http://61.52.198.148:8888/APP/");
}

JNIEXPORT jstring JNICALL Java_com_mp_privatefilm_JniUtils_getJniEncryptKey
        (JNIEnv * env, jclass obj){
    return (*env)->NewStringUTF(env, "com.mobile.privatecinema");
}

JNIEXPORT jstring JNICALL Java_com_mp_privatefilm_JniUtils_getJniEncryptVector
        (JNIEnv * env, jclass obj){
    return (*env)->NewStringUTF(env, "01234567");
}