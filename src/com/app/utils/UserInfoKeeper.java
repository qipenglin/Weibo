package com.app.utils;

import com.app.model.UserInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserInfoKeeper {

	private static final String PREFERENCES_NAME = "UserInfomation";
	/** 用户UID（int64） */
	private static final String USER_ID = "userId";
	/** 字符串型的用户 UID */
	private static final String IDSTR = "idstr";
	/** 用户昵称 */
	private static final String SCREEN_NAME = "screen_name";
	/** 友好显示名称 */
	private static final String NAME = "name";
	/** 用户所在省级ID */
	private static final String PROVINCE = "province";
	/** 用户所在城市ID */
	private static final String CITY = "city";
	/** 用户所在地 */
	private static final String LOCATION = "location";
	/** 用户个人描述 */
	private static final String DESCRIPTION = "description";
	/** 用户博客地址 */
	private static final String URL = "url";
	/** 用户头像地址，50×50像素 */
	private static final String PROFILE_IMAGE_URL = "profile_image_url";
	/** 用户的微博统一URL地址 */
	private static final String PROFILE_URL = "profile_url";
	/** 用户的个性化域名 */
	private static final String DOMAIN = "domain";
	/** 用户的微号 */
	private static final String WEIHAO = "weihao";
	/** 性别，m：男、f：女、n：未知 */
	private static final String GENDER = "gender";
	/** 粉丝数 */
	private static final String FOLLOWERS_COUNT = "followers_count";
	/** 关注数 */
	private static final String FRIEND_COUNT = "friends_count";
	/** 微博数 */
	private static final String STATUSES_COUNT = "statuses_count";
	/** 收藏数 */
	private static final String FAVORITES_COUNT = "favourites_count";
	/** 用户创建（注册）时间 */
	private static final String CREAT_AT = "created_at";
	/** 暂未支持 */
	private static final String FOLLOWING = "following";
	/** 是否允许所有人给我发私信，true：是，false：否 */
	private static final String ALLOW_ALL_ACT_MSG = "allow_all_act_msg";
	/** 是否允许标识用户的地理位置，true：是，false：否 */
	private static final String GEO_ENABLED = "geo_enabled";
	/** 是否是微博认证用户，即加V用户，true：是，false：否 */
	private static final String VERIFIED = "verified";
	/** 暂未支持 */
	private static final String VERIFID_TYPE = "verified_type";
	/** 用户备注信息，只有在查询用户关系时才返回此字段 */
	private static final String REMARK = "remark";
	// /** 用户的最近一条微博信息字段 */
	// private Status status;
	/** 是否允许所有人对我的微博进行评论，true：是，false：否 */
	private static final String ALLOW_ALL_COMMENT = "allow_all_comment";
	/** 用户大头像地址 */
	private static final String AVATAR_LARGE = "avatar_large";
	/** 用户高清大头像地址 */
	private static final String AVATAR_HD = "avatar_hd";
	/** 认证原因 */
	private static final String VERIFIED_REASON = "verified_reason";
	/** 该用户是否关注当前登录用户，true：是，false：否 */
	private static final String FOLLOW_ME = "follow_me";
	/** 用户的在线状态，0：不在线、1：在线 */
	private static final String ONLINE_STATUS = "online_status";
	/** 用户的互粉数 */
	private static final String BI_FLOOWER_COUNT = "bi_followers_count";
	/** 用户当前的语言版本，zh-cn：简体中文，zh-tw：繁体中文，en：英语 */
	private static final String LANG = "lang";

	/** 注意：以下字段暂时不清楚具体含义，OpenAPI 说明文档暂时没有同步更新对应字段 */
	private static final String STAR = "star";
	private static final String MBTYPE = "mbtype";
	private static final String MBRANK = "mbrank";
	private static final String BLOCK_WORD = "block_word";

	/**
	 * 保存 Token 对象到 SharedPreferences。
	 * 
	 * @param context
	 *            应用程序上下文环境
	 * @param token
	 *            Token 对象
	 */
	public static void writeAccessToken(Context context, UserInfo mUserInfo) {
		if (null == context || null == mUserInfo) {
			return;
		}

		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putString(USER_ID, mUserInfo.getUserId());
		editor.putString(IDSTR, mUserInfo.getIdstr());
		editor.putString(SCREEN_NAME, mUserInfo.getScreen_name());
		editor.putString(NAME, mUserInfo.getName());
		editor.putLong(PROVINCE, mUserInfo.getProvince());
		editor.putLong(CITY, mUserInfo.getCity());
		editor.putString(LOCATION, mUserInfo.getLocation());
		editor.putString(DESCRIPTION, mUserInfo.getDescription());
		editor.putString(URL, mUserInfo.getUrl());
		editor.putString(PROFILE_IMAGE_URL, mUserInfo.getProfile_image_url());
		editor.putString(PROFILE_URL, mUserInfo.getProfile_url());
		editor.putString(DOMAIN, mUserInfo.getDomain());
		editor.putString(WEIHAO, mUserInfo.getWeihao());
		editor.putString(GENDER, mUserInfo.getGender());
		editor.putLong(FOLLOWERS_COUNT, mUserInfo.getBi_followers_count());
		editor.putLong(FRIEND_COUNT, mUserInfo.getFriends_count());
		editor.putLong(STATUSES_COUNT, mUserInfo.getStatuses_count());
		editor.putLong(FAVORITES_COUNT, mUserInfo.getFavourites_count());
		editor.putString(CREAT_AT, mUserInfo.getCreated_at());
		editor.putBoolean(FOLLOWING, mUserInfo.isFollowing());
		editor.putBoolean(ALLOW_ALL_ACT_MSG, mUserInfo.isAllow_all_act_msg());
		editor.putBoolean(GEO_ENABLED, mUserInfo.isGeo_enabled());
		editor.putBoolean(VERIFIED, mUserInfo.isVerified());
		editor.putLong(VERIFID_TYPE, mUserInfo.getVerified_type());
		editor.putString(REMARK, mUserInfo.getRemark());
		editor.putBoolean(ALLOW_ALL_COMMENT, mUserInfo.isAllow_all_comment());
		editor.putString(AVATAR_LARGE, mUserInfo.getAvatar_large());
		editor.putString(AVATAR_HD, mUserInfo.getAvatar_hd());
		editor.putString(VERIFIED_REASON, mUserInfo.getVerified_reason());
		editor.putBoolean(FOLLOW_ME, mUserInfo.isFollow_me());
		editor.putLong(ONLINE_STATUS, mUserInfo.getOnline_status());
		editor.putLong(BI_FLOOWER_COUNT, mUserInfo.getFollowers_count());
		editor.putString(LANG, mUserInfo.getLang());
		/** 注意：以下字段暂时不清楚具体含义，OpenAPI 说明文档暂时没有同步更新对应字段 */
		editor.putString(STAR, mUserInfo.getStar());
		editor.putString(MBTYPE, mUserInfo.getMbtype());
		editor.putString(MBRANK, mUserInfo.getMbrank());
		editor.putString(BLOCK_WORD, mUserInfo.getBlock_word());
		editor.commit();
	}

	/**
	 * 从 SharedPreferences 读取 Token 信息。
	 * 
	 * @param context
	 *            应用程序上下文环境
	 * 
	 * @return 返回 Token 对象
	 */

	public static UserInfo readAccessToken(Context context) {
		if (null == context) {
			return null;
		}

		UserInfo mUserInfo = new UserInfo();
		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		// token.setUid(pref.getString(KEY_UID, ""));
		// token.setToken(pref.getString(KEY_ACCESS_TOKEN, ""));
		// token.setRefreshToken(pref.getString(KEY_REFRESH_TOKEN, ""));
		// token.setExpiresTime(pref.getLong(KEY_EXPIRES_IN, 0));

		return mUserInfo;
	}

	/**
	 * 清空 SharedPreferences 中 Token信息。
	 * 
	 * @param context
	 *            应用程序上下文环境
	 */
	public static void clear(Context context) {
		if (null == context) {
			return;
		}

		SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();
	}
}