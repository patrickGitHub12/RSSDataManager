package com.rss.reader.persistence.entities;

public class CategoryPersistedEntity {

	String name;

	public CategoryPersistedEntity(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
