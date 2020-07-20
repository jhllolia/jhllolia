package com.tosok.user.Until;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.tosok.user.VO.ProductVO;
import com.tosok.user.VO.ReviewVO;
import com.tosok.user.service.ReserveService;

@Component("productSellList")
public class ProductSellList {

	@Autowired
	private ReserveService reserveService;

	public List<Map<String, Object>> listProductParam(ProductVO vo, ReviewVO review) {
    	Gson gson = new Gson();
    	List<Map<String, Object>> list = null;
		JsonArray data = new JsonArray();
    	JsonParser parser = new JsonParser();

    	vo.setPRODUCT_STATE("Y");												// 판매 중
		List<ProductVO> arr = reserveService.selectMainPageProductList(vo);		// 판매 중 Product

		for (int i = 0; i < arr.size(); i++) {
			JsonArray sub = new JsonArray();
			JsonObject obj = new JsonObject();

			int seq = arr.get(i).getPRODUCT_SEQ();								// 번호
			String sub_Info = arr.get(i).getPRODUCT_SUB_INFO();					// Product 카테고리
	    	JsonArray str = (JsonArray) parser.parse(sub_Info);					// 

	    	review.setPRODUCT_NUM(Integer.toString(seq));						// 해당 제품 리뷰번호
			ReviewVO count = reserveService.selectProductReviewCount(review);	// 리뷰 개수 / 평균

			obj.addProperty("PRODUCT_SEQ", seq);
			obj.addProperty("PRODUCT_STATE", arr.get(i).getPRODUCT_STATE());
			obj.addProperty("PRODUCT_SALE_MODE", arr.get(i).getPRODUCT_SALE_MODE());
			obj.addProperty("PRODUCT_TITLE", arr.get(i).getPRODUCT_TITLE());
			obj.addProperty("PRODUCT_SELL", arr.get(i).getPRODUCT_SELL());
			obj.addProperty("PRODUCT_MIN_IMG", arr.get(i).getPRODUCT_MIN_IMG());
			obj.addProperty("PRODUCT_MAX_IMG", arr.get(i).getPRODUCT_MAX_IMG());
			obj.addProperty("REVIEW_AVG", count.getAVG_COUNT());
			obj.addProperty("REVIEW_COUNT", count.getREV_COUNT());

			/*
				obj.addProperty("PRODUCT_MIN_TIT", arr.get(i).getPRODUCT_MIN_TIT());
				obj.addProperty("PRODUCT_MAX_TIT", arr.get(i).getPRODUCT_MAX_TIT());
			*/

			int sell_count = 0;

			for (int j = 0; j < str.size(); j++) {
	    		JsonObject unit = new JsonObject();
	    		JsonObject info = (JsonObject) str.get(j);
	    		String sell = info.get("sell").getAsString();

	    		unit.addProperty("NUM", info.get("num").getAsString());
	    		unit.addProperty("NAME", info.get("name").getAsString());
	    		unit.addProperty("OPTION", info.get("option").getAsString());
	    		unit.addProperty("PRICE", info.get("price").getAsString());
	    		unit.addProperty("SALE", info.get("sale").getAsString());
	    		unit.addProperty("SELL", sell);

	    		if(sell.equals("Y")) {
	    			++sell_count;
	    		}

	    		sub.add(unit);
			}

			obj.addProperty("PRODUCT_UPLOAD", sell_count);
	    	obj.add("PRODUCT_SUB_INFO", sub);

	    	data.add(obj);
		}

		list = gson.fromJson(data, new TypeToken<List<Map<String, Object>>>() {}.getType());

		return list;
	}

}