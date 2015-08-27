package com.app.fragment;

import java.util.ArrayList;
import java.util.Iterator;

import org.litepal.crud.DataSupport;

import com.app.adapter.StatusInfoAdapter;
import com.app.model.StatusInfo;
import com.app.model.UserInfo;
import com.app.utils.AccessTokenKeeper;
import com.app.utils.Constants;
import com.app.utils.HttpCallbackListener;
import com.app.utils.HttpUtil;
import com.app.view.NoScrollListView;
import com.app.weibo.R;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.utils.LogUtil;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {

	private static final String TAG = "ProfileFragment";

	private Oauth2AccessToken mAccessToken;

	private ArrayList<Status> statusList;

	private ArrayList<StatusInfo> statusInfoList;

	private StatusInfoAdapter statusInfoAdapter;

	private NoScrollListView mNoScrollListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View settingLayout = inflater.inflate(R.layout.profile_layout, container, false);

		UserInfo currentUser = DataSupport.findFirst(UserInfo.class);

		Toast.makeText(getActivity(), currentUser.getProvince() + "", Toast.LENGTH_LONG).show();

		TextView user_name_TextView = (TextView) settingLayout.findViewById(R.id.user_name);

		user_name_TextView.setText(currentUser.getScreen_name());

		TextView gender_Text = (TextView) settingLayout.findViewById(R.id.gender);

		gender_Text.setText(currentUser.getGender());

		TextView province_TextView = (TextView) settingLayout.findViewById(R.id.province);

		province_TextView.setText(new Integer(currentUser.getProvince()).toString());

		ImageView head_ImageView = (ImageView) settingLayout.findViewById(R.id.head);

		head_ImageView.setImageResource(R.drawable.ic_launcher);

		TextView weibo_Count_TextView = (TextView) settingLayout.findViewById(R.id.weibo_count);

		weibo_Count_TextView.setText(new Integer(currentUser.getStatuses_count()).toString() + '\n' + "Weibo");

		TextView following_count_TextView = (TextView) settingLayout.findViewById(R.id.following_count);

		following_count_TextView.setText(new Integer(currentUser.getFriends_count()).toString() + '\n' + "Following");

		TextView follower_count_TextView = (TextView) settingLayout.findViewById(R.id.follower_count);

		follower_count_TextView.setText(new Integer(currentUser.getBi_followers_count()).toString() + '\n' + "Follower");

		TextView more_TextView = (TextView) settingLayout.findViewById(R.id.more);

		more_TextView.setText("More");

		TextView all_TextView = (TextView) settingLayout.findViewById(R.id.all_weibo);

		TextView orinal_TextView = (TextView) settingLayout.findViewById(R.id.original);

		TextView album_TextView = (TextView) settingLayout.findViewById(R.id.album);

		TextView album_favorate = (TextView) settingLayout.findViewById(R.id.favourite);

		mNoScrollListView = (NoScrollListView) settingLayout.findViewById(R.id.my_weibolist);

		mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());

		statusInfoList = new ArrayList<StatusInfo>();

		if (mAccessToken == null)
			Toast.makeText(getActivity(), "没有获取到密钥", Toast.LENGTH_LONG).show();

		if (mAccessToken != null && mAccessToken.isSessionValid()) {
			String address = "https://api.weibo.com/2/statuses/user_timeline.json";
			String access_token = mAccessToken.getToken();
			String url = address + "?" + "access_token=" + access_token;
			HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
				@Override
				public void onFinish(String response) {
					if (!TextUtils.isEmpty(response)) {
						statusList = StatusList.parse(response).statusList;
						statusList = StatusList.parse(response).statusList;
						Iterator<Status> statusIterator = statusList.iterator();
						while (statusIterator.hasNext()) {
							statusInfoList.add(new StatusInfo(statusIterator.next()));
						}
					}
					if (!statusList.isEmpty()) {
						statusInfoAdapter = new StatusInfoAdapter(getActivity(), 0, statusInfoList, mNoScrollListView);
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								mNoScrollListView.setAdapter(statusInfoAdapter);
							}
						});
					}
				}

				@Override
				public void onError(Exception e) {
					Log.d(TAG, "error");
					LogUtil.e(TAG, e.getMessage());
					ErrorInfo info = ErrorInfo.parse(e.getMessage());
					Toast.makeText(getActivity(), info.toString(), Toast.LENGTH_LONG).show();
				}
			});

			// if (mAccessToken == null)
			// Toast.makeText(getActivity(), "没有获取到密钥",
			// Toast.LENGTH_LONG).show();
			// mStatusesAPI.friendsTimeline(0L, 0L, 100, 1, false, 0, false, new
			// RequestListener() {
			// @Override
			// public void onComplete(String response) {
			// if (!TextUtils.isEmpty(response)) {
			//
			// statusList = StatusList.parse(response).statusList;
			// Toast.makeText(getActivity(), statusList.get(1).id,
			// Toast.LENGTH_LONG).show();
			//
			// }
			// if (!statusList.isEmpty()) {
			// statusAdapter = new StatusAdapter(getActivity(), 0, statusList,
			// mNoScrollListView);
			// mNoScrollListView.setAdapter(statusAdapter);
			// }
			// }
			//
			// @Override
			// public void onWeiboException(WeiboException e) {
			// LogUtil.e(TAG, e.getMessage());
			// ErrorInfo info = ErrorInfo.parse(e.getMessage());
			// Toast.makeText(getActivity(), info.toString(),
			// Toast.LENGTH_LONG).show();
			// }
			// });

		}
		return settingLayout;
	}

	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.all_weibo:
	// // 当点击了消息tab时，选中第1个tab
	// setTabSelection(0);
	// break;
	// case R.id.original:
	// // 当点击了联系人tab时，选中第2个tab
	// setTabSelection(1);
	// break;
	// case R.id.album:
	// // 当点击了动态tab时，选中第3个tab
	// break;
	// case R.id.favourite:
	// // 当点击了设置tab时，选中第4个tab
	// break;
	// default:
	// break;
	// }
	//
	// }

	// private void setTabSelection(int i) {
	// StringBuffer address = new StringBuffer("");
	// switch (i) {
	// case 0:
	// address.append("https://api.weibo.com/2/statuses/user_timeline.json");
	// break;
	// default:
	// address.append(b)
	// break;
	// }
	//
	// }
}
