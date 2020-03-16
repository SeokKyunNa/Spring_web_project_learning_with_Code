package com.zerock.domain;

import java.util.Date;

public class MessageVO {

	private Integer mno;
	private String targetid;
	private String sender;
	private String message;
	private Date openDate;
	private Date senddate;
	
	public Integer getMid() {
		return mno;
	}
	public void setMid(Integer mno) {
		this.mno = mno;
	}
	public String getTargetid() {
		return targetid;
	}
	public void setTargetid(String targetid) {
		this.targetid = targetid;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public Date getSenddate() {
		return senddate;
	}
	public void setSenddate(Date senddate) {
		this.senddate = senddate;
	}
	
	@Override
	public String toString() {
		return "MessageVO [mno=" + mno + ", targetid=" + targetid + ", sender=" + sender + ", message=" + message
				+ ", openDate=" + openDate + ", senddate=" + senddate + "]";
	}
	
}