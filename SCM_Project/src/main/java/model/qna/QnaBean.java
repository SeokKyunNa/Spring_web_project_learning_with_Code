package model.qna;

public class QnaBean {
	private int qna_num;
	private String qna_id;
	private String qna_subject;
	private String qna_type;
	private String qna_body;
	private String qna_code;
	private String qna_file;
	private int qna_hits;
	private String qna_date;
	private int qna_reply;
	public int getQna_reply() {
		return qna_reply;
	}
	public void setQna_reply(int qna_reply) {
		this.qna_reply = qna_reply;
	}
	public int getQna_num() {
		return qna_num;
	}
	public void setQna_num(int qna_num) {
		this.qna_num = qna_num;
	}
	public String getQna_id() {
		return qna_id;
	}
	public void setQna_id(String qna_id) {
		this.qna_id = qna_id;
	}
	public String getQna_subject() {
		return qna_subject;
	}
	public void setQna_subject(String qna_subject) {
		this.qna_subject = qna_subject;
	}
	public String getQna_type() {
		return qna_type;
	}
	public void setQna_type(String qna_type) {
		this.qna_type = qna_type;
	}
	public String getQna_body() {
		return qna_body;
	}
	public void setQna_body(String qna_body) {
		this.qna_body = qna_body;
	}
	public String getQna_code() {
		return qna_code;
	}
	public void setQna_code(String qna_code) {
		this.qna_code = qna_code;
	}
	public String getQna_file() {
		return qna_file;
	}
	public void setQna_file(String qna_file) {
		this.qna_file = qna_file;
	}
	public int getQna_hits() {
		return qna_hits;
	}
	public void setQna_hits(int qna_hits) {
		this.qna_hits = qna_hits;
	}
	public String getQna_date() {
		String year = qna_date.substring(2, 4);
		String month = qna_date.substring(5,7);
		String date = qna_date.substring(8, 10);
		return year+"/"+month+"/"+date;
	}
	public void setQna_date(String qna_date) {
		this.qna_date = qna_date;
	}
	
}
