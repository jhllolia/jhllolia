package com.tosok.user.VO;

import java.sql.Date;

public class CommentVO {

	private int c_Seq;
	private int b_Seq;
	private String c_Id;
	private String c_content;
	private int c_parent_seq;
	private int c_depth;
	private String c_state;
	private Date c_date;

	public int getC_Seq() {
		return c_Seq;
	}

	public void setC_Seq(int c_Seq) {
		this.c_Seq = c_Seq;
	}

	public int getB_Seq() {
		return b_Seq;
	}

	public void setB_Seq(int b_Seq) {
		this.b_Seq = b_Seq;
	}

	public String getC_Id() {
		return c_Id;
	}

	public void setC_Id(String c_Id) {
		this.c_Id = c_Id;
	}

	public String getC_content() {
		return c_content;
	}

	public void setC_content(String c_content) {
		this.c_content = c_content;
	}

	public int getC_parent_seq() {
		return c_parent_seq;
	}

	public void setC_parent_seq(int c_parent_seq) {
		this.c_parent_seq = c_parent_seq;
	}

	public int getC_depth() {
		return c_depth;
	}

	public void setC_depth(int c_depth) {
		this.c_depth = c_depth;
	}

	public String getC_state() {
		return c_state;
	}

	public void setC_state(String c_state) {
		this.c_state = c_state;
	}

	public Date getC_date() {
		return c_date;
	}

	public void setC_date(Date c_date) {
		this.c_date = c_date;
	}

}
