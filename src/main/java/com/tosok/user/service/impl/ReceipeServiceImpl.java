package com.tosok.user.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tosok.user.DAO.ReceipeDAO;
import com.tosok.user.Until.Criteria;
import com.tosok.user.Upload.SeatFileInsert;
import com.tosok.user.VO.CommentVO;
import com.tosok.user.VO.ImageVO;
import com.tosok.user.VO.ReceipeVO;
import com.tosok.user.service.ReceipeService;

@Service("ReceipeService")
public class ReceipeServiceImpl implements ReceipeService {	

    @Resource(name="ReceipeDAO")
    private ReceipeDAO receipeDao;
    
    @Autowired
    private SeatFileInsert seatFileInsert;

	@Override
	public int updateGallaryData(ImageVO vo) {
		int result = 0;
		
		if("Y".equals(vo.getDel_yn())) {
			
			ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = sra.getRequest();
			HttpSession session = request.getSession();

			String rootPath = session.getServletContext().getRealPath("/resources/upload/gallary/");
			
			File webFile = new File(rootPath + vo.getWeb_image());
			File mobFile = new File(rootPath + vo.getMob_image());

			webFile.delete();	// 이미지 삭제
			mobFile.delete();	// 이미지 삭제
			
			// 이미지 삭제
			result = receipeDao.deleteGallaryData(vo);
		} else {
			
			MultipartFile file = vo.getFiles();
			
			String web = seatFileInsert.insertFileProc(vo.getPcWidth(), vo.getPcHight(), file);
			String mob = seatFileInsert.insertFileProc(vo.getMobWidth(), vo.getMobHight(), file);

			if(!"".equals(web) && !"".equals(mob)) {
				vo.setWeb_image(web);
				vo.setMob_image(mob);
			}
			
			if(!"".equals(vo.getImg_idx())) {
				result = 1;
				receipeDao.updateGallaryData(vo);
			} else {

				int ordCnt = receipeDao.selectGallaryCnt(vo);
				vo.setOrd(Integer.toString(ordCnt));

				result = receipeDao.insertGallaryData(vo);
			}
		}

		return result;
	}
    
	@Override
	public List<ReceipeVO> listReceipeDao(Criteria cri) {
		return receipeDao.listReceipeDao(cri);
	}
	
	@Override
	public int selectReceipeListCnt(Criteria cri) {
		return receipeDao.selectReceipeListCnt(cri);
	}

	@Override
	public int writeReceipeDao(Map<String, Object> paramMap) {
		return receipeDao.writeReceipeDao(paramMap);
	}

	@Override
	public int updateReceipeDao(Map<String, Object> paramMap) {
		return receipeDao.updateReceipeDao(paramMap);
	}

	@Override
	public ReceipeVO viewReceipeDao(Map<String, Object> paramMap) {
		return receipeDao.viewReceipeDao(paramMap);
	}

	@Override
	public Object prevReceipeDao(String intSeq) {
		return receipeDao.prevReceipeDao(intSeq);
	}

	@Override
	public Object nextReceipeDao(String intSeq) {
		return receipeDao.nextReceipeDao(intSeq);
	}

	@Override
	public int deleteReceipeDao(Map<String, Object> paramMap) {
		return receipeDao.deleteReceipeDao(paramMap);
	}

	@Override
	public int insertBlog(ReceipeVO vo) {
		return receipeDao.insertBlog(vo);
	}

	@Override
	public int insertComment(Map<String, Object> paramMap) {
		return receipeDao.insertComment(paramMap);
	}

	@Override
	public List<CommentVO> selectReceipeComment(Map<String, Object> paramMap) {
        List<CommentVO> boardReplyList = receipeDao.selectReceipeComment(paramMap);

        List<CommentVO> boardReplyListParent = new ArrayList<CommentVO>();	// parent
        List<CommentVO> boardReplyListChild = new ArrayList<CommentVO>();	// child
        List<CommentVO> newBoardReplyList = new ArrayList<CommentVO>();		// all

        for(CommentVO vo : boardReplyList) {
        	if(vo.getC_depth() == 0) {
        		boardReplyListParent.add(vo);
            } else {
                boardReplyListChild.add(vo);
            }
		}

        for(CommentVO boardReplyParent: boardReplyListParent) {
            newBoardReplyList.add(boardReplyParent);
            for(CommentVO boardReplyChild: boardReplyListChild) {
                if(boardReplyParent.getC_Seq() == boardReplyChild.getC_parent_seq()) {
                    newBoardReplyList.add(boardReplyChild);
                }
            }
        }

        return newBoardReplyList;
	}

	@Override
	public int selectReceipeCommentCnt(String intSeq) {
		return receipeDao.selectReceipeCommentCnt(intSeq);
	}

	@Override
	public int deleteComment(Map<String, Object> paramMap) {
		return receipeDao.deleteComment(paramMap);
	}

	@Override
	public int updateComment(Map<String, Object> paramMap) {
		return receipeDao.updateComment(paramMap);
	}

	@Override
	public List<CommentVO> selectDelComment(Map<String, Object> paramMap) {
		return receipeDao.selectDelComment(paramMap);
	}

	@Override
	public void deleteChildComment(int child) {
		receipeDao.deleteChildComment(child);
	}

	@Override
	public List<ImageVO> selectGallayTotalImage(ImageVO vo) {
		return receipeDao.selectGallayTotalImage(vo);
	}

	@Override
	public void listGallaryOrdChange(HttpServletRequest req) {
		JsonParser parser = new JsonParser();
		String str = req.getParameter("arr");
    	JsonArray arr = (JsonArray) parser.parse(str);
    	
    	for (int i = 0; i < arr.size(); i++) {
			ImageVO vo = new ImageVO();
    		JsonObject data = (JsonObject) arr.get(i);

    		vo.setImg_idx(data.get("idx").getAsString());
    		vo.setTitle(data.get("title").getAsString());
    		vo.setOrd(data.get("ord").getAsString());

    		receipeDao.updateGallaryData(vo);
    	}
	}

}