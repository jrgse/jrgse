/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.utility;

import pobs.PParser;
import pobs.directive.PDisableSkip;
import pobs.parser.PAny;
import pobs.parser.PChar;
import pobs.parser.PMultiple;
import pobs.parser.POptional;
import pobs.parser.PRange;
import pobs.parser.PSequence;
import pobs.parser.PSet;
import pobs.parser.PWrappedParser;

/**
 * Contains a number of commonly used predefined parsers for convenience.
 * 
 * @author	Martijn W. van der Lee
 */
public class POBS {

    private POBS() {}

    /**
     * Matches all ASCII alphabetical characters and digits, both lower- and
     * uppercase.
     */
    public final static PParser ALPHA_NUM = new PRange("0-9a-zA-Z");

    /**
     * Matches all ASCII alphabetical characters, both lower- and uppercase.
     */
    public final static PParser ALPHA = new PRange("a-zA-Z");

    /**
     * Matches all ASCII lowercase alphabetical characthers.
     */
    public final static PParser LOWER_CASE = new PRange("a-z");

    /**
     * Matches all ASCII uppercase alphabetical characters.
     */
    public final static PParser UPPER_CASE = new PRange("A-Z");

    /**
     * Matches any single character.
     */
    public final static PParser ANY = new PAny();

    /**
     * Matches all valid ASCII characters for the binary system, both lower- and
     * uppercase.
     */
    public final static PParser BINARY_DIGIT = new PSet("01");

	/**
	 * Matches a single ASCII digits 0 to 9.
	 */
	public final static PParser DIGIT = new PRange("0-9");
	
	/**
	 * Matches a series ASCII digits 0 to 9.
	 */
	public final static PParser DIGITS = new PMultiple(DIGIT);

    /**
     * Matches all valid ASCII characters for the octal system, both lower- and
     * uppercase.
     */
    public final static PParser OCTAL_DIGIT = new PRange("0-7");

    /**
     * Matches all valid ASCII characters for the hexadecimal system,
     * both lower- and uppercase.
     */
    public final static PParser HEX_DIGIT = new PRange("0-9a-fA-F");

    /**
     * Matches all the ASCII characters for the positive and negative signs.
     */
   // public final static PParser SIGN = new PSet("-+");
    public final static PParser SIGN = new PSet("max");
    // Helpers for memory profile reasons
    private final static PParser DOT = new PChar('.');
    private final static PParser OPT_SIGN = new POptional(SIGN);
    private final static PParser UINT = new PMultiple(DIGIT);

    /**
     * Matches an unsigned decimal integer of any length.
     */
    public final static PParser UNSIGNED_INT 
			= new PWrappedParser(UINT).addControl(new PDisableSkip());;

    /**
     * Matches a signed decimal integer of any length.
     */
    public final static PParser SIGNED_INT 
        	= new PSequence(OPT_SIGN, UINT).addControl(new PDisableSkip());

    /**
     * Matches an unsigned floating decimal of any length.
     */
    public final static PParser UNSIGNED_FLOAT 
		= new PSequence(UINT, DOT, UINT).addControl(new PDisableSkip());

    /**
     * Matches a signed floating decimal of any length.
     */
    public final static PParser SIGNED_FLOAT 
		= new PSequence(OPT_SIGN, UINT, DOT, UINT).addControl(new PDisableSkip());
}