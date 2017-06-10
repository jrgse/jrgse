/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches any letter character; any alphabetic character in any of the
 * international alphabets defined in UniCode. 
 * Uses case sensitive directive.
 * @author	Martijn W. van der Lee
 */
public class PLetter extends pobs.PParser {

    /**
     * Sole constructor.
     */
    public PLetter() {
        super();
    }

    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {

        if (begin < input.length()
            && Character.isLetter(context.getDirective().convert(input.charAt(begin)))) {
            return new pobs.PMatch(true, 1);
        }

        return new pobs.PMatch(false, 0);
    }
}
