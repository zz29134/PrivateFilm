/*
 * Copyright (C) 2011 The Android Open Source Project
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

package com.mp.privatefilm.net;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.ParseError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.mp.privatefilm.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * A request for retrieving a {@link JSONObject} response body at a given URL,
 * allowing for an optional {@link JSONObject} to be passed in as part of the
 * request body.
 */
public class JsonObjectRequest extends Request<JSONObject> {

	private Listener<JSONObject> listener;
	private Map<String, String> params;

	public JsonObjectRequest(String url, Listener<JSONObject> reponseListener,
			ErrorListener errorListener) {
		this(Method.GET, url, null, reponseListener, errorListener);
	}

	public JsonObjectRequest(String url, Map<String, String> params,
			Listener<JSONObject> reponseListener, ErrorListener errorListener) {
		this(Method.GET, url, params, reponseListener, errorListener);
	}

	public JsonObjectRequest(int method, String url, Map<String, String> params,
			Listener<JSONObject> reponseListener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.setRetryPolicy(new DefaultRetryPolicy(10000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		this.listener = reponseListener;
		this.params = params;
	}

	protected Map<String, String> getParams() throws AuthFailureError {
		return params;
	}

	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data, "utf-8");
//			String str = decode(jsonString);
			String str = Des3.decode(jsonString);
			JSONObject jo = new JSONObject(str);
			if (str.length() <= 3400) {
				Log.d("返回的解密的值：" , str);
			} else {
				int flag = 0;
				for (int i = 0; i < (str.length() / 3400); i++) {
					flag = i + 1;
					Log.d("返回的解密的值(" + flag + "):"
							, str.substring(i * 3400, flag * 3400));
				}
				Log.d("返回的解密的值(" + (flag + 1) + "):"
						, str.substring(flag * 3400, str.length()));
			}
			return Response.success(jo, HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JSONException je) {
			return Response.error(new ParseError(je));
		} catch (Exception e) {
			return Response.error(new ParseError(e));
		}
	}
	
//	public static String decode(String code) {
//		try {
//			byte[] bcode = Base64.decode(code, Base64.DEFAULT);
//			byte[] bkey = "com.mobile.privatecinema".getBytes("GBK");
//			int len = bcode.length;
//			int mlen = bkey.length;
//			for (int i = 0; i < len; i++) {
//				bcode[i] ^= bkey[i % mlen];
//			}
//			return new String(bcode, "GBK"); // 返回密码结果
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "{\"code\":\"003\",\"state\":\"程序异常\",\"content\":\"\"}";
//		}
//	}

	@Override
	protected void deliverResponse(JSONObject response) {
		listener.onResponse(response);
	}
}
