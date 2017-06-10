package uk.co.marcoratto.ftp.test;

import java.util.Vector;

import uk.co.marcoratto.ftp.Runme;

import junit.framework.TestCase;

public abstract class TestBase {
	
	protected Runme runme = null;
	protected Vector<String> params = null;

	// MUST be static for the Factory
	protected static Throwable actualThrowable = null;  
	protected static int actualEndTotalFiles;	
	protected static int actualStartTotalFiles;
	protected static int actualReturnCode;

	protected final static String USER_WITHOUT_PASSWORD = "watir";
	protected final static String WRONG_PASSWORD = "drowssaP";
	protected final static String USER = "ftp"; //ori is ftp
	protected final static String PASS = "123";	//ori is 123
	protected final static String SERVER = "127.0.0.1";
	protected final static String REMOTE_DIR = "/home/ftp"; //ori is /Users/czb
	
	protected String remotePath = null;
	protected String remotePathWithoutPassword = null;

	protected void setUp() {
		System.out.println(this.getClass() + ".setUp()");	

		try {
			runme = new Runme();
			this.params = new Vector<String>();
			
			actualReturnCode = -1;
			actualEndTotalFiles = -1;
			actualStartTotalFiles = -1;
			actualThrowable = null;

		} catch (Throwable t) {
			t.printStackTrace();
		} 			
	}
	
}
