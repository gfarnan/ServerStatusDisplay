package systemstatus;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * 
 * SSH client for connecting to remote servers
 *
 */
public class SSHClient{
	
	static Logger logger = Logger.getLogger(SSHClient.class);
	
    private JSch shell;
    private Session session;
    private Channel channel;
    private static OutputStream out;
    private static InputStream in;
    private final String COMMAND_EOF = "$((1+1))EOF";
    private final String RESPONSE_EOF = "2EOF";
    private final static int ERROR = -1;
    private String lastResponse = "";
    private boolean silentMode = false;
    private final int OK = 0;
    //Timeout for unsuccessful connection 
    private int timeout = 30000; 

    public void connect(String username, String password, String host, int port) 
    		throws JSchException, IOException, InterruptedException{

        shell = new JSch();

        session = shell.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        // Required to avoid prompting from Kerbos authentication on Solaris
        session.setConfig("PreferredAuthentications", 
                "publickey,keyboard-interactive,password");
        
    	logger.info("Connecting to: " + username + "@" + host );
        session.connect(timeout);

        channel = session.openChannel("shell");
        channel.setInputStream(null);
        channel.setOutputStream(null);

        in = channel.getInputStream();
        out = channel.getOutputStream();
        ((ChannelShell)channel).setPtyType("xterm");
        channel.connect();
    }

    public int send(String command) throws IOException, InterruptedException{
        return send(command, "");
    }
    
    public int send(String command, String expectedResult) throws IOException, InterruptedException{
        byte[] tmp = new byte[1024];

        String EOF_DELIMITER = ";echo $?;echo " + COMMAND_EOF;
        out.write((command + EOF_DELIMITER).getBytes());
        out.write(("\n").getBytes());
        out.flush();

        String result = "";
        int exitCode = ERROR;
        while (true){
            while (in.available() > 0){
                int i = in.read(tmp, 0, 1024);
                if (i < 0) {
                    break;
                }
                String line = new String(tmp, 0, i);
                if(!silentMode){
                    System.out.print(line);                    
                }
                result = result + (line);
                this.lastResponse = result;
                if(!expectedResult.equalsIgnoreCase("") && line.contains(expectedResult)){
                    return OK;
                }
            }
            if (result.lastIndexOf(RESPONSE_EOF) != -1){
                int exitCodePosition = result.lastIndexOf(RESPONSE_EOF);
                try{
                    String resultCodeStr = result.substring(exitCodePosition - 3, exitCodePosition - 2);
                    exitCode = Integer.parseInt(resultCodeStr);
                }
                catch (NumberFormatException n){
                    System.err.println("Error getting exit code. Returning " + ERROR);
                    exitCode = ERROR;
                }
                break;
            }
            try{
                Thread.sleep(100);
            }
            catch (Exception ee)
            {
            }
        }
        this.lastResponse = result;
        return exitCode;
    }
    
    /**
     * Execute a comand and return its one line response
     * @param command
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String getOneLineResult(String command) throws IOException, InterruptedException{
    	
    	int responseCode = send(command);
    	
    	if(responseCode != OK){
    		return "Invalid response. Status code " + responseCode + " received.";
    	}
    	
    	return getLastResponseOneLine();  	
    }

    public boolean isConnected(){
        return (channel != null && channel.isConnected());
    }

    public void disconnect(){
        if (isConnected()){
            channel.disconnect();
            session.disconnect();
        }
    }
    
    public String getLastResponse(){
        return lastResponse;
    }
    
    /**
     * Get a one line response
     * @return
     */
    public String getLastResponseOneLine(){
    	String[] temp = lastResponse.split("\n");
    	return temp[temp.length - 4].replace("\r", "");
    }
    
    public void setSilentMode(boolean silentMode){
        this.silentMode = silentMode;
    }
}