/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs;

import java.util.Vector;

/**
 * @author Franz-Josef Elmer
 */
public class PDefaultErrorHandler implements PErrorHandler {
	private static class ErrorEntry {
		public final long position;
		public final String info;
		public ErrorEntry(long position, PParser parser) {
			this.position = position;
			this.info = parser.getErrorInfo();
		}
	}
	
	private final Vector list = new Vector();
	private long maxPosition;
	
	public void notify(long position, PParser parser) {
		list.addElement(new ErrorEntry(position, parser));
		maxPosition = Math.max(maxPosition, position);
	}
	
	public long getErrorPosition() {
		return maxPosition;
	}

	public String createErrorMessage(PScanner input) {
		removeEntriesNotAtMaxPosition();
		StringBuffer buffer = new StringBuffer();
		buffer.append("Error at ").append(maxPosition).append(": ");
		for (int i = 0, n = list.size(); i < n; i++) {
			ErrorEntry errorEntry = (ErrorEntry) list.elementAt(i);
			String delim = "";
			if (i > 0) {
				if (i < n - 1) {
					delim = ", ";
				} else {
					delim = n > 2 ? ", or " : " or ";
				}
			}
			buffer.append(delim).append(errorEntry.info);
		}
		return buffer.append(" expected.").toString();
	}

	/**
	 * 
	 */
	private void removeEntriesNotAtMaxPosition() {
		for (int i = list.size() - 1; i >= 0; i--) {
			ErrorEntry errorEntry = (ErrorEntry) list.elementAt(i);
			if (errorEntry.position < maxPosition 
					|| errorEntry.info == null) {
				list.removeElementAt(i);
			}
		}
	}

}
