package com.mp.commonsdk;

import java.util.List;
import org.litepal.LitePalApplication;
import org.litepal.exceptions.GlobalException;

import butterknife.ButterKnife;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import android.provider.Settings;

import com.mp.commonsdk.utils.Encrypt;
import com.mp.commonsdk.utils.LocalDisplay;
import com.mp.commonsdk.utils.NetworkStatusManager;
import com.mp.commonsdk.utils.SDFileTool;
import com.mp.commonsdk.utils.SPTool;
import com.mp.commonsdk.utils.VolleyTool;

public class BaseTools {
	private static BaseTools instance;
	private Application mApplication;

	public static void onCreate(Application app) {
		instance = new BaseTools(app);
	}

	private BaseTools(Application application) {
		mApplication = application;
	}

	public void InitTools(String NameSpace, String DataPath, String EncryptKey) {
		ButterKnife.setDebug(BuildConfig.DEBUG);
		VolleyTool.init(mApplication);
		SPTool.init(mApplication, NameSpace);
		LocalDisplay.init(mApplication);
		NetworkStatusManager.init(mApplication);
		SDFileTool.InitPath(DataPath);
		Encrypt.init(EncryptKey);
		LitePalApplication.initialize(mApplication);
	}

	public static BaseTools getInstance() {
		return instance;
	}

	public Context getContext() {
		if (mApplication == null) {
			throw new GlobalException(
					GlobalException.APPLICATION_CONTEXT_IS_NULL);
		}
		return mApplication;
	}

	public String getAndroidId() {
		String id = Settings.Secure.getString(
				mApplication.getContentResolver(), Settings.Secure.ANDROID_ID);
		return id;
	}

	public boolean isServiceRunning(Context mContext, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

}
