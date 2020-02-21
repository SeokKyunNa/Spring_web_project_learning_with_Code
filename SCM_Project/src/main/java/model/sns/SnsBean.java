package model.sns;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class SnsBean {
	private int write_num;
	private String write_user;
	private String write_body;
	private Date write_date;
	private int write_good;
	private String[] write_file2;
	private String write_file;
	private int FileNum;
	
	public int getFileNum() {
		return FileNum;
	}
	public void setFileNum(int fileNum) {
		FileNum = fileNum;
	}
	public String getWrite_file() {
		return write_file;
	}
	public void setWrite_file(String write_file) {
		this.write_file = write_file;
	}
	private String write_pic;
	
	 public String getWrite_file2() {
			String str="";
			for(int i=0; i<write_file2.length; i++){
				str+=write_file2[i]+"'";
				System.out.println(str+"str");
			}
			return str;
	}
	public void setWrite_file2(String[] write_file2) {
		this.write_file2 = write_file2;
	}
	private List<MultipartFile> files;
	
	
	public List<MultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	public int getWrite_reply() {
		return write_reply;
	}
	public void setWrite_reply(int write_reply) {
		this.write_reply = write_reply;
	}
	private int write_reply;
	
	public String getWrite_pic() {
		return write_pic;
	}
	public void setWrite_pic(String write_pic) {
		this.write_pic = write_pic;
	}
	private int reply_wnum;
	private int reply_num;
	private String reply_user;
	private String reply_body;
	private Date reply_date;
	private String reply_pic;
	
	
	
	public String getReply_pic() {
		return reply_pic;
	}
	public void setReply_pic(String reply_pic) {
		this.reply_pic = reply_pic;
	}
	public int getReply_wnum() {
		return reply_wnum;
	}
	public void setReply_wnum(int reply_wnum) {
		this.reply_wnum = reply_wnum;
	}
	public int getReply_num() {
		return reply_num;
	}
	public void setReply_num(int reply_num) {
		this.reply_num = reply_num;
	}
	public String getReply_user() {
		return reply_user;
	}
	public void setReply_user(String reply_user) {
		this.reply_user = reply_user;
	}
	public String getReply_body() {
		return reply_body;
	}
	public void setReply_body(String reply_body) {
		this.reply_body = reply_body;
	}
	public Date getReply_date() {
		return reply_date;
	}
	public void setReply_date(Date reply_date) {
		this.reply_date = reply_date;
	}
	public int getWrite_num() {
		return write_num;
	}
	public void setWrite_num(int write_num) {
		this.write_num = write_num;
	}
	public String getWrite_user() {
		return write_user;
	}
	public void setWrite_user(String write_user) {
		this.write_user = write_user;
	}
	public String getWrite_body() {
		return write_body;
		
	}
	
	public void setWrite_body(String write_body) {
		this.write_body = write_body;
	}
	public Date getWrite_date() {
		return write_date;
	}
	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}
	public int getWrite_good() {
		return write_good;
	}
	public void setWrite_good(int write_good) {
		this.write_good = write_good;
	}


	private int goodnum;
	private String goodid;
	private int good_on;
	public int getGood_on() {
		return good_on;
	}
	public void setGood_on(int good_on) {
		this.good_on = good_on;
	}
	public int getGoodnum() {
		return goodnum;
	}
	public void setGoodnum(int goodnum) {
		this.goodnum = goodnum;
	}
	public String getGoodid() {
		return goodid;
	}
	public void setGoodid(String goodid) {
		this.goodid = goodid;
	}
	
	
	


}
