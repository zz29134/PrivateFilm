package com.mp.privatefilm.home;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.gc.materialdesign.views.ButtonFlat;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.mp.commonsdk.fragment.BaseFragment;
import com.mp.privatefilm.R;
import com.mp.privatefilm.bean.HomeAdvertisment;
import com.mp.privatefilm.net.BuildUrl;
import com.mp.privatefilm.net.JsonObjectRequest;
import com.mp.privatefilm.utils.Constants;
import com.viewpagerindicator.IconPageIndicator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by eE on 2016/4/24.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.setting)
    protected ButtonFlat setting;
    @Bind(R.id.search)
    protected ButtonFlat search;
    @Bind(R.id.viewPager_AD)
    protected ViewPager viewPager_AD;
    @Bind(R.id.Indicator_AD)
    protected IconPageIndicator Indicator_AD;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        setContentView(R.layout.home_fragment);
        setting.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        search.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

        getHomeAD();

    }

    private void getHomeAD() {
        JsonObjectRequest Req = new JsonObjectRequest(BuildUrl.getInstance().parseBuild(Constants.cmd.home_advertisement, null),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString(Constants.jsName.code).equals(
                                    Constants.jsName.code_success)) {
                                List<HomeAdvertisment> list = new Gson().fromJson(response.getString(Constants.jsName.content), new TypeToken<List<HomeAdvertisment>>() {
                                }.getType());
                                // 广告ViewPager
                                ADViewPagerAdapter adViewPagerAdapter = new ADViewPagerAdapter(list);
                                viewPager_AD.setAdapter(adViewPagerAdapter);

                                Indicator_AD.setViewPager(viewPager_AD);
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

        private List<HomeAdvertisment> list = new ArrayList<HomeAdvertisment>();

        public ADViewPagerAdapter(List<HomeAdvertisment> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }
    }

}
