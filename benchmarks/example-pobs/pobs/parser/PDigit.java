/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches any digit character ranging as defined by
 * java.lang.Character.isDigit.
 * This is a terminal parser for which no BNF equivalent exists.
 * @author	Martijn W. van der Lee
 */
public class PDigit extends pobs.PParser {
    /**
     * Sole constructor.
     */
    public PDigit() {
        super();
    }

    /**
     * Checks if there is any input left and matches any single character as
     * defined by java.lang.Character.isDigit.
     */
    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {

        if (begin < input.length()
            && java.lang.Character.isDigit(input.charAt(begin))) {
            return new pobs.PMatch(true, 1);
        }

        return new pobs.PMatch(false, 0);
    }
}
