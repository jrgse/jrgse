package pobs.scanner;

//TODO: Testcase!

/**
 * Remembers the position last scanned in line/column format.
 * XXX: This class is currently not threadsafe
 * 
 * @author Martijn W. van der Lee
 */
public class PLineScanner implements pobs.PScanner {
	final static public byte CRLF = 0;

	final static public byte CR = 1;

	final static public byte LF = 2;

	private pobs.PScanner scanner;

	private boolean absoluteTab = true;

	private int tabSize = 4;

	private long previousIndex = -1;

	private long line = 1;

	private long column = 1;

	private byte endOfLine = CRLF;

	/**
	 * Sole constructor, takes another scanner.
	 * 
	 * @param string
	 *            the source string for this iterator
	 */
	public PLineScanner(pobs.PScanner scanner) {
		super();

		this.scanner = scanner;
	}

	public char charAt(long index) throws IndexOutOfBoundsException {
		parseUptoIndex(index);
		// System.out.println("----PLineScanner--------"+scanner.charAt(index));
		return scanner.charAt(index);
		
//      char c = scanner.charAt(index);
//      char r = Debug.makeConcolicChar("sym_char_" + index, ""+(int)c);
//      System.out.println("BeginToken------" + index +"--"+ r + ":"+(int)r);
//      return r;
	}

	/**
	 * Insert the method's description here.
	 * 
	 * @return long
	 */
	public long getColumn() {
		return column;
	}

	/**
	 * Insert the method's description here.
	 * 
	 * @return byte
	 */
	public byte getEndOfLine() {
		return endOfLine;
	}

	/**
	 * Insert the method's description here.
	 * 
	 * @return long
	 */
	public long getLine() {
		return line;
	}

	/**
	 * Insert the method's description here.
	 * 
	 * @return int
	 */
	public int getTabSize() {
		return tabSize;
	}

	/**
	 * Insert the method's description here.
	 * 
	 * @return boolean
	 */
	public boolean isAbsoluteTab() {
		return absoluteTab;
	}

	public long length() {
		return scanner.length();
	}

	/**
	 * Parses the scanner upto the specified index
	 */
	public void parseUptoIndex(long newIndex) {
		if (newIndex == 0) {
			reset();
		} else if (newIndex <= previousIndex) {
			return;
		}

		for (long index = previousIndex + 1; index <= newIndex; ++index) {

			switch (scanner.charAt(index)) {

			case '\t':
				if (absoluteTab) {
					column += tabSize - ((column - 1) % tabSize);
				} else {
					column += tabSize;
				}
				break;

			case '\n':
				if (endOfLine == LF) {
					++line;
					column = 1;
				} else if (endOfLine != CRLF) {
					++column;
				}
				break;

			case '\r':
				if (endOfLine == CRLF || endOfLine == CR) {
					++line;
					column = 1;
				} else {
					++column;
				}
				break;

			default:
				++column;

			}
		}

		previousIndex = newIndex;
	}

	/**
	 * Insert the method's description here.
	 *  
	 */
	public void reset() {
		column = 1;
		line = 1;
		previousIndex = -1;
	}

	/**
	 * Insert the method's description here.
	 * 
	 * @param newAbsoluteTab
	 *            boolean
	 */
	public void setAbsoluteTab(boolean newAbsoluteTab) {
		absoluteTab = newAbsoluteTab;
	}

	/**
	 * Insert the method's description here.
	 * 
	 * @param newEndOfLine
	 *            byte
	 */
	public void setEndOfLine(byte newEndOfLine) {
		endOfLine = newEndOfLine;
	}

	/**
	 * Insert the method's description here.
	 * 
	 * @param newTabSize
	 *            int
	 */
	public void setTabSize(int newTabSize) {
		tabSize = newTabSize;
	}

	public java.lang.String substring(long beginIndex, long endIndex) {
		parseUptoIndex(endIndex);
		return scanner.substring(beginIndex, endIndex);
	}
}