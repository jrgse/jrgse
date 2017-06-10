/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

/**
 * @(#) Residue.Java 2.0 09/14/99 Michael Dean Jones
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
 * 
 * The Residue object is used to store the mass values for amino acids residues
 * represented by single letter codes.  This object is used by PepTools to store
 * assigned mass values to common and custom amino acids.
 * 
 * 
 * @see jpat.PepTools
 * @version 2.1, 3 March 2000
 * @author Michael Dean Jones
 */
public class Residue implements Comparable
{
	protected char name;
	protected double monResMass, avgResMass;

	/**
	 * @param name Single letter code for Amino Acid Residue
	 * @param monResMass Monoisotopic mass of amino acid residue
	 * @param avgResMass Average mass of amino acid residue
	 */
	public Residue(char name, double monResMass, double avgResMass)
	{
		this.name = name;
		this.monResMass = monResMass;
		this.avgResMass = avgResMass;
	}

	/**
	 * Get the residue single letter code associated with this Residue.
	 * @return single letter code associated with this Residue..
	 */
	public char getName()
	{
		return name;
	}

	/**
	 * Get Mono-Isotopic mass of this residue.
	 * @return Mono-Isotopic mass of this residue.
	 */
	public double getMonResMass()
	{
		return monResMass;
	}

	/**
	 * Get Avg-Isotopic mass of this residue.
	 * @return Avg-Isotopic mass of this residue.
	 */
	public double getAvgResMass()
	{
		return avgResMass;
	}
   /** comparison based on the third field element */
	public int compareTo(Object object) throws ClassCastException
	{
		Residue residue = null;

		try
		{
			residue = (Residue) object;
		}
		catch (ClassCastException e)
		{
			throw e;
		}

		Double thisMass = new Double(getAvgResMass());
		Double compareMass = new Double(residue.getAvgResMass());

		return thisMass.compareTo(compareMass);
	}

}



