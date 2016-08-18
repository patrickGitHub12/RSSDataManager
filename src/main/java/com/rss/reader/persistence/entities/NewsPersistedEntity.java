package com.rss.reader.persistence.entities;

import java.util.Date;

public class NewsPersistedEntity {

	private String title;
	private String link;
	private String guid;
	private String description;
	private int categoryId;
	private Date publishingDate;
	private String enclosure;

	public NewsPersistedEntity(String title, String link, String guid, String description,
			int categoryId, Date publishingDate, String enclosure) {

		this.title = title;
		this.link = link;
		this.guid = guid;
		this.description = description;
		this.categoryId = categoryId;
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

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategory(int categoryId) {
		this.categoryId = categoryId;
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
