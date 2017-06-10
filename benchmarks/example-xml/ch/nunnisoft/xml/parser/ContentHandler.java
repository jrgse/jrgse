/*
 * ContextHandler.java
 *
 * Created on 25. gennaio 2004, 00:36
 */

package ch.nunnisoft.xml.parser;

/**
 *
 * @author  nunnari
 */
public interface ContentHandler {
    public void startDocument() throws LogicError;
    public void startElement( String tagname, java.util.Hashtable attrs ) throws LogicError;
    public void characters( char[] ch, int start, int length ) throws LogicError;
    public void endElement( String tagname ) throws LogicError;
    public void endDocument() throws LogicError;
}
