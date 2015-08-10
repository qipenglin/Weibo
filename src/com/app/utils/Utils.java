/**
 * @(#)Utils.java
 * 
 * @Version: 1
 * @JDK: jdk 1.6.0.XXX
 * @Module: CrazyitWeibo
 */
/*- 				History
 **********************************************
 *  ID      DATE           PERSON       REASON
 *  1     2011-11-5     hanfei.li    Created
 **********************************************
 */

package com.app.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.app.model.Weibo;
import com.app.weibo.R;
import com.sina.weibo.sdk.openapi.models.Status;

/**
 * 官网 www.facejava.org www.fkjava.org 学习交流论坛 www.crazyit.org
 * 
 * @author hanfei.li
 * @since 2011-11-5
 */
public class Utils {
	private static final String TAG = "Utils";

	/**
	 * 进行网络检测
	 */
	public static boolean checkNetwork(final Activity context) {
		if (!Utils.isNetworkAvailable(context)) {

			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			dialog.setTitle("网络状况检查");
			dialog.setMessage("无网络连接");
			dialog.setCancelable(false);
			dialog.setPositiveButton("去设置",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// 设置完毕会返回当前应用
							context.startActivityForResult(
									new Intent(
											android.provider.Settings.ACTION_WIRELESS_SETTINGS),
									0);
						}
					});
			dialog.setNegativeButton("退出",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							context.finish();
						}
					});
			dialog.create().show();

		}
		return true;
	}

	/**
	 * 检测网络是否可用
	 * 
	 * @param context
	 *            上下文
	 * @return true 表示有网络连接 false表示没有可用网络连接
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

//	public static ArrayList<Weibo> Transfer(Context context,
//			ArrayList<Status> statusList) {
//
//		final RequestQueue mQueue = Volley.newRequestQueue(context);
//		final ArrayList<Weibo> weiboList = new ArrayList<Weibo>();
//		final Iterator<Status> iterator = statusList.iterator();
//		while (iterator.hasNext()) {
//			String head_url = iterator.next().user.profile_image_url;
//			ImageRequest imageRequest = new ImageRequest(head_url,
//					new Response.Listener<Bitmap>() {
//						@Override
//						public void onResponse(Bitmap response) {
//							Weibo weibo = new Weibo(response, iterator.next().text);
//							weiboList.add(weibo);
//
//						}
//					}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
//						@Override
//						public void onErrorResponse(VolleyError error) {
//							Weibo weibo = new Weibo(null, iterator.next().text);
//							weiboList.add(weibo);
//						}
//					});
//			mQueue.add(imageRequest);
//		}
//		return weiboList;
//	}
	
	public static List<Weibo> Transfer(Context context,ArrayList<Status> statusList) {
		List<Weibo> weiboList = new ArrayList<Weibo>();
		Iterator<Status> iterator = statusList.iterator();
		while (iterator.hasNext()) {
			String head = iterator.next().user.profile_image_url;
			String text = iterator.next().text;
			Weibo weibo = new Weibo(head, text);
			weiboList.add(weibo);
		}
		return weiboList;
	}

}
