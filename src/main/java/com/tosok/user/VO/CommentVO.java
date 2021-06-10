package com.tosok.user.VO;

import java.sql.Date;

import lombok.Data;

@Data
public class CommentVO {

	private int c_Seq;
	private int b_Seq;
	private String c_Id;
	private String c_content;
	private int c_parent_seq;
	private int c_depth;
	private String c_state;
	private Date c_date;

}
