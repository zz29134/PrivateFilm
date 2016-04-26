package com.mp.commonsdk;

import java.util.UUID;

import com.mp.commonsdk.BaseTools;

import android.annotation.SuppressLint;

import android.app.Application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		init();

	}

	@SuppressLint("CommitPrefEdits")
	private void init() {
		BaseTools.onCreate(this);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	static String Deviceid;

	public String getDeviceid() {
		if (TextUtils.isEmpty(Deviceid)) {
			TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
			Deviceid = tm.getDeviceId();
			if (TextUtils.isEmpty(Deviceid))
				Deviceid = UUID.randomUUID().toString();
		}
		return Deviceid;
	}

	public int getVersion() {
		try {
			PackageManager packageManager = getPackageManager();
			PackageInfo packInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			return packInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public String getTelphoneNumber() {
		try {
			TelephonyManager tm = (TelephonyManager) this
					.getSystemService(Context.TELEPHONY_SERVICE);
			return tm.getLine1Number();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getVersionName() {
		String version = "";
		try {
			PackageManager packageManager = getPackageManager();
			PackageInfo packInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			version = packInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return version;
	}

}
