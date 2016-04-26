package com.mp.privatefilm.net;

import android.net.Uri;

import com.mp.commonsdk.utils.Entry;
import com.mp.privatefilm.utils.Constants;

import java.util.List;

public class BuildUrl {

	public static BuildUrl mBuildUrl = null;
	private static String mUrl = Constants.URLHead;

	public static BuildUrl getInstance() {
		if (mBuildUrl == null)
			mBuildUrl = new BuildUrl();
		return mBuildUrl;
	}

	public String geturl() {
		return mUrl;
	}

	public void Create(String url) {
		mUrl = url;
	}

	public String parseBuild(String cmd, List<Entry<String, String>> entrys) {
		Uri.Builder builder = Uri.parse(mUrl + cmd).buildUpon();
		if (null != entrys && entrys.size() > 0) {
			for (Entry<String, String> entry : entrys) {
				builder.appendQueryParameter(entry.key, entry.value);
			}
		}
		/*builder.appendQueryParameter(Constants.reqhead.reqtime, BaseTools.getInstance()
				.getstrAtomicclock());*/
		return builder.toString();
	}

}
