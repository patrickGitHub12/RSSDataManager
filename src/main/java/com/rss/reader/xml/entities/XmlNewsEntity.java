package com.rss.reader.xml.entities;

import java.util.Date;

public class XmlNewsEntity {

	private String title;
	private String link;
	private String guid;
	private String description;
	private String categoryName;
	private Date publishingDate;
	private String enclosure;

	public XmlNewsEntity(String title, String link, String guid, String description,
			String categoryName, Date publishingDate, String enclosure) {

		this.title = title;
		this.link = link;
		this.guid = guid;
		this.description = description;
		this.categoryName = categoryName;
		this.publishingDate = publishingDate;
		this.enclosure = enclosure;

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

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Date getPublishingDate() {
		return publishingDate;
	}

	public void setPublishingDate(Date publishingDate) {
		this.publishingDate = publishingDate;
	}

	public String getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}
}
