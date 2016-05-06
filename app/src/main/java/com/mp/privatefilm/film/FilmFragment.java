package com.mp.privatefilm.film;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mp.commonsdk.CLog;
import com.mp.privatefilm.R;
import com.mp.privatefilm.basefragment.BaseRefreshFragment;
import com.mp.privatefilm.bean.Film;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by eE on 2016/4/24.
 */
public class FilmFragment extends BaseRefreshFragment {

    @Bind(R.id.listView)
    protected ListView listView;

    private FilmListAdapter filmListAdapter;
    private List<Film> filmList = new ArrayList<>();

    @Override
    protected void CreateView() {
        filmListAdapter = new FilmListAdapter(filmList);
        listView.setAdapter(filmListAdapter);
        getFilmData();
        filmListAdapter.notifyDataSetChanged();
    }

    private void getFilmData() {
        filmList.add(new Film("1", "11111"));
        filmList.add(new Film("2", "22222"));
        filmList.add(new Film("3", "33333"));
        filmList.add(new Film("4", "44444"));
        filmList.add(new Film("5", "55555"));
    }

    @Override
    public int getContentRid() {
        return R.layout.film_fragment;
    }

    @Override
    public void onLoadMoreBegin(PtrFrameLayout frame) {
        getFilmData();
        mPtrFrame.refreshComplete();
        filmListAdapter.notifyDataSetChanged();
        CLog.e("onLoadMoreBegin");
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        filmList.clear();
        getFilmData();
        mPtrFrame.refreshComplete();
        filmListAdapter.notifyDataSetChanged();
        CLog.e("onRefreshBegin");
    }

    @Override
    protected void onEmptyButtonClick(View v) {

    }

    private class FilmListAdapter extends BaseAdapter {

        private List<Film> filmList = new ArrayList<>();

        public FilmListAdapter(List<Film> filmList) {
            this.filmList = filmList;
        }

        @Override
        public int getCount() {
            return filmList.size();
        }

        @Override
        public Film getItem(int position) {
            return filmList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.film_list_item, null);
            TextView textView = (TextView) convertView.findViewById(R.id.textView);
            textView.setText(getItem(position).getFilmName());
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

}
