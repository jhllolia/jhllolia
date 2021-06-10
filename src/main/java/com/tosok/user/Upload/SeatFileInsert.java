package com.tosok.user.Upload;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tosok.user.VO.ProductVO;

@Component("SeatFileInsert")
public class SeatFileInsert {
	protected static Log log = LogFactory.getLog(SeatFileInsert.class);

	/* 파일업로드 단일 */
	public static String insertFileProc(String width, String height, MultipartFile file) {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		HttpSession session = request.getSession();

		if (file.isEmpty() == true) {
			return "";
		} else {

			String rootPath = session.getServletContext().getRealPath("/resources/upload/gallary");
			// String rootPath = "http://jhllolia.cdn3.cafe24.com/gallary";
			String fileNm = "";

			try {

				fileNm = RandomStringUtils.randomAlphanumeric(8) + "_" + file.getOriginalFilename();
    			int idx = fileNm.lastIndexOf(".");

    			String extension = fileNm.substring(idx + 1);
    			String regEx = "(jpg|jpeg|JPEG|JPG|png|PNG|gif|GIF|BMP|bmp)";

    			if(extension.matches(regEx)) {
    				rootPath = rootPath + "/" + fileNm;
    				System.out.println("rootPath ===> " + rootPath);

    				// 이미지 업로드
    				byte[] bytes = file.getBytes();
    				Path path = Paths.get(rootPath);
    				Files.write(path, bytes);

    				// 이미지 사이즈 조정
    				Image image = ImageIO.read(new File(rootPath));
    				Image resizeImage = image.getScaledInstance(Integer.parseInt(width), Integer.parseInt(height), Image.SCALE_SMOOTH);

    				// 새 이미지  저장하기
    	            BufferedImage newImage = new BufferedImage(Integer.parseInt(width), Integer.parseInt(height), BufferedImage.TYPE_INT_RGB);
    	            Graphics g = newImage.getGraphics();

    	            g.drawImage(resizeImage, 0, 0, null);
    	            g.dispose();

    	            ImageIO.write(newImage, "jpg", new File(rootPath));

    				return fileNm;
    			} else {
    				return "";
    			}
			} catch (Exception e) {
				
			}

			return "";
		}
	}
	
	/* 파일업로드 여러개 */
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
