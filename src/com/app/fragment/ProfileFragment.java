package com.app.fragment;

import org.litepal.tablemanager.Connector;

import com.app.model.MyUser;
import com.app.utils.AccessTokenKeeper;
import com.app.utils.Constants;
import com.app.utils.Utils;
import com.app.weibo.R;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.User;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;



public class ProfileFragment extends Fragment {
	/** 当前 Token 信息 */
	private Oauth2AccessToken mAccessToken;
	/** 用户信息接口 */
	private UsersAPI mUsersAPI;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View settingLayout = inflater.inflate(R.layout.profile_layout,
				container, false);
		mAccessToken = AccessTokenKeeper.readAccessToken(getActivity());
		// 获取用户信息接口
		mUsersAPI = new UsersAPI(getActivity(), Constants.APP_KEY, mAccessToken);
		

		long uid = Long.parseLong(mAccessToken.getUid());
		mUsersAPI.show(uid, mListener);
		return settingLayout;
	}
	
	private RequestListener mListener = new RequestListener() {
		@Override
		public void onComplete(String response) {
			if (!TextUtils.isEmpty(response)) {
				// 调用 User#parse 将JSON串解析成User对象
				User user = User.parse(response);
				if (user != null) {
					SQLiteDatabase db = Connector.getDatabase();
					
//					Utils.SaveUserToDatabase(user);
					MyUser news = new MyUser(user);
					if (news.save()) {
						Toast.makeText(getActivity(), "存储成功", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getActivity(), "存储失败", Toast.LENGTH_SHORT).show();
					}

					Toast.makeText(getActivity(), "获取User信息成功，用户昵称：" + user.screen_name, Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
				}
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {
			ErrorInfo info = ErrorInfo.parse(e.getMessage());
			Toast.makeText(getActivity(), info.toString(), Toast.LENGTH_LONG).show();
		}
	};


}
