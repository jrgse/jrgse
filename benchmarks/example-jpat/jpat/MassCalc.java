/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

/**
 * @(#) MassCalc.Java 2.0 09/14/99 Michael Dean Jones
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

import java.text.*;

/**
 * Calculates monoisotopic or average mass of protein or peptide sequences.
 * It does this by summing the individual residue masses and then adding on
 * the N and C terminal groups.  May want to extend this later to
 * include non-standard terminal groups. Although end groups can be defined
 * as a unique AA.<br>
 * Use <code>jpat.pepTools.setCustomAA(Residue[])</code>
 * to modify residues or add non-common Amino Acids.<br>
 * <b>Version 2.0 changes</b><br>
 * 1) Fixed error in calculating masses with calcMass(String peptide, boolean average).
 * It was adding on an extra H2O and H+.<br>
 * 2) Added flag for switching from MH+ calculations and M.<br>
 * 
 * @version 2.1, 3 March 2000
 * @author Michael Dean Jones
 * @see jpat.PepTools
 * @see jpat.PepTools#setCustomAA(Residue[])
 */
public class MassCalc extends PepTools implements java.io.Serializable
{


	private boolean mMH = true;

	/**
	 * Constructor invokes jpat.PepTools.fillArray()
	 * @see jpat.PepTools#fillArray()
	 */
	public MassCalc()
	{
		fillArray();
	}

	/**
	 * Return mass of peptide as string in default number format
	 * for the current default locale. The format for US is three decimal places
	 * @param peptide is sequence of protein or peptide to be calculate
	 * @param average is true if average masses are desired and false if
	 * monoisotopic masses are desired
	 * @return String at default number format
	 */
	public String getMassString(String peptide, boolean average)
	{
		if (average)
		{
			String massString = NumberFormat.getInstance().format(calcAvgMass(peptide));

			return massString;
		}
		else
		{
			String massString = NumberFormat.getInstance().format(calcMonoMass(peptide));

			return massString;
		}
	}

	/**
	 * Return mass of peptide as string to specified decimal places.
	 * @param peptide is sequence of protein or peptide to be used for the calculation
	 * @param average is true if average masses are desired and false if
	 * monoisotopic masses are desired
	 * @param decPlaces Number of decimal places to return
	 * @return String with decPlaces decimal places
	 */
	public String getMassString(String peptide, boolean average, int decPlaces)
	{
		NumberFormat nf = NumberFormat.getInstance();

		nf.setMaximumFractionDigits(decPlaces);
		nf.setMinimumFractionDigits(decPlaces);

		if (average)
		{
			String massString = nf.format(calcAvgMass(peptide));

			return massString;
		}
		else
		{
			String massString = nf.format(calcMonoMass(peptide));

			return massString;
		}
	}

	/**
	 * Return double as string to specified decimal places.
	 * @param peptideMass is mass of protein or peptide as a double
	 * @param decPlaces Number of decimal places to return
	 * @return String with decPlaces decimal places
	 */
	public String getMassString(double peptideMass, int decPlaces)
	{
		NumberFormat nf = NumberFormat.getInstance();

		nf.setMaximumFractionDigits(decPlaces);

		String massString = nf.format(peptideMass);

		return massString;
	}

	/**
	 * Return mass of peptide as double.
	 * @param peptide is sequence of protein or peptide to be calculate
	 * @param average is true if average masses are desired and false if
	 * monoisotopic masses are desired
	 * @return peptide mass as double
	 */
	public double calcMass(String peptide, boolean average)
	{
		if (average)
		{
			return calcAvgMass(peptide);
		}
		else
		{
			return calcMonoMass(peptide);
		}
	}

	/**
	 * Return average mass of peptide as double.
	 * @param peptide is sequence of protein or peptide to be calculate
	 * @return average peptide mass as double
	 */
	public double calcAvgMass(String peptide)
	{
		double pepMass = 0.0;
		char aa;

		for (int i = 0; i < peptide.length(); i++)
		{
			for (int j = 0; j <= mAminoAcid.length - 1; j++)
			{
				aa = peptide.charAt(i);

				if (aa == mAminoAcid[j].name)
				{
					pepMass = pepMass + mAminoAcid[j].avgResMass;

					break;
				}		// end if
			}			// end for j
		}				// end for i

		if (pepMass != 0.0)
		{
			if (mMH)
			{
				pepMass = pepMass + 3 * Havg + Oavg;
			}
			else
			{
				pepMass = pepMass + 2 * Havg + Oavg;
			}
		}

		return pepMass;
	}

	/**
	 * Return average mass of peptide as double.
	 * @param peptide is sequence of protein or peptide to be calculate
	 * @return average peptide mass as double
	 */
	public double calcAvgMassFast(String peptide)
	{
		double pepMass = 0.0;
		char aa;
		int pepLength = peptide.length();
		int aaLength = mAminoAcid.length;
		int i, j;		// Set decleration here seems to speed up by 5% but inconsistently

		for (i = 0; i < pepLength; i++)
		{
			for (j = 0; j < aaLength; j++)
			{
				aa = peptide.charAt(i);

				if (aa == mAminoAcid[j].name)
				{
					pepMass = pepMass + mAminoAcid[j].avgResMass;
					j = aaLength;

					break;
				}		// end if
			}			// end for j
		}				// end for i

		if (pepMass != 0.0)
		{
			if (mMH)
			{
				pepMass = pepMass + 3 * Havg + Oavg;
			}
			else
			{
				pepMass = pepMass + 2 * Havg + Oavg;
			}
		}

		return pepMass;
	}

	/**
	 * Return monoisotopic mass of peptide as double.
	 * @param peptide is sequence of protein or peptide to be calculate
	 * @return monoisotopic peptide mass as double
	 */
	public double calcMonoMass(String peptide)
	{
		double pepMass = 0.0;
		char aa;

		for (int i = 0; i < peptide.length(); i++)
		{
			for (int j = 0; j <= mAminoAcid.length - 1; j++)
			{
				aa = peptide.charAt(i);

				if (aa == mAminoAcid[j].name)
				{
					pepMass = pepMass + mAminoAcid[j].monResMass;

					break;
				}		// end if
			}			// end for j
		}				// end for i

		if (pepMass != 0.0)
		{
			if (mMH)
			{
				pepMass = pepMass + 3 * Hmono + Omono;
			}
			else
			{
				pepMass = pepMass + 2 * Hmono + Omono;
			}
		}

		return pepMass;
	}

	/**
	 * @param mh. Sets the flag to indicate if MH+ type ions are used in the calculations.
	 * Default is true.
	 * @return monoisotopic peptide mass as double
	 */
	public void setCalculateMH(boolean mh)
	{
		mMH = mh;
	}

	/**
	 * @return weather MH+ ions are used in the calculations.
	 */
	public boolean getCalculateMH()
	{
		return mMH;
	}

}



