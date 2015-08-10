package com.app.model;


public class Weibo {
	String head;
	String text;
	
	public Weibo(String head, String text){
		this.head = head;
		this.text = text;
		
	}
	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
