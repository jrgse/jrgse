/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

/**
 * @(#) Peptide.Java 2.0 09/14/99 Michael Dean Jones
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
 * For more information on the JPAT API
 * contact Michael Jones
 * 
 * mjones@pixelgate.net http://www.pixelgate.net/mjones
 */
package jpat;

/**
 * Defines Peptide object which stores the sequence and position of the N and
 * C-terminus of the peptide in the sequence of the protein. Used primarily
 * by jpat.Digest. and jpat.ui.DigestDisplayPanel<br>
 * Example usage of an Object of type Peptide:
 * <br>
 * <code>
 * Peptide peptide = new Peptide(pepSequence, nTerm, cTerm)<br>
 * String thisPeptideSequence = peptide.pepSequence
 * </code>
 * <br>
 * 
 * @version 2.1, 3 March 2000
 * @author Michael Dean Jones
 * @see jpat.ui.DigestDisplayPanel
 */
public class Peptide implements java.io.Serializable
{

	/**
	 * sequence associated with Peptide
	 */
	public String pepSequence;

	/**
	 * Location of N-terminus of this peptide within total protein sequence
	 * 
	 */
	public int nTerm;

	/**
	 * Location of C-terminus of this peptide within total protein sequence
	 */
	public int cTerm;

	/**
	 * @param pepSequence Sequence of peptide
	 * @param nTerm Position of N-terminal of this peptide in sequence
	 * @param cTerm Position of C-terminal of this peptide in sequence
	 */
	public Peptide(String pepSequence, int nTerm, int cTerm)
	{
		this.pepSequence = pepSequence;
		this.nTerm = nTerm;
		this.cTerm = cTerm;
	}

}



