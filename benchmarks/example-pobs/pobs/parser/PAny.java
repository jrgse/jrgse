/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches any single character if there is any left on the input.
 * This is a terminal parser for which no BNF equivalent exists.
 * @author	Martijn W. van der Lee
 */
public class PAny extends pobs.PParser {
    /**
     * Sole constructor.
     */
    public PAny() {
        super();
    }

    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {

        if (begin < input.length()) {
            return new pobs.PMatch(true, 1);
        }

        return new pobs.PMatch(false, 0);
    }
}
