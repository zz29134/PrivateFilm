package com.mp.commonsdk.activity;

import com.mp.commonsdk.CLog;
import com.mp.commonsdk.fragment.BaseFragment;
import com.mp.commonsdk.fragment.FragmentParam;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;


import android.widget.Toast;

public abstract class BaseFragmentActivity extends FragmentActivity {

	protected final String LOGTAG = CLog.makeLogTag(this.getClass());
	protected BaseFragment mCurrentFragment;
	private boolean mCloseWarned;

	protected abstract String getCloseWarning();

	protected abstract int getFragmentContainerId();

	// 添加新Fragment入栈，或显示已经入栈的
	public void pushFragmentToBackStack(Class<?> cls, Object data) {
		FragmentParam param = new FragmentParam();
		param.cls = cls;
		param.data = data;
		goToThisFragment(param);
	}

	public void replaceFragmentNoBackStack(Class<?> cls, Object data) {
		FragmentParam param = new FragmentParam();
		param.cls = cls;
		param.data = data;
		replaceThisFragment(param);
	}

	protected String getFragmentTag(FragmentParam param) {
		StringBuilder sb = new StringBuilder(param.cls.toString());
		return sb.toString();
	}

	private void replaceThisFragment(FragmentParam param) {
		int containerId = getFragmentContainerId();
		Class<?> cls = param.cls;
		if (cls == null) {
			return;
		}
		try {
			String fragmentTag = getFragmentTag(param);
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			BaseFragment fragment = (BaseFragment) fm
					.findFragmentByTag(fragmentTag);
			if (fragment == null) {
				fragment = (BaseFragment) cls.newInstance();
			}
			if (mCurrentFragment != null && mCurrentFragment != fragment) {
				mCurrentFragment.onLeave();
				CLog.i(LOGTAG,
						String.format("replace  BackStackCount: %s",
								fm.getBackStackEntryCount()));
				CLog.i(LOGTAG,
						String.format("%s  leave.", mCurrentFragment.getTag()));
			}
			fragment.onEnter(param.data);
			mCurrentFragment = fragment;
			ft.replace(containerId, fragment);
			ft.commitAllowingStateLoss();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		mCloseWarned = false;
	}

	private void goToThisFragment(FragmentParam param) {
		int containerId = getFragmentContainerId();
		Class<?> cls = param.cls;
		if (cls == null) {
			return;
		}
		try {
			String fragmentTag = getFragmentTag(param);
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			BaseFragment fragment = (BaseFragment) fm
					.findFragmentByTag(fragmentTag);
			CLog.i(LOGTAG,
					String.format("BackStackCount: %s",
							fm.getBackStackEntryCount()));
			if (fragment == null) {
				fragment = (BaseFragment) cls.newInstance();
				ft.addToBackStack(fragmentTag);
				CLog.i(LOGTAG,
						String.format("%s  addToBackStack.", fragmentTag));
			}
			if (mCurrentFragment != null && mCurrentFragment != fragment) {
				mCurrentFragment.onLeave();
				ft.hide(mCurrentFragment);
				CLog.i(LOGTAG, String.format("%s  hide.", mCurrentFragment));
			}
			fragment.onEnter(param.data);
			if (fragment.isAdded()) {
				CLog.i(LOGTAG, String.format("%s show.", fragmentTag));
				ft.show(fragment);
			} else {
				ft.add(containerId, fragment, fragmentTag);
				CLog.i(LOGTAG, String.format("%s add.", fragmentTag));
			}
			mCurrentFragment = fragment;

			ft.commitAllowingStateLoss();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		mCloseWarned = false;
	}

	// 退出到栈中指定的Fragment
	public void goToFragment(Class<?> cls, Object data) {
		if (cls == null) {
			return;
		}
		BaseFragment fragment = (BaseFragment) getSupportFragmentManager()
				.findFragmentByTag(cls.toString());
		if (fragment != null) {
			mCurrentFragment = fragment;
			fragment.onBackWithData(data);
		}
		// 给定ID标识所指定的状态之上的所有状态都弹出回退堆栈。会立即执行弹出操作
		getSupportFragmentManager().popBackStackImmediate(cls.toString(), 0);
	}

	// 退出栈最顶部的Fragment，并更新当前
	public void popTopFragment(Object data) {
		FragmentManager fm = getSupportFragmentManager();
		fm.popBackStackImmediate();
		if (tryToUpdateCurrentAfterPop() && mCurrentFragment != null) {
			mCurrentFragment.onBackWithData(data);
		}
	}

	// 退出栈到栈最低部的Fragment，并更新
	public void popToRoot(Object data) {
		FragmentManager fm = getSupportFragmentManager();
		while (fm.getBackStackEntryCount() > 1) {
			fm.popBackStackImmediate();
		}
		popTopFragment(data);
	}

	/**
	 * process the return back logic return true if back pressed event has been
	 * processed and should stay in current view
	 * 
	 * @return
	 */
	protected boolean processBackPressed() {
		return false;
	}

	/**
	 * 返回键
	 */
	@Override
	public void onBackPressed() {
		// process back for fragment
		if (processBackPressed()) {
			return;
		}
		// process back for fragment
		boolean enableBackPressed = true;
		if (mCurrentFragment != null) {
			enableBackPressed = !mCurrentFragment.processBackPressed();
		}
		if (enableBackPressed) {
			int cnt = getSupportFragmentManager().getBackStackEntryCount();
			if (cnt <= 1 && isTaskRoot()) {
				String closeWarningHint = getCloseWarning();
				if (!mCloseWarned && !TextUtils.isEmpty(closeWarningHint)) {
					showMsgs(closeWarningHint);
					mCloseWarned = true;
					new Handler().postDelayed(new Runnable() {
						public void run() {
							mCloseWarned = false;
						}

					}, 1000);
				} else {
					doReturnBack();
				}
			} else {
				mCloseWarned = false;
				doReturnBack();
			}
		}
	}

	public void showMsgs(String msg) {
		try {
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void showMsgl(String msg) {
		try {
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 退出后更新当前即后面的前
	private boolean tryToUpdateCurrentAfterPop() {
		FragmentManager fm = getSupportFragmentManager();
		int cnt = fm.getBackStackEntryCount();
		if (cnt > 0) {
			String name = fm.getBackStackEntryAt(cnt - 1).getName();
			Fragment fragment = fm.findFragmentByTag(name);
			if (fragment != null && fragment instanceof BaseFragment) {
				mCurrentFragment = (BaseFragment) fragment;
			}
			return true;
		}
		return false;
	}

	protected abstract void finishBack();

	public void doReturnBack() {
		int count = getSupportFragmentManager().getBackStackEntryCount();
		if (count <= 1) {
			finishBack();
		} else {
			getSupportFragmentManager().popBackStackImmediate();
			if (tryToUpdateCurrentAfterPop() && mCurrentFragment != null) {
				mCurrentFragment.onBack();
			}
		}
	}

}
