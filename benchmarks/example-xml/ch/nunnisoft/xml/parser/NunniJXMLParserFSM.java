/*
 * $Id: NunniJXMLParserFSM.java,v 1.2 2004/02/07 00:22:04 nunnari Exp $
 *
 * Copyright (c) 2004 Roberto Nunnari - roberto.nunnari@nunnisoft.ch
 * All rights reserved.
 *
 *  This file is part of NunniMJAX.
 *
 *  NunniMJAX is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  NunniMJAX is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with NunniFSMGen; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
/*
 * this file was generated by NunniFSMGen - do not edit!
 */


package ch.nunnisoft.xml.parser;

import java.util.Hashtable;
import java.util.Stack;


public class NunniJXMLParserFSM extends NunniJXMLParser
{
    private NunniJXMLParserState m_state = null;
    
    private NunniJXMLParserFSM() {}


    public NunniJXMLParserFSM( Object o ) {
        super( o );
        m_state = NunniJXMLParserPREState.instance();
    }


    public String toString() {
        return m_state.toString();
    }


    public void openbracket( char o ) throws LogicError {
        m_state.openbracket( this, o );
    }


    public void closebracket( char o ) throws LogicError {
        m_state.closebracket( this, o );
    }


    public void opensqbracket( char o ) throws LogicError {
        m_state.opensqbracket( this, o );
    }


    public void closesqbracket( char o ) throws LogicError {
        m_state.closesqbracket( this, o );
    }


    public void questionmark( char o ) throws LogicError {
        m_state.questionmark( this, o );
    }

    public void escalmatmark( char o ) throws LogicError {
        m_state.escalmatmark( this, o );
    }

    public static void escalmatmark2( char o ) throws LogicError {
        System.out.println("jaja");
    }
    public void minus( char o ) throws LogicError {
        m_state.minus( this, o );
    }


    public void whitespace( char o ) throws LogicError {
        m_state.whitespace( this, o );
    }


    public void slash( char o ) throws LogicError {
        m_state.slash( this, o );
    }


    public void equal( char o ) throws LogicError {
        m_state.equal( this, o );
    }


    public void squote( char o ) throws LogicError {
        m_state.squote( this, o );
    }


    public void dquote( char o ) throws LogicError {
        m_state.dquote( this, o );
    }


    public void literal( char o ) throws LogicError {
        m_state.literal( this, o );
    }


    public void other( char o ) throws LogicError {
        m_state.other( this, o );
    }


    public void end( char o ) throws LogicError {
        m_state.end( this, o );
    }


    void changeState( NunniJXMLParserState newState ) {
        m_state = newState;
    }
}