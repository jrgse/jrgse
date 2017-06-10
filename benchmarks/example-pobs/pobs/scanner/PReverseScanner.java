/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.scanner;
//TODO: Testcase!

/**
 * Reverses any specified scanner.
 * @author	Martijn W. van der Lee
 */
public class PReverseScanner implements pobs.PScanner {
	private pobs.PScanner scanner;

	/**
	 * Sole constructor, takes another scanner.
	 * @param	string	the source string for this iterator
	 */
	public PReverseScanner(pobs.PScanner scanner) {
		super();

		this.scanner = scanner;
	}

	public char charAt(long index) throws IndexOutOfBoundsException {
		char a = scanner.charAt(scanner.length() - ++index);
	//	System.out.println("----PReverseScanner--------"+a);
		return a;
		
//		return scanner.charAt(scanner.length() - ++index);
		
//      char c = scanner.charAt(scanner.length() - ++index);
//      char r = Debug.makeConcolicChar("sym_char_" + scanner.length() - ++index, ""+(int)c);
//      System.out.println("BeginToken------" + scanner.length() - ++index +"--"+ r + ":"+(int)r);
//      return r;
	}

	public long length() {
		return scanner.length();
	}

	public java.lang.String substring(long beginIndex, long endIndex) {
		return scanner.substring(
			scanner.length() - ++beginIndex,
			scanner.length() - ++endIndex);
	}
}
