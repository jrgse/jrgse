package test.html;

import net.htmlparser.jericho.*;

import java.net.*;

import test.fastjson.TestFastJSON;

public class TestHTML {
	
	public static char[] bug1 = {(char)60, (char)64, (char)62, (char)0, (char)60, (char)47, (char)112, (char)10}; 
	
	//public static TestHTML a = new TestHTML();
	
	//String sourceUrlString="file:src/example-jericho/TestHTML1.html";
	
	public static String input = "<p>a</p>ccc"; 
	
	public static void main(String[] args) throws Exception {
		new TestHTML().start();
//		new TestHTML().start1();		
	}
	
	public void e1(){};
	
	public void e2(){};
	
	public void start1() throws Exception {
		Source source=new Source(new String(bug1));
		source.fullSequentialParse();
	}
	
	public void start() throws Exception {
		
		char first = new CharSequenceParseText(input).charAt(0);
		if (first == 'a'){ // generate constraint
			e1();
		}
		
		//Source source=new Source(new URL(sourceUrlString));
		Source source=new Source(input);
		
		// Call fullSequentialParse manually as most of the source will be parsed.
		source.fullSequentialParse();

		//System.out.println(source.getParseText().length());
		char lastlast = source.getParseText().charAt(source.getParseText().length() - 1);
		if (lastlast == 'b') {// generate constraint
			e2();
		}
		
//		System.out.println("\nAll text from file (exluding content inside SCRIPT and STYLE elements):\n");
//		System.out.println(source.getTextExtractor().setIncludeAttributes(true).toString());
	}

}