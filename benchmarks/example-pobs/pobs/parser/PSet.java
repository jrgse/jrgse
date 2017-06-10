/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches if the next character on the input matches any of a specified
 * characters.
 * Uses case sensitivity directive.
 * BNF: <code>parser := 'a' | 'b' | .. </code> (where any number of characters
 * may be specified) 
 * @author	Martijn W. van der Lee
 */
public class PSet extends pobs.PParser {
    private java.lang.String set;

    /**
     * Sole constructor.
     * @param	set		a string containing the set of characters.
     */
    public PSet(java.lang.String set) {
        this.set = set;
        setErrorInfo("a character from '" + set + "'");
    }
   
    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {
       // System.out.println("Enter Pset parsing -----------------------------------------is:"+input.charAt(begin));
       //System.out.println(set);
        if (begin < input.length()
            && set.indexOf(context.getDirective().convert(input.charAt(begin)))
                >= 0) {
        //	System.out.println("--------set-----------tru"+begin);
            return new pobs.PMatch(true, 1);
        }
        //System.out.println("--------set-----------fal"+begin);
        return new pobs.PMatch(false, 0);
    }
}
