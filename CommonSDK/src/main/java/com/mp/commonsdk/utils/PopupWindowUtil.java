package com.mp.commonsdk.utils;







import com.mp.commonsdk.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class PopupWindowUtil {

	public static final int right = 1;
	public static final int left = 2;
	public static final int top = 3;
	public static final int bottom = 4;
	public static final int center = 5;

	public static interface OnCreatViewListener {
		void onCreate(View view);
	}

	public static PopupWindow getPopWindow(Context context, int resource,
			OnCreatViewListener listener) {
		View view = LayoutInflater.from(context).inflate(resource, null);
		if (null != listener) {
			listener.onCreate(view);
		}
		return popu(context, view, center);
	}

	public static PopupWindow getRightPopWindow(Context context, int resource,
			OnCreatViewListener listener) {
		View view = LayoutInflater.from(context).inflate(resource, null);
		if (null != listener) {
			listener.onCreate(view);
		}
		return popu(context, view, right);
	}

	public static PopupWindow getLeftPopWindow(Context context, int resource,
			OnCreatViewListener listener) {
		View view = LayoutInflater.from(context).inflate(resource, null);
		if (null != listener) {
			listener.onCreate(view);
		}
		return popu(context, view, left);
	}

	public static PopupWindow getTopPopWindow(Context context, int resource,
			OnCreatViewListener listener) {
		View view = LayoutInflater.from(context).inflate(resource, null);
		if (null != listener) {
			listener.onCreate(view);
		}
		return popu(context, view, top);
	}

	public static PopupWindow getBottomPopWindow(Context context, int resource,
			OnCreatViewListener listener) {
		View view = LayoutInflater.from(context).inflate(resource, null);
		if (null != listener) {
			listener.onCreate(view);
		}
		return popu(context, view, bottom);
	}

	/**
	 * 
	 * @【方法名称】 getPopWindow
	 * @【方法描述】 居中显示消失
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public static PopupWindow getPopWindow(Context context, View view) {
		return popu(context, view, center);
	}

	/**
	 * 
	 * @【方法名称】 getRightPopWindow
	 * @【方法描述】 右侧显示消失
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public static PopupWindow getRightPopWindow(Context context, View view) {
		return popu(context, view, right);
	}

	/**
	 * 
	 * @【方法名称】 getLeftPopWindow
	 * @【方法描述】 左侧显示消失
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public static PopupWindow getLeftPopWindow(Context context, View view) {
		return popu(context, view, left);
	}

	/**
	 * 
	 * @【方法名称】 getTopPopWindow
	 * @【方法描述】 上侧显示消失
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public static PopupWindow getTopPopWindow(Context context, View view) {
		return popu(context, view, top);
	}

	/**
	 * 
	 * @【方法名称】 getBottomPopWindow
	 * @【方法描述】 下侧显示消失
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	public static PopupWindow getBottomPopWindow(Context context, View view) {
		return popu(context, view, bottom);
	}

	/**
	 * 
	 * @【方法名称】 popu
	 * @【方法描述】 自定义位置显示消失
	 * @【适用条件】
	 * @【执行流程】
	 * @【注意事项】
	 * @param
	 * @return
	 * @Exception
	 */
	@SuppressWarnings("deprecation")
	public static PopupWindow popu(Context context, View view, int position) {
		 PopupWindow window = new PopupWindow(context);
		if (position == right) {// 设置PopupWindow显示和隐藏时的动画
			window.setAnimationStyle(R.style.PopupWindowRightAnimation);
			window.setWidth(LayoutParams.WRAP_CONTENT);
			window.setHeight(LayoutParams.MATCH_PARENT);
		} else if (position == left) {

			window.setAnimationStyle(R.style.PopupWindowLeftAnimation);
			window.setWidth(LayoutParams.WRAP_CONTENT);
			window.setHeight(LayoutParams.MATCH_PARENT);
		} else if (position == bottom) {
			window.setAnimationStyle(R.style.PopupWindowBottomAnimation);
			window.setWidth(LayoutParams.MATCH_PARENT);
			window.setHeight(LayoutParams.WRAP_CONTENT);
		} else if (position == top) {
			window.setAnimationStyle(R.style.PopupWindowTopAnimation);
			window.setWidth(LayoutParams.MATCH_PARENT);
			window.setHeight(LayoutParams.WRAP_CONTENT);
		} else if (position == center) {
			window.setAnimationStyle(R.style.PopupWindowAnimation);
			window.setWidth(LayoutParams.WRAP_CONTENT);
			window.setHeight(LayoutParams.WRAP_CONTENT);
		}

		window.setBackgroundDrawable(new BitmapDrawable());
		window.setContentView(view);// 设置PopupWindow的内容view
		window.setFocusable(true); // 设置PopupWindow可获得焦点
		window.setTouchable(true); // 设置PopupWindow可触摸
		window.setOutsideTouchable(false); // 设置非PopupWindow区域可触摸
		
		return window;
	}
}
