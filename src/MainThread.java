import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

public class MainThread extends Thread {
	private Thread t;
	private String threadName;
	private int totalChunks;
	private String pathOfChunks;
	private int startPortNumber;
	private static int clientPortNumber;
	private static String ServerIP;
	private static int ListenPORT = 9999;
	
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	
	private static Socket clientSocket2;
	// Constructor
	MainThread(String name, int count, String path, int port)
	{
		this.threadName = name;
		this.totalChunks = count;
		this.pathOfChunks = path;
		this.startPortNumber = port;
		System.out.println("--Creating " +  threadName );
		System.out.println(threadName +" will handle "+totalChunks +" chunks at " +pathOfChunks);
	}
	
	public void run() 
	{
		System.out.println("--Running " +  threadName );
	    try 
	    {    
	    	
	    	//Write your code for this thread here
	    	
	    	clientPortNumber = startPortNumber;
	    	
	    	ServerIP = getFilterIPAddresses();
	    	saveServerIPToCloud();
	    	startServer();
	    	
	    } 
	    catch (Exception e) 
	    {
	    	System.out.println("Exception: " +  e);
	    }
	    
	   
	    System.out.println("--Exiting " +  threadName);
	}
	
	@Override
	public void start ()
	{
		System.out.println("--Starting " +  threadName );
	    if (t == null)
	    {
	    	t = new Thread (this, threadName);
	        t.start ();
	     }
	}
	
	
	public static String getFilterIPAddresses() {
		String myIP = null;
		Enumeration<?> e;
		try {
			e = NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration<?> ee = n.getInetAddresses();
				  
				while (ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();
					  
					if(i.getHostAddress().contains(".")) {
					  if(!i.getHostAddress().contains("127.0.")) {
						  myIP = i.getHostAddress();
						  System.out.println(myIP);
					  }
					}
				}
			}
		  } catch (SocketException e2) {
			// TODO Auto-generated catch block
				e2.printStackTrace();
		  }
		return myIP;
	}
	  
	private static void saveServerIPToCloud() throws IOException {
		// set up the command and parameter
		String Port = Integer.toString(ListenPORT);
		String pythonScriptPath = "/home/nishant/JavaWorkspace/OSProjectServer/src/SaveServerIP.py";
		String[] cmd = new String[6];
		cmd[0] = "python"; // check version of installed python: python -V
		cmd[1] = pythonScriptPath;
		cmd[2] = "NishantServer";
		cmd[3] = ServerIP;
		cmd[4] = Port; //port
		cmd[5] = "true"; //running
		 
		// create runtime to execute external command
		Runtime rt = Runtime.getRuntime();
		Process pr = rt.exec(cmd);
		 
		// retrieve output from python script
		BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line = "";
		while((line = bfr.readLine()) != null) {
			// display each output line form python script
			System.out.println(line);
		}
	}
	
	//Start server
	  public static void startServer() {
		  
		  try {
			  serverSocket = new ServerSocket(ListenPORT);  //Server socket      
		    } 
		  catch (IOException e) {
		       System.out.println("Could not listen on port: " +ListenPORT);
		    }
	
		    System.out.println("Server started. Listening to the port " +ListenPORT);
	
		    while (true) {
		        try {
		        	System.out.println("waiting");
		            clientSocket = serverSocket.accept();   //accept the client connection
		            System.out.println("started");
	                // sending to client (pwrite object)
		            OutputStream ostream = clientSocket.getOutputStream(); 
		            PrintWriter pwrite = new PrintWriter(ostream, true);
		            
		            String S2CPort = Integer.toString(clientPortNumber);
		            String C2SPort = Integer.toString(clientPortNumber + 1);
		            clientPortNumber = clientPortNumber + 2;
		            
		            String sendMessage = "S2C=" +S2CPort +" C2S=" +C2SPort;
					
					pwrite.println(sendMessage);             
					pwrite.flush();
					
		           
		        } catch (IOException ex) {
		            System.out.println("Problem in message reading");
		        }
		        finally {
		        	 try {
						clientSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		    }
	  }
	
}
