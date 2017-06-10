/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs;


/**
 * @author Franz-Josef Elmer
 */
public interface PErrorHandler {
	/**
	 * Notifies the error handler that an error occured for the
	 * specified parser at the specified parsing position. This method
	 * might be called several times even if complete parsing is successful.
	 * 
	 * @param position	Parsing position.
	 * @param parser	Parser which recognized a missmatch.
	 */
	public void notify(long position, PParser parser);
	
	/**
	 * Gets the actual error position.
	 * @return actual parsing position where the error occured. 
	 */
	public long getErrorPosition();
	
	/**
	 * Creates a human readable error message using the specified input
	 * to get reasonable error positions (like line number and column number). 
	 * @param input Input source of parsing. 
	 * @return error message.
	 */
	public String createErrorMessage(PScanner input);
}
