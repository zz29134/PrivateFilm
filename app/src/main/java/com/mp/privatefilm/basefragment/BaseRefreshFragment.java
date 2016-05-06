package com.mp.privatefilm.basefragment;

import android.os.Bundle;
import android.view.View;

import com.mp.commonsdk.fragment.BaseFragment;
import com.mp.commonsdk.fragment.IBaseContentFragment;
import com.mp.commonsdk.utils.LocalDisplay;
import com.mp.privatefilm.R;

import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by eE on 2016/4/23.
 */
public abstract class BaseRefreshFragment extends BaseFragment implements PtrHandler2,IBaseContentFragment {

    protected PtrFrameLayout mPtrFrame;

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentRid());

        mPtrFrame = (PtrFrameLayout) findViewById(R.id.ptrFrameLayout);
        mPtrFrame.setMode(PtrFrameLayout.Mode.BOTH);
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setLoadingMinTime(1000);
        mPtrFrame.setDurationToCloseHeader(1000);
        mPtrFrame.setPullToRefresh(true);
        mPtrFrame.setKeepHeaderWhenRefresh(true);

        MaterialHeader header = new MaterialHeader(getContext());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setPtrFrameLayout(mPtrFrame);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);

        PtrClassicDefaultFooter footer = new PtrClassicDefaultFooter(getContext());
        mPtrFrame.setFooterView(footer);
        mPtrFrame.addPtrUIHandler(footer);

        mPtrFrame.setPtrHandler(this);

        CreateView();
    }

    protected abstract void CreateView();

    @Override
    public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
        return PtrDefaultHandler2.checkContentCanBePulledUp(frame, content, footer);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onLoadMoreBegin(PtrFrameLayout frame) {

    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {

    }
}
