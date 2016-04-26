/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mp.commonsdk.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



import android.util.Base64;

/**
 * Class from encrypting string
 */
public class Encrypt {

	private static String key;

	private Encrypt() {

	}

	public static void init(String str) {
		key = str;
	}

	public static String md5(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	public static String decode(String code) {
		try {		
			byte[] bcode = Base64.decode(code, Base64.DEFAULT);
			byte[] bkey = key.getBytes("GBK");
			int len = bcode.length;
			int mlen = bkey.length;
			for (int i = 0; i < len; i++) {
				bcode[i] ^= bkey[i % mlen];
			}
			return new String(bcode, "GBK"); // 返回密码结果
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"code\":\"003\",\"state\":\"程序异常\",\"content\":\"\"}";
		}
	}

	public static String encrypt(String code) {
		try {
			byte[] bcode = code.getBytes("GBK");
			byte[] bkey = key.getBytes("GBK");
			int len = bcode.length;
			int mlen = bkey.length;
			for (int i = 0; i < len; i++) {
				bcode[i] ^= bkey[i % mlen];
			}
			return Base64.encodeToString(bcode, Base64.DEFAULT); // 返回密码结果
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"code\":\"003\",\"state\":\"程序异常\",\"content\":\"\"}";
		}
	}

	public static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}
}
