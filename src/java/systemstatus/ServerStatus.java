package systemstatus;


import grails.util.Holders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;









import com.jcraft.jsch.JSchException;

/**
 * Test Cases to pull server status.
 * @author sryan
 *
 */
public final class ServerStatus {

	/**
	 * Get a logger instance for this class.
	 */
	private static Logger logger = Logger.getLogger(ServerStatus.class);

	/**
	 * Global SSH port numnber
	 */
	private static final int SSH_PORT = 22;
	
	/**
	 * The server usernames which are used to determine the product running on the server
	 */
	private static final String TOUCHPOINT="assure";
	private static final String IRIS="geo";
	

	/**
	 * @testsummary Verify server is in running state 
	 * @teststeps 
	 * 		1. Ping server
	 * 		2. SSH to the  server
	 * 		3. Determine the OS
	 * 		4. 
	 * @testid 1234
	 * @component SystemStatus
	 * @author sryan
	 * @param name
	 * @param age
	 * @throws JSchException Exception during SSH Connection
	 * @throws IOException Exception during SSH Connection
	 * @throws InterruptedException Exception during SSH Connection
	 */
	public static void verifyServerStatus(int id, String hostname, String user, String password ) throws JSchException, IOException, InterruptedException {

		//Database where result will be stored
		DBConnection dbConnection = DBConnection.getInstance();
				
		logger.info("Start Test");
		 if (user.equalsIgnoreCase(TOUCHPOINT)){
			 try {
				 checkTouchpoint(dbConnection, id, user,password, hostname, ServerStatusUtils.executePing(hostname));				 
			 }
			 catch (JSchException ex){
				 logger.info("Server :"+hostname +" not connectible");
			 }

		 }
		 logger.info("End Test");
	}
	
	
	
	/**
	 * Verify the Touchpoint server is running and the Version
	 * Find the following details from the server
	 *  Owner
	 *	System Name
	 *	Server Use
	 *	OS (Solaris or Redhat) Versions
	 *	Oracle
	 *	Jboss
	 *	Java
	 */
	private static void checkTouchpoint(DBConnection dbConnection,int id, String sshUserName, String sshUserPass, String server, boolean status) throws JSchException, IOException, InterruptedException{
		
		String owner= "";		
		String sw = "";
		String swVersion= "";
		String os= "";
		String oracle= "";
		String jboss="";
		String java="";
			
		

		SSHClient tpHostSSHClient = new SSHClient();
		
		if (status){
	
			// Connect to the server
			tpHostSSHClient.connect(sshUserName, sshUserPass, server, SSH_PORT);
			
			//OS Version
			int exitCode = tpHostSSHClient.send("uname -a");
			Assert.assertFalse(exitCode != 0, "uname -a failed with exit code: " + exitCode);
			os = ServerStatusUtils.parseOS(tpHostSSHClient.getLastResponse());
			
			//Check if Assure is installed
			tpHostSSHClient.send("cat /etc/passwd | grep assure | awk -F: '{ print $1 }'");
			Assert.assertFalse(exitCode != 0, "cat /etc/passwd | grep assure | awk -F: '{ print $1 }' a failed with exit code: " + exitCode);
			sw=ServerStatusUtils.swInstalled(tpHostSSHClient.getLastResponse());
			
			//Assure Version
			tpHostSSHClient.send("assure version");
			Assert.assertFalse(exitCode != 0, "assure version failed with exit code: " + exitCode);
			swVersion=ServerStatusUtils.parseAssureVersion(tpHostSSHClient.getLastResponse());
			
			//Owner
			tpHostSSHClient.send("cat /etc/motd  |grep Current |cut -d \\[ -f 2 |cut -d \\] -f 1");
			Assert.assertFalse(exitCode != 0, "Check for owner failed with exit code: " + exitCode);
			owner=ServerStatusUtils.parseOwner(tpHostSSHClient.getLastResponse());
			
			//Oracle
			tpHostSSHClient.send("$ORACLE_HOME/bin/sqlplus -v |grep SQL");
			Assert.assertFalse(exitCode != 0, "Check for Oracle Version failed with exit code: " + exitCode);
			oracle=ServerStatusUtils.parseOracle(tpHostSSHClient.getLastResponse());
			
			//Jboss
			tpHostSSHClient.send(" ls /opt/arantech/current/  |grep jboss- |grep -v client");
			Assert.assertFalse(exitCode != 0, "Check for Jboss failed with exit code: " + exitCode);
			jboss=ServerStatusUtils.parseJboss(tpHostSSHClient.getLastResponse());
			
			//Java
			tpHostSSHClient.send("java -version");
			Assert.assertFalse(exitCode != 0, "Check for Oracle Version failed with exit code: " + exitCode);
			java=ServerStatusUtils.parseJava(tpHostSSHClient.getLastResponse());
			
		}
		
		
		ServerStatusUtils.statusWriter(dbConnection, id, server,  (status)?"alive":"stopped", os, sw, swVersion, owner, oracle, jboss, java);

	}
	
	/**
	 * Get Server Details from Excel sheet
	 * @return List of Servers
	 */
	public static List<Object[]> getServerDetails(){
		
		String filePath = Holders.getFlatConfig().get("statusFile.use").toString();

		return getServerDetailsFromCSV(filePath, "hostname", "user","password");
	}
	
	
	/**
	 * Read the status/hostdetails.csv 
	 */
	public static List<Object[]> getServerDetailsFromCSV(String pathToCsv, String ... columnNames) {
		
		ArrayList<String[]> rowList = FileUtils.getListFromCSV(pathToCsv, columnNames);
		
		List<Object[]> list = new ArrayList<>();
		
		for (String[] row : rowList){

			list.add(Arrays.copyOf(row, row.length, Object[].class));
		}
		
		return list;
	}

	/**
	 * Entry Point to the system
	 */
	public static void getStatus() {
		try {
			int i = 1;
			List<Object[]>serverDetails  = ServerStatus.getServerDetails();
			for (Object[] servers:serverDetails){
				ServerStatus.verifyServerStatus(i,(String)servers[0], (String)servers[1], (String)servers[2]);
				i++;
			}
			
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
