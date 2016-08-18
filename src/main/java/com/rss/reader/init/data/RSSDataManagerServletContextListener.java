package com.rss.reader.init.data;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.rss.reader.persistence.dao.NewsCategoryDAO;
import com.rss.reader.persistence.dao.NewsDAO;
import com.rss.reader.persistence.entities.CategoryPersistedEntity;
import com.rss.reader.persistence.entities.NewsPersistedEntity;
import com.rss.reader.persistence.utils.RSSReaderGetDBConnection;
import com.rss.reader.xml.entities.ParsedXMLData;
import com.rss.reader.xml.entities.XmlNewsEntity;
import com.rss.reader.xml.processors.RSSReaderXMLParser;
import com.rss.reader.xml.processors.RSSReaderXMLParserFactory;

@WebListener
public class RSSDataManagerServletContextListener implements
		ServletContextListener {
	
	public final static Logger logger = Logger.getLogger(RSSDataManagerServletContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		String rssFeedURL = RSSReaderGetFeedURL.getInstance().getRssFeedUrl();
		RSSReaderXMLParser rssReaderXMLParser = RSSReaderXMLParserFactory
				.produceRSSReaderXMLParser(rssFeedURL);
		ParsedXMLData parsedXMLData = rssReaderXMLParser
				.rssFeedEntitiesDataLoader();
		List<XmlNewsEntity> parsedXMLNews = parsedXMLData.getXmlNews();

		try {
			if (CollectionUtils.isNotEmpty(parsedXMLNews)) {

				NewsDAO newsDAO = new NewsDAO(RSSReaderGetDBConnection
						.getInstance().getPooledConnection());
				NewsCategoryDAO newsCategoryDAO = new NewsCategoryDAO(
						RSSReaderGetDBConnection.getInstance()
								.getPooledConnection());
				for (XmlNewsEntity xmlNewsEntity : parsedXMLNews) {
					
					int categoryId = -1;
					CategoryPersistedEntity category = new CategoryPersistedEntity(
							xmlNewsEntity.getCategoryName());
					if (newsCategoryDAO.isCategoryAvailable(category)) {
						categoryId = newsCategoryDAO.getCategoryId(category);
						logger.info("Category " + xmlNewsEntity.getCategoryName()
								+ " already exists in DB, will be extracted the category id.");
					} else {
						newsCategoryDAO.saveCategory(category);
						categoryId = newsCategoryDAO.getCategoryId(category);
					}
					if (categoryId != -1) {
						NewsPersistedEntity news = new NewsPersistedEntity(xmlNewsEntity.getTitle(),
								xmlNewsEntity.getLink(),
								xmlNewsEntity.getGuid(),
								xmlNewsEntity.getDescription(), categoryId,
								xmlNewsEntity.getPublishingDate(),
								xmlNewsEntity.getEnclosure());
						
						if (newsDAO.isNewsAvailable(news) == false) {
							newsDAO.saveEntity(news);
						} else {
							logger.info("News " + news.getGuid()
									+ " already exists in DB, will be updated.");
							newsDAO.updateEntity(news);
						}
					}
				}
			}
		} catch (SQLException e) {
			logger.error("Issues at inserting parsed data in DB : " + e.getMessage());
		}

	}
}
