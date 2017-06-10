/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

/**
 * @(#) Fragment.Java 2.0 09/14/99 Michael Dean Jones
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
 * mjones@pixelgate.net http://www.pixelgate.net/mjones/
 */
package jpat;

import java.util.Vector;

/**
 * Calculates fragments generated from fragmentation of peptide sequences<BR>
 * during MS/MS or PSD MS analysis. Stores result of fragmentation<BR>
 * in FragIon arrays. Right now the fragments return the end mass. i.e. y7 for<BR>
 * peptide of length 7. I am not sure if this is proper as others don't always<BR>
 * include the C-terminal mass.<br>
 * <b>Version 2.0 changes</b><br>
 * 1) inverted the order of reporting of all y-type fragments<BR>
 * since they are usually displayed in the opposite direction<BR>
 * as b-type ions.<BR>
 * 2) Added getFragmentSeries method that returns the fragment series<BR>
 * given as a function argument to the method<BR>
 * @version 2.1, 3 March 2000
 * @author Michael Dean Jones
 * @see jpat.FragIon
 */
public class Fragment extends PepTools implements java.io.Serializable
{

	/**
	 * a type ions
	 */
	public static final String A_SERIES = "a";

	/**
	 * b type ions
	 */
	public static final String B_SERIES = "b";

	/**
	 * c type ions
	 */
	public static final String C_SERIES = "c";

	/**
	 * x type ions
	 */
	public static final String X_SERIES = "x";

	/**
	 * y type ions
	 */
	public static final String Y_SERIES = "y";

	/**
	 * z type ions
	 */
	public static final String Z_SERIES = "z";

	/**
	 * a-H2O type ions. Neutral loss of water from a ions
	 */
	public static final String A_MINUS_H2O_SERIES = "a-H2O";

	/**
	 * a-H2O type ions. Neutral loss of ammonia from a ions
	 */
	public static final String A_MINUS_NH3_SERIES = "a-NH3";

	/**
	 * b-H2O type ions. Neutral loss of water from b ions
	 */
	public static final String B_MINUS_H2O_SERIES = "b-H20";

	/**
	 * b-H2O type ions. Neutral loss of ammonia from b ions
	 */
	public static final String B_MINUS_NH3_SERIES = "b-NH3";

	/**
	 * y-H2O type ions. Neutral loss of ammonia from y ions
	 */
	public static final String Y_MINUS_NH3_SERIES = "y-NH3";

	/**
	 * b-internal type ions. Ions produced from internal cleavage
	 */
	public static final String B_INTERNAL_SERIES = "b-internal";

	/**
	 * b-internal type ions. Ions produced from internal cleavage at Proline residues
	 */
	public static final String B_INTERNAL_PRO_SERIES = "internal-Pro";

	/**
	 * Immonium type ions.
	 */
	public static final String IMMONIUM_ION_SERIES = "immonium";
	private MassCalc massCalc = new MassCalc();

	/**
	 * Sets and formats the sequence.
	 */

	/*
	 * Major change I inverted the order of the arrays returned from all y-type
	 * fragments.
	 */
	public Fragment(String sequence)
	{

		// mSequence = sequence;
		super.setSequence(sequence);
		formatSequence();

		massCalc = new MassCalc();
	}


	public Fragment() {}


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
	 * Fragments the according to the type of ion series desired.
	 * @return FragIon array containing each of the fragments produced by a fragmentation of this peptide.
	 * Returns null if an invalid series is requested.
	 * @see jpat.FragIon
	 */
	public FragIon[] getFragmentSeries(String fragType)
	{
		if (fragType.equals(A_SERIES))
		{
			return getAFragments();
		}
		else if (fragType.equals(B_SERIES))
		{
			return getBFragments();
		}
		else if (fragType.equals(C_SERIES))
		{
			return getCFragments();
		}
		else if (fragType.equals(X_SERIES))
		{
			return getXFragments();
		}
		else if (fragType.equals(Y_SERIES))
		{
			return getYFragments();
		}
		else if (fragType.equals(Z_SERIES))
		{
			return getZFragments();
		}
		else if (fragType.equals(A_MINUS_H2O_SERIES))
		{
			return getAMinusH2OFragments();
		}
		else if (fragType.equals(A_MINUS_NH3_SERIES))
		{
			return getAMinusNH3Fragments();
		}
		else if (fragType.equals(Y_MINUS_NH3_SERIES))
		{
			return getYMinusNH3Fragments();
		}
		else if (fragType.equals(B_MINUS_H2O_SERIES))
		{
			return getBMinusH2OFragments();
		}
		else if (fragType.equals(B_MINUS_NH3_SERIES))
		{
			return getBMinusNH3Fragments();
		}
		else if (fragType.equals(B_INTERNAL_SERIES))
		{
			return getInternalBFragments();
		}
		else if (fragType.equals(B_INTERNAL_PRO_SERIES))
		{
			return getProInternalBFragments();
		}
		else if (fragType.equals(IMMONIUM_ION_SERIES))
		{
			return this.getImmoniumIon();
		}
		else
		{
			return null;
		}
	}

	/**
	 * Fragments the peptide producing 'a' type fragments.  Must subtract 2 hydrogen's, two oxygen's and
	 * one carbon from calculated peptide mass.
	 * @return FragIon array containing each of the fragments produced by a fragmentation of this peptide.
	 * The length of the array is the same as the number of fragments produced.
	 * @see jpat.FragIon
	 */
	public FragIon[] getAFragments()
	{
		int fragNum = 0;
		FragIon[] fragArray = new FragIon[mSequence.length()];

		for (int i = 1; i < mSequence.length() + 1; i++)
		{
			String frag = mSequence.substring(0, i);
			double monoMass = -2 * Hmono - Omono - Cmono - Omono;		// Subtract H2O from mass for b-Fragments
			double averageMass = -2 * Havg - Oavg - Cavg - Oavg;	// Add here for H+ on MH+

			monoMass = monoMass + massCalc.calcMonoMass(frag);
			averageMass = averageMass + massCalc.calcAvgMass(frag);
			fragArray[fragNum] = new FragIon(frag, monoMass, averageMass);
			fragNum++;
		}

		return fragArray;
	}

	/**
	 * Fragments the peptide producing 'b' type fragments.  Must subtract 2 hydrogen's, one oxygen and
	 * from calculated peptide mass.
	 * @return FragIon array containing each of the fragments produced by a fragmentation of this peptide.
	 * The length of the array is the same as the number of fragments produced.
	 * @see jpat.FragIon
	 */
	public FragIon[] getBFragments()
	{
		int fragNum = 0;
		FragIon[] fragArray = new FragIon[mSequence.length()];

		for (int i = 1; i < mSequence.length() + 1; i++)
		{
			String frag = mSequence.substring(0, i);
			double monoMass = -2 * Hmono - Omono;		// Subtract H2O from mass for b-Fragments
			double averageMass = -2 * Havg - Oavg;		// Add here for H+ on MH+

			monoMass = monoMass + massCalc.calcMonoMass(frag);
			averageMass = averageMass + massCalc.calcAvgMass(frag);
			fragArray[fragNum] = new FragIon(frag, monoMass, averageMass);
			fragNum++;
		}

		return fragArray;
	}

	/**
	 * Fragments the peptide producing 'c' type fragments.  Must subtract 2 hydrogen's, two oxygen's and
	 * add one Nitrogen and one hydrogen from calculated peptide mass.  c type fragments can not be made
	 * from proline peptide bonds.
	 * @return FragIon array containing each of the fragments produced by a fragmentation of this peptide.
	 * The length of the array is the same as the number of fragments produced.
	 * @see jpat.FragIon
	 */
	public FragIon[] getCFragments()
	{
		int fragNum = 0;
		FragIon[] fragArray = new FragIon[mSequence.length()];

		for (int i = 1; i < mSequence.length() + 1; i++)
		{
			String frag = mSequence.substring(0, i);
			double monoMass = -Omono + Nmono + Hmono;		// Subtract H2O from mass for b-Fragments
			double averageMass = -Oavg + Navg + Havg;		// Add here for H+ on MH+

			monoMass = monoMass + massCalc.calcMonoMass(frag);
			averageMass = averageMass + massCalc.calcAvgMass(frag);

			if (i == mSequence.length())
			{
				monoMass = 0.00;
				averageMass = 0.00;
			}
			else
			{
				if (mSequence.charAt(i - 1) == 'P')
				{
					monoMass = 0.00;
					averageMass = 0.00;
				}
			}

			fragArray[fragNum] = new FragIon(frag, monoMass, averageMass);
			fragNum++;
		}

		return fragArray;
	}

	/**
	 * Fragments the peptide producing 'x' type fragments.  Must subtract 2 hydrogen's,  and
	 * add one Carbon and one Oxygen from calculated peptide mass.
	 * @return FragIon array containing each of the fragments produced by a fragmentation of this peptide.
	 * The length of the array is the same as the number of fragments produced.
	 * @see jpat.FragIon
	 */
	public FragIon[] getXFragments()
	{
		int fragNum = 0;
		FragIon[] fragArray = new FragIon[mSequence.length()];

		for (int i = mSequence.length() - 1; i >= 0; i--)
		{
			String frag = mSequence.substring(i, mSequence.length());
			double monoMass = Cmono + Omono - 2 * Hmono;	// Subtract H2O from mass for b-Fragments
			double averageMass = Cavg + Oavg - 2 * Hmono;		// Add here for H+ on MH+

			monoMass = monoMass + massCalc.calcMonoMass(frag);
			averageMass = averageMass + massCalc.calcAvgMass(frag);

			if (i == 0)
			{
				monoMass = 0.00;
				averageMass = 0.00;
			}

			fragArray[fragArray.length - fragNum - 1] = new FragIon(frag, monoMass, 
					averageMass);
			fragNum++;
		}

		return fragArray;
	}

	/**
	 * Fragments the peptide producing 'y' type fragments.
	 * @return FragIon array containing each of the fragments produced by a fragmentation of this peptide.
	 * The length of the array is the same as the number of fragments produced.
	 * @see jpat.FragIon
	 */
	public FragIon[] getYFragments()
	{
		int fragNum = 0;
		FragIon[] fragArray = new FragIon[mSequence.length()];

		for (int i = mSequence.length() - 1; i >= 0; i--)
		{
			String frag = mSequence.substring(i, mSequence.length());
			double monoMass = 0.00;			// Subtract H2O from mass for b-Fragments
			double averageMass = 0.00;		// Add here for H+ on MH+

			monoMass = monoMass + massCalc.calcMonoMass(frag);
			averageMass = averageMass + massCalc.calcAvgMass(frag);
			fragArray[fragArray.length - fragNum - 1] = new FragIon(frag, monoMass, 
					averageMass);
			fragNum++;
		}

		return fragArray;
	}

	/**
	 * Fragments the peptide producing 'z' type fragments.  Must subtract 3 hydrogen's  and
	 * one Nitrogen from calculated peptide mass.
	 * @return FragIon array containing each of the fragments produced by a fragmentation of this peptide.
	 * The length of the array is the same as the number of fragments produced.
	 * @see jpat.FragIon
	 */
	public FragIon[] getZFragments()
	{
		int fragNum = 0;
		FragIon[] fragArray = new FragIon[mSequence.length()];

		for (int i = mSequence.length() - 1; i >= 0; i--)
		{
			String frag = mSequence.substring(i, mSequence.length());
			double monoMass = -Nmono - 3 * Hmono;		// Subtract H2O from mass for b-Fragments
			double averageMass = -Navg - 3 * Havg;		// Add here for H+ on MH+

			monoMass = monoMass + massCalc.calcMonoMass(frag);
			averageMass = averageMass + massCalc.calcAvgMass(frag);

			if (mSequence.charAt(i) == 'P')
			{
				monoMass = 0.00;
				averageMass = 0.00;
			}

			fragArray[fragArray.length - fragNum - 1] = new FragIon(frag, monoMass, 
					averageMass);
			fragNum++;
		}

		return fragArray;
	}

	/**
	 * Fragments the peptide producing 'a' type fragments and subtracts one water if peptide contains
	 * serine or threonine.  Must subtract 2 hydrogen's, two oxygen's and
	 * one carbon from calculated peptide mass.
	 * @return FragIon array containing each of the fragments produced by a fragmentation of this peptide.
	 * The length of the array is the same as the number of fragments produced.
	 * @see jpat.FragIon
	 */

	public FragIon[] getAMinusH2OFragments()
	{
		int fragNum = 0;
		FragIon[] fragArrayNoLoss = getAFragments();
		FragIon[] fragArray = new FragIon[mSequence.length()];

		for (int i = 0; i < fragArrayNoLoss.length; i++)
		{
			String frag = fragArrayNoLoss[i].frag;

			if (frag.indexOf("T") == -1 && frag.indexOf("S") == -1)
			{
				fragArray[i] = new FragIon(frag, 0.00, 0.00);
			}
			else
			{
				double monoMass = fragArrayNoLoss[i].monoMass - (2 * Hmono + Omono);
				double averageMass = fragArrayNoLoss[i].averageMass - (2 * Havg + Oavg);

				fragArray[i] = new FragIon(frag, monoMass, averageMass);
			}
		}

		return fragArray;
	}

	/**
	 * Fragments the peptide producing 'a' type fragments and subtracts one ammonia if peptide contains
	 * arganine, lysine or qlutamine.  Must subtract 2 hydrogen's, two oxygen's and
	 * one carbon from calculated peptide mass.
	 * @return FragIon array containing each of the fragments produced by a fragmentation of this peptide.
	 * The length of the array is the same as the number of fragments produced.
	 * @see jpat.FragIon
	 */


	public FragIon[] getAMinusNH3Fragments()
	{
		int fragNum = 0;
		FragIon[] fragArrayNoLoss = getAFragments();
		FragIon[] fragArray = new FragIon[mSequence.length()];

		for (int i = 0; i < fragArrayNoLoss.length; i++)
		{
			String frag = fragArrayNoLoss[i].frag;

			if (frag.indexOf("R") == -1 && frag.indexOf("K") == -1 && frag.indexOf("Q") == -1)
			{
				fragArray[i] = new FragIon(frag, 0.00, 0.00);
			}
			else
			{
				double monoMass = fragArrayNoLoss[i].monoMass - (3 * Hmono + Nmono);
				double averageMass = fragArrayNoLoss[i].averageMass - (3 * Havg + Navg);

				fragArray[i] = new FragIon(frag, monoMass, averageMass);
			}
		}

		return fragArray;
	}

	/**
	 * Fragments the peptide producing 'b' type fragments and subtracts one water if peptide contains
	 * serine or threonine.  Must subtract 2 hydrogen's, one oxygen and
	 * from calculated peptide mass.
	 * @return FragIon array containing each of the fragments produced by a fragmentation of this peptide.
	 * The length of the array is the same as the number of fragments produced.
	 * @see jpat.FragIon
	 */


	public FragIon[] getBMinusH2OFragments()
	{
		int fragNum = 0;
		FragIon[] fragArrayNoLoss = getBFragments();
		FragIon[] fragArray = new FragIon[mSequence.length()];

		for (int i = 0; i < fragArrayNoLoss.length; i++)
		{
			String frag = fragArrayNoLoss[i].frag;

			if (frag.indexOf("T") == -1 && frag.indexOf("S") == -1)
			{
				fragArray[i] = new FragIon(frag, 0.00, 0.00);
			}
			else
			{
				double monoMass = fragArrayNoLoss[i].monoMass - (2 * Hmono + Omono);
				double averageMass = fragArrayNoLoss[i].averageMass - (2 * Havg + Oavg);

				fragArray[i] = new FragIon(frag, monoMass, averageMass);
			}
		}

		return fragArray;
	}

	/**
	 * Fragments the peptide producing 'b' type fragments and subtracts one ammonia if peptide contains
	 * arganine, lysine or qlutamine.  Must subtract 2 hydrogen's, one oxygen and
	 * from calculated peptide mass.
	 * @return FragIon array containing each of the fragments produced by a fragmentation of this peptide.
	 * The length of the array is the same as the number of fragments produced.
	 * @see jpat.FragIon
	 */


	public FragIon[] getBMinusNH3Fragments()
	{
		int fragNum = 0;
		FragIon[] fragArrayNoLoss = getBFragments();
		FragIon[] fragArray = new FragIon[mSequence.length()];

		for (int i = 0; i < fragArrayNoLoss.length; i++)
		{
			String frag = fragArrayNoLoss[i].frag;

			if (frag.indexOf("R") == -1 && frag.indexOf("K") == -1 && frag.indexOf("Q") == -1)
			{

				// fragArray[i] = fragArrayNoLoss[i];
				fragArray[i] = new FragIon(frag, 0.00, 0.00);
			}
			else
			{
				double monoMass = fragArrayNoLoss[i].monoMass - (3 * Hmono + Nmono);
				double averageMass = fragArrayNoLoss[i].averageMass - (3 * Havg + Navg);

				fragArray[i] = new FragIon(frag, monoMass, averageMass);
			}
		}

		return fragArray;
	}

	/**
	 * Fragments the peptide producing 'y' type fragments and subtracts one ammonia if peptide contains
	 * arganine, lysine or qlutamine.
	 * @return FragIon array containing each of the fragments produced by a fragmentation of this peptide.
	 * The length of the array is the same as the number of fragments produced.
	 * @see jpat.FragIon
	 */


	public FragIon[] getYMinusNH3Fragments()
	{
		int fragNum = 0;
		FragIon[] fragArrayNoLoss = getYFragments();
		FragIon[] fragArray = new FragIon[mSequence.length()];

		for (int i = 0; i < fragArrayNoLoss.length; i++)
		{
			String frag = fragArrayNoLoss[i].frag;

			if (frag.indexOf("R") == -1 && frag.indexOf("K") == -1 && frag.indexOf("Q") == -1)
			{
				fragArray[i] = new FragIon(frag, 0.00, 0.00);
			}
			else
			{
				double monoMass = fragArrayNoLoss[i].monoMass - (3 * Hmono + Nmono);
				double averageMass = fragArrayNoLoss[i].averageMass - (3 * Havg + Navg);

				fragArray[i] = new FragIon(frag, monoMass, averageMass);
			}
		}

		return fragArray;
	}

	/**
	 * Fragments the peptide producing internal b type fragments.
	 * @return FragIon array containing each of the fragments produced by a fragmentation of this peptide.
	 * The length of the array is the same as the number of fragments produced.
	 * @see jpat.FragIon
	 */
	public FragIon[] getInternalBFragments()
	{
		int arraySize = 0;
		int fragNum = 0;
		FragIon[] fragArray;

		if (mSequence.length() > 2)
		{
			String internalSequence = mSequence.substring(1, mSequence.length() - 1);

			for (int i = 0; i < internalSequence.length(); i++)
			{
				for (int k = i + 2; k <= internalSequence.length(); k++)
				{
					arraySize++;
				}
			}

			fragArray = new FragIon[arraySize];

			for (int i = 0; i < internalSequence.length(); i++)
			{
				for (int k = i + 2; k <= internalSequence.length(); k++)
				{
					String frag = internalSequence.substring(i, k);
					double monoMass = -2 * Hmono - Omono;		// Subtract H2O from mass for b-Fragments
					double averageMass = -2 * Havg - Oavg;		// Add here for H+ on MH+

					monoMass = monoMass + massCalc.calcMonoMass(frag);
					averageMass = averageMass + massCalc.calcAvgMass(frag);
					fragArray[fragNum] = new FragIon(frag, monoMass, averageMass);
					fragNum++;
				}
			}

			FragIon hold[] = new FragIon[1];

			for (int i = 1; i < fragNum; i++)
			{
				for (int k = 0; k < fragNum - 1; k++)
				{
					if (fragArray[k].monoMass > fragArray[k + 1].monoMass)
					{
						hold[0] = fragArray[k];
						fragArray[k] = fragArray[k + 1];
						fragArray[k + 1] = hold[0];
					}
				}
			}
		}
		else
		{
			fragArray = new FragIon[0];
		}

		// setNumFrags(fragNum);
		return fragArray;
	}

	/**
	 * Fragments the peptide producing internal b type fragments that start with proline.  Proline directed
	 * internal fragmentation is known to be a favorable reaction
	 * @return FragIon array containing each of the fragments produced by a fragmentation of this peptide.
	 * The length of the array is the same as the number of fragments produced.
	 * @see jpat.FragIon
	 */


	public FragIon[] getProInternalBFragments()
	{
		int arraySize = 0;
		int fragNum = 0;
		FragIon[] fragArrayAllInternals = this.getInternalBFragments();
		Vector fragVector = new Vector(fragArrayAllInternals.length);

		for (int i = 0; i < fragArrayAllInternals.length; i++)
		{
			if (fragArrayAllInternals[i].frag.charAt(0) == 'P')
			{
				fragVector.addElement(fragArrayAllInternals[i]);
			}
			else
			{
				fragVector.addElement(new FragIon(fragArrayAllInternals[i].frag, 0.00, 0.00));
			}
		}

		FragIon[] fragArray = new FragIon[fragVector.size()];

		fragVector.copyInto(fragArray);

		return fragArray;
	}

	/**
	 * Fragments the peptide producing imoniuim type fragments.  Immonium fragments are
	 * specific masses associated with amino acids.  By observing Immonium Ions certain
	 * compositions of the peptide can be determined.
	 * @return FragIon array containing each of the fragments produced by a fragmentation of this peptide.
	 * The length of the array is the same as the number of fragments produced.
	 * @see jpat.FragIon
	 */
	public FragIon[] getImmoniumIon()
	{
		int fragNum = 0;
		FragIon[] fragArray = new FragIon[mSequence.length()];

		for (int i = 1; i < mSequence.length() + 1; i++)
		{
			String frag = mSequence.substring(i - 1, i);
			double monoMass = massCalc.calcMonoMass(frag) - 2 * Hmono - Omono - 28;		// Figure out the definition of Imonuim Ions and express this in terms of Hmono ect.
			double averageMass = massCalc.calcAvgMass(frag) - 2 * Hmono - Omono - 28;

			fragArray[fragNum] = new FragIon(frag, monoMass, averageMass);
			fragNum++;
		}

		// setNumFrags(fragNum);
		return fragArray;
	}

}



