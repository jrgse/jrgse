/*
 * DocHandler.java
 *
 * Created on 24. gennaio 2004, 21:56
 */

package test.xml;

import ch.nunnisoft.xml.parser.ContentHandler;
import ch.nunnisoft.xml.parser.LogicError;
import java.util.*;

/**
 *
 * @author  nunnari
 */
public class DocHandler implements ContentHandler {

    private StringBuffer    m_characters = null;

    /** Creates a new instance of DocHandler */
    public DocHandler() {
        m_characters = new StringBuffer();
    }
    
    public void startDocument() throws LogicError {
     //   System.out.println( "start document" );
    }
    
    public void startElement( String tagname, Hashtable attrs ) throws LogicError {
        Enumeration enum1 = attrs.keys();
        while ( enum1.hasMoreElements() ) {
            String name = (String)enum1.nextElement();
            System.out.println( "  attrName: \"" + name + "\"   attrValue: \"" + (String)attrs.get( name ) + "\"" );
        }
        m_characters = new StringBuffer();
    }
    
    public void characters(char[] ch, int start, int length) throws LogicError {
        m_characters.append( ch, start, length );
//        System.out.print( ch );
    }
    
    public void endElement( String tagname ) throws LogicError {
        String data = m_characters.toString().trim();
        if ( data != null && !data.equals( "" ) )
            System.out.println( "" + m_characters.toString() );
        m_characters = new StringBuffer();
        System.out.println( "end element: " + tagname );
    }
    
    public void endDocument() throws LogicError {
        System.out.println( "end document" );
    }
    
}
