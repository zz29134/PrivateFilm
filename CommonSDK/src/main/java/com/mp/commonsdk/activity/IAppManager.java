package com.mp.commonsdk.activity;



import com.mp.commonsdk.BaseApplication;

import android.os.Bundle;

public interface IAppManager {
	public void kill();
	
	public void killOtherActivity(Class<?> cls);
	
	public void killActivity(Class<?> cls);

	public void AppExit();

	public BaseApplication getMyApplication();

	/**
	 * 通过类名启动Activity
	 * 
	 * @param pClass
	 */
	public void openActivity(Class<?> pClass);

	/**
	 * 通过类名启动Activity，并且含有Bundle数据
	 * 
	 * @param pClass
	 * @param pBundle
	 */
	public void openActivity(Class<?> pClass, Bundle pBundle);

	public void openActivityForResult(Class<?> pClass, int requestCode);

	public void openActivityForResult(Class<?> pClass, Bundle pBundle,
									  int requestCode);

	/**
	 * 通过Action启动Activity
	 * 
	 * @param pAction
	 */
	public void openActivity(String pAction);

	/**
	 * 通过Action启动Activity，并且含有Bundle数据
	 * 
	 * @param pAction
	 * @param pBundle
	 */
	public void openActivity(String pAction, Bundle pBundle);

	public void openActivityForResult(String pAction, int requestCode);

	public void openActivityForResult(String pAction, Bundle pBundle,
									  int requestCode);



}
