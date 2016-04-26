package com.mp.privatefilm.basefragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.mp.commonsdk.fragment.BaseFragment;
import com.mp.commonsdk.fragment.IBaseContentFragment;
import com.mp.privatefilm.R;
import com.mp.privatefilm.baseadapter.BaseTabFragmentAdapter;
import com.viewpagerindicator.PageIndicator;

import butterknife.Bind;

public abstract class BaseTabFragment extends BaseFragment implements
		IBaseContentFragment {

	@Bind(R.id.pager)
	protected ViewPager mPager;
	@Bind(R.id.indicator)
	protected PageIndicator mIndicator;
	protected BaseTabFragmentAdapter mAdapter;

	@Override
	protected void onCreateView(Bundle savedInstanceState) {
		setContentView(getContentRid());
		initFragmentAdapter();
		try {
			mPager.setAdapter(mAdapter);
			mIndicator.setViewPager(mPager);
		} catch (Exception e) {
			mPager = (ViewPager) findViewById(R.id.pager);
			mIndicator = (PageIndicator) findViewById(R.id.indicator);
			mPager.setAdapter(mAdapter);
			mIndicator.setViewPager(mPager);
		}
		initListener();

	}

	protected abstract void initFragmentAdapter();

	protected abstract void initListener();

}
