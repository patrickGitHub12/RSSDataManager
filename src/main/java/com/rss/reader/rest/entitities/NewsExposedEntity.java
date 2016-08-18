package com.rss.reader.rest.entitities;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "NewsExposedEntity")
public class NewsExposedEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private String title;
	private String link;
	private String description;
	private Date publishingDate;
	
	public NewsExposedEntity(String title, String link, String description, Date publishingDate){
		
		this.title= title;
		this.link = link;
		this.description = description;
		this.publishingDate =  publishingDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPublishingDate() {
		return publishingDate;
	}

	public void setPublishingDate(Date publishingDate) {
		this.publishingDate = publishingDate;
	}
	
}
