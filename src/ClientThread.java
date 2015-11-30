import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread  extends Thread {
	private Thread t;
	private String threadName;
	private String chunkPath;
	private static int port;
	private static String fileName;
	
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	
	
	
	// Constructor
	ClientThread(String name, String path, int port, int count)
	{
		System.out.println("--Creating " +  threadName );
		this.threadName = name;
		this.chunkPath = path;
		this.port = port;
		this.fileName = path+ Integer.toString(count)+".txt";
		System.out.println("Port Assigned - " +port );
		System.out.println("file being sent - " +fileName); 
		
	}
	
	public void run() 
	{
		System.out.println("--Running " +  threadName );
	    try 
	    {   
	    	//Write your code for this thread here
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
	
	
	//Start server
	  public static void startServer()
	  {
		  
		  try {
		        serverSocket = new ServerSocket(port);  //Server socket
		        

		    } catch (IOException e) {
		        System.out.println("Could not listen on port: "+port);
		    }

		    System.out.println("Server started. Listening to the port "+port);

		    while (true) {
		        try {
		        	
		            clientSocket = serverSocket.accept();   //accept the client connection
		            
                    // sending to client (pwrite object)
		            OutputStream ostream = clientSocket.getOutputStream(); 
		            PrintWriter pwrite = new PrintWriter(ostream, true);
					
					// receiving from server ( receiveRead  object)
					InputStream istream = clientSocket.getInputStream();
					BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
					
					String receiveMessage;
					String sendMessage = "This is from PC by Nishant";
					  
					// The name of the file to open.
				     //String fileName = this.fileName;//"/home/nishant/Documents/OSfilesendTest.txt";
				     String line2 = null;
					  
					  // FileReader reads text files in the default encoding.
			            FileReader fileReader = 
			                new FileReader(fileName);

			            // Always wrap FileReader in BufferedReader.
			            BufferedReader bufferedReader = 
			                new BufferedReader(fileReader);
			            
			            String line3 = "---fileSendingFinishedByServer---";
			            System.out.println("file being sent - " +fileName); 
			            while((line2 = bufferedReader.readLine()) != null) {
						  //line3 = line3 + line2;
						  pwrite.println(line2);             
						  pwrite.flush();
						  //System.out.println(line2);
			            } 	
			            // Always close files.
			            bufferedReader.close();  
			            pwrite.println(line3);             
						pwrite.flush();
					  
					  while(true)
					  {
					    if((receiveMessage = receiveRead.readLine()) != null)  
					    {
					       System.out.println(receiveMessage);         
					    }         
					   
					  }               
				
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
