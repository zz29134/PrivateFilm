package com.mp.privatefilm.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.mp.commonsdk.CLog;
import com.mp.commonsdk.fragment.BaseFragment;
import com.mp.privatefilm.R;
import com.mp.privatefilm.activity.MainActivity;
import com.mp.privatefilm.net.BuildUrl;
import com.mp.privatefilm.net.JsonObjectRequest;
import com.mp.privatefilm.utils.Constants;

import org.json.JSONObject;

/**
 * Created by Zhangzhe on 2016/4/25.
 */
public class LogoFragment extends BaseFragment {

    private Handler handler = new Handler();

    private Runnable runnable_getAD = new Runnable() {
        @Override
        public void run() {
            getWelcomeAdvertisementAddress();
        }
    };

    private Runnable runnable_goToMain = new Runnable() {
        @Override
        public void run() {
            getParent().openActivity(MainActivity.class);
            getParent().kill();
        }
    };

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        ImageView image = new ImageView(context);
        image.setImageResource(R.drawable.logo_default);
        image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        setContentView(image);

        handler.postDelayed(runnable_getAD, 1500);
        handler.postDelayed(runnable_goToMain, 3000);
    }

    private void getWelcomeAdvertisementAddress() {
        JsonObjectRequest Req = new JsonObjectRequest(BuildUrl.getInstance().parseBuild(Constants.cmd.welcome, null),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString(Constants.jsName.code).equals(
                                    Constants.jsName.code_success)) {

                                String imageUrl = response.getString(Constants.jsName.content);
                                imageUrl = "http://img.taopic.com/uploads/allimg/130728/318764-130HPZ33685.jpg";
                                Glide.with(context).load(imageUrl).crossFade().listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                                        handler.removeCallbacks(runnable_goToMain);
                                        handler.postDelayed(runnable_goToMain, 3000);
                                        return false;
                                    }
                                })
                                        .fitCenter().into((ImageView) getContentView());

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
}
