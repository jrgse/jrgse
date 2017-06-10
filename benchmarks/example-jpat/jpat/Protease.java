/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

/**
 * @(#) Protease.Java 2.0 09/14/99 Michael Dean Jones
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
 * Contains static methods to obtain protease info.<BR>
 * Holds Protease information
 * @version 2.1, 3 March 2000
 * @author Michael Dean Jones
 * @see jpat.FragIon
 */
public class Protease implements java.io.Serializable
{

	/**
	 * Trypsin protease. Cleaves at C-Terminal of R and L unless their is an adjacent P.
	 */
	public static final String TRYPSIN = "Trypsin";
	public static final String ARG_C = "Arg-C";
	public static final String LYS_C = "Lys-C";
	public static final String ASP_N = "Asp_N";
	public static final String GLU_C = "Glu-C(V8)";

	/**
	 * No protease.
	 */
	public static final String NONE = "none";


	public Protease(String sequence) {}


	public static String getCleavageResidues(String protease)
	{
		if (protease.equals(TRYPSIN))
		{
			return "KR";
		}
		else if (protease.equals(ARG_C))
		{
			return "R";
		}
		else if (protease.equals(LYS_C))
		{
			return "K";
		}
		else if (protease.equals(ASP_N))
		{
			return "D";
		}
		else if (protease.equals(GLU_C))
		{
			return "DE";
		}

		return "";
	}


	public static boolean isEndoProtease(String protease)
	{
		if (protease.equals(TRYPSIN))
		{
			return true;
		}
		else if (protease.equals(ARG_C))
		{
			return true;
		}
		else if (protease.equals(LYS_C))
		{
			return true;
		}
		else if (protease.equals(ASP_N))
		{
			return false;
		}
		else if (protease.equals(GLU_C))
		{
			return true;
		}

		return true;
	}


	public static char getNotCleave(String protease)
	{
		if (protease.equals(TRYPSIN))
		{
			return 'P';
		}

		return ' ';
	}

}



