package com.mp.commonsdk.fragment;

import java.lang.reflect.Field;

import com.android.volley.Request;
import com.mp.commonsdk.BaseApplication;
import com.mp.commonsdk.CLog;
import com.mp.commonsdk.activity.BaseActivity;
import com.mp.commonsdk.utils.VolleyTool;
import com.mp.commonsdk.R;
import com.gc.materialdesign.views.ButtonFlat;

import butterknife.ButterKnife;
import android.app.Activity;
import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import android.widget.TextView;

public abstract class BaseFragment extends Fragment implements IBaseFragment {
	protected LayoutInflater inflater;
	private View contentView;
	protected Context context;
	private ViewGroup container;
	protected Object mDataIn;
	private boolean mFirstResume = true;
	private View mProgressContainer;
	protected View mContentContainer;
	protected View mEmptyView;
	protected View mEmptyButton;
	protected View mEmptyText;
	protected View mLayerContainer;
	protected View mContentTitle;
	private boolean mContentShown;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		debugStatus("onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity().getApplicationContext();
		debugStatus("onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		debugStatus("onCreateView");
		this.inflater = inflater;
		this.container = container;
		return inflater.inflate(R.layout.fragment_progress, container, false);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		debugStatus("onViewCreated");
		ensureContent();
	}

	protected abstract void onCreateView(Bundle savedInstanceState);

	protected abstract void onEmptyButtonClick(View v);

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		debugStatus("onActivityCreated");
		onCreateView(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		debugStatus("onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		debugStatus("onResume");
		if (!mFirstResume) {
			onBack();
		}
		if (mFirstResume) {
			mFirstResume = false;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		debugStatus("onPause");

	}

	@Override
	public void onStop() {
		super.onStop();
		debugStatus("onStop");
		onLeave();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		debugStatus("onDestroyView");
		container = null;
		inflater = null;
		mContentShown = false;
		mContentTitle = mLayerContainer = mEmptyButton = mProgressContainer = mContentContainer = contentView = mEmptyView = null;
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		debugStatus("onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		debugStatus("onDetach");
		try {
			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public Context getContext() {
		return context;
	}

	public View getContentView() {
		return contentView;
	}

	protected BaseApplication getMyApplication() {
		return (BaseApplication) getParent().getApplication();
	}

	protected BaseActivity getParent() {
		return (BaseActivity) getActivity();
	}

	public View findViewById(int id) {
		if (contentView != null)
			return contentView.findViewById(id);
		return null;
	}

	public View findTitleViewById(int id) {
		if (mContentTitle != null)
			return mContentTitle.findViewById(id);
		return null;
	}

	public View getViewbyRId(int Ridlayoutid) {
		return LayoutInflater.from(context).inflate(Ridlayoutid, null);
	}

	public void setContentView(int layoutResID) {
		setContentView((ViewGroup) inflater.inflate(layoutResID, container, false));
	}

	public void setContentTitleView(int layoutResID) {
		setContentTitleView((ViewGroup) inflater.inflate(layoutResID, container, false));
	}

	public void setContentTitleView(View view) {
		if (view == null) {
			throw new IllegalArgumentException("Content view can't be null");
		}

		if (mContentTitle instanceof ViewGroup) {
			ViewGroup content = (ViewGroup) mContentTitle;
			content.removeAllViews();
			content.addView(view);
		} else {
			throw new IllegalStateException("Can't be used with a custom content view");
		}
	}

	public void setContentView(View view) {
		// ensureContent();
		if (view == null) {
			throw new IllegalArgumentException("Content view can't be null");
		}
		if (mContentContainer instanceof ViewGroup) {
			ViewGroup contentContainer = (ViewGroup) mContentContainer;
			if (contentView == null) {
				contentContainer.addView(view);
			} else {
				int index = contentContainer.indexOfChild(contentView);
				contentContainer.removeView(contentView);
				contentContainer.addView(view, index);
			}
			contentView = view;
			ButterKnife.bind(this, view);
		} else {
			throw new IllegalStateException("Can't be used with a custom content view");
		}

	}

	public <T> void addToRequestQueue(Request<T> req) {
		addToRequestQueue(req, true);
	}

	public <T> void addToRequestQueue(Request<T> req, boolean isCache) {
		req.setShouldCache(isCache);
		VolleyTool.addToRequestQueue(req, getTAG());
	}

	public void cancelPendingRequests(Object tag) {
		debugStatus("cancelPendingRequests");
		if (null != tag)
			VolleyTool.cancelPendingRequests(tag);
		else
			VolleyTool.cancelPendingRequests(getTAG());
	}

	public String getTAG() {
		String[] className = ((Object) this).getClass().getName().split("\\.");
		return className[className.length - 1];
	}

	@Override
	public void onEnter(Object data) {
		mDataIn = data;
		debugStatus("onEnter");
	}

	@Override
	public void onLeave() {
		cancelPendingRequests(null);
		debugStatus("onLeave");
	}

	@Override
	public void onBackWithData(Object data) {
		debugStatus("onBackWithData");
	}

	@Override
	public boolean processBackPressed() {
		return false;
	}

	@Override
	public void onBack() {
		debugStatus("onBack");
	}

	/**
	 * 在这里实现Fragment数据的缓加载.
	 * 
	 */
	public void debugStatus(String status) {
		CLog.d(BaseFragment.class.getName(), String.format("%s %s", getTAG(), status));
	}

	public void ShowMsgS(String msg) {
		try {
			getParent().showMsgs(msg);
		} catch (Exception e) {

		}
	}

	public void ShowMsgL(String msg) {
		try {
			getParent().showMsgl(msg);
		} catch (Exception e) {
		}

	}

	public void ShowLoading() {
		setLoadingShow(true);
	}

	public void HideLoading() {
		setLoadingShow(false);
	}

	public void ShowLayer() {
		setLayer(true);
	}

	public void HideLayer() {
		setLayer(false);
	}

	public void ShowEmpty(String msg) {
		((TextView) mEmptyText).setText(msg);
		mEmptyButton.setVisibility(View.GONE);
		setContentEmpty(true);
	}

	public void ShowEmpty(String msg, int bgcolor, int textcolor) {
		if (null != mEmptyView && null != mEmptyButton && null != mEmptyText) {
			mEmptyView.setBackgroundColor(bgcolor);
			((ButtonFlat) mEmptyButton).setTextColor(textcolor);
			((TextView) mEmptyText).setTextColor(textcolor);
			ShowEmpty(msg);
		}
	}

	public void ShowEmptyWithRetry(String msg) {
		((TextView) mEmptyText).setText(msg);
		mEmptyButton.setVisibility(View.VISIBLE);
		setContentEmpty(true);
	}

	public void ShowEmptyWithRetry(String msg, int bgcolor, int textcolor) {
		if (null != mEmptyView && null != mEmptyButton && null != mEmptyText) {
			mEmptyView.setBackgroundColor(bgcolor);
			((ButtonFlat) mEmptyButton).setTextColor(textcolor);
			((TextView) mEmptyText).setTextColor(textcolor);
			ShowEmpty(msg);
		}
	}

	public void HideEmpty() {
		setContentEmpty(false);
	}

	private void setLayer(boolean shown) {
		if (mLayerContainer == null) {
			return;
			/*
			 * throw new IllegalStateException(
			 * "Layer Container must be initialized before");
			 */
		}
		if (!shown) {
			mLayerContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					android.R.anim.fade_out));
			mLayerContainer.setVisibility(View.GONE);
		} else {
			mLayerContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					android.R.anim.fade_in));
			mLayerContainer.setVisibility(View.VISIBLE);
		}
	}

	private void setLoadingShow(boolean shown) {
		if (mContentShown == shown) {
			return;
		}
		mContentShown = shown;
		if (mProgressContainer == null)
			return;
		if (!shown) {
			mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					android.R.anim.fade_out));
			mProgressContainer.setVisibility(View.GONE);
		} else {
			mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					android.R.anim.fade_in));
			mProgressContainer.setVisibility(View.VISIBLE);
		}
	}

	private void setContentEmpty(boolean isEmpty) {
		try {

			if (contentView == null) {
				throw new IllegalStateException("Content view must be initialized before");
			}
			if (!isEmpty) {
				if (mEmptyView.getVisibility() == View.VISIBLE && contentView.getVisibility() == View.GONE) {
					mEmptyView.startAnimation(AnimationUtils.loadAnimation(getActivity(),
							android.R.anim.fade_out));
					contentView.startAnimation(AnimationUtils.loadAnimation(
							getActivity(), android.R.anim.fade_in));
					mEmptyView.setVisibility(View.GONE);
					contentView.setVisibility(View.VISIBLE);
				}
			} else {
				if (mEmptyView.getVisibility() == View.GONE && contentView.getVisibility() == View.VISIBLE) {
					mEmptyView.startAnimation(AnimationUtils.loadAnimation(getActivity(),
							android.R.anim.fade_in));
					contentView.startAnimation(AnimationUtils.loadAnimation(
							getActivity(), android.R.anim.fade_out));
					mEmptyView.setVisibility(View.VISIBLE);
					contentView.setVisibility(View.GONE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ensureContent() {
		debugStatus("ensureContent");
		if (mContentContainer != null && mProgressContainer != null) {
			return;
		}
		View root = getView();
		if (root == null) {
			throw new IllegalStateException("Content view not yet created");
		}
		mProgressContainer = root.findViewById(R.id.progress_container);
		if (mProgressContainer == null) {
			throw new RuntimeException(
					"Your content must have a ViewGroup whose id attribute is 'R.id.progress_container'");
		}
		mContentContainer = root.findViewById(R.id.content_container);
		if (mContentContainer == null) {
			throw new RuntimeException(
					"Your content must have a ViewGroup whose id attribute is 'R.id.content_container'");
		}
		mEmptyView = root.findViewById(R.id.content_empty);
		mEmptyText = root.findViewById(R.id.empty);
		mEmptyButton = root.findViewById(R.id.button1);
		if (mEmptyButton != null) {
			mEmptyButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ShowLoading();
					onEmptyButtonClick(v);
				}
			});
		}
		mLayerContainer = root.findViewById(R.id.layer_container);
		mContentTitle = root.findViewById(R.id.content_title);
		mContentShown = true;
		if (contentView == null) {
			setLoadingShow(false);
		}
	}

}
