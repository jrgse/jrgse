package test.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.ParserConfig;

public class TestFastJSON {
	
	public static String bug1_text="{0:0e,";
	public static char[] bug2_chars = {'{', '0', ':', '"',(int)2, (int)2, (int)2, (int)2, (int)2, (int)2, (int)2, '"', '}'};
	public static String bug2_text=new String(bug2_chars);
	
	public static String text="[true, false]";
	
	public static void main(String[] args) {
		start();
	}
	
	//public static void start(){}
	
	public void s(){};
	public void e(){};
	
	public static void start(){
		System.out.println("---------------------Start------------------------------");
		TestFastJSON test = new TestFastJSON();
		JSONScanner lexer = new JSONScanner(text, JSON.DEFAULT_PARSER_FEATURE); //generate constraint sym_0 !=62357
		char first = lexer.getCurrent();
		if (first == '{'){ //generate constraint ASCII 123!=Sym_0
			test.s();
    	};
		DefaultJSONParser parser = new DefaultJSONParser(lexer);
        parser.parse(); 
        char last = lexer.charAt(lexer.getBufferPosition() - 1);  // charAt make the value symbolic! 
        if (last == '}'){ //generate constraint ASCII 125!=Sym_12
        	test.e();
    	};
        parser.close(); 
	}

}
