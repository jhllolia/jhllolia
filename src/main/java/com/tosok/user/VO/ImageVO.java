package com.tosok.user.VO;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ImageVO {

	private String img_idx;
	private String[] img_idxs;
	private String title;
	private String use_yn;
	private String del_yn;
	private String addData;

	private String pcWidth;
	private String pcHight;
	private String mobWidth;
	private String mobHight;
	private MultipartFile files;
	private String ord;

	private String web_image;
	private String mob_image;
	private Date reg_dt;

}
