/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches any sequence of atleast one whitespace character, as defined by
 * Character.isWhitespace, from input.
 * @author	Martijn W. van der Lee
 */
public class PWhitespace extends pobs.PParser {
	
    /**
     * Sole constructor.
     */
    public PWhitespace() {
        super();
    }
    
    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {
        	
        long position = begin;
        while (position < input.length()
            && Character.isWhitespace(input.charAt(position))) {
            ++position;
        }

        if (position > begin) {
            return new pobs.PMatch(true, position - begin);
        }

        return new pobs.PMatch(false, 0);
    }
}
