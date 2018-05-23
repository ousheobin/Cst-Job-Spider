package com.tentcoo.CstSpider;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class GetData {
	
	private static SimpleDateFormat sdp = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public static CstWebArticle getArticle(int pageId) throws IOException {
		CstWebArticle article = new CstWebArticle();
		String pageUrl = "http://cst.gcu.edu.cn/demanditem.aspx?id="+pageId;
		article.setSourceUrl(pageUrl);
		Document page = Jsoup.connect(pageUrl).get();
		Element titleElement = page.getElementById("divt");
		Elements dateElements = page.getElementsByClass("list_top_time");
		article.setTitle(titleElement.text());
		if(!dateElements.isEmpty()) {
			try {
				article.setPostDate(sdp.parse(dateElements.get(0).text().replaceAll("日期：", "")));
			} catch (Exception e) {
				article.setPostDate(new Date());
			}
		}else {
			article.setPostDate(new Date());
		}
		Elements divs = page.getElementsByTag("div");
		Iterator<Element> divIter = divs.iterator();
		while (divIter.hasNext()) {
			Element element = divIter.next();
			if (element.hasClass("content")) {
				article.setContent(element.html().replaceAll("<o:p>[a-zA-Z0-9&;]*</o:p>", "").replaceAll("a href=\"/", "a href=\"http://cst.gcu.edu.cn/").replaceAll("\"/upload/", "\"http://cst.gcu.edu.cn/upload/"));
				break;
			}
		}
		return article;
	}

}
