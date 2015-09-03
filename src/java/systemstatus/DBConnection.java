package systemstatus;

import java.sql.*;
import java.util.Map;

//import org.codehaus.groovy.grails.commons.GrailsApplication;
import grails.util.Holders;
import groovy.util.ConfigObject;

import org.apache.log4j.Logger;

/**
 * @author gfarnan
 * Handle Db Connections, Forces system to only use one at a time
 *
 */
public class DBConnection {
	
	private String jdbc_driver;
	private String db_url;
	private String user;
	private String password;
	private static DBConnection dbConnection;
	private Map config;
	
	
	public final Logger logger = Logger.getLogger(DBConnection.class);
	
	/**
	 * Singleton Constructor
	 * @param jdbc_driver
	 * @param db_url
	 * @param user
	 * @param password
	 * @return DBConnection 
	 */
	public static DBConnection getInstance(){
		if (dbConnection==null){
			dbConnection = new DBConnection();

		}
		return dbConnection; 
	}
	
	/**
	 * Private DBConnection constructor
	 * @param jdbc_driver
	 * @param db_url
	 * @param user
	 * @param password
	 */
	private DBConnection() {
		super();
		config = Holders.getFlatConfig();
	}
	
	/**
	 * Returns the Database connection object
	 * @return Connection 
	 */
	public Connection getConnection(){
		Connection conn = null;
		try{
			Class.forName((String)config.get("dataSource.driverClassName"));
		}
		catch (ClassNotFoundException cnfx){
			logger.error("Invalid jdbc driver",cnfx);
		}
		
		try {
			conn = DriverManager.getConnection((String)config.get("dataSource.url"),(String)config.get("dataSource.username"),(String)config.get("dataSource.password"));
		}
		catch (SQLException sx){
			logger.error("Invalid connection details",sx);
		}
		return conn;
	}
}
