package com.tosok.user.Until;

public class SearchCriteria extends Criteria {

	private int seq = 0;
	private String id = "";
	private String searchType = "";
	private String keyword = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "SearchCriteria [seq=" + seq + ", id=" + id + ", searchType=" + searchType + ", keyword=" + keyword
				+ ", getId()=" + getId() + ", getSeq()=" + getSeq() + ", getSearchType()=" + getSearchType()
				+ ", getKeyword()=" + getKeyword() + ", getPage()=" + getPage() + ", getPerPageNum()=" + getPerPageNum()
				+ ", getPageStart()=" + getPageStart() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
