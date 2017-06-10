/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches any number of specified parsers in the given order, each one
 * following the previous in sequence.
 * Uses the skip directive.
 * BNF: <code>this := parser[0] .. parser[..]</code> (any number of parsers
 * can be specified)
 * @author	Martijn W. van der Lee
 */
public class PSequence extends pobs.PParser {
    private pobs.PObject[] parsers = null;

    /**
     * Constructor taking an array of {@link pobs.PObject parsers}.
     * @param	parsers		an array of parsers.
     */
    public PSequence(pobs.PObject[] parsers) {
        super();

        this.parsers = parsers;
    }

    /**
     * Stub constructor taking two parsers.
     */
    public PSequence(pobs.PObject parser1, pobs.PObject parser2) {
        super();

        this.parsers = new pobs.PObject[] { parser1, parser2 };
    }

    /**
     * Stub constructor taking three parsers.
     */
    public PSequence(
        pobs.PObject parser1,
        pobs.PObject parser2,
        pobs.PObject parser3) {

        super();

        this.parsers = new pobs.PObject[] { parser1, parser2, parser3 };
    }

    /**
     * Stub constructor taking four parsers.
     */
    public PSequence(
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
    public PSequence(
        pobs.PObject parser1,
        pobs.PObject parser2,
        pobs.PObject parser3,
        pobs.PObject parser4,
        pobs.PObject parser5) {

        super();

        this.parsers =
            new pobs.PObject[] { parser1, parser2, parser3, parser4, parser5 };
    }

    /**
     * Insert the method's description here.
     */
    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {
    	  // System.out.println("Enter PSequence parsing -----------------------------------------"+begin);
        if (parsers == null) {
            throw new NullPointerException();
        }

        //???: Throw error on zero-length array?
        //	NoParserSpecified

        int length = 0;
        long skipped = 0;
        pobs.PMatch match;

        for (int p = 0; p < this.parsers.length; ++p) { 
            length += skipped;

            match = this.parsers[p].process(input, begin + length, context);

            length += match.getLength();
            //System.out.println(match.isMatch()+"curlen:"+length);

            if (!match.isMatch()) {
            	//System.out.println("failed in the element of sequence:"+p);
                return new pobs.PMatch(false, length);
            }
            else{
            	//System.out.println("succeed in the element of sequence:"+p);
            }
            skipped = context.getDirective().skip(input, begin + length);
        }

        return new pobs.PMatch(true, length);
    }
}
