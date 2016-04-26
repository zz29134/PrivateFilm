package com.mp.privatefilm.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mp.privatefilm.R;
import com.mp.privatefilm.fragment.ContentFragment;
import com.mp.privatefilm.fragment.MenuFragment;

public class MainActivity extends SlidingFragmentActivity {

    protected MenuFragment menuFrag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame);
        pushFragmentToBackStack(ContentFragment.class, getSlidingMenu());
        setBehindContentView(R.layout.menu_frame);
        if (savedInstanceState == null) {
            FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
            menuFrag = new MenuFragment();
            menuFrag.onEnter(getSlidingMenu());
            t.replace(R.id.menu_frame, menuFrag);
            t.commit();
        } else {
            menuFrag = (MenuFragment) this.getSupportFragmentManager().findFragmentById(
                    R.id.menu_frame);
        }
        SlidingMenu menu = getSlidingMenu();
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
    }
}
