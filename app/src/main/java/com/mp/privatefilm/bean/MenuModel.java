package com.mp.privatefilm.bean;

import com.mp.commonsdk.fragment.BaseFragment;

public class MenuModel {

	private String name;// 名称
	private BaseFragment fragment;// 显示�?
	private Integer drawable;// 图标

	public MenuModel(String name, BaseFragment fragment, Integer drawable) {
		this.name = name;
		this.fragment = fragment;
		this.drawable = drawable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BaseFragment getFragment() {
		return fragment;
	}

	public void setFragment(BaseFragment fragment) {
		this.fragment = fragment;
	}

	public Integer getDrawable() {
		return drawable;
	}

	public void setDrawable(Integer drawable) {
		this.drawable = drawable;
	}
}
