package com.app.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import com.app.adapter.StatusInfoAdapter;
import com.app.model.StatusInfo;
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

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

	private static final String TAG = "Homefragment";
	/** 当前 Token 信息 */
	private Oauth2AccessToken mAccessToken;
	/** 用于获取微博信息流等操作的API */
	private StatusesAPI mStatusesAPI;

	private ArrayList<Status> statusList;

	private ArrayList<StatusInfo> statusInfoList;

	private ListView listView;

	private StatusInfoAdapter statusInfoAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SQLiteDatabase db = Connector.getDatabase();
		mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());
		mStatusesAPI = new StatusesAPI(getActivity(), Constants.APP_KEY, mAccessToken);
		statusInfoList = new ArrayList<StatusInfo>();
		if (mAccessToken != null && mAccessToken.isSessionValid()) {
			mStatusesAPI.friendsTimeline(0L, 0L, 200, 1, false, 0, false, new RequestListener() {
				@Override
				public void onComplete(String response) {
					if (!TextUtils.isEmpty(response)) {
						statusList = StatusList.parse(response).statusList;
						Iterator<Status> statusIterator = statusList.iterator();
						while (statusIterator.hasNext()) {
							Status status = statusIterator.next();
							StatusInfo statusInfo = new StatusInfo(status);
							if(!statusInfo.save()){
								Log.d(TAG, "save fialed");
							};
							statusInfoList.add(statusInfo);
						}
						statusInfoAdapter.notifyDataSetChanged();
					}
				}

				@Override
				public void onWeiboException(WeiboException e) {
					LogUtil.e(TAG, e.getMessage());
					ErrorInfo info = ErrorInfo.parse(e.getMessage());
					Toast.makeText(getActivity(), info.toString(), Toast.LENGTH_LONG).show();
				}
			});
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View statusView = inflater.inflate(R.layout.home_layout, container, false);
		listView = (ListView) statusView.findViewById(R.id.weibo_list);
//		List<StatusInfo> statusInfos =  (ArrayList<StatusInfo>) DataSupport.findAll(StatusInfo.class, 1, 3, 5, 7);
		statusInfoAdapter = new StatusInfoAdapter(getActivity(), 0, statusInfoList, listView);
		listView.setAdapter(statusInfoAdapter);
		return statusView;
	}
}
