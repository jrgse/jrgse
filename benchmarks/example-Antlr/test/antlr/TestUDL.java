package test.antlr;


import org.antlr.runtime.*;

public class TestUDL {
	
	
    public static void main(String args[]) throws Exception {
    	start();
    }
    
    public static void start() {
        UDLLexer lex = new UDLLexer(new ANTLRStringStream("{ header : 3 }")); // make the element of string symbolic in ANTLRStringStream! element data-->char[]! 
      //  UDLLexer lex = new UDLLexer(new ANTLRStringStream("{ feader ::3 }"));
    	//UDLLexer lex=new UDLLexer(new ANTLRStringStream("{ h }"));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        UDLParser g = new UDLParser(tokens);
        try {
            //g.uid();
        	g.tableUid();
        } catch (RecognitionException e) {
        	System.out.println("stack trace of eception!!!!!!!!!!!!!!!");
            e.printStackTrace();
        }
    }
}