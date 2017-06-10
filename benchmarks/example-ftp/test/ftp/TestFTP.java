package test.ftp;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.net.ftp.FTPClient;

import uk.co.marcoratto.ftp.Runme;
import uk.co.marcoratto.ftp.listeners.ListenerFactory;
import uk.co.marcoratto.ftp.listeners.ListenerFactoryException;
import uk.co.marcoratto.ftp.test.TestBase;
import uk.co.marcoratto.util.PropertiesManager;
import uk.co.marcoratto.util.PropertiesManagerException;
import uk.co.marcoratto.util.Utility;


public class TestFTP extends TestBase {

	public static void main(String[] args) throws PropertiesManagerException, ListenerFactoryException {
		// TODO Auto-generated method stub
		//System.setProperty("ftp_config_file", "./src/example-ftp/ftp.properties");
		//System.out.println(System.getProperty("ftp_config_file"));
		//PropertiesManager.getInstance().setProperty("ftpsListener", "uk.co.marcoratto.ftp.listeners.ListenerPrintStream");
		TestFTP test = new TestFTP();
		test.setUp();		
		test.remotePath = USER + ":" + PASS + "@" + SERVER + ":" + REMOTE_DIR;
		test.remotePathWithoutPassword = USER + "@" + SERVER + ":" + REMOTE_DIR;
		ListenerFactory.getInstance().getListener();
		test.start();
//		FTPClient client = new FTPClient();
//		try {
//			client.disconnect();
//			client.login("test", "abc");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	
	//public void start(){}
	
	public static void createFile(){
		//Random r = new Random();
		File f = new File("./src/example-ftp/dummy.txt");
		try{
			if (!f.exists()) {
				f.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(f));
				bw.write("Hello World");
				bw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void start(){
	  TestFTP.createFile();
		//System.out.println(this.getClass().getName() + ".start()");
//		File f = new File("./src/example-ftp/dummy.txt");
//		try{
//			if (!f.exists()) {
//				f.createNewFile();
//				BufferedWriter bw = new BufferedWriter(new FileWriter(f));
//				bw.write("abc");
//				bw.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println("abc");
		try {		 
			this.params.clear();
			this.params.add("-source");
			this.params.add("./src/example-ftp/dummy.txt");
			this.params.add("-target");
			this.params.add(this.remotePath + "/dummy.txt"); 
			this.params.add("-o");
			runme = new Runme();
			runme.runme((String[]) this.params.toArray(new String[0]));
			//runme.execute((String[]) this.params.toArray(new String[0]));			
			actualReturnCode = Runme.getRetCode();			

		} catch (Throwable t) {
			t.printStackTrace();
		} 		
	}
	

}
