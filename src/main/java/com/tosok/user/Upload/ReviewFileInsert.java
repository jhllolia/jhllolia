package com.tosok.user.Upload;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tosok.user.VO.ReviewVO;

@Component("ReviewFileInsert")
public class ReviewFileInsert {
	protected static Log log = LogFactory.getLog(ReviewFileInsert.class);

	public static List<Map<String,Object>> parseInsertFileInfo(ReviewVO vo, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();

		MultipartFile file = null;
		String rootPath = "";
		String filename = "";

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> listMap = null;

        MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multi.getFileNames();
        rootPath = session.getServletContext().getRealPath("/resources/upload/review/");

		while (iterator.hasNext()) {
			file = multi.getFile(iterator.next());							// 순차 실행

			File exist = new File(rootPath + file.getOriginalFilename());	// 파일 존재여부

			/* ============= 파일명 변경 =========== */
    		UUID uuid = UUID.randomUUID();
        	filename = uuid.toString() + "_" + file.getOriginalFilename();

			/* ============= 파일 확장자 유효성 =========== */
			int idx = filename.lastIndexOf(".");
			String extension = filename.substring(idx + 1);
			String regEx = "(jpg|jpeg|JPEG|JPG|png|PNG|gif|GIF|BMP|bmp)";

			if(extension.matches(regEx)) {
				listMap = new HashMap<String,Object>();

				/* ============= 파일 존재여부 =========== */
				if(exist.isFile()) {
					listMap.put("FILE_NAME", file.getOriginalFilename());
				} else {
					File rootFile = new File(rootPath + "/" + filename);	// 경로
					file.transferTo(rootFile);								// 업로드

	                listMap.put("FILE_NAME", filename);
				}

				listMap.put("ORDER_NUM", vo.getORDER_NUM());
                listMap.put("FILE_TITLE", file.getName());
                listMap.put("FILE_SIZE", file.getSize());
                
				log.info("=========================================");
				log.info("name : " + file.getName());
				log.info("filename : " + file.getOriginalFilename());
				log.info("size : " + file.getSize());

                list.add(listMap);
			}
		}

		return list;
	}
}
