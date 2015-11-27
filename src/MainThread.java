public class MainThread extends Thread {
	private Thread t;
	private String threadName;
	   
	// Constructor
	MainThread(String name)
	{
		this.threadName = name;
		System.out.println("Creating " +  threadName );
	}
	
	public void run() 
	{
		System.out.println("Running " +  threadName );
	    try 
	    {    
	    	
	    	
	    } 
	    catch (Exception e) 
	    {
	    	System.out.println("Exception: " +  e);
	    }
	    
	   
	    System.out.println("Thread " +  threadName + " exiting.");
	}
	
	@Override
	public void start ()
	{
		System.out.println("Starting " +  threadName );
	    if (t == null)
	    {
	    	t = new Thread (this, threadName);
	        t.start ();
	     }
	}
	
	
}
