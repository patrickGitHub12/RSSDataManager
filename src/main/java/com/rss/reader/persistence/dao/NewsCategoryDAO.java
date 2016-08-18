package com.rss.reader.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.rss.reader.persistence.entities.CategoryPersistedEntity;
import com.rss.reader.persistence.utils.RSSReaderGetDBConnection;

public class NewsCategoryDAO implements RSSReaderEntitiCategoryDAO<CategoryPersistedEntity> {

	private Connection conn;

	public NewsCategoryDAO(Connection conn) {
		this.conn = conn;
	}

	public void saveCategory(CategoryPersistedEntity category) throws SQLException {
		StringBuilder sqlInsertCategory = new StringBuilder();
		sqlInsertCategory
				.append("INSERT INTO NEWS_CATEGORIES (CategoryName) VALUES(?)");

		if (conn.isClosed()) {
			conn = RSSReaderGetDBConnection.getInstance().getPooledConnection();
		}

		PreparedStatement insertNewsCategoryStatement = conn
				.prepareStatement(sqlInsertCategory.toString());
		insertNewsCategoryStatement.setString(1, category.getName());
		insertNewsCategoryStatement.executeUpdate();
		insertNewsCategoryStatement.close();
		conn.close();
	}

	public int getCategoryId(CategoryPersistedEntity category) throws SQLException {
		StringBuilder sqlGetCategoryByName = new StringBuilder();
		sqlGetCategoryByName
				.append("SELECT CategoryId from NEWS_CATEGORIES WHERE CategoryName = ?");
		if (conn.isClosed()) {
			conn = RSSReaderGetDBConnection.getInstance().getPooledConnection();
		}

		PreparedStatement getNewsCategoryIdStatement = conn
				.prepareStatement(sqlGetCategoryByName.toString());
		getNewsCategoryIdStatement.setString(1, category.getName());
		ResultSet rs = getNewsCategoryIdStatement.executeQuery();
		if (rs.next()) {
			return rs.getInt("CategoryId");
		}
		getNewsCategoryIdStatement.close();
		conn.close();
		return -1;
	}

	public boolean isCategoryAvailable(CategoryPersistedEntity category) throws SQLException {
		StringBuilder sqlGetCategoryByName = new StringBuilder();
		sqlGetCategoryByName
				.append("SELECT CategoryId from NEWS_CATEGORIES WHERE CategoryName = ?");
		if (conn.isClosed()) {
			conn = RSSReaderGetDBConnection.getInstance().getPooledConnection();
		}

		PreparedStatement getNewsCategoryIdStatement = conn
				.prepareStatement(sqlGetCategoryByName.toString());
		getNewsCategoryIdStatement.setString(1, category.getName());
		ResultSet rs = getNewsCategoryIdStatement.executeQuery();
		if (rs.next()) {
			return true;
		}
		getNewsCategoryIdStatement.close();
		conn.close();
		return false;
	}
}
