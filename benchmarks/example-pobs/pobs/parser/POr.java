/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Tries a number of parsers specified until one of them matches. The order of
 * the parsers determines the order of parsing.
 * Only the parsers upto the first which matches the input are parsed so be sure
 * to put the longest of two parses which share the same beginning before the
 * other. To clarify, <code>rule := "a" | "ab"</code> will match "a" even when
 * input is "abc" because the second parser is never evaluated if the first one
 * matched. 
 * Uses alternatives directive.
 * BNF: <code>this := parser[0] | .. | parser[..]</code> (any number of parsers
 * can be specified).
 * @author	Martijn W. van der Lee
 */
public class POr extends pobs.PParser {
    private pobs.PObject[] parsers;

    /**
     * Constructor taking an array of any number of parsers.
     * @param parsers	array of parsers
     */
    public POr(pobs.PObject[] parsers) {
        super();

        this.parsers = parsers;
    }

    /**
     * Stub constructor taking two parsers.
     */
    public POr(pobs.PObject parser1, pobs.PObject parser2) {
        super();

        this.parsers = new pobs.PObject[] { parser1, parser2 };
    }

    /**
     * Stub constructor taking three parsers.
     */
    public POr(
        pobs.PObject parser1,
        pobs.PObject parser2,
        pobs.PObject parser3) {

        super();

        this.parsers = new pobs.PObject[] { parser1, parser2, parser3 };
    }

    /**
     * Stub constructor taking four parsers.
     */
    public POr(
        pobs.PObject parser1,
        pobs.PObject parser2,
        pobs.PObject parser3,
        pobs.PObject parser4) {

        super();

        this.parsers =
            new pobs.PObject[] { parser1, parser2, parser3, parser4 };
    }

    /**
     * Stub constructor taking five parsers.
     */
    public POr(
        pobs.PObject parser1,
        pobs.PObject parser2,
        pobs.PObject parser3,
        pobs.PObject parser4,
        pobs.PObject parser5) {

        super();

        this.parsers =
            new pobs.PObject[] { parser1, parser2, parser3, parser4, parser5 };
    }

    public static int count=1000;
    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {
    	 //  System.out.println("Enter Por parsing -----------------------------------------");
		pobs.PMatch match = new pobs.PMatch(false, 0);

        if (context.getDirective().getAlternatives() == pobs.PDirective.FIRST) { // it will enter the true branch for our test!
            for (int p = 0; p < this.parsers.length; ++p) {
            	//System.out.println("hahah"+count+this.parsers[p].toString()+"huhu"+this.parsers.length);
            	count++;
                pobs.PMatch m = this.parsers[p].process(input, begin, context);
                if (m.isMatch()) {
                //	System.out.println("matched of an element in Por!!");
                	return m;
                }
                else{
                //	System.out.println("failed in the element of Por is"+p);
                }
                if(m.getLength() > match.getLength()) {
                	match = m;
                }
            }
        } else {
            for (int p = 0; p < this.parsers.length; ++p) { // return the selected match!
            	count++;
                match =
						context.getDirective().alternative(
                        match,
                        this.parsers[p].process(input, begin, context));
            }
        }
        count=1000;
		return match;
    }
}
