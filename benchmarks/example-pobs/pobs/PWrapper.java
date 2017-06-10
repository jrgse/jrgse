/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs;

/**
 * Wraps any object so it can be bound to other objectes.
 * 
 * @author Martijn W. van der Lee
 */
public class PWrapper {
	public java.lang.Object object;

	/**
	 * Sole constructor.
	 * 
	 * @param object
	 *            an object which is to be accessed through binding
	 */
	public PWrapper(Object object) {
		super();

		this.object = object;
	}
}