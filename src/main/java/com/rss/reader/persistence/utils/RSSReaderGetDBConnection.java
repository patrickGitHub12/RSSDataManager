package com.rss.reader.persistence.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class RSSReaderGetDBConnection {

	public final static Logger logger = Logger.getLogger(RSSReaderGetDBConnection.class);
	
	private static final String DATABASE_CONFIGURATION_FILE = "DatabaseConfiguration.properties";

	private static RSSReaderGetDBConnection instance = null;

	private static final String DATABASE_KEY = "database";
	private static final String HOST_KEY = "host";
	private static final String PORT_KEY = "port";
	private static final String USER_KEY = "user";
	private static final String PASSWORD_KEY = "password";
	private static MysqlDataSource dataSource;

	private RSSReaderGetDBConnection() {
		initDataSource();
	}

	public static RSSReaderGetDBConnection getInstance() {
		if (instance == null) {
			instance = new RSSReaderGetDBConnection();
		}
		return instance;
	}

	public void initDataSource() {

		Map<String, String> dataSourceConfig = loadDataSourceConfiguration
				(DATABASE_CONFIGURATION_FILE);
		dataSource = new MysqlDataSource();
		dataSource.setDatabaseName(dataSourceConfig.get(DATABASE_KEY));
		dataSource.setServerName(dataSourceConfig.get(HOST_KEY));
		dataSource.setPort(Integer.parseInt(dataSourceConfig.get(PORT_KEY).trim()));
		dataSource.setUser(dataSourceConfig.get(USER_KEY));
		dataSource.setPassword(dataSourceConfig.get(PASSWORD_KEY));

	}

	public Connection getPooledConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public Map<String, String> loadDataSourceConfiguration(String propFile) {

		Map<String, String> dataSourceConfig = new HashMap<String, String>();

		Properties properties = new Properties();
		InputStream input = null;

		try {
			input = getClass().getClassLoader().getResourceAsStream(propFile);
			if (input != null) {
				properties.load(input);

				String db = properties.getProperty(DATABASE_KEY);
				String host = properties.getProperty(HOST_KEY);
				String port = properties.getProperty(PORT_KEY);
				String user = properties.getProperty(USER_KEY);
				String password = properties.getProperty(PASSWORD_KEY);

				notifyDBConfigurationIssues(db, host, port, user, password);

				dataSourceConfig.put(DATABASE_KEY, db);
				dataSourceConfig.put(HOST_KEY, host);
				dataSourceConfig.put(PORT_KEY, port);
				dataSourceConfig.put(USER_KEY, user);
				dataSourceConfig.put(PASSWORD_KEY, password);

				input.close();
			} else {
				logger.error("Properties file " + DATABASE_CONFIGURATION_FILE
						+ " not found .");
			}

		} catch (FileNotFoundException e) {
			logger.error("Properties file " + DATABASE_CONFIGURATION_FILE
					+ " not found . " + e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return dataSourceConfig;
	}

	private void notifyDBConfigurationIssues(String db, String host,
			String port, String user, String password) {

		if (db == null) {
			logger.error("DB could not be retrieved from "
					+ DATABASE_CONFIGURATION_FILE + " : " + DATABASE_KEY);
		}

		if (host == null) {
			logger.error("Host could not be retrieved from "
					+ DATABASE_CONFIGURATION_FILE + " : " + HOST_KEY);
		}

		if (port == null) {
			logger.error("Port could not be retrieved from "
					+ DATABASE_CONFIGURATION_FILE + " : " + PORT_KEY);
		}

		if (user == null) {
			logger.error("User could not be retrieved from "
					+ DATABASE_CONFIGURATION_FILE + " : " + USER_KEY);
		}

		if (password == null) {
			logger.error("Password could not be retrieved from "
					+ DATABASE_CONFIGURATION_FILE + " : " + PASSWORD_KEY);
		}
	}

}
