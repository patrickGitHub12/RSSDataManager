package com.rss.reader.xml.processors;

public class RSSReaderXMLParserFactory {

	public static RSSReaderXMLParser produceRSSReaderXMLParser(
			String rssFeedURLAdress) {
		return new XMLEntitiesParser(rssFeedURLAdress);
	}
}
