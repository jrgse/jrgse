/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Jimple, a 3-address code Java(TM) bytecode representation.        *
 * Copyright (C) 1997, 1998 Raja Vallee-Rai (kor@sable.mcgill.ca)    *
 * All rights reserved.                                              *
 *                                                                   *
 * This work was done as a project of the Sable Research Group,      *
 * School of Computer Science, McGill University, Canada             *
 * (http://www.sable.mcgill.ca/).  It is understood that any         *
 * modification not identified as such is not covered by the         *
 * preceding statement.                                              *
 *                                                                   *
 * This work is free software; you can redistribute it and/or        *
 * modify it under the terms of the GNU Library General Public       *
 * License as published by the Free Software Foundation; either      *
 * version 2 of the License, or (at your option) any later version.  *
 *                                                                   *
 * This work is distributed in the hope that it will be useful,      *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of    *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU *
 * Library General Public License for more details.                  *
 *                                                                   *
 * You should have received a copy of the GNU Library General Public *
 * License along with this library; if not, write to the             *
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,      *
 * Boston, MA  02111-1307, USA.                                      *
 *                                                                   *
 * Java is a trademark of Sun Microsystems, Inc.                     *
 *                                                                   *
 * To submit a bug report, send a comment, or get the latest news on *
 * this project and other Sable Research Group projects, please      *
 * visit the web site: http://www.sable.mcgill.ca/                   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Coffi, a bytecode parser for the Java(TM) language.               *
 * Copyright (C) 1996, 1997 Clark Verbrugge (clump@sable.mcgill.ca). *
 * All rights reserved.                                              *
 *                                                                   *
 * This work was done as a project of the Sable Research Group,      *
 * School of Computer Science, McGill University, Canada             *
 * (http://www.sable.mcgill.ca/).  It is understood that any         *
 * modification not identified as such is not covered by the         *
 * preceding statement.                                              *
 *                                                                   *
 * This work is free software; you can redistribute it and/or        *
 * modify it under the terms of the GNU Library General Public       *
 * License as published by the Free Software Foundation; either      *
 * version 2 of the License, or (at your option) any later version.  *
 *                                                                   *
 * This work is distributed in the hope that it will be useful,      *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of    *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU *
 * Library General Public License for more details.                  *
 *                                                                   *
 * You should have received a copy of the GNU Library General Public *
 * License along with this library; if not, write to the             *
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,      *
 * Boston, MA  02111-1307, USA.                                      *
 *                                                                   *
 * Java is a trademark of Sun Microsystems, Inc.                     *
 *                                                                   *
 * To submit a bug report, send a comment, or get the latest news on *
 * this project and other Sable Research Group projects, please      *
 * visit the web site: http://www.sable.mcgill.ca/                   *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

/*
 Reference Version
 -----------------
 This is the latest official version on which this file is based.
 The reference version is: $CoffiVersion: 1.1 $
                           $SootVersion: 1.beta.4.dev.7 $

 Change History
 --------------
 A) Notes:

 Please use the following template.  Most recent changes should
 appear at the top of the list.

 - Modified on [date (March 1, 1900)] by [name]. [(*) if appropriate]
   [description of modification].

 Any Modification flagged with "(*)" was done as a project of the
 Sable Research Group, School of Computer Science,
 McGill University, Canada (http://www.sable.mcgill.ca/).

 You should add your copyright, using the following template, at
 the top of this file, along with other copyrights.

 *                                                                   *
 * Modifications by [name] are                                       *
 * Copyright (C) [year(s)] [your name (or company)].  All rights     *
 * reserved.                                                         *
 *                                                                   *

 B) Changes:

 - Modified on November 2, 1998 by Raja Vallee-Rai (kor@sable.mcgill.ca) (*)
   Repackaged all source files and performed extensive modifications.
   First initial release of Soot.

 - Modified on September 29, 1998 by Raja Vallee-Rai (kor@sable.mcgill.ca) (*)
   Corrected the ints2long method.

 - Modified on 15-Jun-1998 by Raja Vallee-Rai (kor@sable.mcgill.ca). (*)
   First internal release (Version 0.1).
*/

package ca.mcgill.sable.soot.coffi;

import java.io.*;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

/** Base abstract class for constant pool entries; includes some utility methods.
 * @see ClassFile#constant_pool
 * @author Clark Verbrugge
 */
abstract class cp_info {

   public static final byte CONSTANT_Utf8 = 1;
   public static final byte CONSTANT_Integer = 3;
   public static final byte CONSTANT_Float = 4;
   public static final byte CONSTANT_Long = 5;
   public static final byte CONSTANT_Double = 6;
   public static final byte CONSTANT_Class = 7;
   public static final byte CONSTANT_String = 8;
   public static final byte CONSTANT_Fieldref = 9;
   public static final byte CONSTANT_Methodref = 10;
   public static final byte CONSTANT_InterfaceMethodref = 11;
   public static final byte CONSTANT_NameAndType = 12;

   /** One of the CONSTANT_* constants. */
   public byte tag;

   /** Returns the size of this entry.
    * @return size (in bytes) of this entry.
    */
   public abstract int size();

   /** Returns a String representation of this entry.
    * @param constant_pool constant pool of ClassFile.
    * @return String representation of this entry.
    */
   public abstract String toString(cp_info constant_pool[]);

   /** Returns a String description of what kind of entry this is.
    * @return String representation of this kind of entry.
    */
   public abstract String typeName();

   /** Compares this entry with another cp_info object (which may reside
    * in a different constant pool).
    * @param constant_pool constant pool of ClassFile for this.
    * @param cp constant pool entry to compare against.
    * @param cp_constant_pool constant pool of ClassFile for cp.
    * @return a value <0, 0, or >0 indicating whether this is smaller,
    * the same or larger than cp.
    */
   public abstract int compareTo(cp_info constant_pool[],cp_info cp,
                                 cp_info cp_constant_pool[]);


   /** Utility method, converts two integers into a single long.
    * @param high upper 32 bits of the long.
    * @param low lower 32 bits of the long.
    * @return a long value composed from the two ints.
    */
   public static long ints2long(long high,long low) {
      long h,l;
      h = high; l = low;
      return ((h<<32) + l);
   }

   /** Utility method, returns a String binary representation of the
    * given integer.
    * @param i the integer in question.
    * @return a String of 0's and 1's.
    * @see cp_info#printBits(long)
    */
   public static String printBits(int i) {
      String s = "";
      int j,k;
      k = 1;
      for (j=0;j<32; j++) {
         if ((i&k)!=0)
            s = "1" + s;
         else
            s = "0" + s;
         k = k<<1;
      }
      return s;
   }

   /** Utility method, returns a String binary representation of the
    * given long.
    * @param i the long in question.
    * @return a String of 0's and 1's.
    * @see cp_info#printBits(int)
    */
   public static String printBits(long i) {
      String s = "";
      long j,k;
      k = 1;
      for (j=0;j<64; j++) {
         if ((i&k)!=0)
            s = "1" + s;
         else
            s = "0" + s;
         k = k<<1;
      }
      return s;
   }

   /** Locates the name of the corresponding class, given the constant
    * pool index of either a CONSTANT_Class, _Fieldref, Methodref or
    * InterfaceMethodref.
    * @param constant_pool constant pool of ClassFile.
    * @param i index of cp_info entry in question.
    * @return name of the associated class.
    * @see CONSTANT_Class_info#toString(constant_pool)
    * @see CONSTANT_Fieldref_info#class_index
    * @see CONSTANT_Methodref_info#class_index
    * @see CONSTANT_InterfaceMethodref_info#class_index
    */
   public static String getClassname(cp_info constant_pool[],int i) {
      cp_info c = constant_pool[i];
      switch (c.tag) {
      case cp_info.CONSTANT_Class:
         return c.toString(constant_pool);
      case cp_info.CONSTANT_Fieldref:
         return getClassname(constant_pool,((CONSTANT_Fieldref_info)c).class_index);
      case cp_info.CONSTANT_Methodref:
         return getClassname(constant_pool,((CONSTANT_Methodref_info)c).class_index);
      case cp_info.CONSTANT_InterfaceMethodref:
         return getClassname(constant_pool,
                             ((CONSTANT_InterfaceMethodref_info)c).class_index);
      }
      System.out.println("Request for classname for non-class object!");
      return "Can't find classname. Sorry.";
   }

   /** Returns the name of the given constant pool object, assuming it is
    * of type CONSTANT_NameAndType, _FieldRef, _Methodref or _InterfaceMethodref.
    * @param constant_pool constant pool of ClassFile.
    * @param i index of cp_info entry in question.
    * @return name of the associated object.
    * @see CONSTANT_Utf8_info
    * @see CONSTANT_NameAndType_info#name_index
    * @see CONSTANT_Fieldref_info#name_and_type_index
    * @see CONSTANT_Methodref_info#name_and_type_index
    * @see CONSTANT_InterfaceMethodref_info#name_and_type_index
    */
   public static String getName(cp_info constant_pool[],int i) {
      cp_info c = constant_pool[i];
      switch (c.tag) {
      case cp_info.CONSTANT_Utf8:
         return c.toString(constant_pool);
      case cp_info.CONSTANT_NameAndType:
         return getName(constant_pool,((CONSTANT_NameAndType_info)c).name_index);
      case cp_info.CONSTANT_Fieldref:
         return getName(constant_pool,((CONSTANT_Fieldref_info)c).name_and_type_index);
      case cp_info.CONSTANT_Methodref:
         return getName(constant_pool,((CONSTANT_Methodref_info)c).name_and_type_index);
      case cp_info.CONSTANT_InterfaceMethodref:
         return getName(constant_pool,
                        ((CONSTANT_InterfaceMethodref_info)c).name_and_type_index);
      }
      System.out.println("Request for name for non-named object!");
      return "Can't find name of that object. Sorry.";
   }

   /** Counts the number of parameters of the given method.
    * @param constant_pool constant pool of ClassFile.
    * @param m a constant pool index as accepted by getTypeDescr.
    * @return the number of parameters.
    * @see ClassFile#parseMethodDesc_params
    * @see cp_info#getTypeDescr
    */
   public static int countParams(cp_info constant_pool[],int m) {
      StringTokenizer st;
      String s = getTypeDescr(constant_pool,m);
      s = ClassFile.parseMethodDesc_params(s);
      st = new StringTokenizer(s,",",false);
      return st.countTokens();
   }

   /** Returns the type descriptor for the given constant pool
    * object, which must be a CONSTANT_Utf8, CONSTANT_NameAndType,
    * CONSTANT_Fieldref, CONSTANT_MethodRef, or CONSTANT_InterfaceMethodRef.
    * @param constant_pool constant pool of ClassFile.
    * @param i a constant pool index for an entry of type CONSTANT_Utf8,
    * CONSTANT_NameAndType, CONSTANT_MethodRef, or CONSTANT_InterfaceMethodRef.
    * @return the type descriptor.
    * @see CONSTANT_Utf8_info
    * @see CONSTANT_NameAndType_info#name_index
    * @see CONSTANT_Fieldref_info#name_and_type_index
    * @see CONSTANT_Methodref_info#name_and_type_index
    * @see CONSTANT_InterfaceMethodref_info#name_and_type_index
    */
   public static String getTypeDescr(cp_info constant_pool[],int i) {
      cp_info c = constant_pool[i];
      if (c instanceof CONSTANT_Utf8_info)
         return c.toString(constant_pool);
      if (c instanceof CONSTANT_NameAndType_info)
         return getTypeDescr(constant_pool,
                             ((CONSTANT_NameAndType_info)c).descriptor_index);
      if (c instanceof CONSTANT_Methodref_info)
         return getTypeDescr(constant_pool,
                             ((CONSTANT_Methodref_info)c).name_and_type_index);
      if (c instanceof CONSTANT_InterfaceMethodref_info)
         return getTypeDescr(constant_pool,
                             ((CONSTANT_InterfaceMethodref_info)c).name_and_type_index);
      if (c instanceof CONSTANT_Fieldref_info)
         return getTypeDescr(constant_pool,
                             ((CONSTANT_Fieldref_info)c).name_and_type_index);
      System.out.println("Invalid request for type descr!");
      return "Invalid type descriptor request.";
   }

   /** Returns the name of the field type of the given constant pool object.
    * @param constant_pool constant pool of ClassFile.
    * @param i a constant pool index for an entry of type CONSTANT_Utf8,
    * CONSTANT_NameAndType, or CONSTANT_FieldRef.
    * @return the type of the field.
    * @see CONSTANT_Utf8_info
    * @see CONSTANT_NameAndType_info#name_index
    * @see CONSTANT_Fieldref_info#name_and_type_index
    * @see cp_info#getTypeDescr
    * @see ClassFile#parseDesc
    */
   public static String fieldType(cp_info constant_pool[],int i) {
      return ClassFile.parseDesc(getTypeDescr(constant_pool,i),"");
   }
}
