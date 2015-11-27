import java.awt.Container;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import javax.swing.JFileChooser;

public class SplitFileThread extends Thread {
	private Thread t;
	private String threadName;
	   
	// Constructor
	SplitFileThread(String name)
	{
		this.threadName = name;
		System.out.println("Creating " +  threadName );
	}
	
	public void run() 
	{
		File selectedFile;
		System.out.println("Running " +  threadName );
	    try 
	    {
	    	//Open UI for file chooser
	    	Container c = new Container();
	    	JFileChooser fileChooser = new JFileChooser();
	    	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	    	int result = fileChooser.showOpenDialog(c);
	    	
	    	if (result == JFileChooser.APPROVE_OPTION) {
	    		selectedFile = fileChooser.getSelectedFile();
	    		System.out.println("Selected file: " + selectedFile.getAbsolutePath());
	    		
	    		
	    	}
	    	
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
	
	private static RandomAccessFile read_file() throws FileNotFoundException {
		
		RandomAccessFile read = new RandomAccessFile(new File("F:\\my_docs\\Admission letter.pdf"),"r");
		return read;
	}
	
	
	
}
