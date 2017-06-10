/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.directive;

import pobs.PDirective;

/**
 * Enables the skip directive.
 * 
 * @author	Martijn W. van der Lee
 * @author Franz-Josef Elmer
 */
public class PEnableSkip implements PDirectiveControl {
	public Object modifyState(PDirective directive) {
		Boolean result = directive.isSkip() ? Boolean.TRUE : Boolean.FALSE;
		directive.setSkip(true);
		return result;
	}

	public void reestablishPreviousState(PDirective directive,
										 Object previousState) {
		directive.setSkip(previousState == Boolean.TRUE);
	}
}
