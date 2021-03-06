/**
 * @(#)NetworkChekingUtils.java
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;

import javax.net.ssl.HttpsURLConnection;

import org.litepal.tablemanager.Connector;

import com.app.model.UserInfo;
import com.sina.weibo.sdk.openapi.models.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 官网 www.facejava.org www.fkjava.org 学习交流论坛 www.crazyit.org
 * 
 * @author hanfei.li
 * @since 2011-11-5
 */
public class NetworkChekingUtils {
	private static final String TAG = "NetworkChekingUtils";

	/**
	 * 进行网络检测
	 */
	public static boolean checkNetwork(final Activity context) {
		if (!NetworkChekingUtils.isNetworkAvailable(context)) {

			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			dialog.setTitle("网络状况检查");
			dialog.setMessage("无网络连接");
			dialog.setCancelable(false);
			dialog.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					// 设置完毕会返回当前应用
					context.startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);
				}
			});
			dialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {

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
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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

	// public static final SQLiteDatabase db = Connector.getDatabase();
	//
	// public static void SaveUserToDatabase(User user) {
	//
	// UserInfo myUser = new UserInfo(user);
	// myUser.save();
	// }

}
