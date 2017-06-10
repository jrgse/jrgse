/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Matches a specified starting tag, content and end tag.
 * Use the config parser for parsing things like HTML tags or Java comments.
 * BNF: <code>parser := start (content - end) end</code>
 * @author	Martijn W. van der Lee
 */
public class PConfix extends pobs.PParser {
    private pobs.PObject parser;
//???: Parse content PKleene times?
    /**
     * Sole constructor.
     * @param parserStart	the starting tag
     * @param parserContent	the content parser
     * @param parserEnd		the end tag
     */
    public PConfix(
        pobs.PObject parserStart,
        pobs.PObject parserContent,
        pobs.PObject parserEnd) {

        super();

        this.parser =
            new pobs.parser.PSequence(
                parserStart,
                new pobs.parser.PExcept(parserContent, parserEnd),
                parserEnd);
    }
    
    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {

        return parser.process(input, begin, context);
    }
}
