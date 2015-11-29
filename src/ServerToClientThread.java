import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class ServerToClientThread extends Thread {
	private Thread t;
	private String threadName;
	
	// Constructor
	ServerToClientThread(String name, String path, String PORT)
	{
		this.threadName = name;
		System.out.println("--Creating " +  threadName );
		
	}
	
	public void run() 
	{
		System.out.println("--Running " +  threadName );
	    try 
	    {    
	    	
	    	//Write your code for this thread here
	    	
//	    	byte[] file_bytes = new byte[(int)read.length()];
//	    	int val = read.read(file_bytes);
//	    	if(val != -1)
//	    	{	
//	    	while(true)
//	    	{
//	    	server_socket = new ServerSocket(PORT);
//	    	socket = server_socket.accept();
//	    	os = socket.getOutputStream();
//	    	System.out.println("file sending");
//	    	os.write(file_bytes);
//	    	os.flush();
//	    	break;
//	    	}	
//	    	}
//	    	socket.close();
//	    	os.close();
//	    	server_socket.close();
	    	
	    	
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
	
	
	private static RandomAccessFile GetFileChunks(int count)
	{
		RandomAccessFile read = null;
		try {
			read = new RandomAccessFile(new File("F:\\OS_File_Chunks\\Chunk."+count+".pdf"),"r");
			System.out.println("file Chunk."+count+".pdf read");	
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return read;	
	}
	
}
