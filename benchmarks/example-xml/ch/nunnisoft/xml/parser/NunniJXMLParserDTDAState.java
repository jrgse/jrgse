/*
 * $Id: NunniJXMLParserDTDAState.java,v 1.2 2004/02/07 00:22:04 nunnari Exp $
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


class NunniJXMLParserDTDAState extends NunniJXMLParserState
{
    private static NunniJXMLParserDTDAState m_instance = null;


    private NunniJXMLParserDTDAState() {}


    static NunniJXMLParserDTDAState instance() {
        if ( m_instance == null )
            m_instance = new NunniJXMLParserDTDAState();
        return m_instance;
    }


    void openbracket( NunniJXMLParserFSM ctx,  char o ) throws LogicError {
        ctx.changeState( NunniJXMLParserERRORState.instance() );
        throw new LogicError();
    }


    void closebracket( NunniJXMLParserFSM ctx,  char o ) throws LogicError {
        ctx.changeState( NunniJXMLParserERRORState.instance() );
        throw new LogicError();
    }


    void opensqbracket( NunniJXMLParserFSM ctx,  char o ) throws LogicError {
        ctx.changeState( NunniJXMLParserERRORState.instance() );
        throw new LogicError();
    }


    void closesqbracket( NunniJXMLParserFSM ctx,  char o ) throws LogicError {
        ctx.changeState( NunniJXMLParserERRORState.instance() );
        throw new LogicError();
    }


    void questionmark( NunniJXMLParserFSM ctx,  char o ) throws LogicError {
        ctx.changeState( NunniJXMLParserERRORState.instance() );
        throw new LogicError();
    }


    void escalmatmark( NunniJXMLParserFSM ctx,  char o ) throws LogicError {
        ctx.changeState( NunniJXMLParserERRORState.instance() );
        throw new LogicError();
    }


    void minus( NunniJXMLParserFSM ctx,  char o ) throws LogicError {
        ctx.changeState( NunniJXMLParserERRORState.instance() );
        throw new LogicError();
    }


    void whitespace( NunniJXMLParserFSM ctx,  char o ) throws LogicError {
        try {
            ctx.dtdaWhitespace( o );
        }
        catch( LogicError e ) {
            ctx.changeState( NunniJXMLParserDTDBState.instance() );
            throw e;
        }
        ctx.changeState( NunniJXMLParserDTDBState.instance() );
    }


    void slash( NunniJXMLParserFSM ctx,  char o ) throws LogicError {
        ctx.changeState( NunniJXMLParserERRORState.instance() );
        throw new LogicError();
    }


    void equal( NunniJXMLParserFSM ctx,  char o ) throws LogicError {
        ctx.changeState( NunniJXMLParserERRORState.instance() );
        throw new LogicError();
    }


    void squote( NunniJXMLParserFSM ctx,  char o ) throws LogicError {
        ctx.changeState( NunniJXMLParserERRORState.instance() );
        throw new LogicError();
    }


    void dquote( NunniJXMLParserFSM ctx,  char o ) throws LogicError {
        ctx.changeState( NunniJXMLParserERRORState.instance() );
        throw new LogicError();
    }


    void literal( NunniJXMLParserFSM ctx,  char o ) throws LogicError {
        ctx.dtdaLiteral( o );
    }


    void other( NunniJXMLParserFSM ctx,  char o ) throws LogicError {
        ctx.changeState( NunniJXMLParserERRORState.instance() );
        throw new LogicError();
    }


    void end( NunniJXMLParserFSM ctx,  char o ) throws LogicError {
        ctx.changeState( NunniJXMLParserERRORState.instance() );
        throw new LogicError();
    }
}
