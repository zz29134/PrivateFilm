package com.mp.commonsdk.activity;

import com.mp.commonsdk.AppManager;
import com.mp.commonsdk.BaseApplication;
import com.mp.commonsdk.CLog;
import com.mp.commonsdk.R;

import butterknife.ButterKnife;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

@SuppressLint("InflateParams")
public abstract class BaseActivity extends BaseFragmentActivity implements
		IAppManager {

	public void debugStatus(String status) {
		final String[] className = ((Object) this).getClass().getName()
				.split("\\.");
		CLog.i(BaseActivity.class.getName(),
				String.format("%s %s", className[className.length - 1], status));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		ButterKnife.bind(this);
		AppManager.getInstance().addActivity(this);
	}

	@Override
	public void kill() {
		AppManager.getInstance().killActivity(this);
	}

	@Override
	public void AppExit() {
		AppManager.getInstance().AppExit(getApplicationContext());
	}

	@Override
	public BaseApplication getMyApplication() {
		return (BaseApplication) getApplication();
	}

	@Override
	public void killOtherActivity(Class<?> cls) {
		AppManager.getInstance().killOtherActivity(cls);

	}

	@Override
	public void killActivity(Class<?> cls) {
		AppManager.getInstance().killActivity(cls);

	}

	/**
	 * 通过类名启动Activity
	 * 
	 * @param pClass
	 */
	@Override
	public void openActivity(Class<?> pClass) {
		openActivity(pClass, null);
	}

	/**
	 * 通过类名启动Activity，并且含有Bundle数据
	 * 
	 * @param pClass
	 * @param pBundle
	 */
	@Override
	public void openActivity(Class<?> pClass, Bundle pBundle) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	@Override
	public void openActivityForResult(Class<?> pClass, int requestCode) {
		openActivityForResult(pClass, null, requestCode);
	}

	@Override
	public void openActivityForResult(Class<?> pClass, Bundle pBundle,
			int requestCode) {
		Intent intent = new Intent(this, pClass);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivityForResult(intent, requestCode);
	}

	/**
	 * 通过Action启动Activity
	 * 
	 * @param pAction
	 */
	@Override
	public void openActivity(String pAction) {
		openActivity(pAction, null);
	}

	/**
	 * 通过Action启动Activity，并且含有Bundle数据
	 * 
	 * @param pAction
	 * @param pBundle
	 */
	@Override
	public void openActivity(String pAction, Bundle pBundle) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivity(intent);
	}

	@Override
	public void openActivityForResult(String pAction, int requestCode) {
		openActivityForResult(pAction, null, requestCode);
	}

	@Override
	public void openActivityForResult(String pAction, Bundle pBundle,
			int requestCode) {
		Intent intent = new Intent(pAction);
		if (pBundle != null) {
			intent.putExtras(pBundle);
		}
		startActivityForResult(intent, requestCode);
	}

	protected void finishBack() {
		kill();
	}

	public void hideKeyboardForCurrentFocus() {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (getCurrentFocus() != null) {
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
					.getWindowToken(), 0);
		}
	}

	public void showKeyboardAtView(View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager
				.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
	}

	protected void exitFullScreen() {
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
	}

	public Animation getAnimation(int id) {
		return AnimationUtils.loadAnimation(this, id);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	protected String getCloseWarning() {
		return "再按一次返回键关闭系统";
	}

	@Override
	protected int getFragmentContainerId() {
		return R.id.content_frame;
	}
}
