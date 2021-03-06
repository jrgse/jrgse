/**
 * All files in the distribution of BLOAT (Bytecode Level Optimization and
 * Analysis tool for Java(tm)) are Copyright 1997-2001 by the Purdue
 * Research Foundation of Purdue University.  All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms are permitted
 * provided that this entire copyright notice is duplicated in all
 * such copies, and that any documentation, announcements, and other
 * materials related to such distribution and use acknowledge that the
 * software was developed at Purdue University, West Lafayette, IN by
 * Antony Hosking, David Whitlock, and Nathaniel Nystrom.  No charge
 * may be made for copies, derivations, or distributions of this
 * material without the express written consent of the copyright
 * holder.  Neither the name of the University nor the name of the
 * author may be used to endorse or promote products derived from this
 * material without specific prior written permission.  THIS SOFTWARE
 * IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR PURPOSE.
 *
 * <p>
 * Java is a trademark of Sun Microsystems, Inc.
 */

package EDU.purdue.cs.bloat.reflect;

import java.util.*;
import java.io.*;

/**
 * LineNumberDebugInfo is used to map a range of instructions to a
 * line number in the original Java source file.  An instance of
 * <tt>file.LineNumberTable</tt> contains an array of these 
 * guys.
 *
 * @see EDU.purdue.cs.bloat.file.LineNumberTable
 *
 * @author Nate Nystrom
 *         (<a href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class LineNumberDebugInfo
{
  private int startPC;
  private int lineNumber;

  /**
   * Constructor.
   *
   * @param startPC
   *        The start PC of the instructions for this line number.
   * @param lineNumber
   *        The line number for this group of instructions.
   */
  public LineNumberDebugInfo(int startPC, int lineNumber)
  {
    this.startPC = startPC;
    this.lineNumber = lineNumber;
  }

  /**
   * Get the start PC of the instructions for this line number.
   *
   * @return
   *        The start PC.
   */
  public int startPC()
  {
    return startPC;
  }

  /**
   * Get the line number for this group of instructions.
   *
   * @return
   *        The line number.
   */
  public int lineNumber()
  {
    return lineNumber;
  }

  /**
   * Convert the attribute to a string.
   *
   * @return
   *        A string representation of the attribute.
   */
  public String toString()
  {
    return "(line #" + lineNumber + " pc=" + startPC + ")";
  }

  public Object clone() {
    return(new LineNumberDebugInfo(this.startPC, this.lineNumber));
  }
}
