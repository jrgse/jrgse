/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

/**
 * @(#) PepTools.Java 2.0 09/14/99 Michael Dean Jones
 * 
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

import gov.nasa.jpf.jdart.Debug;

/**
 * PepTools is the superclass of classes Digest, Fragment, MassCalc and PepSearch. It defines several important
 * constants, supplies methods for formatting sequences, defining standard and custom amino acids
 * and checking for unknown amino acids.
 * @version 2.1, 3 March 2000
 * @author Michael Dean Jones
 * 
 */
public class PepTools implements java.io.Serializable
{

	// String formatSequence = "";
	String mSequence = "";
	protected Residue[] mAminoAcid;

	// boolean aaCheck;
	int AAnum = 19;

	/**
	 * Constant value of Carbon monoisotopic mass
	 */
	public static final double Cmono = 12.00000;

	/**
	 * Constant value of  Hydrogen monoisotopic mass
	 */
	public static final double Hmono = 1.0078250;

	/**
	 * Constant value of Nitrogen monoisotopic mass
	 */
	public static final double Nmono = 14.0030740;

	/**
	 * Constant value of Oxygen monoisotopic mass
	 */
	public static final double Omono = 15.9949146;

	/**
	 * Constant value of Carbon average mass
	 */
	public static final double Cavg = 12.011;

	/**
	 * Constant value of Hydrogen average mass
	 */
	public static final double Havg = 1.00794;

	/**
	 * Constant value of Nitrogen average mass
	 */
	public static final double Navg = 14.00674;

	/**
	 * Constant value of Oxygen average mass
	 */
	public static final double Oavg = 15.9994;

	/**
	 * Constructor fills AminoAcid Array.  Array needed for checking for unknown amino acids.
	 * May want to do this check from massCalc so that you keep the handle when you add
	 * unknown amino acids.
	 * @see #customAA(Residue[])
	 * @see jpat.MassCalc
	 */
	public PepTools()
	{
		fillArray();
	}

	/**
	 * Sets the sequence.
	 */
	public void setSequence(String sequence)
	{
		mSequence = sequence;
	}

	/**
	 * Get the set sequence.
	 * @return The sequence
	 */
	public String getSequence()
	{
		return mSequence;
	}

	/**
	 * Format sequence by Removing all non letters, including spaces and carriage returns, and convert to all CAPS.
	 * @return The formatted sequence as well as setting the sequence in the current PepTools object
	 */
	public String formatSequence()
	{
		String formatSequence = "";

		mSequence = mSequence.toUpperCase();

		for (int i = 0; i < mSequence.length(); i++)
		{
			char c = PepTools.getChar(mSequence, i);
			//if (Character.isLetter(PepTools.getChar(mSequence, i)))			
			if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z'))
			{
				formatSequence = formatSequence + PepTools.getChar(mSequence, i);// mSequence.charAt(i);
			}
		}

		mSequence = formatSequence;
		return mSequence;
	}

	/**
	 * Format sequence by Removing all non letters, including spaces and carriage returns, and convert to all CAPS.
	 * @return The formatted sequence as well as setting the sequence in the current PepTools object
	 */
	public String formatSequence(String unformatedSeq)
	{
		String sequence = unformatedSeq.toUpperCase();
		String formatSequence = "";

		for (int i = 0; i < sequence.length(); i++)
		{
			//if (Character.isLetter(PepTools.getChar(mSequence, i)))
			char c = PepTools.getChar(mSequence, i);
			// comment by yhb
			if ( ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z'))
			{
				formatSequence = formatSequence + PepTools.getChar(sequence, i);//sequence.charAt(i);
			}
		}

		sequence = formatSequence;
		return sequence;
	}

	/**
	 * Create initial aminoAcid array using the Residue object.
	 * Masses for all twenty common amino acids are defined here.
	 * jpat.MassCalc is the only method that currently uses these values.
	 * Can expand the initial aminoAcid Array with customAA.
	 * @see #customAA(Residue[])
	 * @see jpat.Residue
	 * @see jpat.MassCalc
	 */
	protected void fillArray()
	{
		mAminoAcid = new Residue[AAnum + 1];
		mAminoAcid[0] = new Residue('A', 71.03711, 71.0788);
		mAminoAcid[1] = new Residue('R', 156.10111, 156.1876);
		mAminoAcid[2] = new Residue('N', 114.04293, 114.1039);
		mAminoAcid[3] = new Residue('D', 115.02694, 115.0886);
		mAminoAcid[4] = new Residue('C', 103.00919, 103.1448);
		mAminoAcid[5] = new Residue('E', 129.04259, 129.1155);
		mAminoAcid[6] = new Residue('Q', 128.05858, 128.1308);
		mAminoAcid[7] = new Residue('G', 57.02146, 57.0520);
		mAminoAcid[8] = new Residue('H', 137.05891, 137.1412);
		mAminoAcid[9] = new Residue('I', 113.08406, 113.1595);
		mAminoAcid[10] = new Residue('L', 113.08406, 113.1595);
		mAminoAcid[11] = new Residue('K', 128.09496, 128.1742);
		mAminoAcid[12] = new Residue('M', 131.04049, 131.1986);
		mAminoAcid[13] = new Residue('F', 147.06841, 147.1766);
		mAminoAcid[14] = new Residue('P', 97.05276, 97.1167);
		mAminoAcid[15] = new Residue('S', 87.03203, 87.0782);
		mAminoAcid[16] = new Residue('T', 101.04768, 101.1051);
		mAminoAcid[17] = new Residue('W', 186.07931, 186.2133);
		mAminoAcid[18] = new Residue('Y', 163.06333, 163.1760);
		mAminoAcid[19] = new Residue('V', 99.0684, 99.1326);
	}

	/**
	 * @deprecated As of JPAT version 2.0,
	 * Should use setCustomAA(Residue[]).
	 * @see #setCustomAA(Residue[])
	 */
	public void customAA(Residue[] modAminoAcid)
	{
		boolean AAfound = false;

		for (int i = 0; i < modAminoAcid.length; i++)
		{
			AAfound = false;

			for (int k = 0; k < 20; k++)
			{
				if (modAminoAcid[i].name == mAminoAcid[k].name)
				{
					mAminoAcid[k] = modAminoAcid[i];
					AAfound = true;

					break;
				}
			}

			if (!AAfound)
			{
				AAnum++;

				resizeAminoAcidArray(AAnum);

				mAminoAcid[AAnum] = modAminoAcid[i];
			}
		}
	}

	/**
	 * Expand or modify the initial aminoAcid array. Using the Residue object.
	 * You can add to or modify aminoAcid array in the following way
	 * The following would change all C's to carboxyamidomethylcystine
	 * and add Z as pyroglutamic acid
	 * <p>
	 * Residue[] modAminoAcid = new Residue[2];<br>
	 * modAminoAcid[0] = new Residue('C',160.05919, 160.1948);<br>
	 * modAminoAcid[1] = new Residue('Z',111.03203, 111.1002);<br>
	 * handle.customAA(modAminoAcid);<br>
	 * <p>
	 * Note: Handle could be a PepTools handle or a handle to any of it's derived classes.
	 * Most likely MassCalc.
	 * @param modAminoAcid Residue[] object with custom amino acids described
	 * @see jpat.Residue
	 * @see jpat.MassCalc
	 */
	public void setCustomAA(Residue[] modAminoAcid)
	{
		customAA(modAminoAcid);
	}


	private void resizeAminoAcidArray(int AAnum)
	{
		Residue[] Hold = new Residue[mAminoAcid.length];

		System.arraycopy(mAminoAcid, 0, Hold, 0, mAminoAcid.length);

		mAminoAcid = new Residue[AAnum + 1];

		System.arraycopy(Hold, 0, mAminoAcid, 0, mAminoAcid.length - 1);
	}

	/**
	 * Check Sequence for amino acids that have not been defined in fillArray()
	 * or customAA.
	 * @exception UnknownAminoAcidException with message indicating which amino acids are unknown.
	 * @see PepTools#fillArray
	 * @see PepTools#customAA
	 */
	public void checkAA() throws UnknownAminoAcidException
	{
		UnknownAminoAcidException AA;
		String unknownAA = "\n";
		boolean aaCheck;
		boolean unknownAAException = false;

		for (int i = 0; i < mSequence.length(); i++)
		{
			aaCheck = false;

			for (int j = 0; j < mAminoAcid.length; j++)
			{
				if (PepTools.getChar(mSequence, i) == mAminoAcid[j].name)
				{
					aaCheck = true;
				}
			}

			if (!aaCheck)
			{
				unknownAAException = true;
				unknownAA = unknownAA + " " + PepTools.getChar(mSequence, i) + " at " + i + "\n";
			}
		}

		if (unknownAAException)
		{
			AA = new UnknownAminoAcidException(unknownAA);

			throw AA;
		}
	}

	protected void sortAA()
	{
		java.util.Vector aaList = new java.util.Vector();

		for (int i = 0; i < mAminoAcid.length; i++)
		{
			aaList.add(mAminoAcid[i]);
		}

		java.util.Collections.sort(aaList);
		aaList.toArray(mAminoAcid);
	}

	public static char getChar(String str, int i) {
		char c = str.charAt(i);
		char r = Debug.makeConcolicChar("sym_char_" + i, ""+(int)c);
		return r;
	}
	
}		// end PepTools class



