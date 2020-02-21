package model.msg;

import java.sql.Date;

public class BbsBean {
	private int MESSAGE_NUM;
	private String MESSAGE_USER;
	private String MESSAGE_SUBJECT;
	private String MESSAGE_BODY;
	private String MESSAGE_REC;
	private String MESSAGE_FILE;
	private String MESSAGE_CHECK;
	private Date MESSAGE_DATE;
	public int getMESSAGE_NUM() {
		return MESSAGE_NUM;
	}
	public void setMESSAGE_NUM(int mESSAGE_NUM) {
		MESSAGE_NUM = mESSAGE_NUM;
	}
	public String getMESSAGE_USER() {
		return MESSAGE_USER;
	}
	public void setMESSAGE_USER(String mESSAGE_USER) {
		MESSAGE_USER = mESSAGE_USER;
	}
	public String getMESSAGE_SUBJECT() {
		return MESSAGE_SUBJECT;
	}
	public void setMESSAGE_SUBJECT(String mESSAGE_SUBJECT) {
		MESSAGE_SUBJECT = mESSAGE_SUBJECT;
	}
	public String getMESSAGE_BODY() {
		return MESSAGE_BODY;
	}
	public void setMESSAGE_BODY(String mESSAGE_BODY) {
		MESSAGE_BODY = mESSAGE_BODY;
	}
	public String getMESSAGE_REC() {
		return MESSAGE_REC;
	}
	public void setMESSAGE_REC(String mESSAGE_REC) {
		MESSAGE_REC = mESSAGE_REC;
	}
	public String getMESSAGE_FILE() {
		return MESSAGE_FILE;
	}
	public void setMESSAGE_FILE(String mESSAGE_FILE) {
		MESSAGE_FILE = mESSAGE_FILE;
	}
	public String getMESSAGE_CHECK() {
		return MESSAGE_CHECK;
	}
	public void setMESSAGE_CHECK(String mESSAGE_CHECK) {
		MESSAGE_CHECK = mESSAGE_CHECK;
	}
	public Date getMESSAGE_DATE() {
		return MESSAGE_DATE;
	}
	public void setMESSAGE_DATE(Date mESSAGE_DATE) {
		MESSAGE_DATE = mESSAGE_DATE;
	}
}