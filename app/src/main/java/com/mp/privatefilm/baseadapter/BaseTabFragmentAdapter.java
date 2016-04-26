package com.mp.privatefilm.baseadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

import java.util.List;


public abstract class BaseTabFragmentAdapter extends FragmentPagerAdapter implements
		IconPagerAdapter {

	private List<String> Title;
	private List<Integer> ICONS;
	private int mCount;

	public BaseTabFragmentAdapter(FragmentManager fm, Object mdata) {
		super(fm);
		init(mdata);
	}

	protected abstract void init(Object mdata);

	@Override
	public Fragment getItem(int position) {
		return getFragment(position);
	}

	protected abstract Fragment getFragment(int position);

	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public String getPageTitle(int position) {
		if (Title != null && mCount > position) {
			return Title.get(position);
		} else {
			return "";
		}
	}

	public void setData(List<String> title, List<Integer> ICONS) {
		this.Title = title;
		this.ICONS = ICONS;
		mCount = Title.size();
	}

	@Override
	public int getIconResId(int index) {
		if (ICONS != null && mCount > index) {
			return ICONS.get(index).intValue();
		} else {
			return 0;
		}
	}

	public void setPageTitle(int position, String Value) {
		if (Title != null && mCount > position) {
			Title.set(position, Value);
		} else {
			throw new IllegalStateException("position OutOfIndexError");
		}
	}

	public void setIconResId(int index, int Value) {
		if (ICONS != null && mCount > index) {
			ICONS.set(index, Value);
		} else {
			throw new IllegalStateException("index OutOfIndexError");
		}
	}

	public void setCount(int count) {
		if (count > 0 && count <= mCount) {
			mCount = count;
		} else {
			throw new IllegalStateException("count OutOfIndexError");
		}

	}
}