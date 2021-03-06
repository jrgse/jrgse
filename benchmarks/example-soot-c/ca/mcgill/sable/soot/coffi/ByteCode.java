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

 - Modified on 28-Aug-1998 by Raja Vallee-Rai (kor@sable.mcgill.ca). (*)
   Fixed the wide instructions.

 - Modified on 15-Jun-1998 by Raja Vallee-Rai (kor@sable.mcgill.ca). (*)
   First internal release (Version 0.1).
*/

package ca.mcgill.sable.soot.coffi;

/** Procedural code for parsing and otherwise handling bytecode.
 * @author Clark Verbrugge
 */
class ByteCode {

   public static final int NOP = 0;
   public static final int ACONST_NULL = 1;
   public static final int ICONST_M1 = 2;
   public static final int ICONST_0 = 3;
   public static final int ICONST_1 = 4;
   public static final int ICONST_2 = 5;
   public static final int ICONST_3 = 6;
   public static final int ICONST_4 = 7;
   public static final int ICONST_5 = 8;
   public static final int LCONST_0 = 9;
   public static final int LCONST_1 = 10;
   public static final int FCONST_0 = 11;
   public static final int FCONST_1 = 12;
   public static final int FCONST_2 = 13;
   public static final int DCONST_0 = 14;
   public static final int DCONST_1 = 15;
   public static final int BIPUSH = 16;
   public static final int SIPUSH = 17;
   public static final int LDC1 = 18;
   public static final int LDC2 = 19;
   public static final int LDC2W = 20;
   public static final int ILOAD = 21;
   public static final int LLOAD = 22;
   public static final int FLOAD = 23;
   public static final int DLOAD = 24;
   public static final int ALOAD = 25;
   public static final int ILOAD_0 = 26;
   public static final int ILOAD_1 = 27;
   public static final int ILOAD_2 = 28;
   public static final int ILOAD_3 = 29;
   public static final int LLOAD_0 = 30;
   public static final int LLOAD_1 = 31;
   public static final int LLOAD_2 = 32;
   public static final int LLOAD_3 = 33;
   public static final int FLOAD_0 = 34;
   public static final int FLOAD_1 = 35;
   public static final int FLOAD_2 = 36;
   public static final int FLOAD_3 = 37;
   public static final int DLOAD_0 = 38;
   public static final int DLOAD_1 = 39;
   public static final int DLOAD_2 = 40;
   public static final int DLOAD_3 = 41;
   public static final int ALOAD_0 = 42;
   public static final int ALOAD_1 = 43;
   public static final int ALOAD_2 = 44;
   public static final int ALOAD_3 = 45;
   public static final int IALOAD = 46;
   public static final int LALOAD = 47;
   public static final int FALOAD = 48;
   public static final int DALOAD = 49;
   public static final int AALOAD = 50;
   public static final int BALOAD = 51;
   public static final int CALOAD = 52;
   public static final int SALOAD = 53;
   public static final int ISTORE = 54;
   public static final int LSTORE = 55;
   public static final int FSTORE = 56;
   public static final int DSTORE = 57;
   public static final int ASTORE = 58;
   public static final int ISTORE_0 = 59;
   public static final int ISTORE_1 = 60;
   public static final int ISTORE_2 = 61;
   public static final int ISTORE_3 = 62;
   public static final int LSTORE_0 = 63;
   public static final int LSTORE_1 = 64;
   public static final int LSTORE_2 = 65;
   public static final int LSTORE_3 = 66;
   public static final int FSTORE_0 = 67;
   public static final int FSTORE_1 = 68;
   public static final int FSTORE_2 = 69;
   public static final int FSTORE_3 = 70;
   public static final int DSTORE_0 = 71;
   public static final int DSTORE_1 = 72;
   public static final int DSTORE_2 = 73;
   public static final int DSTORE_3 = 74;
   public static final int ASTORE_0 = 75;
   public static final int ASTORE_1 = 76;
   public static final int ASTORE_2 = 77;
   public static final int ASTORE_3 = 78;
   public static final int IASTORE = 79;
   public static final int LASTORE = 80;
   public static final int FASTORE = 81;
   public static final int DASTORE = 82;
   public static final int AASTORE = 83;
   public static final int BASTORE = 84;
   public static final int CASTORE = 85;
   public static final int SASTORE = 86;
   public static final int POP = 87;
   public static final int POP2 = 88;
   public static final int DUP = 89;
   public static final int DUP_X1 = 90;
   public static final int DUP_X2 = 91;
   public static final int DUP2 = 92;
   public static final int DUP2_X1 = 93;
   public static final int DUP2_X2 = 94;
   public static final int SWAP = 95;
   public static final int IADD = 96;
   public static final int LADD = 97;
   public static final int FADD = 98;
   public static final int DADD = 99;
   public static final int ISUB = 100;
   public static final int LSUB = 101;
   public static final int FSUB = 102;
   public static final int DSUB = 103;
   public static final int IMUL = 104;
   public static final int LMUL = 105;
   public static final int FMUL = 106;
   public static final int DMUL = 107;
   public static final int IDIV = 108;
   public static final int LDIV = 109;
   public static final int FDIV = 110;
   public static final int DDIV = 111;
   public static final int IREM = 112;
   public static final int LREM = 113;
   public static final int FREM = 114;
   public static final int DREM = 115;
   public static final int INEG = 116;
   public static final int LNEG = 117;
   public static final int FNEG = 118;
   public static final int DNEG = 119;
   public static final int ISHL = 120;
   public static final int LSHL = 121;
   public static final int ISHR = 122;
   public static final int LSHR = 123;
   public static final int IUSHR = 124;
   public static final int LUSHR = 125;
   public static final int IAND = 126;
   public static final int LAND = 127;
   public static final int IOR = 128;
   public static final int LOR = 129;
   public static final int IXOR = 130;
   public static final int LXOR = 131;
   public static final int IINC = 132;
   public static final int I2L = 133;
   public static final int I2F = 134;
   public static final int I2D = 135;
   public static final int L2I = 136;
   public static final int L2F = 137;
   public static final int L2D = 138;
   public static final int F2I = 139;
   public static final int F2L = 140;
   public static final int F2D = 141;
   public static final int D2I = 142;
   public static final int D2L = 143;
   public static final int D2F = 144;
   public static final int INT2BYTE = 145;
   public static final int INT2CHAR = 146;
   public static final int INT2SHORT = 147;
   public static final int LCMP = 148;
   public static final int FCMPL = 149;
   public static final int FCMPG = 150;
   public static final int DCMPL = 151;
   public static final int DCMPG = 152;
   public static final int IFEQ = 153;
   public static final int IFNE = 154;
   public static final int IFLT = 155;
   public static final int IFGE = 156;
   public static final int IFGT = 157;
   public static final int IFLE = 158;
   public static final int IF_ICMPEQ = 159;
   public static final int IF_ICMPNE = 160;
   public static final int IF_ICMPLT = 161;
   public static final int IF_ICMPGE = 162;
   public static final int IF_ICMPGT = 163;
   public static final int IF_ICMPLE = 164;
   public static final int IF_ACMPEQ = 165;
   public static final int IF_ACMPNE = 166;
   public static final int GOTO = 167;
   public static final int JSR = 168;
   public static final int RET = 169;
   public static final int TABLESWITCH = 170;
   public static final int LOOKUPSWITCH = 171;
   public static final int IRETURN = 172;
   public static final int LRETURN = 173;
   public static final int FRETURN = 174;
   public static final int DRETURN = 175;
   public static final int ARETURN = 176;
   public static final int RETURN = 177;
   public static final int GETSTATIC = 178;
   public static final int PUTSTATIC = 179;
   public static final int GETFIELD = 180;
   public static final int PUTFIELD = 181;
   public static final int INVOKEVIRTUAL = 182;
   public static final int INVOKENONVIRTUAL = 183;
   public static final int INVOKESTATIC = 184;
   public static final int INVOKEINTERFACE = 185;
   /*    public static final int  = 186;*/
   public static final int NEW = 187;
   public static final int NEWARRAY = 188;
   public static final int ANEWARRAY = 189;
   public static final int ARRAYLENGTH = 190;
   public static final int ATHROW = 191;
   public static final int CHECKCAST = 192;
   public static final int INSTANCEOF = 193;
   public static final int MONITORENTER = 194;
   public static final int MONITOREXIT = 195;
   public static final int WIDE = 196;
   public static final int MULTIANEWARRAY = 197;
   public static final int IFNULL = 198;
   public static final int IFNONNULL = 199;
   public static final int GOTO_W = 200;
   public static final int JSR_W = 201;
   public static final int BREAKPOINT = 202;
   /*    public static final int  = 203;
        public static final int  = 204;
        public static final int  = 205;
        public static final int  = 206;
        public static final int  = 207;
        public static final int  = 208;*/
   public static final int RET_W = 209;
   /*    public static final int  = 210;
        public static final int  = 211;
        public static final int  = 212;
        public static final int  = 213;
        public static final int  = 214;
        public static final int  = 215;
        public static final int  = 216;
        public static final int  = 217;
        public static final int  = 218;
        public static final int  = 219;
        public static final int  = 220;
        public static final int  = 221;
        public static final int  = 222;
        public static final int  = 223;
        public static final int  = 224;
        public static final int  = 225;
        public static final int  = 226;
        public static final int  = 227;
        public static final int  = 228;
        public static final int  = 229;
        public static final int  = 230;
        public static final int  = 231;
        public static final int  = 232;
        public static final int  = 233;
        public static final int  = 234;
        public static final int  = 235;
        public static final int  = 236;
        public static final int  = 237;
        public static final int  = 238;
        public static final int  = 239;
        public static final int  = 240;
        public static final int  = 241;
        public static final int  = 242;
        public static final int  = 243;
        public static final int  = 244;
        public static final int  = 245;
        public static final int  = 246;
        public static final int  = 247;
        public static final int  = 248;
        public static final int  = 249;
        public static final int  = 250;
        public static final int  = 251;
        public static final int  = 252;
        public static final int  = 253;
        public static final int  = 254;
        public static final int  = 255;*/

   private int icount;
   private Instruction instructions[];

   /** Constructor---does nothing. */
   ByteCode() {  }

   /** Main entry point for disassembling bytecode into Instructions; this
    * method converts the given single bytecode into an Instruction (with
    * label set to index).
    * @param bc complete array of bytecode.
    * @param index offset within bc of the bytecode to parse.
    * @return a single Instruction object; note that Instruction references will
    * not be filled in (use build to post-process).
    * @see ClassFile#parseMethod
    * @see Instruction#parse
    * @see ByteCode#build
    */
   public Instruction disassemble_bytecode(byte bc[],int index) {
      // returns a string representing the disassembly of the
      // bytecode at the given index
      byte b = bc[index];
      boolean isWide = false;
      Instruction i;
      int x;

      x = ((int)b)&0xff;

      switch(x) {
      case BIPUSH:
         i = (Instruction)new Instruction_Bipush();
         break;
      case SIPUSH:
         i = (Instruction)new Instruction_Sipush();
         break;
      case LDC1:
         i = (Instruction)new Instruction_Ldc1();
         break;
      case LDC2:
         i = (Instruction)new Instruction_Ldc2();
         break;
      case LDC2W:
         i = (Instruction)new Instruction_Ldc2w();
         break;
      case ACONST_NULL:
         i = (Instruction)new Instruction_Aconst_null();
         break;
      case ICONST_M1:
         i = (Instruction)new Instruction_Iconst_m1();
         break;
      case ICONST_0:
         i = (Instruction)new Instruction_Iconst_0();
         break;
      case ICONST_1:
         i = (Instruction)new Instruction_Iconst_1();
         break;
      case ICONST_2:
         i = (Instruction)new Instruction_Iconst_2();
         break;
      case ICONST_3:
         i = (Instruction)new Instruction_Iconst_3();
         break;
      case ICONST_4:
         i = (Instruction)new Instruction_Iconst_4();
         break;
      case ICONST_5:
         i = (Instruction)new Instruction_Iconst_5();
         break;
      case LCONST_0:
         i = (Instruction)new Instruction_Lconst_0();
         break;
      case LCONST_1:
         i = (Instruction)new Instruction_Lconst_1();
         break;
      case FCONST_0:
         i = (Instruction)new Instruction_Fconst_0();
         break;
      case FCONST_1:
         i = (Instruction)new Instruction_Fconst_1();
         break;
      case FCONST_2:
         i = (Instruction)new Instruction_Fconst_2();
         break;
      case DCONST_0:
         i = (Instruction)new Instruction_Dconst_0();
         break;
      case DCONST_1:
         i = (Instruction)new Instruction_Dconst_1();
         break;
      case ILOAD:
         i = (Instruction)new Instruction_Iload();
         break;
      case ILOAD_0:
         i = (Instruction)new Instruction_Iload_0();
         break;
      case ILOAD_1:
         i = (Instruction)new Instruction_Iload_1();
         break;
      case ILOAD_2:
         i = (Instruction)new Instruction_Iload_2();
         break;
      case ILOAD_3:
         i = (Instruction)new Instruction_Iload_3();
         break;
      case LLOAD:
         i = (Instruction)new Instruction_Lload();
         break;
      case LLOAD_0:
         i = (Instruction)new Instruction_Lload_0();
         break;
      case LLOAD_1:
         i = (Instruction)new Instruction_Lload_1();
         break;
      case LLOAD_2:
         i = (Instruction)new Instruction_Lload_2();
         break;
      case LLOAD_3:
         i = (Instruction)new Instruction_Lload_3();
         break;
      case FLOAD:
         i = (Instruction)new Instruction_Fload();
         break;
      case FLOAD_0:
         i = (Instruction)new Instruction_Fload_0();
         break;
      case FLOAD_1:
         i = (Instruction)new Instruction_Fload_1();
         break;
      case FLOAD_2:
         i = (Instruction)new Instruction_Fload_2();
         break;
      case FLOAD_3:
         i = (Instruction)new Instruction_Fload_3();
         break;
      case DLOAD:
         i = (Instruction)new Instruction_Dload();
         break;
      case DLOAD_0:
         i = (Instruction)new Instruction_Dload_0();
         break;
      case DLOAD_1:
         i = (Instruction)new Instruction_Dload_1();
         break;
      case DLOAD_2:
         i = (Instruction)new Instruction_Dload_2();
         break;
      case DLOAD_3:
         i = (Instruction)new Instruction_Dload_3();
         break;
      case ALOAD:
         i = (Instruction)new Instruction_Aload();
         break;
      case ALOAD_0:
         i = (Instruction)new Instruction_Aload_0();
         break;
      case ALOAD_1:
         i = (Instruction)new Instruction_Aload_1();
         break;
      case ALOAD_2:
         i = (Instruction)new Instruction_Aload_2();
         break;
      case ALOAD_3:
         i = (Instruction)new Instruction_Aload_3();
         break;
      case ISTORE:
         i = (Instruction)new Instruction_Istore();
         break;
      case ISTORE_0:
         i = (Instruction)new Instruction_Istore_0();
         break;
      case ISTORE_1:
         i = (Instruction)new Instruction_Istore_1();
         break;
      case ISTORE_2:
         i = (Instruction)new Instruction_Istore_2();
         break;
      case ISTORE_3:
         i = (Instruction)new Instruction_Istore_3();
         break;
      case LSTORE:
         i = (Instruction)new Instruction_Lstore();
         break;
      case LSTORE_0:
         i = (Instruction)new Instruction_Lstore_0();
         break;
      case LSTORE_1:
         i = (Instruction)new Instruction_Lstore_1();
         break;
      case LSTORE_2:
         i = (Instruction)new Instruction_Lstore_2();
         break;
      case LSTORE_3:
         i = (Instruction)new Instruction_Lstore_3();
         break;
      case FSTORE:
         i = (Instruction)new Instruction_Fstore();
         break;
      case FSTORE_0:
         i = (Instruction)new Instruction_Fstore_0();
         break;
      case FSTORE_1:
         i = (Instruction)new Instruction_Fstore_1();
         break;
      case FSTORE_2:
         i = (Instruction)new Instruction_Fstore_2();
         break;
      case FSTORE_3:
         i = (Instruction)new Instruction_Fstore_3();
         break;
      case DSTORE:
         i = (Instruction)new Instruction_Dstore();
         break;
      case DSTORE_0:
         i = (Instruction)new Instruction_Dstore_0();
         break;
      case DSTORE_1:
         i = (Instruction)new Instruction_Dstore_1();
         break;
      case DSTORE_2:
         i = (Instruction)new Instruction_Dstore_2();
         break;
      case DSTORE_3:
         i = (Instruction)new Instruction_Dstore_3();
         break;
      case ASTORE:
         i = (Instruction)new Instruction_Astore();
         break;
      case ASTORE_0:
         i = (Instruction)new Instruction_Astore_0();
         break;
      case ASTORE_1:
         i = (Instruction)new Instruction_Astore_1();
         break;
      case ASTORE_2:
         i = (Instruction)new Instruction_Astore_2();
         break;
      case ASTORE_3:
         i = (Instruction)new Instruction_Astore_3();
         break;
      case IINC:
         i = (Instruction)new Instruction_Iinc();
         break;
      case WIDE:
      {
         int nextIndex = ((int) bc[index+1]) & 0xff;

         switch(nextIndex)
         {
            case ILOAD:
                i = new Instruction_Iload();
                break;

            case FLOAD:
                i = new Instruction_Fload();
                break;

            case ALOAD:
                i = new Instruction_Aload();
                break;

            case LLOAD:
                i = new Instruction_Lload();
                break;

            case ISTORE:
                i = new Instruction_Istore();
                break;

            case FSTORE:
                i = new Instruction_Fstore();
                break;

            case ASTORE:
                i = new Instruction_Astore();
                break;

            case LSTORE:
                i = new Instruction_Lstore();
                break;

            case RET:
                i = new Instruction_Ret();
                break;

            case IINC:
                i = new Instruction_Iinc();
                break;

            default:
                throw new RuntimeException("invalid wide instruction: " + nextIndex);
         }

         ((Instruction_bytevar) i).isWide = true;
         isWide = true;
      }

        break;

      case NEWARRAY:
         i = (Instruction)new Instruction_Newarray();
         break;
      case ANEWARRAY:
         i = (Instruction)new Instruction_Anewarray();
         break;
      case MULTIANEWARRAY:
         i = (Instruction)new Instruction_Multianewarray();
         break;
      case ARRAYLENGTH:
         i = (Instruction)new Instruction_Arraylength();
         break;
      case IALOAD:
         i = (Instruction)new Instruction_Iaload();
         break;
      case LALOAD:
         i = (Instruction)new Instruction_Laload();
         break;
      case FALOAD:
         i = (Instruction)new Instruction_Faload();
         break;
      case DALOAD:
         i = (Instruction)new Instruction_Daload();
         break;
      case AALOAD:
         i = (Instruction)new Instruction_Aaload();
         break;
      case BALOAD:
         i = (Instruction)new Instruction_Baload();
         break;
      case CALOAD:
         i = (Instruction)new Instruction_Caload();
         break;
      case SALOAD:
         i = (Instruction)new Instruction_Saload();
         break;
      case IASTORE:
         i = (Instruction)new Instruction_Iastore();
         break;
      case LASTORE:
         i = (Instruction)new Instruction_Lastore();
         break;
      case FASTORE:
         i = (Instruction)new Instruction_Fastore();
         break;
      case DASTORE:
         i = (Instruction)new Instruction_Dastore();
         break;
      case AASTORE:
         i = (Instruction)new Instruction_Aastore();
         break;
      case BASTORE:
         i = (Instruction)new Instruction_Bastore();
         break;
      case CASTORE:
         i = (Instruction)new Instruction_Castore();
         break;
      case SASTORE:
         i = (Instruction)new Instruction_Sastore();
         break;
      case NOP:
         i = (Instruction)new Instruction_Nop();
         break;
      case POP:
         i = (Instruction)new Instruction_Pop();
         break;
      case POP2:
         i = (Instruction)new Instruction_Pop2();
         break;
      case DUP:
         i = (Instruction)new Instruction_Dup();
         break;
      case DUP2:
         i = (Instruction)new Instruction_Dup2();
         break;
      case DUP_X1:
         i = (Instruction)new Instruction_Dup_x1();
         break;
      case DUP_X2:
         i = (Instruction)new Instruction_Dup_x2();
         break;
      case DUP2_X1:
         i = (Instruction)new Instruction_Dup2_x1();
         break;
      case DUP2_X2:
         i = (Instruction)new Instruction_Dup2_x2();
         break;
      case SWAP:
         i = (Instruction)new Instruction_Swap();
         break;
      case IADD:
         i = (Instruction)new Instruction_Iadd();
         break;
      case LADD:
         i = (Instruction)new Instruction_Ladd();
         break;
      case FADD:
         i = (Instruction)new Instruction_Fadd();
         break;
      case DADD:
         i = (Instruction)new Instruction_Dadd();
         break;
      case ISUB:
         i = (Instruction)new Instruction_Isub();
         break;
      case LSUB:
         i = (Instruction)new Instruction_Lsub();
         break;
      case FSUB:
         i = (Instruction)new Instruction_Fsub();
         break;
      case DSUB:
         i = (Instruction)new Instruction_Dsub();
         break;
      case IMUL:
         i = (Instruction)new Instruction_Imul();
         break;
      case LMUL:
         i = (Instruction)new Instruction_Lmul();
         break;
      case FMUL:
         i = (Instruction)new Instruction_Fmul();
         break;
      case DMUL:
         i = (Instruction)new Instruction_Dmul();
         break;
      case IDIV:
         i = (Instruction)new Instruction_Idiv();
         break;
      case LDIV:
         i = (Instruction)new Instruction_Ldiv();
         break;
      case FDIV:
         i = (Instruction)new Instruction_Fdiv();
         break;
      case DDIV:
         i = (Instruction)new Instruction_Ddiv();
         break;
      case IREM:
         i = (Instruction)new Instruction_Irem();
         break;
      case LREM:
         i = (Instruction)new Instruction_Lrem();
         break;
      case FREM:
         i = (Instruction)new Instruction_Frem();
         break;
      case DREM:
         i = (Instruction)new Instruction_Drem();
         break;
      case INEG:
         i = (Instruction)new Instruction_Ineg();
         break;
      case LNEG:
         i = (Instruction)new Instruction_Lneg();
         break;
      case FNEG:
         i = (Instruction)new Instruction_Fneg();
         break;
      case DNEG:
         i = (Instruction)new Instruction_Dneg();
         break;
      case ISHL:
         i = (Instruction)new Instruction_Ishl();
         break;
      case ISHR:
         i = (Instruction)new Instruction_Ishr();
         break;
      case IUSHR:
         i = (Instruction)new Instruction_Iushr();
         break;
      case LSHL:
         i = (Instruction)new Instruction_Lshl();
         break;
      case LSHR:
         i = (Instruction)new Instruction_Lshr();
         break;
      case LUSHR:
         i = (Instruction)new Instruction_Lushr();
         break;
      case IAND:
         i = (Instruction)new Instruction_Iand();
         break;
      case LAND:
         i = (Instruction)new Instruction_Land();
         break;
      case IOR:
         i = (Instruction)new Instruction_Ior();
         break;
      case LOR:
         i = (Instruction)new Instruction_Lor();
         break;
      case IXOR:
         i = (Instruction)new Instruction_Ixor();
         break;
      case LXOR:
         i = (Instruction)new Instruction_Lxor();
         break;
      case I2L:
         i = (Instruction)new Instruction_I2l();
         break;
      case I2F:
         i = (Instruction)new Instruction_I2f();
         break;
      case I2D:
         i = (Instruction)new Instruction_I2d();
         break;
      case L2I:
         i = (Instruction)new Instruction_L2i();
         break;
      case L2F:
         i = (Instruction)new Instruction_L2f();
         break;
      case L2D:
         i = (Instruction)new Instruction_L2d();
         break;
      case F2I:
         i = (Instruction)new Instruction_F2i();
         break;
      case F2L:
         i = (Instruction)new Instruction_F2l();
         break;
      case F2D:
         i = (Instruction)new Instruction_F2d();
         break;
      case D2I:
         i = (Instruction)new Instruction_D2i();
         break;
      case D2L:
         i = (Instruction)new Instruction_D2l();
         break;
      case D2F:
         i = (Instruction)new Instruction_D2f();
         break;
      case INT2BYTE:
         i = (Instruction)new Instruction_Int2byte();
         break;
      case INT2CHAR:
         i = (Instruction)new Instruction_Int2char();
         break;
      case INT2SHORT:
         i = (Instruction)new Instruction_Int2short();
         break;
      case IFEQ:
         i = (Instruction)new Instruction_Ifeq();
         break;
      case IFNULL:
         i = (Instruction)new Instruction_Ifnull();
         break;
      case IFLT:
         i = (Instruction)new Instruction_Iflt();
         break;
      case IFLE:
         i = (Instruction)new Instruction_Ifle();
         break;
      case IFNE:
         i = (Instruction)new Instruction_Ifne();
         break;
      case IFNONNULL:
         i = (Instruction)new Instruction_Ifnonnull();
         break;
      case IFGT:
         i = (Instruction)new Instruction_Ifgt();
         break;
      case IFGE:
         i = (Instruction)new Instruction_Ifge();
         break;
      case IF_ICMPEQ:
         i = (Instruction)new Instruction_If_icmpeq();
         break;
      case IF_ICMPLT:
         i = (Instruction)new Instruction_If_icmplt();
         break;
      case IF_ICMPLE:
         i = (Instruction)new Instruction_If_icmple();
         break;
      case IF_ICMPNE:
         i = (Instruction)new Instruction_If_icmpne();
         break;
      case IF_ICMPGT:
         i = (Instruction)new Instruction_If_icmpgt();
         break;
      case IF_ICMPGE:
         i = (Instruction)new Instruction_If_icmpge();
         break;
      case LCMP:
         i = (Instruction)new Instruction_Lcmp();
         break;
      case FCMPL:
         i = (Instruction)new Instruction_Fcmpl();
         break;
      case FCMPG:
         i = (Instruction)new Instruction_Fcmpg();
         break;
      case DCMPL:
         i = (Instruction)new Instruction_Dcmpl();
         break;
      case DCMPG:
         i = (Instruction)new Instruction_Dcmpg();
         break;
      case IF_ACMPEQ:
         i = (Instruction)new Instruction_If_acmpeq();
         break;
      case IF_ACMPNE:
         i = (Instruction)new Instruction_If_acmpne();
         break;
      case GOTO:
         i = (Instruction)new Instruction_Goto();
         break;
      case GOTO_W:
         i = (Instruction)new Instruction_Goto_w();
         break;
      case JSR:
         i = (Instruction)new Instruction_Jsr();
         break;
      case JSR_W:
         i = (Instruction)new Instruction_Jsr_w();
         break;
      case RET:
         i = (Instruction)new Instruction_Ret();
         break;
      case RET_W:
         i = (Instruction)new Instruction_Ret_w();
         break;
      case RETURN:
         i = (Instruction)new Instruction_Return();
         break;
      case IRETURN:
         i = (Instruction)new Instruction_Ireturn();
         break;
      case LRETURN:
         i = (Instruction)new Instruction_Lreturn();
         break;
      case FRETURN:
         i = (Instruction)new Instruction_Freturn();
         break;
      case DRETURN:
         i = (Instruction)new Instruction_Dreturn();
         break;
      case ARETURN:
         i = (Instruction)new Instruction_Areturn();
         break;
      case BREAKPOINT:
         i = (Instruction)new Instruction_Breakpoint();
         break;
      case TABLESWITCH:
         i = (Instruction)new Instruction_Tableswitch();
         break;
      case LOOKUPSWITCH:
         i = (Instruction)new Instruction_Lookupswitch();
         break;
      case PUTFIELD:
         i = (Instruction)new Instruction_Putfield();
         break;
      case GETFIELD:
         i = (Instruction)new Instruction_Getfield();
         break;
      case PUTSTATIC:
         i = (Instruction)new Instruction_Putstatic();
         break;
      case GETSTATIC:
         i = (Instruction)new Instruction_Getstatic();
         break;
      case INVOKEVIRTUAL:
         i = (Instruction)new Instruction_Invokevirtual();
         break;
      case INVOKENONVIRTUAL:
         i = (Instruction)new Instruction_Invokenonvirtual();
         break;
      case INVOKESTATIC:
         i = (Instruction)new Instruction_Invokestatic();
         break;
      case INVOKEINTERFACE:
         i = (Instruction)new Instruction_Invokeinterface();
         break;
      case ATHROW:
         i = (Instruction)new Instruction_Athrow();
         break;
      case NEW:
         i = (Instruction)new Instruction_New();
         break;
      case CHECKCAST:
         i = (Instruction)new Instruction_Checkcast();
         break;
      case INSTANCEOF:
         i = (Instruction)new Instruction_Instanceof();
         break;
      case MONITORENTER:
         i = (Instruction)new Instruction_Monitorenter();
         break;
      case MONITOREXIT:
         i = (Instruction)new Instruction_Monitorexit();
         break;
      default:
         //int j;
         //j = ((int)b)&0xff;
         //System.out.println("Unknown instruction op=" + j +
         //                   " at offset " + index);
         i = (Instruction)new Instruction_Unknown(b);
         break;
      }

      i.label = index;

      if(isWide)
        i.parse(bc,index+2);
      else
        i.parse(bc,index+1);

      return i;
   }

   /** Given a list of Instructions, this method converts all offsets
    * to pointers.
    * @param insts list of instructions; labels must be accurate.
    * @see Instruction#offsetToPointer
    * @see ClassFile#parseMethod
    * @see ClassFile#relabel
    */
   public void build(Instruction insts) {
      Instruction i,j;
      attribute_info ai;
      Code_attribute ca;

      i = insts;
      // find out how many instructions that is
      icount = 0;
      while (i != null) {
         icount++;
         i = i.next;
      }
      // build array of instructions
      if (icount>0) {
         instructions = new Instruction[icount];
         // and put the instructions into the array
         // identify targets of branch instructions. Why build an array
         // when we already have a list? In order to be able to locate
         // an instruction given its numeric label quickly.
         int k;
         k = 0;
         i = insts;
         while (i != null) {
            instructions[k] = i;
            k++;
            i = i.next;
         }

         // now convert all offsets to pointers
         i = insts;
         while (i!=null) {
            i.offsetToPointer(this);
            i = i.next;
         }
      }
   }

   /** Displays the code (in the form of Instructions) for the given list
    * of Instructions.
    * @param inst input list of instructions.
    * @param constant_pool constant pool of the ClassFile object.
    * @see ByteCode#showCode(Instruction, int, cp_info)
    */
   public static void showCode(Instruction inst,cp_info constant_pool[]) {
      showCode(inst,0,constant_pool);
   }
   /** Displays the code (in the form of Instructions) for the given list
    * of Instructions.
    * @param inst input list of instructions.
    * @param startinst index of the label of the instruction at which to begin.
    * @param constant_pool constant pool of the ClassFile object.
    * @see ByteCode#showCode(Instruction, cp_info)
    */
   public static void showCode(Instruction inst,int startinst,cp_info constant_pool[]) {
      int i;
      Instruction j = inst;
      String pref;
      i = startinst;
      while (j!=null) {
         if (i>999) pref = "";
         else if (i>99) pref = " ";
         else if (i>9) pref = "  ";
         else pref = "   ";
        // System.out.print(pref + i + ": ");
        // System.out.println(j.toString(constant_pool));
         i = j.nextOffset(i);
         j = j.next;
      }
   }

   /** Locates the Instruction in the list with the given label.
    * @param index label of desired instruction
    * @return Instruction object wiht that label, or <i>null</i> if not found.
    */
   // locates the instruction with an index value of the given
   public Instruction locateInst(int index) {
      return locateInstr(index,0,icount);
   }
   /** Performs a binary search of the instructions[] array. */
   private Instruction locateInstr(int index,int mini,int maxi) {
      int mid = (maxi-mini)/2 + mini;

      if (mini>maxi) return null;
      if (instructions[mid].label==index)
         return instructions[mid];
      if (instructions[mid].label > index)
         return locateInstr(index,mini,mid-1);
      return locateInstr(index,mid+1,maxi);
   }
}
