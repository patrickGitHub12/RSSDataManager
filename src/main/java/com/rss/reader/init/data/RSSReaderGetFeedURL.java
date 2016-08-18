package com.rss.reader.init.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class RSSReaderGetFeedURL {

	public final static Logger logger = Logger.getLogger(RSSReaderGetFeedURL.class);
	private static RSSReaderGetFeedURL instance = null;

	private static final String RSS_FEED_URL_KEY = "rssFeedURL";
	private static final String RSS_FEED_CONFIGURATION_FILE = "RSSFeedURLConfiguration.properties";
	private String rssFeedUrl;

	private RSSReaderGetFeedURL() {
		this.rssFeedUrl = loadRSSFeedURL(RSS_FEED_CONFIGURATION_FILE);
	}

	public static RSSReaderGetFeedURL getInstance() {
		if (instance == null) {
			instance = new RSSReaderGetFeedURL();
		}
		return instance;
	}

	private String loadRSSFeedURL(String propFile) {
		Properties properties = new Properties();
		InputStream input = null;
		String rssFeedUrl = null;

		try {
			input = getClass().getClassLoader().getResourceAsStream(propFile);
			if (input != null) {
				properties.load(input);
				rssFeedUrl = properties.getProperty(RSS_FEED_URL_KEY);
				if (rssFeedUrl == null) {
					logger.error("RSS Feed Url could not be retrieved from "
							+ RSS_FEED_CONFIGURATION_FILE + " : "
							+ RSS_FEED_URL_KEY);
				}
				input.close();
			} else {
				logger.error("Properties file " + RSS_FEED_CONFIGURATION_FILE
						+ " not found");
			}
		} catch (FileNotFoundException e) {
			logger.error("Properties file " + RSS_FEED_CONFIGURATION_FILE
					+ " not found . " + e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return rssFeedUrl;
	}

	public String getRssFeedUrl() {
		return rssFeedUrl;
	}
	
	public static void main(String []argv){
		RSSReaderGetFeedURL rssReaderGetFeedURL = RSSReaderGetFeedURL.getInstance();
		rssReaderGetFeedURL.loadRSSFeedURL(RSS_FEED_CONFIGURATION_FILE);
	}
}
