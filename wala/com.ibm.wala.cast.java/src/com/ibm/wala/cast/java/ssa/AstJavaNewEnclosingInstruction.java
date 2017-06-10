package com.ibm.wala.cast.java.ssa;

import java.util.Collection;

import com.ibm.wala.classLoader.JavaLanguage;
import com.ibm.wala.classLoader.NewSiteReference;
import com.ibm.wala.ssa.SSAInstruction;
import com.ibm.wala.ssa.SSAInstructionFactory;
import com.ibm.wala.ssa.SSANewInstruction;
import com.ibm.wala.types.TypeReference;

// A new instruction with an explicit outer class, i.e. "Inner inner = outer.new Inner();"
public class AstJavaNewEnclosingInstruction extends SSANewInstruction {

  int enclosing;
  
  public int getNumberOfUses() {
    return 1;
  }
  
  public int getUse(int i) {
    assert i == 0;
    return enclosing;
  }
  
  public AstJavaNewEnclosingInstruction(int result, NewSiteReference site, int enclosing) throws IllegalArgumentException {
    super(result, site);
    this.enclosing = enclosing;
  }
  
  public int getEnclosing() {
    return this.enclosing;
  }
  
  public String toString() {
    return super.toString() + " ENCLOSING v" + enclosing;
  }

  public SSAInstruction copyForSSA(SSAInstructionFactory insts, int[] defs, int[] uses) {
    return ((AstJavaInstructionFactory)insts).JavaNewEnclosingInstruction(defs==null? getDef(0): defs[0], getNewSite(), uses==null? enclosing: uses[0]);
  }
  
  public Collection<TypeReference> getExceptionTypes() {
    return JavaLanguage.getNewScalarExceptions();
  }

}
