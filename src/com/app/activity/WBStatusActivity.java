package com.app.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.app.model.WeiboInfo;
import com.app.weibo.R;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.utils.LogUtil;

public class WBStatusActivity extends Activity {

	private static final String TAG = WBStatusActivity.class.getName();
	/** 当前 Token 信息 */
	private Oauth2AccessToken mAccessToken;
	/** 用于获取微博信息流等操作的API */
	private StatusesAPI mStatusesAPI;

	private StatusList list;
	
	private ArrayList<Status> statusList;

	private TextView textView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_weibo);

		textView = (TextView) findViewById(R.id.weiboinfo);

		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		
		mStatusesAPI = new StatusesAPI(this, Constants.APP_KEY, mAccessToken);

		if (mAccessToken != null && mAccessToken.isSessionValid()) {
			mStatusesAPI.friendsTimeline(0L, 0L, 10, 1, false, 0, false,
					new RequestListener() {
						@Override
						public void onComplete(String response) {
							if (!TextUtils.isEmpty(response)) {
								LogUtil.i(TAG, response);

								// 调用 StatusList#parse 解析字符串成微博列表对象
								list = StatusList.parse(response);
								statusList = list.statusList;
								String weibo = list.statusList.get(2).text;

								textView.setText(weibo);
								if (list != null && list.total_number > 0) {
									Toast.makeText(WBStatusActivity.this,
											weibo, Toast.LENGTH_LONG).show();
								}

							}
						}

						@Override
						public void onWeiboException(WeiboException e) {
							LogUtil.e(TAG, e.getMessage());
							ErrorInfo info = ErrorInfo.parse(e.getMessage());
							Toast.makeText(WBStatusActivity.this,
									info.toString(), Toast.LENGTH_LONG).show();
						}
					});
		}

	}

	/**
	 * 微博 OpenAPI 回调接口。
	 */
	// private RequestListener mListener = new RequestListener() {
	// @Override
	// public void onComplete(String response) {
	// if (!TextUtils.isEmpty(response)) {
	// LogUtil.i(TAG, response);
	//
	// // 调用 StatusList#parse 解析字符串成微博列表对象
	// StatusList list = StatusList.parse(response);
	// String weibo = list.statusList.get(0).created_at;
	// if (list != null && list.total_number > 0) {
	// Toast.makeText(WBStatusActivity.this, weibo,
	// Toast.LENGTH_LONG).show();
	// }
	//
	// }
	// }
	//
	// @Override
	// public void onWeiboException(WeiboException e) {
	// LogUtil.e(TAG, e.getMessage());
	// ErrorInfo info = ErrorInfo.parse(e.getMessage());
	// Toast.makeText(WBStatusActivity.this, info.toString(),
	// Toast.LENGTH_LONG).show();
	// }
	// };
}

// private WeiboInfo parseJSONWithJSONObject(String jsonString) {
// WeiboInfo weiboInfo = new WeiboInfo();
// try {
// JSONArray jsonArray = new JSONArray(jsonString);
// for (int i = 0; i < jsonArray.length(); i++) {
// JSONObject jsonObject = jsonArray.getJSONObject(i);
// weiboInfo.setCreated_at(jsonObject.getString("creat_at"));
// weiboInfo.setId(jsonObject.getString("id"));
// weiboInfo.setText(jsonObject.getString("text"));
// }
// } catch (Exception e) {
// e.printStackTrace();
// }
// return weiboInfo;
// }
