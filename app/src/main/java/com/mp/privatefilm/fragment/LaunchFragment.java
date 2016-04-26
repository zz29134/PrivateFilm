package com.mp.privatefilm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mp.commonsdk.activity.BaseActivity;
import com.mp.commonsdk.utils.SPTool;
import com.mp.privatefilm.R;
import com.mp.privatefilm.baseadapter.BaseTabFragmentAdapter;
import com.mp.privatefilm.basefragment.BaseTabFragment;
import com.mp.privatefilm.utils.Constants;

/**
 * Created by Zhangzhe on 2016/4/25.
 */
public class LaunchFragment extends BaseTabFragment {

    static BaseActivity baseActivity;
    static int[] image = { R.drawable.launch_00, R.drawable.launch_01 };

    @Override
    protected void initFragmentAdapter() {
        baseActivity = getParent();
        mAdapter = new LaunchAdapter(getChildFragmentManager());
    }

    @Override
    protected void initListener() {

    }

    @Override
    public int getContentRid() {
        return R.layout.launch_frame;
    }

    class LaunchAdapter extends BaseTabFragmentAdapter {

        public LaunchAdapter(FragmentManager fm) {
            super(fm, null);
        }

        @Override
        public int getCount() {
            return image.length;
        }

        @Override
        protected Fragment getFragment(int position) {
            return ImageFragment.newInstance(image[position], position);
        }

        @Override
        protected void init(Object mdata) {

        }

    }

    public static class ImageFragment extends Fragment {

        static String ResID = "ResID";
        static String Index = "Index";
        int resid;
        int index;

        public ImageFragment() {

        }

        public static Fragment newInstance(int resid, int n) {
            ImageFragment imageFragment = new ImageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ResID, resid);
            bundle.putInt(Index, n);
            imageFragment.setArguments(bundle);
            return imageFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Bundle bundle = getArguments();
            resid = bundle.getInt(ResID);
            index = bundle.getInt(Index);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.launch_item_frame,
                    container, false);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            Button btn = (Button) view.findViewById(R.id.btn);
            img.setImageResource(resid);
            if (index == image.length - 1) {
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        v.setEnabled(false);
                        SPTool.addSessionMap(Constants.keywords.Initialed, true);
                        baseActivity.replaceFragmentNoBackStack(LogoFragment.class, null);
                    }
                });
            }
            return view;
        }
    }

    /*private void obtainData() {
        String url = NtgsString.getInstance().UrlPathString();
//		String url = "http://192.168.12.148/trade/Mobile/MobileServices_V1.ashx";
//		String url = "http://36.110.49.69:8081/glj/Mobile/MobileServices_V1.ashx";
//		String url = "http://36.110.49.69:8081/trade/Mobile/MobileServices_V1.ashx";
        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter(Constants.reqhead.cmd, Constants.cmd.url001);
        builder.appendQueryParameter(Constants.reqhead.version, getMyApplication().getVersion()
                + "");
        builder.appendQueryParameter(Constants.reqhead.reqtime, Suven.getInstance()
                .getstrAtomicclock());
        JsonObjectRequest Req = new JsonObjectRequest(builder.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {
                        try {
                            if (result.getString(Constants.jsName.code).equals(Constants.jsName.code_success)) {
                                JSONObject jo = new JSONObject(result
                                        .getString(Constants.jsName.content));
                                String url = jo.getString(Constants.jsName.code_url);
                                String host = jo.getString(Constants.jsName.code_host);
                                String port = jo.getString(Constants.jsName.code_port);
                                SPTool.addSessionMap(Constants.keywords.Url, url);
                                SPTool.addSessionMap(Constants.keywords.Host, host);
                                SPTool.addSessionMap(Constants.keywords.Port, port);
                                BuildUrl.getInstance().Create(url);
                                getParent().openActivity(MainActivity.class);
                                getParent().kill();
                            } else {
                                errorgoto();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            errorgoto();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                errorgoto();
            }
        });
        addToRequestQueue(Req, false);
    }

    private void errorgoto() {
        ShowMsgS(Constants.netstate.readfail);
        showQuitTips();
    }

    protected void showQuitTips() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("提示");
        builder.setMessage("网络故障，稍后再试");
        builder.setCancelable(false);
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getParent().AppExit();
            }
        });

        builder.create();
        builder.show();
    }*/

    @Override
    protected void onEmptyButtonClick(View v) {

    }

}
