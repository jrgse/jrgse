package nanoxml;

import gov.nasa.jpf.jdart.Debug;
import gov.nasa.jpf.jvm.Verify;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

public class DumpXML
{
    public static void main(String[] args) throws Exception
      
    {
    	new DumpXML().start();
//       testBug1(); 
//       testSPFbug1();
//    	StringBuffer buf = new StringBuffer();
//       tryDebug_resolveEntity(buf);
    }
    
    public static String input_xml = "<s>good</s>";
    //public static String input_xml = "<\n>&;";
    /***
     * test driver for test all funs. 
     * search depth=27 can find a bug. in resolveEntity().
     * @throws Exception 
     */
    public void testDriver1()   throws Exception{
    	try{
    		XMLElement xml = new XMLElement();           //2
    		//FileReader reader = new FileReader("src/example-nanoxml/test2.xml");
    		StringReader reader = new StringReader(input_xml);

    		char c1 = getChar(1);
    		if (c1 == 'b') {
    			e1();
    		}
    		DumpXML.count = 0;
    	    xml.parseFromReader(reader);                 //3

    		char c2 = getChar(input_xml.length() - 2);
    		Debug.printCurrentPC();    		
    		if (c2 == 'w') {
    			e2();
    		}
    	//	Debug.printCurrentPC();    		

//    		System.out.println(xml);                      //4
    	} catch (Exception e) {
    		char c2 = getChar(input_xml.length() - 2);
    		if (c2 == 'w') {
    			e2();
    		}
    	//	Debug.printCurrentPC();    		
    		throw e;
    	} finally {
    	}
    }
    
    public char getChar(int i) {
    	char c = input_xml.charAt(i);
    	char r = Debug.makeConcolicChar("sym_cnf_char_"+i, "" + (int)c);
    	System.out.println("XMLElement.readChar(): " + DumpXML.count + "----" + r + ":" + (int)r);
    	return r;
    }
    public void e1(){}
    
    public void e2(){}
    
    public void start() {
    	try {
    		count = 0;
			testDriver1();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /***
     * test for the bug find in testDriver1.
     * really recreate the bug
     */
    public static void testBug1() {
//    	XMLElement xml = new XMLElement();      
    	StringBuffer buf = new StringBuffer();
//    	try {
//    		simulate_resolveEntity(buf);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
    
//    public static void simulate_resolveEntity(StringBuffer buf) {
//    	char ch = '\0';
//        StringBuffer keyBuf = new StringBuffer();
//        int count = 1;
//        for (;;) {
//            ch = (char)Debug.makeSymbolicInteger("xml_char_" + (++count), "0");
//            if (ch == ';') {
//                break;
//            }
//            keyBuf.append(ch);
//        }
//        
//        String key = keyBuf.toString();
//        Debug.printPC("########################################prepare to out of range index, PC: \n");
//        if (key.charAt(0) == '#') { 
//        	System.out.println("%%%%%%%%%%%%%%%%%%%%%% reach here");
//        }
//        
//        
//    }
//    
//    
//    public static void simulate_resolveEntity2(StringBuffer buf) {
//    	char ch = '\0';
//        StringBuffer keyBuf = new StringBuffer();
//        int count = 1;
//            ch = (char)Debug.makeSymbolicInteger("xml_char_" + (++count));
//            keyBuf.append(ch);
//        
//        String key = keyBuf.toString();
//        int len = key.length();
//        if(len >1)
//        	System.out.println("======================================== reach here");
//        System.out.println(key.length());
//        if (key.charAt(0) == '#') { 
//        	System.out.println("%%%%%%%%%%%%%%%%%%%%%% reach here");
//        }
//        
//        
//    }
    
    
    public static void testSPFbug1() {
    	StringBuffer bug = new StringBuffer();
    	testSPFbug1(bug);
    }
    
    
    public static void testSPFbug1(StringBuffer buf) {
    	XMLElement xml = new XMLElement();
    	char ch = '\0';
        StringBuffer keyBuf = new StringBuffer();
//        int count = 0;
//        for (;;) {
//            ch = (char)Debug.makeSymbolicInteger("char_" + count++);
//            Verify.ignoreIf(ch < 0);
//            if (ch == ';') {
//                break;
//            }
//            keyBuf.append(ch);
//        }
        String key = keyBuf.toString();
//        Debug.printPC("########################################prepare to out of range index, PC: \n");
        System.out.println("########################################" + key);
        //debug
        if (key.charAt(0) == '#') {
            try {
                if (key.charAt(1) == 'x') {
                    ch = (char) Integer.parseInt(key.substring(2), 16);
                } else {
                    ch = (char) Integer.parseInt(key.substring(1), 10);
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException();
            } 
            buf.append(ch);
        } else {
            char[] value = (char[]) xml.entities.get(key);
            if (value == null) {
                throw xml.unknownEntity(key);
            }
            buf.append(value);
        }
    }
    
    
    public static void tryDebug_resolveEntity(StringBuffer buf)
        throws IOException
    {
    	StringBuffer keyBuf = new StringBuffer();
        char ch = (char) Debug.makeConcolicInteger("char1", "1");
        keyBuf.append(ch);
//        ch = (char) Debug.makeSymbolicInteger("char2");
        keyBuf.append(ch);
//        ch = (char) Debug.makeSymbolicInteger("char3");
        keyBuf.append(ch);
//        ch = (char) Debug.makeSymbolicInteger("char4");
        keyBuf.append(ch);
        
        String key = keyBuf.toString();
        ch = (char) Integer.parseInt(key.substring(2), 16);
//        Debug.printPC("########################################prepare to out of range index, PC: \n");
        if (key.charAt(0) == '#') {
            try {
                if (key.charAt(1) == 'x') {
                    ch = (char) Integer.parseInt(key.substring(2), 16);
                } else {
                    ch = (char) Integer.parseInt(key.substring(1), 10);
                }
            } catch (NumberFormatException e) {
                throw new RuntimeException();
            }
            buf.append(ch);
        } else {
//            char[] value = (char[]) this.entities.get(key);
//            if (value == null) {
//                throw this.unknownEntity(key);
//            }
//            buf.append(value);
        }
    }


	public static int count = 0;
	public static int maxSymbolNum = 20;

    
    
}