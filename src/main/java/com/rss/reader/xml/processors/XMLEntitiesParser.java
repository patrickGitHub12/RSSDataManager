package com.rss.reader.xml.processors;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.log4j.Logger;

import com.rss.reader.xml.entities.XmlNewsEntity;
import com.rss.reader.xml.entities.ParsedXMLData;

public class XMLEntitiesParser implements RSSReaderXMLParser {

	public final static Logger logger = Logger.getLogger(XMLEntitiesParser.class);
	
	private String rssFeedURLAdress;
	private InputStream rssFeedInputStream;
	private XMLStreamReader rssFeedParserStreamReader;

	public XMLEntitiesParser(String rssFeedURLAdress) {
		this.rssFeedURLAdress = rssFeedURLAdress;
		initXMLParser();
	}

	public void initXMLParser() {
		try {
			URL rssURL = new URL(this.rssFeedURLAdress);
			URLConnection urlConnection = rssURL.openConnection();
			rssFeedInputStream = urlConnection.getInputStream();
			XMLInputFactory factory = XMLInputFactory.newInstance();
			factory.setProperty(XMLInputFactory.IS_COALESCING, true);
			rssFeedParserStreamReader = factory
					.createXMLStreamReader(rssFeedInputStream);
		} catch (MalformedURLException e) {
			logger.error("Invalid rss feed url : " + rssFeedURLAdress + e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (XMLStreamException e) {
			logger.error("Possible invalid xml data: " + e.getMessage());
		}

	}

	public ParsedXMLData rssFeedEntitiesDataLoader() {
		ParsedXMLData parsedXMLData = new ParsedXMLData();
		try {
			int event = rssFeedParserStreamReader.next();
			String nodeName = rssFeedParserStreamReader.hasName() ? rssFeedParserStreamReader
					.getLocalName() : XMLNewsEntityConst.NOT_START_OR_END_ELEMENT;
			while (rssFeedParserStreamReader.hasNext()) {
				if (XMLStreamConstants.START_ELEMENT == event
						&& nodeName.equals(XMLNewsEntityConst.NEWS)) {

					XmlNewsEntity xmlNewsEntity = buildXMLNewsEntity(event,
							nodeName);
					parsedXMLData.getXmlNews().add(xmlNewsEntity);
				}
				if (XMLStreamConstants.END_DOCUMENT == event) {
					break;
				} else {
					event = rssFeedParserStreamReader.next();
					nodeName = rssFeedParserStreamReader.hasName() ? rssFeedParserStreamReader
							.getLocalName() : XMLNewsEntityConst.NOT_START_OR_END_ELEMENT;
				}
			}
			rssFeedParserStreamReader.close();
			rssFeedInputStream.close();
			
		} catch (XMLStreamException e) {
			logger.error("Possible invalid xml data: " + e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} 

		return parsedXMLData;
	}
	
	private XmlNewsEntity buildXMLNewsEntity(int event, String nodeName)
			throws XMLStreamException {

		String newsTitle = null;
		String newsLink = null;
		String newsGuid = null;
		String newsDescription = null;
		String newsCategory = null;
		Date newsPublishingDate = null;
		String newsEnclosure = null;

		while (!(XMLStreamConstants.END_ELEMENT == event && nodeName
				.equals(XMLNewsEntityConst.NEWS))) {
			if (XMLStreamConstants.START_ELEMENT == event) {
				switch (rssFeedParserStreamReader.getLocalName()) {
				case XMLNewsEntityConst.TITLE:
					newsTitle = parseNewsPropFromNodeVal(event);
					break;
				case XMLNewsEntityConst.LINK:
					newsLink = parseNewsPropFromNodeVal(event);
					break;
				case XMLNewsEntityConst.GUID:
					String newsCompleteGuid = parseNewsPropFromNodeVal(event);
					newsGuid = newsCompleteGuid.substring(newsCompleteGuid
							.lastIndexOf("/") + 1);
					break;
				case XMLNewsEntityConst.DESCRIPTION:
					newsDescription = parseNewsPropFromNodeVal(event);
					break;
				case XMLNewsEntityConst.CATEGORY:
					newsCategory = parseNewsPropFromNodeVal(event);
					break;

				case XMLNewsEntityConst.PUBLISHING_DATE:
					DateFormat formatter = new SimpleDateFormat(
							"EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
					String dateString = parseNewsPropFromNodeVal(event);
					try {
						newsPublishingDate = (Date) formatter
								.parse(dateString);
					} catch (ParseException e) {
						logger.error("Could not parse data from " + dateString + e.getMessage());
					}
					break;
				case XMLNewsEntityConst.ENCLOSURE:
					newsEnclosure = parseNewsPropFromNodeAttr(event, "url");
					break;
				}
			}
			event = rssFeedParserStreamReader.next();
			nodeName = rssFeedParserStreamReader.hasName() ? rssFeedParserStreamReader
					.getLocalName() : XMLNewsEntityConst.NOT_START_OR_END_ELEMENT;

		}
		return new XmlNewsEntity(newsTitle, newsLink, newsGuid,
				newsDescription, newsCategory, newsPublishingDate,
				newsEnclosure);
	}

	private String parseNewsPropFromNodeVal(int event) throws XMLStreamException {
		while (event != XMLStreamConstants.END_ELEMENT) {
			if (event == XMLStreamConstants.CHARACTERS) {
				return (rssFeedParserStreamReader.getText());
			}
			event = rssFeedParserStreamReader.next();
		}
		return null;
	}
	
	private String parseNewsPropFromNodeAttr(int event, String attr)
			throws XMLStreamException {

		for (int i = 0, n = rssFeedParserStreamReader.getAttributeCount(); i < n; ++i) {
			QName name = rssFeedParserStreamReader.getAttributeName(i);
			if (attr.equals(name.getLocalPart())) {
				return rssFeedParserStreamReader.getAttributeValue(i);
			}
		}

		return null;
	}
}
