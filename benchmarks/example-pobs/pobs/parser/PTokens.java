/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches any of the tokens specified similar to an {@link pobs.parser.POr
 * POr}'ed list of [@linke pobs.parser.PToken PToken} objects.
 * Please note that, by default, this class return upon the first token in the
 * list found. Therefore you should take care in correctly ordering the tokens
 * as supplied to this class, eg. "Applepie" before "Apple".
 * Uses case sensitivity and alternatives directive.
 * BNF: <code>parser := "aa" | "bb" | .. | "zz" </code> (where any number of
 * strings can be specified)
 * @author	Martijn W. van der Lee
 */
public class PTokens extends pobs.PParser {
    private java.lang.String[] tokens;

    /**
     * Sole constructor.
     * @param	tokens	sorted array of strings
     */
    public PTokens(java.lang.String[] tokens) {
        super();

        this.tokens = tokens;
    }

    public PTokens(java.lang.String token1, java.lang.String token2) {
        super();

        this.tokens = new java.lang.String[] { token1, token2 };
    }

    public PTokens(
        java.lang.String token1,
        java.lang.String token2,
        java.lang.String token3) {
        super();

        this.tokens = new java.lang.String[] { token1, token2, token3 };
    }

    public PTokens(
        java.lang.String token1,
        java.lang.String token2,
        java.lang.String token3,
        java.lang.String token4) {
        super();

        this.tokens = new java.lang.String[] { token1, token2, token3, token4 };
    }

    public PTokens(
        java.lang.String token1,
        java.lang.String token2,
        java.lang.String token3,
        java.lang.String token4,
        java.lang.String token5) {
        super();

        this.tokens =
            new java.lang.String[] { token1, token2, token3, token4, token5 };
    }

    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {
    	
//    	System.out.println("PTokens");
    	
        pobs.PMatch match = new pobs.PMatch(false, 0);

        //???: Shortcut for FIRST mode?

        token : for (int t = 0; t < this.tokens.length; ++t) {
            int c = 0;
            for (;
                c < this.tokens[t].length() && c < input.length() - begin;
                ++c) {
                if (context.getDirective().convert(this.tokens[t].charAt(c))
                    != context.getDirective().convert(input.charAt(begin + c))) {
                    match =
                        context.getDirective().alternative(
                            match,
                            new pobs.PMatch(false, c));
                    continue token;
                }
            }
            match = context.getDirective().alternative(match, new pobs.PMatch(true, c));
        }

        return match;
    }
}