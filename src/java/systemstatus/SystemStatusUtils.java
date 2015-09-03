package systemstatus;


import org.apache.log4j.Logger;

/**
 *Class containing utility methods for parsing system status output.
 *@author sryan
 **/
public final class SystemStatusUtils {
	
	private SystemStatusUtils() {
		//not called
	}

	/**
	 * Get a logger instance for this class.
	 */
	private static Logger logger = Logger.getLogger(SystemStatusUtils.class);

	/**
	 * Parse the irStatAll command for a status other than the desired status. 
	 * @param output - Pass the output of the irStatAll command
	 * @param status - Desired status
	 * @return - True if all processes have Status = desired status, false otherwise.
	 */
	public static boolean parseIrStatALL(String output, String status) {

		String[] seperated = output.split("\n");
		for (int i = 0; i < seperated.length; i++) {
			//
			if (seperated[i].contains("status")) {
				if (!seperated[i].contains(status)) {
					logger.error("Expected status: " + status + " for " + seperated[i - 1] 
							+ " but current " +  seperated[ i ]);  
					return false;
				}
			}
		}

		logger.info("All processes are in state: " + status);
		return true;
	}
	
	/**
	 * Parse the irStat command output to verify the status of an individual process. 
	 * @param output - Pass the output of the irStat command
	 * @param processName - Name of process for which the status will be verified
	 * @param status - Desired status
	 * @return - True if process has Status = desired status, false otherwise.
	 */
	public static boolean parseIrStatProcess(String output, String processName, String status) {

		String[] seperated = output.split("\n");
		for (int i = 0; i < seperated.length; i++) {
			//
			if (seperated[i].contains("Process '" + processName)) {
				if (!seperated[i + 1].contains(status)) {
					
					logger.error("Expected status: " + status + " for " + processName 
							+ " but current " +  seperated[ i + 1 ]);  
					return false;
				} else {
					logger.info("Process " + processName + " is in state: " + status);
					return true;
				}
			}
		}
		logger.info("Process " + processName + " status not found");
		return false;
	}

}
