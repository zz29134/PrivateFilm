package com.mp.privatefilm.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.bumptech.glide.Glide;
import com.gc.materialdesign.views.ButtonFlat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.mp.commonsdk.CLog;
import com.mp.commonsdk.fragment.BaseFragment;
import com.mp.commonsdk.utils.LocalDisplay;
import com.mp.privatefilm.R;
import com.mp.privatefilm.bean.HomeAdvertisment;
import com.mp.privatefilm.eventbus.ToggleSettingMenu;
import com.mp.privatefilm.net.BuildUrl;
import com.mp.privatefilm.net.JsonObjectRequest;
import com.mp.privatefilm.utils.Constants;
import com.mp.commonsdk.utils.GsonUtil;
import com.nineoldandroids.animation.ValueAnimator;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.IconPageIndicator;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by eE on 2016/4/24.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.setting)
    protected ButtonFlat setting;
    @OnClick(R.id.setting)
    protected void toggleSettingMenu() {
        EventBus.getDefault().post(new ToggleSettingMenu());
    }

    @Bind(R.id.search)
    protected ButtonFlat search;
    @Bind(R.id.search_ll)
    protected LinearLayout search_ll;
    @OnClick(R.id.search)
    protected void showSearch() {
        ValueAnimator mAnimator = null;
        if (null != search_ll.getTag() && (Boolean) search_ll.getTag()) {
            mAnimator = ValueAnimator.ofInt(LocalDisplay.dp2px(50), 0);
            search_ll.setTag(false);
        } else {
            mAnimator = ValueAnimator.ofInt(0, LocalDisplay.dp2px(50));
            search_ll.setTag(true);
        }
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int animatorValue = (int)valueAnimator.getAnimatedValue();
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) search_ll.getLayoutParams();
                marginLayoutParams.topMargin = animatorValue;
                search_ll.setLayoutParams(marginLayoutParams);
            }
        });
        mAnimator.setDuration(1000);
        mAnimator.setTarget(search_ll);
        mAnimator.start();
    }

    @Bind(R.id.viewPager_AD)
    protected ViewPager viewPager_AD;
    @Bind(R.id.indicator_AD)
    protected CirclePageIndicator indicator_AD;

    private static final int nextAD = 0;
    // 广告自动轮播控制器
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case nextAD:
                    if (viewPager_AD.getCurrentItem() + 1 == viewPager_AD.getAdapter().getCount()) {
                        viewPager_AD.setCurrentItem(0, true);
                    } else {
                        viewPager_AD.setCurrentItem(viewPager_AD.getCurrentItem() + 1, true);
                    }
                    handler.sendEmptyMessageDelayed(nextAD, 5000);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.home_fragment);
        setting.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        search.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        indicator_AD.setFillColor(ContextCompat.getColor(context, R.color.blue));
        indicator_AD.setPageColor(ContextCompat.getColor(context, R.color.red));
        indicator_AD.setRadius(10);

//        getHomeAD();
        {
            List<HomeAdvertisment> list = new ArrayList<>();
            list.add(new HomeAdvertisment("http://img.taopic.com/uploads/allimg/130728/318764-130HPZ33685.jpg"));
            list.add(new HomeAdvertisment("http://pic18.nipic.com/20111212/7361508_090142090000_2.jpg"));
            list.add(new HomeAdvertisment("http://pic18.nipic.com/20120108/8824038_153011351333_2.jpg"));
            list.add(new HomeAdvertisment("http://pic29.nipic.com/20130506/11732023_184140013389_2.jpg"));
            ADViewPagerAdapter adViewPagerAdapter = new ADViewPagerAdapter(list);
            viewPager_AD.setAdapter(adViewPagerAdapter);

            indicator_AD.setViewPager(viewPager_AD);

            if (null != list && list.size() > 1) {
                handler.sendEmptyMessageDelayed(nextAD, 5000);
            }
        }
    }

    /**
     * 获取首页广告列表
     */
    private void getHomeAD() {
        JsonObjectRequest Req = new JsonObjectRequest(BuildUrl.getInstance().parseBuild(Constants.cmd.home_advertisement, null),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString(Constants.jsName.code).equals(
                                    Constants.jsName.code_success)) {
                                List<HomeAdvertisment> list = GsonUtil.fromJson(
                                        response.getString(Constants.jsName.content), new TypeToken<List<HomeAdvertisment>>() {
                                        });
                                ADViewPagerAdapter adViewPagerAdapter = new ADViewPagerAdapter(list);
                                viewPager_AD.setAdapter(adViewPagerAdapter);
                                indicator_AD.setViewPager(viewPager_AD);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                ShowMsgL("返回值错误");
            }
        });
        addToRequestQueue(Req, true);
    }

    @Override
    protected void onEmptyButtonClick(View v) {

    }

    private class ADViewPagerAdapter extends PagerAdapter {

        private List<HomeAdvertisment> list = new ArrayList<>();
        private List<ImageView> images = new ArrayList<>();

        public ADViewPagerAdapter(List<HomeAdvertisment> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            Glide.with(context).load(list.get(position).getAD()).crossFade()
                    .fitCenter().into(imageView);
            images.add(imageView);
            container.addView(images.get(position));
            return images.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(images.get(position));
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }

}
