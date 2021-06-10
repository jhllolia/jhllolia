package com.tosok.user.VO;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileUploadVO {

	private String attachPath;
	private String FileName;
	private MultipartFile upload;
	private String CKEditorFuncNum;

}