package systemstatus;



import java.io.IOException;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * Sftp client.
 *  (Taken from Touchpoint Automation)
 * @author mlester
 *
 */
public class SftpClient {
	private JSch shell;
	private Session session;
	private ChannelSftp channel;

	/**
	 * Connects to the remote host.
	 * 
	 * @param username
	 * @param password
	 * @param host
	 * @param port
	 * @throws JSchException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void connect(String username, String password, String host, int port) throws JSchException, IOException, InterruptedException {
		shell = new JSch();
		session = shell.getSession(username, host, port);
		session.setPassword(password);
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();

		channel = (ChannelSftp)session.openChannel("sftp");
		channel.connect();
	}
	
	 /**
     * Copy a remote file locally.
     * 
     * @param remotefilename
     * @param localfilename
     * @throws SftpException
     */
    public void getFile(String remotefilename, String localfilename) throws SftpException{
    	channel.get(remotefilename, localfilename );
    }
    
    /**
     * Copy a local file remotely.
     * 
     * @param localfilename
     * @param remotefilename
     * @throws SftpException
     */
    public void putFile(String localfilename, String remotefilename) throws SftpException{
    	channel.put(localfilename, remotefilename );
    }
    
    /**
     * 
     * @return true if the client is connected
     */
    public boolean isConnected(){
        return (channel != null && channel.isConnected());
    }

    /**
     * Disconnection.
     */
    public void disconnect(){
        if (isConnected()){
            channel.disconnect();
            session.disconnect();
        }
    }
}
