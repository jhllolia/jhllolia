package com.tosok.user.VO;

import lombok.Data;

@Data
public class ReceipeVO {

	private int intSeq;
	private String recipe_Category;
	private String user_Id;
	private String recipe_Title;
	private String recipe_SubTitle;
	private String recipe_Subject;
	private String recipe_Content;
	private String register_Date;
	private String r_state;

}
