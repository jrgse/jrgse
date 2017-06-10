/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

/**
 * @(#) Digest.Java 2.0 09/14/99 Michael Dean Jones
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
 * For more information on the JPAT API
 * contact Michael Jones
 * 
 * mjones@pixelgate.net http://www.pixelgate.net/mjones
 */
package jpat;

import java.util.Vector;

/**
 * Performs digestion of the protein sequence with the specified protease.<br>
 * It is advisable that the user uses the empty constructor and then uses<br>
 * the setter methods to set the variables needed<br>
 * <b>Version 2.0 changes</b><br>
 * 1) Add beans getter and setter methods.<br>
 * 2) Re-implemented digestion algorithm.<br>
 * @version 2.1, 3 March 2000
 * @author Michael Dean Jones
 * @see jpat.Peptide
 */
public class Digest extends PepTools implements java.io.Serializable
{

	/**
	 * Variable to store cleavable residues. Cleavage residues expressed as single
	 * letter symbols which the protease will digest with are enter as a string.<br>
	 * Example: trypsin cleaves lysines and arganines so protease = "KR";
	 */
	private String mProtease = "";

	/**
	 * true If protease is an endoprotease false for exoprotease.
	 * An endoprotease cleaves on the C-terminal side of amino acid residues
	 * and exoproteasees cleave on the N-terminal side of amino acids.
	 * then this is true.
	 */
	private boolean mEndoProtease = true;

	/**
	 * If protease does not cleave if a certien residue is C-terminally adjacent
	 * to a cleavage residue that residue can be set in notCleave <br>
	 * Example: Trypsin will not cleave at a cleavage residue if the next residue
	 * is a proline.
	 * So notCleave = 'P'. May be neccesary for notCleave to be a string to handle cases
	 * were more then one AA can needs to be set.
	 */
	private char mNotCleave = ' ';

	/**
	 * Constructs parameters for a digest if the protease can cleave at all
	 * cleavable residues. <br>
	 * Example: Asp-N is a exoprotease, that cleaves at the N-terminus of Aspartic
	 * acid residues<br>
	 * newDigest = new Digest(proteinSequence, "D", false)
	 * @param sequence The sequence to be digested. All non valid characters are removed.
	 * @param protease the protease in the form of string of AA.
	 * @param endo ture if this is an endo protease.
	 */
	public Digest(String sequence, String protease, boolean endo)
	{
		mSequence = sequence;

		setSequence(mSequence);

		mProtease = protease;
		mEndoProtease = endo;
       
		//comment by yhb bellow
	   formatSequence();

		// mCleavSites = numPep();
	}

	/**
	 * Constructs parameters for a digest for proteases that cannot cleave at
	 * certain cleavable residue sites. <br>
	 * Example: Trypsin is a endoprotease, that cleaves at the C-terminus of
	 * lysine and lrganine residues unless their is a proline residue C-terminally
	 * to the cleavable residue<br>
	 * newDigest = new Digest(protineSequence, "KR", true, 'P')
	 * @param sequence The sequence to be digested. All non valid characters are removed.
	 * @param protease the protease in the form of string of AA.
	 * @param endo true if this is an endo protease.
	 * @param notCleave indicate AA to not cleave at.
	 */
	public Digest(String sequence, String protease, boolean endo, char notCleave)
	{
		mNotCleave = notCleave;
		mSequence = sequence;

		setSequence(sequence);

		mProtease = protease;
		mEndoProtease = endo;

		formatSequence();

		// mCleavSites = numPep();
	}

	/**
	 * Parameter less Constructor for beans development.
	 * Use setter and getter methods to set member variables.<br>
	 * <br>
	 * Example: For Trypsin digest<br>
	 * <code>
	 * Digest thisDigest = new Digest();<br>
	 * //No need to format it since the setter method will do it for me<br>
	 * thisDigest.setSequence(mySequenceString);<br>
	 * thisDigest.setProtease("KR");<br>
	 * thisDigest.setEndoProtease(true);<br>
	 * thisDigest.setNotCleave('P');<br>
	 * Peptide[] peptides = thisDigest.getPeptides();<br>
	 * </code>
	 */
	public Digest() {}

	/**
	 * Set the sequence to be digested.
	 * The sequence is formatted after entry to convert
	 * to all caps and remove any invalid (Non-Alpha)characters.
	 * @param newSequence description.
	 * @return description.
	 * 
	 */
	public void setSequence(String newSequence)
	{
		mSequence = formatSequence(newSequence);
	}

	/**
	 * Get sequence set for this Digest.
	 * @return sequence for this digest.
	 */
	public String getSequence()
	{
		return mSequence;
	}

	/**
	 * Set the protease for the digest.
	 * The format of the protease is a string for each amino acid that
	 * the protease cleaves at.
	 * @param newProtease A string of cleavable AA.
	 */
	public void setProtease(String newProtease)
	{
		mProtease = newProtease;
	}

	/**
	 * Get protease set for this Digest.
	 * @return the string off cleavable AA.
	 */
	public String getProtease()
	{
		return mProtease;
	}

	/**
	 * Change to a endo or an exo protease depending on the input parameter.
	 * The format of the protease is a string for each amino acid that
	 * the protease cleaves at.
	 * @param endoProtease A string off cleavable AA.
	 */
	public void setEndoProtease(boolean endoProtease)
	{
		mEndoProtease = endoProtease;
	}

	/**
	 * Determine whether this is an EndoProtease or not.
	 * @return the value of the endoProtease parameter.
	 */
	public boolean isEndoProtease()
	{
		return mEndoProtease;
	}

	/**
	 * Indicate AA that if adjacent to a cleavable residue will
	 * block cleavage.
	 * @param notCleave A non cleavable adjacent AA.
	 */
	public void setNotCleave(char notCleave)
	{
		mNotCleave = notCleave;
	}

	/**
	 * Get the value non-cleavable AA residue
	 * @return notCleave.
	 */
	public char getNotCleave()
	{
		return mNotCleave;
	}

	/**
	 * Get Peptides produced from the digest of the sequence with the current
	 * parameters.
	 * @return An array of peptides produced from the digest.
	 */
	public Peptide[] getPeptides()
	{
		return seqDigest();
	}

	/**
	 * @deprecated As of JPAT version 2.0,
	 * Use <code>getPeptides()</code> instead.
	 * @see #getPeptides()
	 */
	public Peptide[] seqDigest()
	{
		String peptide;
		int nTerm = 0;

		// Peptide[] pepArray = new Peptide[mCleavSites];
		Vector pepVector = new Vector();

		// int pepNum = 0;
		for (int j = 0; j < mSequence.length(); j++) // the given sequence long
		{
			for (int i = 0; i < mProtease.length(); i++)  // the given sequence short KR
			{
				if (PepTools.getChar(mSequence, j) == mProtease.charAt(i))
				{
					if (mEndoProtease) // this label is set to true
					{
						boolean cleave = true;

						if (j < mSequence.length() - 1)
						{
							if (PepTools.getChar(mSequence, j+1) == mNotCleave) // mNotCleave is set to ''
							{
								cleave = false;
							}
						}

						if (cleave)
						{
							peptide = mSequence.substring(nTerm, j + 1); // from begin to index j

							pepVector.addElement(new Peptide(peptide, nTerm + 1, j + 1));

							// pepArray [pepNum] = new Peptide(peptide, nTerm+1, j+1);
							nTerm = j + 1;

							// pepNum ++;
						}
					}
					else
					{
						if (j > 0)
						{
							peptide = mSequence.substring(nTerm, j);

							pepVector.addElement(new Peptide(peptide, nTerm + 1, j));

							// pepArray [pepNum] = new Peptide(peptide, nTerm+1, j);
							nTerm = j;

							// pepNum ++;
						}
					}

					break;
				}		// end if
			}			// end for i
		}				// end for j

		// if (nTerm < mSequence.length()){ pepArray [pepNum] = new Peptide(mSequence.substring(nTerm,mSequence.length()), nTerm+1, mSequence.length());}
		if (nTerm < mSequence.length())
		{
			pepVector.addElement(new Peptide(mSequence.substring(nTerm, mSequence.length()), 
											 nTerm + 1, mSequence.length()));
		}

		Peptide[] pepArray = new Peptide[pepVector.size()];

		pepVector.copyInto(pepArray);

		return pepArray;
	}		// end seqDigest()

}



