package test.pobs;

import net.htmlparser.jericho.CharSequenceParseText;
import example.calc.SolverVar;
import pobs.*;
import pobs.scanner.*;
import pobs.utility.POBS;
import pobs.directive.*;
import pobs.parser.*;

/**
 * Insert the type's description here.
 * 
 * @author: Martijn W. van der Lee
 */
public class SimpleCalc {

    private pobs.PObject parser;
    private SolverVar solver;

    /**
     * SimpleCalc constructor comment.
     */
    public SimpleCalc() {
        super();

        solver = new SolverVar();

        /*
           	number		= sdouble | sint		TODO: Special double parser?
        	variable	= lexeme(alpha+)       	
        	args		= expr (',' expr)*
        	function	= lexeme(alpha+) '(' args ')'
        	expr		= term ( ('+' term) | ('-' term) )*
            term		= factor ( ('*' factor) | ('/' factor) )*
            factor		= number | function | variable | '(' expr ')'            	
         */

        PPointer p_term = new PPointer();
        PPointer p_factor = new PPointer();

        // terminals
        PParser number = new POr(POBS.SIGNED_FLOAT, POBS.SIGNED_INT); // parser or of two parser Single_float and single_int
        number.setMatchAction(solver.value);

        PParser var = new PMultiple(POBS.ALPHA).addControl(new PDisableSkip());
        var.setMatchAction(solver.variable);

        // non-terminals
        PParser expr_plus = new PSequence(new PChar('+'), p_factor);
        expr_plus.setMatchAction(solver.operand(solver.PLUS));

        PParser expr_minus = new PSequence(new PChar('-'), p_factor);
        expr_plus.setMatchAction(solver.operand(solver.MINUS));

        PObject expr =
            new PSequence(p_term, new PKleene(new POr(expr_plus, expr_minus)));

        PParser term_multiply = new PSequence(new PChar('*'), p_factor);
        term_multiply.setMatchAction(solver.operand(solver.MULTIPLY));

        PParser term_divide = new PSequence(new PChar('/'), p_factor);
        term_divide.setMatchAction(solver.operand(solver.DIVIDE));

        PObject term =
            new PSequence(
                p_factor,
                new PKleene(new POr(term_multiply, term_divide)));
        p_term.set(term);

        PObject args =
            new PSequence(
                new PChar('('),
                new PList(expr, new PChar(',')),
                new PChar(')'));

        PParser function_max = new PSequence(new PToken("max"), args);
        function_max.setMatchAction(solver.operand(solver.MAX));

		PParser function_min = new PSequence(new PToken("mix"), args);
		function_min.setMatchAction(solver.operand(solver.MIN));
		
        PObject function = new POr(function_min, function_max);
        PObject factor =
            new POr(
                number,
                function,
                var,
                new PSequence(new PChar('('), expr, new PChar(')')) );
        p_factor.set(factor);

        parser = factor;
    }
    /**
     * Starts the application.
     * @param args an array of command-line arguments
     */
    public static void main(java.lang.String[] args) {
        SimpleCalc c = new SimpleCalc();
        c.start();


    }
    
    public static long result = 0;
    public static String input = "max (3, PI + ( 4 * 5.5 ) )";
//    public static String input = "1 + ( 2 * 3 )";    
    
//    public void e1(){};
//    public void e2(){};
 
    //                                   1         2         3         4
    //                          1234567890123456789012345678901234567890
//	System.out.println(parse("max (3, PI + ( 4 * 5.5 ) )")); //$NON-NLS-1$
//    solver.print();
//    System.out.println(solver.solve());
   
    public void e1(){};
    public void e2(){};
    public void start(){
    	char first = new PStringScanner(input).charAt(1);  // charAt make the value of string symbolic !
		if (first == 'i'){
			e1();
		}
//		System.out.println("first");
    	System.out.println(parser.toString()+"mmmmmmmmmmmmmmmmm");
    	parse(input);
    	
    	char last = new PStringScanner(input).charAt(input.length() - 6); // charAt make the value of string symbolic! 
		if (last == '4') {
			e2();
		}
//		System.out.println("last");
    }
    
//	System.out.println(result);
    public void parse(String input) {
        result = parser
            .process(   // this process function will return the matching information of parsing!
                new PStringScanner(input), // PStringScanner make the value of string symbolic! it is just a traditional scanner of string: charAt. subString and so on. 
                0, // the PWhitespace is the match rule! ----skipper!
                new PContext(new PDirective(new PWhitespace()))) // PContext contains 3 elements: PDirective, PTarget, errorHandler!  the last 2 are default ones! 
            .getLength();
    }
}
