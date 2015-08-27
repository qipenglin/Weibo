package com.app.fragment;

import com.app.utils.AccessTokenKeeper;
import com.app.utils.Constants;
import com.app.utils.HttpCallbackListener;
import com.app.utils.HttpUtil;
import com.app.utils.NetworkChekingUtils;
import com.app.weibo.R;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class DiscoverFragment extends Fragment {

	private Oauth2AccessToken mAccessToken;

	// private StatusesAPI mStatusesAPI;

	private static final String TAG = "discover";

	private HttpCallbackListener mHttpCallbackListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.discover_layout, container, false);

		String address = "https://api.weibo.com/2/statuses/user_timeline.json";

		String source = Constants.APP_KEY;

		mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());

		String access_token = mAccessToken.getToken();

		String url = address + "?" + "access_token=" + access_token;

		Log.d(TAG, url);

		HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
			@Override
			public void onFinish(final String response) {
				Log.d(TAG, "finish");
				Log.d(TAG, response);
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(), "子线程中不能更新UI", Toast.LENGTH_LONG).show();
					}
				});

			}

			@Override
			public void onError(Exception e) {
				Log.d(TAG, "error");
			}
		});

		return newsLayout;
	}

}
