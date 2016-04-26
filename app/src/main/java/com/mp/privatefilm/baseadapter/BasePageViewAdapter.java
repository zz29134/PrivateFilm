package com.mp.privatefilm.baseadapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BasePageViewAdapter extends PagerAdapter {
	public List<View> mListViews;

	public BasePageViewAdapter(List<View> Views) {
		this.mListViews = Views;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(mListViews.get(position), 0);
		View view = mListViews.get(position);
		InitView(view, position);
		return mListViews.get(position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(mListViews.get(position));
	}

	@Override
	public int getCount() {
		return mListViews.size();
	}

	protected abstract void InitView(View view, int position);

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

}
