package com.mp.privatefilm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.mp.privatefilm.baseadapter.BaseTabFragmentAdapter;
import com.mp.privatefilm.bean.MenuModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eE on 2016/4/24.
 */
public class MenuFragmentAdapter extends BaseTabFragmentAdapter {

    List<MenuModel> list;
    List<String> listtitle;
    List<Integer> listicons;

    public MenuFragmentAdapter(FragmentManager fm, Object mdata) {
        super(fm, mdata);
    }

    @Override
    protected void init(Object mdata) {
        listtitle = new ArrayList<String>();
        listicons = new ArrayList<Integer>();
        list = (List<MenuModel>) mdata;
        for (MenuModel entity : list) {
            listtitle.add(entity.getName());
            listicons.add(entity.getDrawable());
        }
        setData(listtitle, listicons);
    }

    @Override
    public void notifyDataSetChanged() {
        listtitle.clear();
        listicons.clear();
        for (MenuModel entity : list) {
            listtitle.add(entity.getName());
            listicons.add(entity.getDrawable());
        }
        setData(listtitle, listicons);
        super.notifyDataSetChanged();
    }

    @Override
    protected Fragment getFragment(int position) {
        return list.get(position).getFragment();
    }
}
