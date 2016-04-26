package com.mp.commonsdk.utils;

import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

public class LocalDisplay {

	public static int SCREEN_WIDTH_PIXELS;
	public static int SCREEN_HEIGHT_PIXELS;
	public static float SCREEN_DENSITY;
	public static float SCREEN_DENSITY_DPI;
	public static int SCREEN_WIDTH_DP;
	public static int SCREEN_HEIGHT_DP;
	private static boolean sInitialed;

	public static void init(Context context) {
		if (sInitialed || context == null) {
			return;
		}
		sInitialed = true;
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		SCREEN_WIDTH_PIXELS = dm.widthPixels;
		SCREEN_HEIGHT_PIXELS = dm.heightPixels;
		SCREEN_DENSITY = dm.density;
		SCREEN_DENSITY_DPI= dm.densityDpi;
		SCREEN_WIDTH_DP = (int) (SCREEN_WIDTH_PIXELS / dm.density);
		SCREEN_HEIGHT_DP = (int) (SCREEN_HEIGHT_PIXELS / dm.density);
	}

	public static int dp2px(float dp) {
		final float scale = SCREEN_DENSITY;
		return (int) (dp * scale + 0.5f);
	}

	public static int designedDP2px(float designedDp) {
		if (SCREEN_WIDTH_DP != 320) {
			designedDp = designedDp * SCREEN_WIDTH_DP / 320f;
		}
		return dp2px(designedDp);
	}

	public static void setPadding(final View view, float left, float top,
			float right, float bottom) {
		view.setPadding(designedDP2px(left), dp2px(top), designedDP2px(right),
				dp2px(bottom));
	}

	public static int getViewMeasuredHeight(View view) {
		// int height = view.getMeasuredHeight();
		// if(0 < height){
		// return height;
		// }
		calcViewMeasure(view);
		return view.getMeasuredHeight();
	}

	/**
	 * 获取控件的宽度，如果获取的宽度为0，则重新计算尺寸后再返回宽度
	 * 
	 * @param view
	 * @return
	 */
	public static int getViewMeasuredWidth(View view) {
		calcViewMeasure(view);
		return view.getMeasuredWidth();
	}

	/**
	 * 测量控件的尺寸
	 * 
	 * @param view
	 */
	public static void calcViewMeasure(View view) {
		int width = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int expandSpec = View.MeasureSpec.makeMeasureSpec(
				Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
		view.measure(width, expandSpec);
	}

	@SuppressWarnings("rawtypes")
	public static int getAllListViewSectionCounts(ListView lv, List dataSource) {
		if (null == lv || isEmpty(dataSource)) {
			return 0;
		}
		return dataSource.size() + lv.getHeaderViewsCount()
				+ lv.getFooterViewsCount();
	}

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Collection collection) {
		return null == collection || collection.isEmpty();
	}

	/**
	 * 使用ColorFilter来改变亮度
	 * 
	 * @param imageview
	 * @param brightness
	 */
	public static void changeBrightness(ImageView imageview, float brightness) {
		imageview.setColorFilter(getBrightnessMatrixColorFilter(brightness));
	}

	public static void changeBrightness(Drawable drawable, float brightness) {
		drawable.setColorFilter(getBrightnessMatrixColorFilter(brightness));
	}

	private static ColorMatrixColorFilter getBrightnessMatrixColorFilter(
			float brightness) {
		ColorMatrix matrix = new ColorMatrix();
		matrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0,
				brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
		return new ColorMatrixColorFilter(matrix);
	}

}
