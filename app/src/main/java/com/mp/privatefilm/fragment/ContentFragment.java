package com.mp.privatefilm.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mp.privatefilm.R;
import com.mp.privatefilm.adapter.MenuFragmentAdapter;
import com.mp.privatefilm.basefragment.BaseTabFragment;
import com.mp.privatefilm.bean.MenuModel;
import com.mp.privatefilm.cinema.CinemaFragment;
import com.mp.privatefilm.film.FilmFragment;
import com.mp.privatefilm.home.HomeFragment;
import com.mp.privatefilm.mine.MineFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eE on 2016/4/24.
 */
public class ContentFragment extends BaseTabFragment {

    SlidingMenu menu;
    int index = 0;

    @Override
    protected void initFragmentAdapter() {
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this);
//        }
        menu = (SlidingMenu) mDataIn;
        mAdapter = new MenuFragmentAdapter(getChildFragmentManager(), getMenuList());

    }

    private List<MenuModel> getMenuList() {
        List<MenuModel> list = new ArrayList<MenuModel>();
        list.add(new MenuModel("首页", new HomeFragment(), R.drawable.home_icon));
        list.add(new MenuModel("电影", new FilmFragment(), R.drawable.home_icon));
        list.add(new MenuModel("影院", new CinemaFragment(), R.drawable.home_icon));
        list.add(new MenuModel("我的", new MineFragment(), R.drawable.home_icon));
        return list;
    }

    boolean isfirst = true;

    @Override
    protected void initListener() {
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {

                switch (arg0) {
                    case 0:
                        isfirst = true;
                        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                        break;
                    case 1:
                        isfirst = false;
                        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                        break;
                    case 2:
                        isfirst = false;
                        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                        break;
                    case 3:
                        isfirst = false;
                        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                        break;
                    default:
                        isfirst = false;
                        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                        break;
                }
                mIndicator.notifyDataSetChanged();
                index = arg0;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    @Override
    public void onLeave() {
        super.onLeave();
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
    }

    @Override
    public void onBackWithData(Object data) {
        super.onBackWithData(data);
        if (isfirst)
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
    }

    @Override
    public void onBack() {
        super.onBack();
        if (isfirst)
            menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
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
//        EventBus.getDefault().unregister(this);
    }
}
