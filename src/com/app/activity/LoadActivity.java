/**
 * @(#)LoadActivity.java
 * 
 * @Version: 1
 * @JDK: jdk 1.8.0.XXX
 * @Module: WBStatusActivity
 */
/*- 				History
 **********************************************
 *  ID      DATE           PERSON       REASON
 *  1     2011-11-5     hanfei.li    Created
 **********************************************
 */

package com.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;

import org.litepal.tablemanager.Connector;

import com.app.model.UserInfo;
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

/**
 * 启动加载
 * 
 * @auther qipeng
 * @since 2015-7-26
 */

public class LoadActivity extends Activity {
	
	private Oauth2AccessToken mAccessToken;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_load);
		ImageView loadImage = (ImageView) findViewById(R.id.loadimage);
		AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
		// 动画持续时间
		animation.setDuration(200);
		// 动画绑定到ImageView组件上执行
		loadImage.setAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {

			/**
			 * 动画开始时触发
			 */
			@Override
			public void onAnimationStart(Animation animation) {
				Toast.makeText(LoadActivity.this, "动画开始", Toast.LENGTH_SHORT).show();
				init();
			}

			/**
			 * 
			 * 动画结束的时候执行
			 */
			@Override
			public void onAnimationEnd(Animation animation) {
				Toast.makeText(LoadActivity.this, "动画结束", Toast.LENGTH_SHORT).show();
				mAccessToken = AccessTokenKeeper.readAccessToken(LoadActivity.this);
				if (mAccessToken == null || !mAccessToken.isSessionValid()) {
					startActivity(new Intent(LoadActivity.this, WBAuthActivity.class));
					finish();
				} else {
					startActivity(new Intent(LoadActivity.this, MainActivity.class));
					finish();
				}
			}

			/**
			 * 重复执行触发
			 */
			@Override
			public void onAnimationRepeat(Animation animation) {
			}

		});
	}

	

	public void init() {
		Utils.checkNetwork(this);
	}

}
