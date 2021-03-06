import java.awt.Container;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class SplitFileThread extends Thread {
	private Thread t;
	private String threadName;
	private static String chunkPath;
	private static int totalChunks;
	private static int countMainThreads = 0;
	private static ArrayList<MainThread> listMainThreads = new ArrayList<MainThread>();
	private static int portStart = 10000;
	
	
	// Constructor
	SplitFileThread(String name)
	{
		this.threadName = name;
		System.out.println("--Creating " +  threadName );
	}
	
	public void run() 
	{
		File selectedFile;
		System.out.println("--Running " +  threadName );
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
	    		
	    		totalChunks = splitFile(selectedFile);
	    		System.out.println("Total " +  totalChunks +" chunks created at "
	    				+chunkPath );
	    		
	    		countMainThreads++;
	    		String MainThreadID = Integer.toString(countMainThreads);
	    		portStart = portStart + listMainThreads.size() * 2000;
	    		listMainThreads.add(
	    				new MainThread(
	    						"Main_Thread-"+MainThreadID,
	    						totalChunks,
	    						chunkPath,
	    						portStart
	    						));
	    		listMainThreads.get(listMainThreads.size()-1).start();
	    	}
	    	
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
	
	private static int splitFile(File file){
		int count = 0;
		String line = null;
		int chunkNumer = 0;
		
		try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(file);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            String chunkfileContent = "";
            while((line = bufferedReader.readLine()) != null) {
            	count++;
                //System.out.println(line);
            	chunkNumer = count/20;
            	String extentionRemoved = file.getName().split("\\.")[0];
            	chunkPath = "/home/nishant/Documents/OS Project/chunks"+extentionRemoved+"/";
            	
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
            	//System.out.println("Line: "+line);
            	chunkfileContent = chunkfileContent +"\n"+ line;
            	//System.out.println("chunkfileContent: "+chunkfileContent);
            	if(count%20 == 0) {
            		
            		writeLineToFile(chunkfileContent,chunkPath,chunkNumer);
            		chunkfileContent = "";
            	}
            	
            }   
            writeLineToFile(chunkfileContent,chunkPath,chunkNumer);
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
		
		return chunkNumer;
	}
	
	private static void writeLineToFile(String line, String path, int chunkNumber) {
		
		String chunkNumberString = Integer.toString(chunkNumber);
		// The name of the file to open.
        String fileName = path +chunkNumberString +".txt";
        File file = new File(fileName);
		try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(file);

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
