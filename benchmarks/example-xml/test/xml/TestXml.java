/*
 * $Id: Run.java,v 1.1.1.1 2004/02/01 19:23:43 nunnari Exp $
 *
 * Created on 24. gennaio 2004, 20:22
 */

package test.xml;

import ch.nunnisoft.xml.parser.NunniJXMLParserFSM;
import ch.nunnisoft.xml.parser.LogicError;
import gov.nasa.jpf.jdart.Debug;

import java.io.*;

import pobs.scanner.PStringScanner;

/**
 *
 * @author  Roberto Nunnari
 */
public class TestXml {
    
    /** Creates a new instance of Run */
    public TestXml() {
    }
    
    public static String file = "src/example-xml/test/xml/Structure.xml";
    
    public static void main1(String[] args) {
    	int i,j = 0;
    	i = j + 1;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        new TestXml().start();
//    	char i = (char)-1;
//    	
//    	System.out.println((int)i);
    	
    }
    
    public void e1(){};
    public void e2(){};
    
    //public static String input = "<T>Z</T>";
    public static String input = "!T!Z</T>";
    public static char[] bug_input_char_array = {(char)60, (char)63, (char)63, (char)62, (char)60, (char)63, (char)0, (char)29};
    
    public static String bug_input = new String(bug_input_char_array);
    
    public void start(){
    	NunniJXMLParserFSM parser = new NunniJXMLParserFSM( null );

//        System.out.println( "===============================" );
//        System.out.println( "file: " + file );

        try {
            // This is all the code we need to parse
            // a document with our DocHandler.
//            BufferedReader fr =  new BufferedReader( new FileReader( file ) );
        	BufferedReader fr = new BufferedReader(new StringReader(input));
//        	BufferedReader fr = new BufferedReader(new StringReader(bug_input));
            DocHandler handler = new DocHandler();
            
        	char first = charAt(1);
    		if (first == 'B'){
    			e1();
    		}
    		
        	
    		parser.parse( fr, handler );
        	
//        	char last = charAt(input.length() - 2);
//    		if (last == 'B') {
//    			e2();
//    		}
//    		s
            
        }
        catch( IOException e ) {
            e.printStackTrace();
        }
        catch( LogicError e ) {
//        	char last = charAt(input.length() - 2);
//    		if (last == 'B') {
//    			e2();
// 		}
            e.printStackTrace();
        } 
        finally{
        	char last = charAt(input.length() - 2);
    		if (last == 'B') {
    			e2();
    		}
        }
    }  
    
    public char charAt(int index){
    	char c = input.charAt(index);
//    	return c;
		char r = Debug.makeConcolicChar("sym_char_" + index, ""+(int)c);
	    System.out.println("------" + index +"--"+ r + ":"+(int)r);
	    return r;
    }
}
