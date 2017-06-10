/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

/**
 * @(#) UnknownAminoAcidException.Java 2.0 09/14/99 Michael Dean Jones
 * 
 * Copyright (C) 09/14/99 Michael Dean Jones
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * or
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * mjones@pixelgate.net http://www.pixelgate.net/mjones
 */
package jpat;

/**
 * The class UnknownAminoAcidException
 * is thrown if an unknown Amino Acid is presented to
 * PepTools.setSequence(String sequence).
 * 
 * It is called by Peptools.checkAminoAcid().
 * @version 2.1, 3 March 2000
 * @author Michael Dean Jones
 * @see jpat.PepTools#checkAA()
 * 
 */
public class UnknownAminoAcidException extends Exception implements java.io.Serializable
{

	/**
	 * Constructs an Exception with no specified detail message.
	 */
	UnknownAminoAcidException()
	{
		super();
	}

	/**
	 * Constructs an UnknownAminoAcidException with the specified detail message.
	 */
	UnknownAminoAcidException(String unknownAA)
	{
		super(unknownAA);
	}

}



