/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

/**
 * @(#) PepSearch.Java 2.0 09/14/99 Michael Dean Jones
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
 * Defines tools for searching protein sequences for sequences and masses.
 * Can search by Mass range or String.<br>
 * <b>Version 2.0 changes</b><br><br>
 * 1) Re-implemented mass search algorithm.<br>
 * -Better then twice as fast for a query of a 45K protein with a mass
 * search value of 1000 +/- 2.0.<br>
 * -Time for original search algorithm 58359 ms<br>
 * -Time for current search algorithm 29172 ms<br>
 * -It is still to slow and I need to speed it up.
 * <br><br>
 * <b>Version 2.2 changes</b><br>
 * Re-implemented mass search algorithm.<br>
 * It is now pretty fast. I basically calculated
 * the min and max word size for each search mass based on
 * the largest and smallest amino acid mass. An example of the original
 * algorithm is in getMatchingAvgPeptidesSlow.
 * <br>
 * Here is the stats:
 * <p>
 * For Mass 387.0<br>
 * Slowtime was 15122 fastTime was 30<br>
 * The Ratio (SlowTime/FastTime) =504<br>
 * <br>
 * For Mass 777.0<br>
 * Slowtime was 15171 fastTime was 50<br>
 * The Ratio (SlowTime/FastTime) =303<br>
 * <br>
 * For Mass 5749.0<br>
 * Slowtime was 15232 fastTime was 1693<br>
 * The Ratio (SlowTime/FastTime) =8<br>
 * <br>
 * For Mass 40000.0<br>
 * Slowtime was 15172 fastTime was 8492<br>
 * The Ratio (SlowTime/FastTime) =1<br>
 * @version 2.2, 11 May 2000
 * @author Michael Dean Jones
 */
public class PepSearch extends PepTools implements java.io.Serializable
{
	private MassCalc massCalc = new MassCalc();

	/**
	 * sets and formats the sequence to be searched.
	 */
	public PepSearch(String sequence)
	{
		setSequence(sequence);
		formatSequence();
	}


	public PepSearch() {}


	public void setSequence(String sequence)
	{
		super.setSequence(sequence);
		formatSequence();
	}


	public String getSequence()
	{
		return super.getSequence();
	}

	/**
	 * Search the set sequence for the given searchPeptide.
	 * @param searchPeptide is the case insensitive sequence to search the protein with.
	 * @return Peptide array containing all of the matched peptides
	 */
	public Peptide[] getMatchingPeptides(String searchPeptide)
	{
		searchPeptide = searchPeptide.toUpperCase();

		int nTerm = 0;
		int pepNum = 0;
		int pepFoundN = 0;

		while (pepFoundN != -1)
		{
			pepFoundN = mSequence.indexOf(searchPeptide, nTerm);

			if (pepFoundN != -1)
			{
				nTerm = pepFoundN + searchPeptide.length();
				pepNum++;
			}
		}

		Peptide[] pepArray = new Peptide[pepNum];

		pepFoundN = 0;
		nTerm = 0;
		pepNum = 0;

		while (pepFoundN != -1)
		{
			pepFoundN = mSequence.indexOf(searchPeptide, nTerm);

			if (pepFoundN != -1)
			{
				pepArray[pepNum] = new Peptide(searchPeptide, pepFoundN + 1, 
											   pepFoundN + searchPeptide.length());
				nTerm = pepFoundN + searchPeptide.length();
				pepNum++;
			}
		}

		return pepArray;
	}

	/**
	 * Search the sequence for the given mass within the given mass range.
	 * Compares all of the possible masses that can be derived from the sequence
	 * to the searchMass +/- massRange.
	 * @param searchMass is the mass to search the protein with.
	 * @param massRange is the tolerance allowed to search the protein with.
	 * @param average is true if the entered mass is averaged and false for monoisotopic masses.
	 * @return Peptide array containing all of the matched peptides
	 */
	public Peptide[] getMatchingPeptides(double searchMass, double massRange, boolean average)
	{
		if (average)
		{
			return getMatchingAvgPeptides(searchMass, massRange);
		}
		else
		{
			return getMatchingMonoPeptides(searchMass, massRange);
		}
	}

	/**
	 * Search the sequence for the given average mass within the given mass range.
	 * Compares all of the possible masses that can be derived from the sequence
	 * to the searchMass +/- massRange.
	 * @param searchMass is the mass to search the protein with.
	 * @param massRange is the tolerance allowed to search the protein with.
	 * @return Peptide array containing all of the matched peptides
	 */
	public Peptide[] getMatchingAvgPeptides(double searchMass, double massRange)
	{

		// Sort the array first
		sortAA();


		// Get word length for lightest residue. First residue in array after sort
		char aa = mAminoAcid[0].getName();
		int lowMassWordSize = 0;
		String wordCalculateSequence = "";

		while (massCalc.calcAvgMass(wordCalculateSequence += aa) < searchMass + massRange)
		{
			lowMassWordSize++;
		}

		// Get word length for heaveast residue. Last residue in array after sort
		aa = mAminoAcid[mAminoAcid.length - 1].getName();
		wordCalculateSequence = "";

		int highMassWordSize = 0;

		while (massCalc.calcAvgMass(wordCalculateSequence += aa) < searchMass + massRange)
		{
			highMassWordSize++;
		}

		Vector pepVector = new Vector();
		double pepMass;
		String pep;

		for (int i = 0; i < mSequence.length(); i++)
		{
			for (int k = highMassWordSize + i; k <= lowMassWordSize + i; k++)
			{
				if (k > mSequence.length())
				{
					break;
				}

				pep = mSequence.substring(i, k);
				pepMass = massCalc.calcAvgMass(pep);

				if (searchMass <= pepMass + massRange && searchMass >= pepMass - massRange)
				{
					pepVector.addElement(new Peptide(pep, i + 1, k));
				}
			}
		}

		Peptide[] pepArray = new Peptide[pepVector.size()];

		pepVector.copyInto(pepArray);

		return pepArray;
	}

	/**
	 * Search the sequence for the given average mass within the given mass range.
	 * Compares all of the possible masses that can be derived from the sequence
	 * to the searchMass +/- massRange.
	 * @param searchMass is the mass to search the protein with.
	 * @param massRange is the tolerance allowed to search the protein with.
	 * @return Peptide array containing all of the matched peptides
	 */
	public Peptide[] getMatchingMonoPeptides(double searchMass, double massRange)
	{

		// Sort the array first
		sortAA();


		// Get word length for lightest residue. First residue in array after sort
		char aa = mAminoAcid[0].getName();
		int lowMassWordSize = 0;
		String wordCalculateSequence = "";

		while (massCalc.calcMonoMass(wordCalculateSequence += aa) < searchMass + massRange)
		{
			lowMassWordSize++;
		}

		// Get word length for heaveast residue. Last residue in array after sort
		aa = mAminoAcid[mAminoAcid.length - 1].getName();
		wordCalculateSequence = "";

		int highMassWordSize = 0;

		while (massCalc.calcMonoMass(wordCalculateSequence += aa) < searchMass + massRange)
		{
			highMassWordSize++;
		}

		Vector pepVector = new Vector();
		double pepMass;
		String pep;

		for (int i = 0; i < mSequence.length(); i++)
		{
			for (int k = highMassWordSize + i; k <= lowMassWordSize + i; k++)
			{
				if (k > mSequence.length())
				{
					break;
				}

				pep = mSequence.substring(i, k);
				pepMass = massCalc.calcMonoMass(pep);

				if (searchMass <= pepMass + massRange && searchMass >= pepMass - massRange)
				{

					pepVector.addElement(new Peptide(pep, i + 1, k));
				}
			}
		}

		Peptide[] pepArray = new Peptide[pepVector.size()];

		pepVector.copyInto(pepArray);

		return pepArray;
	}

	/**
	 * <b>ALERT:This is the slow use getMatchingAvgPeptides method instead.
	 * It is left in to illustrate how I optimized the algorithm.<b>
	 * Search the sequence for the given monoisotopic mass within the given mass range.
	 * Compares all of the possible masses that can be derived from the sequence
	 * to the searchMass +/- massRange.
	 * @param searchMass is the mass to search the protein with.
	 * @param massRange is the tolerance allowed to search the protein with.
	 * @return Peptide array containing all of the matched peptides
	 */
	public Peptide[] getMatchingAvgPeptidesSlow(double searchMass, double massRange)
	{
		Vector pepVector = new Vector();
		double pepMass;
		String pep;

		for (int i = 0; i < mSequence.length(); i++)
		{
			for (int k = i + 1; k <= mSequence.length(); k++)
			{
				pep = mSequence.substring(i, k);
				pepMass = massCalc.calcAvgMass(pep);

				if (searchMass <= pepMass + massRange && searchMass >= pepMass - massRange)
				{
					pepVector.addElement(new Peptide(pep, i + 1, k));
				}
			}
		}

		Peptide[] pepArray = new Peptide[pepVector.size()];

		pepVector.copyInto(pepArray);

		return pepArray;
	}

}		// end PepSearch class



