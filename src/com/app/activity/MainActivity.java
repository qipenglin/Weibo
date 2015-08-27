package com.app.activity;

import java.util.ArrayList;
import java.util.Iterator;

import org.litepal.tablemanager.Connector;

import com.app.fragment.DiscoverFragment;
import com.app.fragment.HomeFragment;
import com.app.fragment.MessageFragment;
import com.app.fragment.ProfileFragment;
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

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 * 
 * @author guolin
 */
public class MainActivity extends Activity implements OnClickListener {

	private static String TAG = "MainActivity";

	/**
	 * 用于展示消息的Fragment
	 */
	private HomeFragment homeFragment;

	/**
	 * 用于展示联系人的Fragment
	 */
	private MessageFragment messageFragment;

	/**
	 * 用于展示动态的Fragment
	 */
	private DiscoverFragment discoverFragment;

	/**
	 * 用于展示设置的Fragment
	 */
	private ProfileFragment profileFragment;

	/**
	 * 消息界面布局
	 */
	private View homeLayout;

	/**
	 * 联系人界面布局
	 */
	private View messageLayout;

	/**
	 * 动态界面布局
	 */
	private View discoverLayout;

	/**
	 * 设置界面布局
	 */
	private View profileLayout;

	/**
	 * 在Tab布局上显示消息图标的控件
	 */
	private ImageView homeImage;

	/**
	 * 在Tab布局上显示联系人图标的控件
	 */
	private ImageView messageImage;

	/**
	 * 在Tab布局上显示动态图标的控件
	 */
	private ImageView discoverImage;

	/**
	 * 在Tab布局上显示设置图标的控件
	 */
	private ImageView profileImage;

	/**
	 * 在Tab布局上显示消息标题的控件
	 */
	private TextView homeText;

	/**
	 * 在Tab布局上显示联系人标题的控件
	 */
	private TextView messageText;

	/**
	 * 在Tab布局上显示动态标题的控件
	 */
	private TextView discoverText;

	/**
	 * 在Tab布局上显示设置标题的控件
	 */
	private TextView profileText;

	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		// 初始化布局元素
		initViews();
		
		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
		// 获取当前已保存过的 Token

	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // 为ActionBar扩展菜单项
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.main, menu);
	// return super.onCreateOptionsMenu(menu);
	// }

	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	private void initViews() {
		homeLayout = findViewById(R.id.home_layout);
		messageLayout = findViewById(R.id.message_layout);
		discoverLayout = findViewById(R.id.discover_layout);
		profileLayout = findViewById(R.id.profile_layout);
		homeImage = (ImageView) findViewById(R.id.home_image);
		messageImage = (ImageView) findViewById(R.id.message_image);
		discoverImage = (ImageView) findViewById(R.id.discover_image);
		profileImage = (ImageView) findViewById(R.id.profile_image);
		homeText = (TextView) findViewById(R.id.home_text);
		messageText = (TextView) findViewById(R.id.message_text);
		discoverText = (TextView) findViewById(R.id.discover_text);
		profileText = (TextView) findViewById(R.id.profile_text);
		homeLayout.setOnClickListener(this);
		messageLayout.setOnClickListener(this);
		discoverLayout.setOnClickListener(this);
		profileLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_layout:
			// 当点击了消息tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.message_layout:
			// 当点击了联系人tab时，选中第2个tab
			setTabSelection(1);
			break;
		case R.id.discover_layout:
			// 当点击了动态tab时，选中第3个tab
			setTabSelection(2);
			break;
		case R.id.profile_layout:
			// 当点击了设置tab时，选中第4个tab
			setTabSelection(3);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			homeImage.setImageResource(R.drawable.home_selected);
			homeText.setTextColor(Color.WHITE);
			if (homeFragment == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				homeFragment = new HomeFragment();
				transaction.add(R.id.content, homeFragment);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(homeFragment);
			}
			break;
		case 1:
			// 当点击了联系人tab时，改变控件的图片和文字颜色
			messageImage.setImageResource(R.drawable.message_selected);
			messageText.setTextColor(Color.WHITE);
			if (messageFragment == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				messageFragment = new MessageFragment();
				transaction.add(R.id.content, messageFragment);
			} else {
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.show(messageFragment);
			}
			break;
		case 2:
			// 当点击了动态tab时，改变控件的图片和文字颜色
			discoverImage.setImageResource(R.drawable.discover_selected);
			discoverText.setTextColor(Color.WHITE);
			if (discoverFragment == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				discoverFragment = new DiscoverFragment();
				transaction.add(R.id.content, discoverFragment);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(discoverFragment);
			}
			break;
		case 3:
		default:
			// 当点击了设置tab时，改变控件的图片和文字颜色
			profileImage.setImageResource(R.drawable.profile_selected);
			profileText.setTextColor(Color.WHITE);
			if (profileFragment == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				profileFragment = new ProfileFragment();
				transaction.add(R.id.content, profileFragment);
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(profileFragment);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		homeImage.setImageResource(R.drawable.home_unselected);
		homeText.setTextColor(Color.parseColor("#82858b"));
		messageImage.setImageResource(R.drawable.message_unselected);
		messageText.setTextColor(Color.parseColor("#82858b"));
		discoverImage.setImageResource(R.drawable.discover_unselected);
		discoverText.setTextColor(Color.parseColor("#82858b"));
		profileImage.setImageResource(R.drawable.profile_unselected);
		profileText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (homeFragment != null) {
			transaction.hide(homeFragment);
		}
		if (messageFragment != null) {
			transaction.hide(messageFragment);
		}
		if (discoverFragment != null) {
			transaction.hide(discoverFragment);
		}
		if (profileFragment != null) {
			transaction.hide(profileFragment);
		}
	}
}
