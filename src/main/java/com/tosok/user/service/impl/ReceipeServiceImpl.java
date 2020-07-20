package com.tosok.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tosok.user.DAO.ReceipeDAO;
import com.tosok.user.Until.Criteria;
import com.tosok.user.VO.CommentVO;
import com.tosok.user.VO.ReceipeVO;
import com.tosok.user.service.ReceipeService;

@Service("ReceipeService")
public class ReceipeServiceImpl implements ReceipeService {	

    @Resource(name="ReceipeDAO")
    private ReceipeDAO receipeDao;

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


}