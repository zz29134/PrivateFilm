package com.mp.commonsdk;

import java.util.Stack;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

public class AppManager {
	private static Stack<Activity> mActivityStack=null;
	private static AppManager mAppManager;

	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getInstance() {
		if (mAppManager == null) {
			synchronized (AppManager.class) {
				mAppManager = new AppManager();
				if (mActivityStack == null) {
					mActivityStack = new Stack<Activity>();
				}
			}
			
		}
		return mAppManager;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		synchronized (mActivityStack) {			
			mActivityStack.add(activity);
		}
	}

	/**
	 * 获取栈顶Activity（堆栈中最后一个压入的）
	 */
	public Activity getTopActivity() {
		Activity activity;
		synchronized (mActivityStack) {
			activity = mActivityStack.lastElement();
		}
		return activity;
	}

	/**
	 * 结束栈顶Activity（堆栈中最后一个压入的）
	 */
	public void killTopActivity() {
		Activity activity = mActivityStack.lastElement();
		killActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void killActivity(Activity activity) {
		synchronized (mActivityStack) {
			if (activity != null) {
				mActivityStack.remove(activity);
				activity.finish();
				activity = null;
			}
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void killActivity(Class<?> cls) {
		synchronized (mActivityStack) {
			for (Activity activity : mActivityStack) {
				if (activity.getClass().equals(cls)) {
					killActivity(activity);
				}
			}
		}
	}
	/**
	 * 结束除指定Activity的其他Activity
	 * @param cls
	 */
	public void killOtherActivity(Class<?> cls) {
		synchronized (mActivityStack) {
			for (Activity activity : mActivityStack) {
				if (!activity.getClass().equals(cls)) {
					killActivity(activity);
				}
			}
		}
	}


	/**
	 * 结束所有Activity
	 */
	public void killAllActivity() {
		synchronized (mActivityStack) {
			for (int i = 0, size = mActivityStack.size(); i < size; i++) {
				if (null != mActivityStack.get(i)) {
					mActivityStack.get(i).finish();
				}
			}
			mActivityStack.clear();
		}
	}

	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			killAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
		}
	}
}
