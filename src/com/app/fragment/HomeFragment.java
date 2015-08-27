package com.app.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.app.adapter.StatusAdapter;
import com.app.utils.AccessTokenKeeper;
import com.app.utils.Constants;
import com.app.weibo.R;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.utils.LogUtil;

public class HomeFragment extends Fragment {

	private static final String TAG = "WBStatusActivity";
	/** 当前 Token 信息 */
	private Oauth2AccessToken mAccessToken;
	/** 用于获取微博信息流等操作的API */
	private StatusesAPI mStatusesAPI;

	private ArrayList<Status> statusList;

	private ListView listView;

	private StatusAdapter statusAdapter;

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);
		

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View statusView = inflater.inflate(R.layout.home_layout, container,
				false);
		listView = (ListView) statusView.findViewById(R.id.weibo_list);

		mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());
		mStatusesAPI = new StatusesAPI(getActivity(), Constants.APP_KEY,
				mAccessToken);

		if (mAccessToken == null)
			Toast.makeText(getActivity(), "没有获取到密钥", Toast.LENGTH_LONG).show();
		if (mAccessToken != null && mAccessToken.isSessionValid()) {
			if (mAccessToken == null)
				Toast.makeText(getActivity(), "没有获取到密钥", Toast.LENGTH_LONG)
						.show();
			mStatusesAPI.friendsTimeline(0L, 0L, 100, 1, false, 0, false,
					new RequestListener() {
						@Override
						public void onComplete(String response) {
							if (!TextUtils.isEmpty(response)) {

								statusList = StatusList.parse(response).statusList;
								Toast.makeText(getActivity(),
										statusList.get(1).id, Toast.LENGTH_LONG)
										.show();

							}
							if (!statusList.isEmpty()) {
								statusAdapter = new StatusAdapter(
										getActivity(), 0, statusList, listView);
								listView.setAdapter(statusAdapter);
							}
						}

						@Override
						public void onWeiboException(WeiboException e) {
							LogUtil.e(TAG, e.getMessage());
							ErrorInfo info = ErrorInfo.parse(e.getMessage());
							Toast.makeText(getActivity(), info.toString(),
									Toast.LENGTH_LONG).show();
						}
					});
		}

		return statusView;
	}
}
