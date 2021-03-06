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

package EDU.purdue.cs.bloat.file;

import EDU.purdue.cs.bloat.reflect.*;
import java.util.*;
import java.io.*;

/**
 * The ConstantValue attribute stores an index into the constant pool
 * that represents constant value.  A class's static fields have 
 * constant value attributes.
 *
 * @see Field
 *
 * @author Nate Nystrom
 *         (<a href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class ConstantValue extends Attribute
{
  private int constantValueIndex;

  /**
   * Creates a new <code>ConstantValue</code> from scratch
   *
   * @param nameIndex
   *        The index in the constant pool of the UTF8 string
   *        "ConstantValue"  
   * @param constantValueIndex
   *        The index in the constant pool of the Constant containing
   *        the constant value
   */
  ConstantValue(int nameIndex, int length, int constantValueIndex) {
    super(nameIndex, length);
    this.constantValueIndex = constantValueIndex;
  }

  /**
   * Constructor.  Create a ConstantValue attribute from a data stream.
   *
   * @param in
   *        The data stream of the class file.
   * @param nameIndex
   *        The index into the constant pool of the name of the attribute.
   * @param length
   *        The length of the attribute, excluding the header.
   * @exception IOException
   *        If an error occurs while reading.
   */
  public ConstantValue(DataInputStream in, int nameIndex, int length)
    throws IOException
  {
    super(nameIndex, length);
    constantValueIndex = in.readUnsignedShort();
  }

  /**
   * Write the attribute to a data stream.
   *
   * @param out
   *        The data stream of the class file.
   */
  public void writeData(DataOutputStream out)
    throws IOException
  {
    out.writeShort(constantValueIndex);
  }

  /**
   * Returns the index into the constant pool of the constant value.
   */
  public int constantValueIndex() {
    return constantValueIndex;
  }

  /**
   * Set the index into the constant pool of the constant value.
   */
  public void setConstantValueIndex(int index) {
    this.constantValueIndex = index;
  }

  /**
   * Private constructor used for cloning.
   */
  private ConstantValue(ConstantValue other) {
    super(other.nameIndex, other.length);

    this.constantValueIndex = other.constantValueIndex;
  }

  public Object clone() {
    return(new ConstantValue(this));
  }

  /**
   * Converts the attribute to a string.
   */
  public String toString() {
    return "(constant-value " + constantValueIndex + ")";
  }
}
