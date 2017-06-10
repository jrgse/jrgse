/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

/**
 * @(#) FragIon.Java 2.0 09/14/99 Michael Dean Jones
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

/**
 * Defines FragIon object which store values for peptide
 * fragments. Used primarily by jpat.fragment.
 * Example usage of an Object of type FragIon:
 * <br>
 * <code>
 * FragIon fragIon = new Fragion(frag, monoMass, averageMass)<br>
 * double thisFragmentMass = fragIon.monoMass
 * </code>
 * <br>
 * 
 * @version 2.1, 3 March 2000
 * @author Michael Dean Jones
 * @see jpat.Fragment
 */


public class FragIon implements java.io.Serializable
{

	/**
	 * Sequence associated with fragment
	 */
	public String frag;

	/**
	 * Mono-isotopic mass of fragment
	 */
	public double monoMass;

	/**
	 * Average-isotopic mass of fragment
	 */
	public double averageMass;

	/**
	 * @param frag Sequence associated with the fragment
	 * @param monResMass Monoisotopic mass of fragment
	 * @param avgResMass Average mass of fragment
	 */
	public FragIon(String frag, double monoMass, double averageMass)
	{
		this.frag = frag;
		this.monoMass = monoMass;
		this.averageMass = averageMass;
	}

}

