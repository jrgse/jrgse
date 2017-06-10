/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

import pobs.PContext;
import pobs.PMatch;
import pobs.PParser;
import pobs.PScanner;

/**
 * Wrapper of a {@link PParser}. Calls are delegated to the wrapped parser.
 * 
 * @author Franz-Josef Elmer
 */
public class PWrappedParser extends PParser {
	private final PParser parser;
	public PWrappedParser(PParser parser) {
		this.parser = parser;
	}
	
	protected PMatch parse(PScanner input, long begin, PContext context) {
		return parser.process(input, begin, context);
	}

}
