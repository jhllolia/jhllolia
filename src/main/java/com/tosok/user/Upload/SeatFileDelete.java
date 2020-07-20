package com.tosok.user.Upload;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.tosok.user.VO.ProductVO;

@Component("SeatFileDelete")
public class SeatFileDelete {
	protected static Log log = LogFactory.getLog(SeatFileDelete.class);

	public static String tableFileDelete( ProductVO vo, HttpServletRequest request ) throws Exception {
		HttpSession session = request.getSession();
		String rootPath = "";

		try {

			String Folder[] = { "table", "logo" };

			log.info("================ File Delete START ================");

			for (int i = 0; i < Folder.length; i++) {
				rootPath = session.getServletContext().getRealPath("/resources/upload/" + Folder[i] + "/" + vo.getPRODUCT_SEQ());

				File hasFolder = new File(rootPath);
				if(hasFolder.exists()) {
					File[] file = hasFolder.listFiles();

					for (int j = 0; j < file.length; j++) {
	    				log.info("Delete FileName : " + file[j].getName());

	    				file[j].delete();
					}
				}
			}

			log.info("================ File Delete END ==================");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
}
