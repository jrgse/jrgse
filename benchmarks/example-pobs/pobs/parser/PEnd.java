/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches successfull when no more input is available, always returns a length
 * of zero.
 * @author	Martijn W. van der Lee
 */
public class PEnd extends pobs.PParser {
    /**
     * Sole constructor.
     */
    public PEnd() {
        super();
    }

    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {

        if (begin >= input.length()) {
            return new pobs.PMatch(true, 0);
        }

        return new pobs.PMatch(false, 0);
    }
}
