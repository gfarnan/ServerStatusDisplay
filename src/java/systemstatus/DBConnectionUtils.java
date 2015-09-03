package systemstatus;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;

public class DBConnectionUtils {
	
	public final Logger logger = Logger.getLogger(DBConnectionUtils.class);	
	
	public static void queryDatabase(Connection connection, String... serverStatus) throws SQLException {
		 PreparedStatement deletePS = null;
		 int i = 0;
		 deletePS = connection.prepareStatement(serverStatus[i]);
		 for (String s:serverStatus){
			 if ((serverStatus.length>1) && (i>0)){
					 deletePS.setString(i, s);
			 }
			 i++;
		 }
		 
		 deletePS.executeUpdate();
	}
	
}
