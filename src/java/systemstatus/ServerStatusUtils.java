package systemstatus;


//import java.io.BufferedReader;
import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
import java.net.InetAddress;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 *Class containing utility methods for parsing server status output.
 *@author sryan
 **/
public final class ServerStatusUtils {

	/**
	 * Get a logger instance for this class.
	 */
	private static Logger logger = Logger.getLogger(ServerStatusUtils.class);
	

	/**
	 * Excute Ping to verify the System is up 
	 * @param hostname
	 * @return
	 */
	public static Boolean executePing(String hostname){

		try {
			final InetAddress host = InetAddress.getByName(hostname);
			return host.isReachable(10000);
		}
		catch (Exception ex){
			return false;
		}
	}
	
	/**
	 * Write the system details to the Database
	 * @param dbConnection
	 * @param id
	 * @param host
	 * @param status
	 * @param os
	 * @param swInstalled
	 * @param swVersion
	 * @param owner
	 * @param oracle
	 * @param jboss
	 * @param java
	 */
	public static void statusWriter(DBConnection dbConnection,
									int id, 
									String host, 
									String status, 
									String os, 
									String swInstalled, 
									String swVersion, 
									String owner, 
									String oracle, 
									String jboss, 
									String java)  {
		try{
			String deleteStatus = "DELETE FROM servers where  host = ?";		
			DBConnectionUtils.queryDatabase(dbConnection.getConnection(),deleteStatus, host);		
	
			String serverStatus = "INSERT INTO servers (id,version, host, status, os,sw_Installed,sw_Version, owner, oracle,jboss, java ) VALUES(?,3,?, ?, ?, ?, ?, ?, ?, ?, ?) ";  
			DBConnectionUtils.queryDatabase(dbConnection.getConnection(), serverStatus ,Integer.toString(id),host, status, os, swInstalled, swVersion, owner, oracle, jboss, java);
		}	
		catch (SQLException sx){
			logger.error("Invalid connection details",sx);
		}		
	}
	
	
	/**
	 * Parse output for the OS
	 * @param output - Pass the output of the command
	 * @return - OS or Unknown
	 */
	public static String parseOS(String output) {

		String[] seperated = output.split("\n");
		for (int i = 0; i < seperated.length; i++) {
			//
			if (seperated[i].contains("SunOS")) {
				return seperated[i].replaceAll("\r", "");
			}
			else if (seperated[i].contains("Linux")) {
				return seperated[i].replaceAll("\r", "");
			}

		}

		logger.info("Cannot determine OS");
		return "Unknown";
	}


	/**
	 * Parse output for the Jboss
	 * @param output - Pass the output of the command
	 * @return - Jboss Version or Unknown
	 */
	public static String parseJboss(String output) {

		String[] seperated = output.split("\n");
		for (int i = 1; i < seperated.length; i++) {
			//
			if (seperated[i].contains("jboss")) {
				return seperated[i].replaceAll("\r", "");
			}
		}

		logger.info("Cannot determine Jboss");
		return "Unknown";
	}
	
	/**
	 * Parse output for the Jboss
	 * @param output - Pass the output of the command
	 * @return - Jboss Version or Unknown
	 */
	public static String parseJava(String output) {

		String[] seperated = output.split("\n");
		for (int i = 1; i < seperated.length; i++) {
			//
			if (seperated[i].contains("java")) {
				return seperated[i].replaceAll("\r", "");
			}
		}

		logger.info("Cannot determine Jboss");
		return "Unknown";
	}
	
	

	/**
	 * Parse output for the Oracle Version
	 * @param output - Pass the output of the command
	 * @return - Oracle Home
	 */
	public static String parseOracle(String output) {

		String[] seperated = output.split("\n");
		for (int i = 0; i < seperated.length; i++) {
			if (seperated[i].matches("SQL.+\\r")) {
				return seperated[i].replaceAll("\r", "");
			}
		}

		logger.info("Cannot determine owner: ");
		return "Unknown";
	}
	
	/**
	 * Parse output for the Owner
	 * @param output - Pass the output of the command
	 * @return - Owner name
	 */
	public static String parseOwner(String output) {

		String[] seperated = output.split("\n");
		for (int i = 0; i < seperated.length; i++) {
			if (seperated[i].matches("Assigned.+\\r")) {
				return seperated[i].replaceAll("\r", "");
			}
		}

		logger.info("Cannot determine owner: ");
		return "Unknown";
	}

	
	/**
	 * Parse output for the assure Version
	 * @param output - Pass the output of the command
	 * @return - Assure Version
	 */
	public static String parseAssureVersion(String output) {

		String[] seperated = output.split("\n");
		for (int i = 0; i < seperated.length; i++) {
			if (seperated[i].matches("^(V|\\d).+\\s")) {
				return seperated[i].replaceAll("\r", "");
			}
		}

		logger.info("Cannot determine assure version: ");
		return "Unknown";
	}
	
	
	/**
	 * Parse output for the Software Version
	 * @param output - Pass the output of the command
	 * @return - SW Version
	 */
	public static String parseSwVersion(String output) {

		String[] seperated = output.split("\n");
		for (int i = 0; i < seperated.length; i++) {
			if (seperated[i].matches("^(V|\\d).+\\s")) {
				return seperated[i].replaceAll("\r", "");
			}
		}

		logger.info("Cannot determine software version: ");
		return "Unknown";
	}
	
	
	/**
	 * Parse output for the check the installed software
	 * @param output - Pass the output of the command
	 * @return - SW Version
	 */
	public static String swInstalled(String output) {

		String[] seperated = output.split("\n");
		for (int i = 0; i < seperated.length; i++) {
			//
			if ((seperated[i].startsWith("iris")) ||(seperated[i].startsWith("assure")) || (seperated[i].startsWith("touchpoint"))) {
				return seperated[i].replaceAll("\r", "");
			}
		}

		logger.info("Cannot determine SW installed: ");
		return "Unknown";
	}
}
