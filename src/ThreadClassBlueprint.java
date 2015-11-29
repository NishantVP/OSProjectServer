
public class ThreadClassBlueprint extends Thread {
	private Thread t;
	private String threadName;
	
	// Constructor
	ThreadClassBlueprint(String name)
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
	

}
