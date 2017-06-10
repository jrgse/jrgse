package test.htmlparser;

import org.htmlparser.Parser;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.IteratorImpl;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.ParserException;

public class TestHTMLParser {

	//String text = "<%td>aaaa</td>";
	//public static String text = "<%a>";
	//public static String text = "</html>abc";
	//public static String text = "<html>abcdefghijkl</html>";  //---the original !
	//public static String text = "abc";
	public static String text = "<html>abcdefghijklabcdefghijklabcdefghijklabcdefghijklabcdefghijkl<p>abc</p>abc</html>";
	//public static String text = "<htmlabcdefghijklabcdefghijklabcdefghijklabcdefghijklabcdefghijkl";
	//public static String text = "</></</l>";
	
	public static void main(String[] args) {
		TestHTMLParser test = new TestHTMLParser();
//		for (int i = 0; i < 10; i++){
//			text = text + i;
//			test.start1();
//		}
		test.start();	
	}
	
	//public void start(){}
	public void start(){
		Page mPage=new Page (text);
        Lexer mLexer =  new Lexer (mPage); // text set the source's element string, charset use the default one---> "ISO-8859-1"
        Parser parser = new Parser(mLexer, new DefaultParserFeedback(DefaultParserFeedback.DEBUG));
        try {
        	IteratorImpl it = parser.elementsImpl();
        	//String str = "";
        	while (it.hasMoreNodes()){
        		//Node n = it.nextNode();
        		it.nextNode();
        	}
        	//System.out.println(str);
		} catch (ParserException e) {
			e.printStackTrace();
		}
        System.out.println("--------------------End------------------------");
	}

}
