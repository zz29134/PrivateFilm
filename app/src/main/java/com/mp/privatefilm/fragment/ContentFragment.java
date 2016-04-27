package com.mp.privatefilm.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mp.privatefilm.R;
import com.mp.privatefilm.adapter.MenuFragmentAdapter;
import com.mp.privatefilm.basefragment.BaseTabFragment;
import com.mp.privatefilm.bean.MenuModel;
import com.mp.privatefilm.cinema.CinemaFragment;
import com.mp.privatefilm.eventbus.ToggleSettingMenu;
import com.mp.privatefilm.film.FilmFragment;
import com.mp.privatefilm.home.HomeFragment;
import com.mp.privatefilm.mine.MineFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eE on 2016/4/24.
 */
public class ContentFragment extends BaseTabFragment {

    private SlidingMenu slidingMenu;

    @Override
    protected void initFragmentAdapter() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        slidingMenu = (SlidingMenu) mDataIn;
        mAdapter = new MenuFragmentAdapter(getChildFragmentManager(), getMenuList());

    }

    private List<MenuModel> getMenuList() {
        List<MenuModel> list = new ArrayList<>();
        list.add(new MenuModel("首页", new HomeFragment(), R.drawable.home_icon));
        list.add(new MenuModel("电影", new FilmFragment(), R.drawable.home_icon));
        list.add(new MenuModel("影院", new CinemaFragment(), R.drawable.home_icon));
        list.add(new MenuModel("我的", new MineFragment(), R.drawable.home_icon));
        return list;
    }

    @Override
    protected void initListener() {
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void menuToggle(ToggleSettingMenu toggleSettingMenu) {
        slidingMenu.toggle();
    }

    @Override
    public void onLeave() {
        super.onLeave();
    }

    @Override
    public void onBackWithData(Object data) {
        super.onBackWithData(data);
    }

    @Override
    public void onBack() {
        super.onBack();
    }

    @Override
    protected void onEmptyButtonClick(View v) {

    }

    @Override
    public int getContentRid() {
        return R.layout.tab_frame;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
