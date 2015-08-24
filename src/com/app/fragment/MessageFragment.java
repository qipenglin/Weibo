package com.app.fragment;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import com.app.model.UserInfo;
import com.app.weibo.R;

public class MessageFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contactsLayout = inflater.inflate(R.layout.message_layout,
				container, false);
		UserInfo user = DataSupport.findFirst(UserInfo.class);
		Toast.makeText(getActivity(), user.getScreen_name(),Toast.LENGTH_LONG).show();
		return contactsLayout;
	}

}
