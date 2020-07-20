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

import com.tosok.user.VO.ProductVO;

@Component("SeatFileInsert")
public class SeatFileInsert {
	protected static Log log = LogFactory.getLog(SeatFileInsert.class);

	public static List<Map<String,Object>> parseInsertFileInfo(ProductVO vo, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();

		MultipartFile file = null;
		String rootPath = "";
		String filename = "";

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> listMap = null;

        MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multi.getFileNames();

		while (iterator.hasNext()) {
			file = multi.getFile(iterator.next());

			if (file.isEmpty() == false) {
        		UUID uuid = UUID.randomUUID();
            	filename = uuid.toString() + "_" + file.getOriginalFilename();

    			int idx = filename.lastIndexOf(".");

    			String extension = filename.substring(idx + 1);
    			String regEx = "(jpg|jpeg|JPEG|JPG|png|PNG|gif|GIF|BMP|bmp)";

    			if(extension.matches(regEx)) {
                	/* ======== 로고 / 슬라이드 이미지 분류 ======== */
    				if(file.getName().indexOf("file_") > -1) {
    					rootPath = session.getServletContext().getRealPath("/resources/upload/table/");					
    				} else if(file.getName().indexOf("logo") > -1) {
    					rootPath = session.getServletContext().getRealPath("/resources/upload/logo/");	
    				}

                	/* ======== 존재 여부 체크 이후 파입업로드 ======== */
                	File hasFolder = new File(rootPath + vo.getPRODUCT_SEQ() + "/");
                	if (!hasFolder.exists()) {
                		hasFolder.mkdir();
                	}

                	/* ======== 파일 업로드 ======== */
    				File rootFile = new File(rootPath + vo.getPRODUCT_SEQ() + "/" + filename);
    				file.transferTo(rootFile);
    				
    				log.info("=========================================");
    				log.info("name : " + file.getName());
    				log.info("filename : " + file.getOriginalFilename());
    				log.info("rootFile : " + rootFile);
    				log.info("size : " + file.getSize());

    				listMap = new HashMap<String,Object>();

    				listMap.put("PRODUCT_SEQ", vo.getPRODUCT_SEQ());
                    listMap.put("PRODUCT_FILE_TITLE", file.getName());
                    listMap.put("PRODUCT_FILE_NAME", filename);
                    listMap.put("PRODUCT_FILE_SIZE", file.getSize());

                    list.add(listMap);
    			}
			}
		}

		return list;
	}
}
