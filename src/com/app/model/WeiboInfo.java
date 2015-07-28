package com.app.model;

public class WeiboInfo {
	private int iconId;
	private String time;
	private String user;
	private int imageId;
	private String text;

	public WeiboInfo(int iconId, String time, String user, int imageId,
			String text) {
		this.iconId = iconId;
		this.time = time;
		this.user = user;
		this.imageId = imageId;
		this.text = text;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
