package com.rss.reader.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rss.reader.persistence.entities.NewsPersistedEntity;
import com.rss.reader.persistence.utils.RSSReaderGetDBConnection;

public class NewsDAO implements RSSReaderEntityDAO<NewsPersistedEntity> {

	private Connection conn;

	public NewsDAO(Connection conn) {
		this.conn = conn;
	}

	public void saveEntity(NewsPersistedEntity newsEntity) throws SQLException {
		StringBuilder sqlInsertNews = new StringBuilder();
		sqlInsertNews
				.append("INSERT INTO NEWS (NewsGuid, NewsTitle, NewsLink, NewsDescription,"
						+ " NewsCategoryId, NewsPublishingDate, NewsEnclosure) VALUES(?,?,?,?,?,?,?)");

		if (conn.isClosed()) {
			conn = RSSReaderGetDBConnection.getInstance().getPooledConnection();
		}

		PreparedStatement insertNewsStatement = conn
				.prepareStatement(sqlInsertNews.toString());
		insertNewsStatement.setString(1, newsEntity.getGuid());
		insertNewsStatement.setString(2, newsEntity.getTitle());
		insertNewsStatement.setString(3, newsEntity.getLink());
		insertNewsStatement.setString(4, newsEntity.getDescription());
		insertNewsStatement.setInt(5, newsEntity.getCategoryId());
		insertNewsStatement.setDate(6, new java.sql.Date(newsEntity
				.getPublishingDate().getTime()));
		insertNewsStatement.setString(7, newsEntity.getEnclosure());
		insertNewsStatement.executeUpdate();
		insertNewsStatement.close();
		conn.close();

	}

	public void updateEntity(NewsPersistedEntity entity) throws SQLException {
		StringBuilder sqlUpdateNews = new StringBuilder();
		sqlUpdateNews
				.append("UPDATE NEWS SET NewsTitle=?, NewsLink=?, NewsDescription=?, NewsCategoryId=?, "
						+ "NewsPublishingDate=?, NewsEnclosure=? WHERE NewsGuid=?");

		if (conn.isClosed()) {
			conn = RSSReaderGetDBConnection.getInstance().getPooledConnection();
		}

		PreparedStatement insertNewsStatement = conn
				.prepareStatement(sqlUpdateNews.toString());
		insertNewsStatement.setString(1, entity.getTitle());
		insertNewsStatement.setString(2, entity.getLink());
		insertNewsStatement.setString(3, entity.getDescription());
		insertNewsStatement.setInt(4, entity.getCategoryId());
		insertNewsStatement.setDate(5, new java.sql.Date(entity
				.getPublishingDate().getTime()));
		insertNewsStatement.setString(6, entity.getEnclosure());
		insertNewsStatement.setString(7, entity.getGuid());
		insertNewsStatement.executeUpdate();
		insertNewsStatement.close();
		conn.close();
	}

	public boolean isNewsAvailable(NewsPersistedEntity news) throws SQLException {
		StringBuilder sqlGetNewsByGuid = new StringBuilder();
		sqlGetNewsByGuid
				.append("SELECT * from NEWS WHERE NewsGuid = ?");
		if (conn.isClosed()) {
			conn = RSSReaderGetDBConnection.getInstance().getPooledConnection();
		}

		PreparedStatement getNewsStatement = conn
				.prepareStatement(sqlGetNewsByGuid.toString());
		getNewsStatement.setString(1, news.getGuid());
		ResultSet rs = getNewsStatement.executeQuery();
		if (rs.next()) {
			return true;
		}
		
		getNewsStatement.close();
		conn.close();
		return false;
	}
	public List<NewsPersistedEntity> getEntities() throws SQLException{
		List<NewsPersistedEntity> newsStored = new ArrayList<NewsPersistedEntity>();
		StringBuilder sqlGetNews = new StringBuilder();
		sqlGetNews
				.append("SELECT * from NEWS");
		if (conn.isClosed()) {
			conn = RSSReaderGetDBConnection.getInstance().getPooledConnection();
		}

		PreparedStatement getNewsStatement = conn
				.prepareStatement(sqlGetNews.toString());
		ResultSet rs = getNewsStatement.executeQuery();
		while (rs.next()) {
			NewsPersistedEntity news = new NewsPersistedEntity(rs.getString("NewsTitle"),
					rs.getString("NewsLink"), rs.getString("NewsGuid"),
					rs.getString("NewsDescription"),
					rs.getInt("NewsCategoryId"),
					rs.getDate("NewsPublishingDate"),
					rs.getString("NewsEnclosure"));
			newsStored.add(news);
		}
		
		getNewsStatement.close();
		conn.close();
		return newsStored;
	}
}
