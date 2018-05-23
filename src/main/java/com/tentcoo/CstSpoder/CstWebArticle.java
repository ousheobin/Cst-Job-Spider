package com.tentcoo.CstSpoder;

import java.util.Date;

public class CstWebArticle {

	private String title;
	private String content;
	private String sourceUrl;
	private Date postDate;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	@Override
	public String toString() {
		return "CstWebArticle [title=" + title + ", sourceUrl=" + sourceUrl + ", postDate="
				+ postDate + "]";
	}

}
