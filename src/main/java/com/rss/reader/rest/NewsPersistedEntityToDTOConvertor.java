package com.rss.reader.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rss.reader.dto.NewsDTO;
import com.rss.reader.persistence.dao.NewsDAO;
import com.rss.reader.persistence.entities.NewsPersistedEntity;
import com.rss.reader.persistence.utils.RSSReaderGetDBConnection;

public class NewsPersistedEntityToDTOConvertor {

	public final static Logger logger = Logger.getLogger(NewsPersistedEntityToDTOConvertor.class);

	List<NewsDTO> convert(){
		
		List<NewsDTO> newsDTOs = new ArrayList<NewsDTO>();
		try {
			NewsDAO newsDAO = new NewsDAO(RSSReaderGetDBConnection
					.getInstance().getPooledConnection());
			for(NewsPersistedEntity newsPersistedEntity : newsDAO.getEntities()){
				
				NewsDTO newsDTO = new NewsDTO(newsPersistedEntity.getTitle(), 
						newsPersistedEntity.getLink(), newsPersistedEntity.getDescription(), 
						newsPersistedEntity.getPublishingDate());
				newsDTOs.add(newsDTO);
			}
		} catch (SQLException e) {
			logger.error("Issues in retrieving news persisted entities from db: " + e.getMessage());
		}
		
		
		return newsDTOs;
	}
}
