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
import EDU.purdue.cs.bloat.tbaa.*;
import java.util.*;

/**
 * Expr is the superclass for a number of other classes representing
 * expressions in byte code.  Expressions are typed and may be nested.
 *
 * @see DefExpr
 */
public abstract class Expr extends Node implements Cloneable {
  protected Type type;           // The type (descriptor) of this expression
  private DefExpr def;           // The expression in which this expression
                                 // is defined (if applicable)
  private Object comparator;

  /**
   * Constructor.  Initializes an expression with a given type.
   *
   * @param type
   *        The initial Type (descriptor) of this expression.
   */
  public Expr(Type type) {
    this.def = null;
    this.comparator = new ExprComparator();
    this.type = type;   
  }
  
  /** 
   * Sets the type of this expression.  Returns whether or not the
   * type changed as a result of calling this method.
   */
  public boolean setType(Type type) {
    
    if (!this.type.equals(type)) {
//        if (Tree.DEBUG) {
//  	System.out.println("      setting typeof(" + this + ") = " + type);
//        }
      this.type = type;

      return true;
    }
    
    return false;
  }
  

  /**
   * Returns whether or not this expression is a defining occurrence.
   * By default, false is returned.
   */
  public boolean isDef() {
    return false;
  }

  /**
   * Returns the statement to which this expression belongs.  It 
   * essentially searches up the expression tree for this expression's
   * first ancestor which is a Stmt.
   */  
  public Stmt stmt() {
    Node p = parent;
    
    while (! (p instanceof Stmt)) {
      Assert.isTrue(! (p instanceof Tree), "Invalid ancestor of " + this);
      Assert.isTrue(p != null, "Null ancestor of " + this);
      p = p.parent;
    }
    
    return (Stmt) p;
  }

  /**
   * Returns the Type of this expression.
   */  
  public Type type() {
    return type;
  }
  
  /**
   * Cleans up this expression only, not its children.
   */
  public void cleanupOnly() {
    setDef(null);
  }

  /**
   * Sets the expression that defines this expression.
   *
   * @param def
   *        Defining expression.
   */  
  public void setDef(DefExpr def) {
//      if (Tree.DEBUG) {
//        System.out.println("      setting def of " + this +
//  			 " (" + System.identityHashCode(this) + ") to " + def);
//      }
    
    if (this.def == def) {
      return;
    }
   
    // If this Expr already had a defining statement, remove this from the
    // DefExpr use list.
    if (this.def != null) {
      this.def.removeUse(this);
    }
    
    if (this.isDef()) {
      Assert.isTrue(def == this || def == null);
      this.def = null;
      return;
    }
    
    this.def = def;
    
    if (this.def != null) {
      this.def.addUse(this);    // This Expr is a use of def
    }
  }

  /**
   * Returns the expression in which this Expr is defined.
   */  
  public DefExpr def() {
    return def;
  }
  
  /**
   * Returns the hash code for this expresion.
   */
  public abstract int exprHashCode();

  /**
   * Compares this expression to another.
   *
   * @param other
   *        Expr to which to compare this.
   */
  public abstract boolean equalsExpr(Expr other);

  public abstract Object clone(); 
  
  /**
   * Copies the contents of another expression in this one.
   *
   * @param expr
   *        The expression from which to copy.
   */
  protected Expr copyInto(Expr expr) {
    expr = (Expr) super.copyInto(expr);
    
    DefExpr def = def();
    
    if (isDef()) {
      expr.setDef(null);
    }
    else {
      expr.setDef(def);
    }
    
    return expr;
  }

  /**
   * Returns an Object that can be used to compare other Expr to this.
   */  
  public Object comparator() {
    return comparator;
  }
  
  /**
   * ExprComparator is used to provide a different notion of equality
   * among expressions than the default ==.  In most cases, we want ==,
   * but occasionally we want the equalsExpr() functionality when inserting
   * in Hashtables, etc.
   */
  private class ExprComparator {
    Expr expr = Expr.this;
    
    public boolean equals(Object obj) {
      if (obj instanceof ExprComparator) {
	Expr other = ((ExprComparator) obj).expr;
	return expr.equalsExpr(other) &&
	  expr.type.simple().equals(other.type.simple());
      }
      
      return false;
    }
    
    public int hashCode() {
      return Expr.this.exprHashCode();
    }
  }
}
