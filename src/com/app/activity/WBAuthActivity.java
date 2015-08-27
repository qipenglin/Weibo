package com.app.activity;

import java.util.ArrayList;
import java.util.Iterator;

import org.litepal.tablemanager.Connector;

import com.app.model.StatusInfo;
import com.app.model.UserInfo;
import com.app.utils.AccessTokenKeeper;
import com.app.utils.Constants;
import com.app.weibo.R;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.utils.LogUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.Toast;

public class WBAuthActivity extends Activity {

	private static final String TAG = "WBActivity";

	private ArrayList<Status> statusList;

	/** 显示认证后的信息，如 AccessToken */
	private AuthInfo mAuthInfo;

	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
	private Oauth2AccessToken mAccessToken;
	/** 用于获取微博信息流等操作的API */
	private StatusesAPI mStatusesAPI;
	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_auth);

		SQLiteDatabase db = Connector.getDatabase();
		// 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
		mAuthInfo = new AuthInfo(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
		mSsoHandler = new SsoHandler(WBAuthActivity.this, mAuthInfo);

		AlertDialog.Builder authDialog = new AlertDialog.Builder(WBAuthActivity.this);
		authDialog.setTitle("请求授权");
		authDialog.setMessage("是否进行授权");
		authDialog.setCancelable(false);
		authDialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				mSsoHandler.authorize(new AuthListener());
				// mSsoHandler.authorizeWeb(new AuthListener());
			}
		});
		authDialog.create().show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}

	}

	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			// 从这里获取用户输入的 电话号码信息
			String phoneNum = mAccessToken.getPhoneNum();
			if (mAccessToken.isSessionValid()) {
				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(WBAuthActivity.this, mAccessToken);

				UsersAPI mUsersAPI = new UsersAPI(WBAuthActivity.this, Constants.APP_KEY, mAccessToken);
				long uid = Long.parseLong(mAccessToken.getUid());
				mUsersAPI.show(uid, new RequestListener() {
					@Override
					public void onComplete(String response) {
						if (!TextUtils.isEmpty(response)) {
							// 调用 User#parse 将JSON串解析成User对象
							User user = User.parse(response);
							if (user != null) {
								UserInfo mUserInfo = new UserInfo(user);
								mUserInfo.save();
							}
						}
					}

					@Override
					public void onWeiboException(WeiboException e) {
						ErrorInfo info = ErrorInfo.parse(e.getMessage());
						Toast.makeText(WBAuthActivity.this, info.toString(), Toast.LENGTH_LONG).show();
					}
				});

				Toast.makeText(WBAuthActivity.this, "认证成功", Toast.LENGTH_SHORT).show();

				// mStatusesAPI = new StatusesAPI(WBAuthActivity.this,
				// Constants.APP_KEY, mAccessToken);
				// mStatusesAPI.friendsTimeline(0L, 0L, 100, 1, false, 0, false,
				// new RequestListener() {
				// @Override
				// public void onComplete(String response) {
				// if (!TextUtils.isEmpty(response)) {
				// statusList = StatusList.parse(response).statusList;
				// Iterator<Status> statusIterator = statusList.iterator();
				// while (statusIterator.hasNext()) {
				// Status status = statusIterator.next();
				// StatusInfo statusInfo = new StatusInfo(status);
				// statusInfo.save();
				// }
				// }
				// }
				//
				// @Override
				// public void onWeiboException(WeiboException e) {
				// LogUtil.e(TAG, e.getMessage());
				// ErrorInfo info = ErrorInfo.parse(e.getMessage());
				// Toast.makeText(WBAuthActivity.this, info.toString(),
				// Toast.LENGTH_LONG).show();
				// }
				// });
				startActivity(new Intent(WBAuthActivity.this, MainActivity.class));
			} else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = values.getString("code");
				String message = "认证失败";
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(WBAuthActivity.this, message, Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onCancel() {
			Toast.makeText(WBAuthActivity.this, "cancel", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(WBAuthActivity.this, "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
}
