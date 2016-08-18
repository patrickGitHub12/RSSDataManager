package com.rss.reader.persistence.dao;

import java.sql.SQLException;
import java.util.List;

public interface RSSReaderEntityDAO<T> {

	void saveEntity(T entity) throws SQLException;

	void updateEntity(T entity) throws SQLException;
	
	boolean isNewsAvailable(T entity) throws SQLException;

	List<T> getEntities() throws SQLException;
}
