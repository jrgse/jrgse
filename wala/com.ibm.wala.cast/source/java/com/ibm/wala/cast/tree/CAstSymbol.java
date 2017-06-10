package com.ibm.wala.cast.tree;

public interface CAstSymbol {

  public static Object NULL_DEFAULT_VALUE = new Object() {
    public String toString() {
      return "NULL DEFAULT VALUE";
    }
  };

  public String name();

  /**
   * like final in Java; can only be declared / assigned once
   */
  public boolean isFinal();

  public boolean isCaseInsensitive();

  public Object defaultInitValue();

  public boolean isInternalName();

}

