/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches any single character which is part of the specified UnicodeBlock.
 * Note that this class explicitely does not use the case sensitive directive.
 * @author	Martijn W. van der Lee
 */
public class PUnicodeBlock extends pobs.PParser {
    public java.lang.Character.UnicodeBlock unicodeBlock;

    /**
     * Sole constructor taking the UnicodeBlock to match.
     * @param	unicodeBlock	the UnicodeBlock to match
     */
    public PUnicodeBlock(java.lang.Character.UnicodeBlock unicodeBlock) {
        super();

        this.unicodeBlock = unicodeBlock;
    }

    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {

        if (begin < input.length()
            && java.lang.Character.UnicodeBlock.of(input.charAt(begin))
                == this.unicodeBlock) {
            return new pobs.PMatch(true, 1);
        }

        return new pobs.PMatch(false, 0);
    }
}
