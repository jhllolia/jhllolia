package com.tosok.user.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tosok.user.Until.PageMaker;
import com.tosok.user.Until.SearchCriteria;
import com.tosok.user.VO.CommentVO;
import com.tosok.user.service.ReceipeService;

@Controller
public class ReceipeController {
	protected Log log = LogFactory.getLog(ReceipeController.class);

    @Autowired
    private ReceipeService ReceipeService;

    /* ========== SPECIAL PROJECT ========== */
	@RequestMapping(value = "/bbs/recipe", method = RequestMethod.GET)
	public ModelAndView recipe( @ModelAttribute("scri") SearchCriteria scri, Model model) {
		ModelAndView mav = new ModelAndView();
		PageMaker pageMaker = new PageMaker();

		pageMaker.setCri(scri);
		pageMaker.setTotalCount(ReceipeService.selectReceipeListCnt(scri));

		model.addAttribute("list", ReceipeService.listReceipeDao(scri));
		model.addAttribute("pageMaker", pageMaker);

		mav.setViewName("/bbs/recipe");
		return mav;
	}

    /* ========== SPECIAL PROJECT ========== */
	@RequestMapping(value = "/update/{intSeq}")
	public ModelAndView view_update(@RequestParam Map<String, Object> paramMap, @PathVariable String intSeq) {
		ModelAndView mav = new ModelAndView();
		paramMap.put("intSeq", intSeq);

		if(intSeq == "") {
			mav.setViewName("/bbs/recipe"); 
			mav.addObject("msg","fail"); 
		} else {
			mav.addObject("view", ReceipeService.viewReceipeDao(paramMap));
			mav.setViewName("/bbs/recipe_update"); 
		}

		return mav;
	}
	
	@RequestMapping(value = "/delete/bbs")
	@ResponseBody
	public Object view_delete(@RequestParam Map<String, Object> paramMap) {
		Map<String, Object> retVal = new HashMap<String, Object>();

        List<CommentVO> c_delete = ReceipeService.selectDelComment(paramMap);
        if(c_delete != null && !c_delete.equals("")) {
        	for (int i = 0; i < c_delete.size(); i++) {
        		int child = c_delete.get(i).getC_Seq();
    			ReceipeService.deleteChildComment(child);
    		}
        }

		int result = ReceipeService.deleteReceipeDao(paramMap);
    	if(result == 1) {
			retVal.put("code", "OK");
			retVal.put("message", "삭제 되었습니다.");
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "실패했습니다. 다시 시도 해주세요");
		}

		return retVal;
	}

	@RequestMapping(value = "/bbs/recipe_write")
	public String recipeWrite() {
		return "/bbs/recipe_write";
	}
	
	@RequestMapping(value = "/bbs/recipe_view")
	public String recipeView(@RequestParam Map<String, Object> paramMap, Model model) {
		String intSeq =  (String) paramMap.get("intSeq");

		model.addAttribute("comment", ReceipeService.selectReceipeComment(paramMap));
		model.addAttribute("count", ReceipeService.selectReceipeCommentCnt(intSeq));
		model.addAttribute("view", ReceipeService.viewReceipeDao(paramMap));
		model.addAttribute("prev", ReceipeService.prevReceipeDao(intSeq));
		model.addAttribute("next", ReceipeService.nextReceipeDao(intSeq));

		return "/bbs/recipe_view";
	}

	@RequestMapping(value="/recipe_write", method=RequestMethod.POST)
	@ResponseBody
	public Object recipe_write(@RequestParam Map<String, Object> paramMap, Model model) {
		Map<String, Object> retVal = new HashMap<String, Object>();

		String recipe_Content = (String) paramMap.get("content");
		Document doc = Jsoup.parseBodyFragment(recipe_Content);
		Elements imgs = doc.getElementsByTag("img");
		Elements iframe_title = doc.getElementsByTag("iframe"); // 동영상

		String subContent = Jsoup.parse(recipe_Content).text();
		String recipe_Title = "";
		String thumbnail = "";
	
		if(imgs.size() > 0) {
			recipe_Title = imgs.get(0).attr("src");	
		} else if(iframe_title.size() > 0) {
			String youtube = thumbnail.substring(thumbnail.lastIndexOf('/') + 1, thumbnail.length());
			thumbnail = iframe_title.get(0).attr("src");
			recipe_Title = "http://img.youtube.com/vi/" + youtube + "/0.jpg";
		} else {
			recipe_Title = "";	
		}

		subContent = subContent.replaceAll("'", "");

		paramMap.put("recipe_Category", "후기");
		paramMap.put("user_Id", paramMap.get("id"));
		paramMap.put("recipe_Subject", paramMap.get("subject"));
		paramMap.put("recipe_Title", recipe_Title);
		paramMap.put("recipe_SubTitle", subContent);
		paramMap.put("recipe_Content", recipe_Content);
		paramMap.put("r_state", "Y");

		int result = ReceipeService.writeReceipeDao(paramMap);
		if(result > 0) {
			retVal.put("code", "OK");
			retVal.put("message", "등록되었습니다.");
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "실패했습니다. 다시 시도 해주세요");
		}

		return retVal;
	}

	@RequestMapping(value="/recipe_update", method=RequestMethod.POST)
	@ResponseBody
	public Object update(@RequestParam Map<String, Object> paramMap, Model model) {
		Map<String, Object> retVal = new HashMap<String, Object>();	

		String recipe_Content = (String) paramMap.get("content");
		Document doc = Jsoup.parseBodyFragment(recipe_Content);
		Elements imgs = doc.getElementsByTag("img");
		Elements iframe_title = doc.getElementsByTag("iframe"); // 동영상

		String subContent = Jsoup.parse(recipe_Content).text();
		String recipe_Title = "";
		String thumbnail = "";

		if(imgs.size() > 0) {
			recipe_Title = imgs.get(0).attr("src");	
		} else if(iframe_title.size() > 0) {
			String youtube = thumbnail.substring(thumbnail.lastIndexOf('/') + 1, thumbnail.length());
			thumbnail = iframe_title.get(0).attr("src");
			recipe_Title = "http://img.youtube.com/vi/" + youtube + "/0.jpg";
		} else {
			recipe_Title = "";	
		}

		subContent = subContent.replaceAll("'", "");

		paramMap.put("intSeq", paramMap.get("intSeq"));
		paramMap.put("recipe_Subject", paramMap.get("subject"));
		paramMap.put("recipe_Title", recipe_Title);
		paramMap.put("recipe_SubTitle", subContent);
		paramMap.put("recipe_Content", recipe_Content);

		int result = ReceipeService.updateReceipeDao(paramMap);
		if(result > 0) {
			retVal.put("code", "OK");
			retVal.put("message", "수정되었습니다.");
		} else {
			retVal.put("code", "FAIL");
			retVal.put("message", "수정에 실패했습니다.");
		}
		
		return retVal;
	}
	
	@RequestMapping(value="/comment_write", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> comment_write(@RequestParam Map<String, Object> paramMap) {
		Map<String, Object> retVal = new HashMap<String, Object>();	
		
		int result = ReceipeService.insertComment(paramMap);
		if(result > 0) {
			retVal.put("code", "OK");
			retVal.put("seq", paramMap.get("c_Seq"));
		} else {
			retVal.put("code", "FAIL");
		}

		return retVal;
	}
	
	@RequestMapping(value="/comment_update", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> comment_update(@RequestParam Map<String, Object> paramMap) {
		Map<String, Object> retVal = new HashMap<String, Object>();	
		int result = ReceipeService.updateComment(paramMap);
		if(result > 0) {
			retVal.put("code", "OK");
		} else {
			retVal.put("code", "FAIL");
		}

		return retVal;
	}
	
	@RequestMapping(value = "/c_delete/comment_num")
	public void comment_delete(@RequestParam Map<String, Object> paramMap, CommentVO vo, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");

        try {

        	List<CommentVO> c_delete = ReceipeService.selectDelComment(paramMap);
            if(c_delete !=null && !c_delete.equals("")) {
            	for (int i = 0; i < c_delete.size(); i++) {
            		int child = c_delete.get(i).getC_Seq();
        			ReceipeService.deleteChildComment(child);
        		}
            }

			int result = ReceipeService.deleteComment(paramMap);
			if(result > 0) {
				response.getWriter().print(mapper.writeValueAsString(true));
			} else {
				response.getWriter().print(mapper.writeValueAsString(false)); 
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
