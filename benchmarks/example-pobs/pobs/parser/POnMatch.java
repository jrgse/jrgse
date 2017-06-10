/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Utility parser which allows actions to be performed at specific stages during
 * the parsing of the input on either a succesful match, a failed match or both.
 * This parser does not add anything to the actual parsing but merely performs
 * the specified {@link pobs.PAction actions} where appropriate.
 * @author	Martijn W. van der Lee
 */
public class POnMatch extends pobs.PParser {
    private pobs.PParser parser;

    /**
     * Match-only constructor.
     * Specifies only the action to be performed on a succesful match of the
     * specified parser.
     * @param	parser			a parser which may trigger the action		
     * @param	action_match	an action performed upon a succesful match 
     */
    public POnMatch(pobs.PParser parser, pobs.PAction action_match) {
        super();

        this.parser = parser;
        this.parser.setMatchAction(action_match);
    }

    /**
     * Match and mismatch constructor.
     * Specifies both the actions to be performed on a succesful match and on a
     * mismatch of the specified parser.
     * @param	parser			a parser which may trigger the action		
     * @param	action_match	an action performed upon a succesful match 
     * @param	action_mismatch	an action performed upon a failed match 
     */
    public POnMatch(
        pobs.PParser parser,
        pobs.PAction action_match,
        pobs.PAction action_mismatch) {

        super();

        this.parser = parser;
		this.parser.setMatchAction(action_match);
		this.parser.setMismatchAction(action_mismatch);
    }

    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {

        if (this.parser == null) {
            throw new NullPointerException();
        }

        return this.parser.process(input, begin, context);
    }
}
