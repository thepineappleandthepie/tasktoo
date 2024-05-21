package main.java.sdp.prac2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.json.JSONObject;

import java.util.Set;

public class SAXHandler extends DefaultHandler {
    private Set<String> fields;  // Set of fields to be extracted from the XML
    private StringBuilder currentValue = new StringBuilder();  // To accumulate character data
    private JSONObject currentRecord;  // To store the current record as a JSON object
    private boolean isFieldSelected;  // Flag to check if the current field is selected

    /**
     * Constructor to initialize the handler with the set of fields to be extracted.
     *
     * @param fields Set of field names to be extracted from the XML.
     */
    public SAXHandler(Set<String> fields) {
        this.fields = fields;
    }

    /**
     * Called at the start of an XML element.
     * 
     * @param uri the Namespace URI, or the empty string if the element has no Namespace URI or if Namespace processing is not being performed.
     * @param localName the local name (without prefix), or the empty string if Namespace processing is not being performed.
     * @param qName the qualified name (with prefix), or the empty string if qualified names are not available.
     * @param attributes the attributes attached to the element. If there are no attributes, it shall be an empty Attributes object.
     * @throws SAXException any SAX exception, possibly wrapping another exception.
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // When a new <record> element starts, initialize a new JSONObject
        if ("record".equals(qName)) {
            currentRecord = new JSONObject();
        } else if (fields.contains(qName)) {
            // If the element is one of the selected fields, set the flag
            isFieldSelected = true;
        }
    }

    /**
     * Called with the text contents within an XML element.
     * 
     * @param ch the characters from the XML document.
     * @param start the start position in the array.
     * @param length the number of characters to read from the array.
     * @throws SAXException any SAX exception, possibly wrapping another exception.
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // Accumulate the characters if the field is selected
        if (isFieldSelected) {
            currentValue.append(ch, start, length);
        }
    }

    /**
     * Called at the end of an XML element.
     * 
     * @param uri the Namespace URI, or the empty string if the element has no Namespace URI or if Namespace processing is not being performed.
     * @param localName the local name (without prefix), or the empty string if Namespace processing is not being performed.
     * @param qName the qualified name (with prefix), or the empty string if qualified names are not available.
     * @throws SAXException any SAX exception, possibly wrapping another exception.
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // When an </record> element ends, print the JSON object
        if ("record".equals(qName)) {
            System.out.println(currentRecord.toString(4));
        } else if (fields.contains(qName)) {
            // If the element is one of the selected fields, add it to the JSON object
            currentRecord.put(qName, currentValue.toString().trim());
            currentValue.setLength(0);  // Clear the accumulated characters
            isFieldSelected = false;  // Reset the flag
        }
    }

    /**
     * Called at the end of the XML document.
     * 
     * @throws SAXException 
     */
    @Override
    public void endDocument() throws SAXException {
        // Any cleanup if needed after parsing
    }
}
