/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches only if the next character on input is the character specified.
 * Uses case sensitivity directive.
 * BNF: <code>parser := 'c'</code> (where <code>c</code> is the character
 * specified)
 * @author	Martijn W. van der Lee
 */
public class PChar extends pobs.PParser {
    private char character;

    /**
     * Sole constructor which allows specification of the character to be
     * matched.
     * @param	character	the character that will be matched
     */
    public PChar(char character) {
        super();

        this.character = character;
        setErrorInfo("'" + character + "'");
    }

    /**
     * Determines if there is any input left and returns a succesful match if
     * the next character on input matches the specified character.
     */
    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {
    	  // System.out.println("Enter PChar parsing -----------------------------------------");
        if (begin < input.length()
            && context.getDirective().convert(input.charAt(begin))
                == context.getDirective().convert(this.character)) {
            return new pobs.PMatch(true, 1);
        }

        return new pobs.PMatch(false, 0);
    }
}
