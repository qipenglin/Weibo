package com.app.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.app.adapter.WeiboAdapter;
import com.app.model.Weibo;
import com.app.utils.Utils;
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

	private static final String TAG = "WBStatusActivity";
	/** 当前 Token 信息 */
	private Oauth2AccessToken mAccessToken;
	/** 用于获取微博信息流等操作的API */
	private StatusesAPI mStatusesAPI;

	ArrayList<Status> statusList;

	private List<Weibo> weiboList;

	private ListView listView;

	private WeiboAdapter weiboAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wbstatus);
		listView = (ListView) findViewById(R.id.weibo_list);
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		mStatusesAPI = new StatusesAPI(this, Constants.APP_KEY, mAccessToken);

		if (mAccessToken != null && mAccessToken.isSessionValid()) {
			mStatusesAPI.friendsTimeline(0L, 0L, 10, 1, false, 0, false,
					new RequestListener() {
						@Override
						public void onComplete(String response) {
							if (!TextUtils.isEmpty(response)) {
								LogUtil.i(TAG, response);
								statusList = StatusList.parse(response).statusList;

								weiboList = Utils.Transfer(
										WBStatusActivity.this, statusList);

							}
							if (!weiboList.isEmpty()) {
								weiboAdapter = new WeiboAdapter(WBStatusActivity.this, 0, weiboList, listView);
								listView.setAdapter(weiboAdapter);
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
}
