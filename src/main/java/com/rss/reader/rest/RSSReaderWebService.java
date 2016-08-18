package com.rss.reader.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.rss.reader.dto.NewsDTO;
import com.rss.reader.rest.entitities.NewsExposedEntity;

@Path("/displayNews")
public class RSSReaderWebService {

	public final static Logger logger = Logger
			.getLogger(RSSReaderWebService.class);

	@GET
	@Path("/newsCompleteInfo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response parseRSSFeed() {

		NewsPersistedEntityToDTOConvertor newsPersistedEntityToDTOConvertor = 
				new NewsPersistedEntityToDTOConvertor();
		List<NewsDTO> newsDTOs = newsPersistedEntityToDTOConvertor.convert();
		List<NewsExposedEntity> newsExposedEntities = new ArrayList<NewsExposedEntity>();

		for (NewsDTO newsDTO : newsDTOs) {
			newsExposedEntities.add(new NewsExposedEntity(newsDTO.getTitle(),
					newsDTO.getLink(), newsDTO.getDescription(), newsDTO
							.getPublishingDate()));
		}
		return Response.status(Status.OK).entity(newsExposedEntities).build();
	}
}
