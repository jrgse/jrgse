/*
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

package EDU.purdue.cs.bloat.tree;

import EDU.purdue.cs.bloat.editor.*;
import EDU.purdue.cs.bloat.cfg.*;
import EDU.purdue.cs.bloat.util.*;
import java.util.*;

/**
 * NewExpr represents the <tt>new</tt> opcode that creates a new 
 * object of a specified type.
 */
public class NewExpr extends Expr {
  // new
  Type objectType;
  
  /**
   * Constructor.
   *
   * @param objectType
   *        The type of the object to create.
   * @param type
   *        The type of this expression.
   */
  public NewExpr(Type objectType, Type type) {
    super(type);
    this.objectType = objectType;
  }
  
  /**
   * Returns the <tt>Type</tt> of the object being created.
   */
  public Type objectType() {
    return objectType;
  }
  
  public void visitForceChildren(TreeVisitor visitor) {
  }
  
  public void visit(TreeVisitor visitor) {
    visitor.visitNewExpr(this);
  }
  
  public int exprHashCode() {
    return 16 + objectType.hashCode();
  }
  
  public boolean equalsExpr(Expr other) {
    return false;
  }
  
  public Object clone() {
    return copyInto(new NewExpr(objectType, type));
  }
}
