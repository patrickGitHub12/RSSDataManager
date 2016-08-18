package com.rss.reader.persistence.dao;

import java.sql.SQLException;

public interface RSSReaderEntitiCategoryDAO<T> {

	void saveCategory(T category) throws SQLException;
	
	int getCategoryId(T category) throws SQLException;
	
	boolean isCategoryAvailable(T category) throws SQLException;

}
