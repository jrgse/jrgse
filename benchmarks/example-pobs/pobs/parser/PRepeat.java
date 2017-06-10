/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.parser;

/**
 * Non-terminal parser which matches the same parser multiple times, optionally
 * limited.
 * Uses skip directive.
 * BNF: <code>this := parser[min, max]</code> (where <code>min</code> is the
 * minimum number of repeats required and <code>max</code> is the maximum number
 * of repeats allowed)
 * @author	Martijn W. van der Lee
 */
public class PRepeat extends pobs.PParser {
    private long min;
    private long max;
    private pobs.PObject parser;

    /**
     * Repeat upto any number of times.
     * Though this field specifies a fixed number of repetitions, it is for
     * all practical purposes infinite.
     */
    public final static long INFINITE = java.lang.Long.MAX_VALUE;

    /**
     * Constructor allowing specification of a lower and upper boundary to
     * the number of repetitions.
     * @param	parser	the parser to be repeated
     * @param	min		the minimum number of repetitions required. 
     * @param	max		the maximum number of repetitions required. 
     */
    public PRepeat(pobs.PObject parser, long min, long max) {
        super();

        this.parser = parser;
        this.min = min;
        this.max = max;
    }

    /**
     * Stub constructor allow specification an exact number of repetitions.
     * @param	parser	the parser to be repeated
     * @param	count	the exact number of repetitions required. 
     */
    public PRepeat(pobs.PObject parser, long count) {
        this(parser, count, count);
    }

    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {
    	  // System.out.println("Enter PRepeat parsing -----------------------------------------min is:"+this.min);
        int matches = 0;
        int length = 0;
        pobs.PMatch match = this.parser.process(input, begin + length, context);
        
        //???: Special case for exact repetitions (if min == max);

        while (matches < this.max && match.isMatch()) {
            length += match.getLength();

            if (matches < this.min && !match.isMatch()) {
                return new pobs.PMatch(false, length);
            }

            ++matches;

            length += context.getDirective().skip(input, begin + length);

            match = this.parser.process(input, begin + length, context);
        }

        return new pobs.PMatch(matches >= this.min, length);
    }
}
