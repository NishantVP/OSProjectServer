import java.awt.Container;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
	    		
	    		int count = countLines(selectedFile);
	    		System.out.println("Number of Lines " +  count );
	    		
	    		
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
	
	private static int countLines(File file){
		int count = 0;
		String line = null;
		
		try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(file);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
            	count++;
            	int chunkNumer = count/1000;
            	String extentionRemoved = file.getName().split("\\.")[0];
            	String chunkPath = "/home/nishant/Documents/OS Project/chunks"+extentionRemoved+"/";
            	
            	File theDir = new File(chunkPath);

            	// if the directory does not exist, create it
            	if (!theDir.exists()) {
            	    System.out.println("creating new directory: ");
            	    boolean result = false;

            	    try{
            	        theDir.mkdir();
            	        result = true;
            	    } 
            	    catch(SecurityException se){
            	        //handle it
            	    }        
            	    if(result) {    
            	        System.out.println("DIR created");  
            	    }
            	}
            	
            	writeLineToFile(line,chunkPath,chunkNumer);
            	
       
            	
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                		file + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + file + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }   
		
		return count;
	}
	
	private static void writeLineToFile(String line, String path, int chunkNumber) {
		
		String chunkNumberString = Integer.toString(chunkNumber);
		// The name of the file to open.
        String fileName = path +chunkNumberString +".txt";
        File file = new File(fileName);
		try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(file,true);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);
            
            // Note that write() does not automatically
            // append a newline character.
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
		
	}
	
	
	
	
}
