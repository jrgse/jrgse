package pobs.parser;

/**
 * Matches any single character as long as it is defined in Unicode.
 * This is a terminal parser for which no BNF equivalent exists.
 * @author	Martijn W. van der Lee
 */
public class PUnicode extends pobs.PParser {
	
    /**
     * Sole constructor.
     */
    public PUnicode() {
        super();
    }
    
    public pobs.PMatch parse(
        pobs.PScanner input,
        long begin,
        pobs.PContext context) {
            
        if (begin < input.length()
            && Character.isDefined(input.charAt(begin))) {
            return new pobs.PMatch(true, 1);
        }

        return new pobs.PMatch(false, 0);
    }
}
