package test.expression;

import org.nfunk.jep.*;

/**
* A seven line program for testing whether the JEP library can be found
* by the compiler and at run-time.<br>
* Upon successful compilation and running of the program, the program should
* print out one line: "1+2 = 3.0"
*/
public class TestExpression {
	
	public static String expression = "1+2+3";
	
	public static void main(String args[]) {
		new TestExpression().start();
	}
	
	public void start(){
		System.out.println("-----------------------Start-------------");
		JEP myParser = new JEP();
		myParser.parseExpression(expression);
		//System.out.println(expression + " = " + myParser.getValue());
	}
}
