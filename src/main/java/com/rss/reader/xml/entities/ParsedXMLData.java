package com.rss.reader.xml.entities;

import java.util.ArrayList;
import java.util.List;

public class ParsedXMLData {

	private List<XmlNewsEntity> xmlNews;
	
	public ParsedXMLData(){
		this.xmlNews = new ArrayList<XmlNewsEntity>();
	}

	public List<XmlNewsEntity> getXmlNews() {
		return xmlNews;
	}

	public void setXmlNews(List<XmlNewsEntity> xmlNews) {
		this.xmlNews = xmlNews;
	}
	
}
