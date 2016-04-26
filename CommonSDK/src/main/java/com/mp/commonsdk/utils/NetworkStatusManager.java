/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mp.commonsdk.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * A wrapper for a broadcast receiver that provides network connectivity state
 * information, independent of network type (mobile, Wi-Fi, etc.). {@hide
 * 
 * 
 * 
 * 
 * }
 */
public class NetworkStatusManager {

	private static final String TAG = "NetworkStatusManager";
	private static final boolean DBG = true;

	private Context mContext;
	private State mState;
	private boolean mListening;
	private String mReason;
	private boolean mIsFailOver;
	private NetworkInfo mNetworkInfo;
	private boolean mIsWifi = false;	
	private NetworkInfo mOtherNetworkInfo;
	private ConnectivityBroadcastReceiver mReceiver;
	
	
	private static NetworkStatusManager sInstance;
	
	public static void init(Context context) {
		sInstance = new NetworkStatusManager();
		sInstance.mIsWifi = checkIsWifi(context);
		sInstance.startListening(context);
	}

	public static NetworkStatusManager getInstance() {
		return sInstance;
	}

	

	private class ConnectivityBroadcastReceiver extends BroadcastReceiver {
		@SuppressWarnings("deprecation")
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (!action.equals(ConnectivityManager.CONNECTIVITY_ACTION)
					|| mListening == false) {
				Log.w(TAG, "onReceived() called with " + mState.toString()
						+ " and " + intent);
				return;
			}
			// 值为true表示当前设备没有连接到网络；
			boolean noConnectivity = intent.getBooleanExtra(
					ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

			if (noConnectivity) {
				mState = State.NOT_CONNECTED;
			} else {
				mState = State.CONNECTED;
			}

			// 返回NetworkInfo对象，包含了当前连接事件相关的网络详细信息；
			mNetworkInfo = (NetworkInfo) intent
					.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			// 在一个网络断开连接时，这个值返回NetworkInfo对象，包含了可能的网络失效重连的详细信息；
			mOtherNetworkInfo = (NetworkInfo) intent
					.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);
			// 如果当前的广播代表网络失效，则这个值包含了连接失效的原因描述；
			mReason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
			// 值为true说明当前的连接是首选网络失效重连后的连接；
			mIsFailOver = intent.getBooleanExtra(
					ConnectivityManager.EXTRA_IS_FAILOVER, false);
			if (DBG) {
				Log.d(TAG,
						"onReceive(): mNetworkInfo="
								+ mNetworkInfo
								+ " mOtherNetworkInfo = "
								+ (mOtherNetworkInfo == null ? "[none]"
										: mOtherNetworkInfo + " noConn="
												+ noConnectivity) + " mState="
								+ mState.toString());
			}

			mIsWifi = checkIsWifi(mContext);
		}
	}

	;

	public static boolean checkIsWifi(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) (context
				.getSystemService(Context.CONNECTIVITY_SERVICE));
		if (connectivity != null) {
			NetworkInfo networkInfo = connectivity
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
				return true;
			}
		}
		return false;
	}

	public enum State {
		UNKNOWN,

		/**
		 * This state is returned if there is connectivity to any network *
		 */
		CONNECTED,
		/**
		 * This state is returned if there is no connectivity to any network.
		 * This is set to true under two circumstances:
		 * <ul>
		 * <li>When connectivity is lost to one network, and there is no other
		 * available network to attempt to switch to.</li>
		 * <li>When connectivity is lost to one network, and the attempt to
		 * switch to another network fails.</li>
		 */
		NOT_CONNECTED
	}

	private NetworkStatusManager() {
		mState = State.UNKNOWN;
		mReceiver = new ConnectivityBroadcastReceiver();
	}

	public State getmState() {
		return mState;
	}

	/**
	 * This method starts listening for network connectivity state changes.
	 * 
	 * @param context
	 */
	public synchronized void startListening(Context context) {
		if (!mListening) {
			mContext = context;
			IntentFilter filter = new IntentFilter();
			filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
			context.registerReceiver(mReceiver, filter);
			mListening = true;
		}
	}

	/**
	 * This method stops this class from listening for network changes.
	 */
	public synchronized void stopListening() {
		if (mListening) {
			mContext.unregisterReceiver(mReceiver);
			mContext = null;
			mNetworkInfo = null;
			mOtherNetworkInfo = null;
			mIsFailOver = false;
			mReason = null;
			mListening = false;
		}
	}

	/**
	 * Return the NetworkInfo associated with the most recent connectivity
	 * event.
	 * 
	 * @return {@code NetworkInfo} for the network that had the most recent
	 *         connectivity event.
	 */
	public NetworkInfo getNetworkInfo() {
		return mNetworkInfo;
	}

	/**
	 * If the most recent connectivity event was a DISCONNECT, return any
	 * information supplied in the broadcast about an alternate network that
	 * might be available. If this returns a non-null value, then another
	 * broadcast should follow shortly indicating whether connection to the
	 * other network succeeded.
	 * 
	 * @return NetworkInfo
	 */
	public NetworkInfo getOtherNetworkInfo() {
		return mOtherNetworkInfo;
	}

	/**
	 * Returns true if the most recent event was for an attempt to switch over
	 * to a new network following loss of connectivity on another network.
	 * 
	 * @return {@code true} if this was a fail over attempt, {@code false}
	 *         otherwise.
	 */
	public boolean isFailover() {
		return mIsFailOver;
	}

	/**
	 * An optional reason for the connectivity state change may have been
	 * supplied. This returns it.
	 * 
	 * @return the reason for the state change, if available, or {@code null}
	 *         otherwise.
	 */
	public String getReason() {
		return mReason;
	}

	public boolean isWifi() {
		return mIsWifi;
	}

	//判断WiFi和移动流量是否已连接：
	public static boolean checkNetworkConnection(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo mobile = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (wifi.isAvailable() || mobile.isAvailable()) // getState()方法是查询是否连接了数据网络
			return true;
		else
			return false;
	}
}
