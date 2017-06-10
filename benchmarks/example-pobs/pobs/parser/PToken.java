/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches The tokens specified similar character by character.
 * Uses case sensitivity directive.
 * BNF: <code>parser := "abc"</code>
 * @author	Martijn W. van der Lee
 */
public class PToken extends pobs.PParser {
    private java.lang.String token;

    /**
     * Sole constructor.
     * @param	token	a string which is matched
     */
    public PToken(java.lang.String token) {
        super();

        this.token = token;
    }

    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {
       //System.out.println("Enter PToken parse -------"+token);
//    	System.out.println("PToken"); 
    	
        if (this.token.length() > input.length() - begin) {
            return new pobs.PMatch(false, 0);
        }

        int c = 0;

        for (; c < this.token.length() && c < input.length() - begin; c++) {
            if (context.getDirective().convert(this.token.charAt(c))
                != context.getDirective().convert(input.charAt(begin + c))) {
            	//System.out.println("failed in Ptoken is:"+c);
                return new pobs.PMatch(false, c);
            }
            else
            {
           // 	System.out.println("---match--token---"+c+"---"+begin);
            }
        }

        return new pobs.PMatch(true, c);
    }
}
