package com.app.model;

import java.util.ArrayList;

import org.litepal.crud.DataSupport;

import com.app.utils.DateUtil;
import com.sina.weibo.sdk.openapi.models.Geo;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.openapi.models.Visible;

public class StatusInfo extends DataSupport {

	/** 微博创建时间 */
	private long created_at;
	/** 微博ID */
	private String statusId;
	/** 微博MID */
	private String mid;
	/** 字符串型的微博ID */
	private String idstr;
	/** 微博信息内容 */
	private String text;
	/** 微博来源 */
	private String source;
	/** 是否已收藏，true：是，false：否 */
	private boolean favorited;
	/** 是否被截断，true：是，false：否 */
	private boolean truncated;
	/** （暂未支持）回复ID */
	private String in_reply_to_status_id;
	/** （暂未支持）回复人UID */
	private String in_reply_to_user_id;
	/** （暂未支持）回复人昵称 */
	private String in_reply_to_screen_name;
	/** 缩略图片地址（小图），没有时不返回此字段 */
	private String thumbnail_pic;
	/** 中等尺寸图片地址（中图），没有时不返回此字段 */
	private String bmiddle_pic;
	/** 原始图片地址（原图），没有时不返回此字段 */
	private String original_pic;
	/** 地理信息字段 */
	private Geo geo;
	/** 微博作者的用户信息字段 */
	private UserInfo userInfo;
	/** 被转发的原微博信息字段，当该微博为转发微博时返回 */
	Status retweeted_status;
	/** 转发数 */
	private int reposts_count;
	/** 评论数 */
	private int comments_count;
	/** 表态数 */
	private int attitudes_count;
	/** 暂未支持 */
	private int mlevel;
	/**
	 * 微博的可见性及指定可见分组信息。该 object 中 type 取值， 0：普通微博，1：私密微博，3：指定分组微博，4：密友微博；
	 * list_id为分组的组号
	 */
	Visible visible;
	/** 微博配图地址。多图时返回多图链接。无配图返回"[]" */
	ArrayList<String> pic_urls;

	/** 微博流内的推广微博ID */
	// private Ad ad;
	public long getCreated_at() {
		return created_at;
	}

	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getIdstr() {
		return idstr;
	}

	public void setIdstr(String idstr) {
		this.idstr = idstr;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public boolean isTruncated() {
		return truncated;
	}

	public void setTruncated(boolean truncated) {
		this.truncated = truncated;
	}

	public String getIn_reply_to_status_id() {
		return in_reply_to_status_id;
	}

	public void setIn_reply_to_status_id(String in_reply_to_status_id) {
		this.in_reply_to_status_id = in_reply_to_status_id;
	}

	public String getIn_reply_to_user_id() {
		return in_reply_to_user_id;
	}

	public void setIn_reply_to_user_id(String in_reply_to_user_id) {
		this.in_reply_to_user_id = in_reply_to_user_id;
	}

	public String getIn_reply_to_screen_name() {
		return in_reply_to_screen_name;
	}

	public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
		this.in_reply_to_screen_name = in_reply_to_screen_name;
	}

	public String getThumbnail_pic() {
		return thumbnail_pic;
	}

	public void setThumbnail_pic(String thumbnail_pic) {
		this.thumbnail_pic = thumbnail_pic;
	}

	public String getBmiddle_pic() {
		return bmiddle_pic;
	}

	public void setBmiddle_pic(String bmiddle_pic) {
		this.bmiddle_pic = bmiddle_pic;
	}

	public String getOriginal_pic() {
		return original_pic;
	}

	public void setOriginal_pic(String original_pic) {
		this.original_pic = original_pic;
	}

	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUser(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Status getRetweeted_status() {
		return retweeted_status;
	}

	public void setRetweeted_status(Status retweeted_status) {
		this.retweeted_status = retweeted_status;
	}

	public int getReposts_count() {
		return reposts_count;
	}

	public void setReposts_count(int reposts_count) {
		this.reposts_count = reposts_count;
	}

	public int getComments_count() {
		return comments_count;
	}

	public void setComments_count(int comments_count) {
		this.comments_count = comments_count;
	}

	public int getAttitudes_count() {
		return attitudes_count;
	}

	public void setAttitudes_count(int attitudes_count) {
		this.attitudes_count = attitudes_count;
	}

	public int getMlevel() {
		return mlevel;
	}

	public void setMlevel(int mlevel) {
		this.mlevel = mlevel;
	}

	public Visible getVisible() {
		return visible;
	}

	public void setVisible(Visible visible) {
		this.visible = visible;
	}

	public ArrayList<String> getPic_urls() {
		return pic_urls;
	}

	public void setPic_urls(ArrayList<String> pic_urls) {
		this.pic_urls = pic_urls;
	}

	public StatusInfo(Status status) {
		created_at = DateUtil.parseStringToDate(status.created_at).getTime();
		statusId = status.id;
		mid = status.mid;
		idstr = status.idstr;
		text = status.text;
		source = status.source;
		favorited = status.favorited;
		truncated = status.truncated;
		in_reply_to_status_id = status.in_reply_to_status_id;
		in_reply_to_user_id = status.in_reply_to_user_id;
		in_reply_to_screen_name = status.in_reply_to_screen_name;
		thumbnail_pic = status.thumbnail_pic;
		bmiddle_pic = status.bmiddle_pic;
		original_pic = status.original_pic;
		geo = status.geo;
		userInfo = new UserInfo(status.user);
		retweeted_status = status.retweeted_status;
		reposts_count = status.reposts_count;
		comments_count = status.comments_count;
		attitudes_count = status.attitudes_count;
		mlevel = status.mlevel;
		visible = status.visible;
		pic_urls = status.pic_urls;
	}

	// public Status GetStatus(StatusInfo statusInfo) {
	// Status status = new Status();
	// status.created_at = status.created_at;
	// status.id = statusInfo.id;
	// status.mid = statusInfo.mid;
	// status.idstr = statusInfo.idstr;
	// status.text = statusInfo.text;
	// status.source = statusInfo.source;
	// status.favorited = statusInfo.favorited;
	// status.truncated = statusInfo.truncated;
	// status.in_reply_to_status_id = statusInfo.in_reply_to_status_id;
	// status.in_reply_to_user_id = statusInfo.in_reply_to_user_id;
	// status.in_reply_to_screen_name = statusInfo.in_reply_to_screen_name;
	// status.thumbnail_pic = statusInfo.thumbnail_pic;
	// status.bmiddle_pic = statusInfo.bmiddle_pic;
	// status.original_pic = statusInfo.original_pic;
	// status.geo = statusInfo.geo;
	// status.user = statusInfo.user;
	// status.retweeted_status = statusInfo.retweeted_status;
	// status.reposts_count = statusInfo.reposts_count;
	// status.comments_count = statusInfo.comments_count;
	// status.attitudes_count = statusInfo.attitudes_count;
	// status.mlevel = statusInfo.mlevel;
	// status.visible = statusInfo.visible;
	// status.pic_urls = statusInfo.pic_urls;
	// return status;
	// }

}
