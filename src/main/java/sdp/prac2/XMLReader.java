
package main.java.sdp.prac2;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class XMLReader {
    public static void main(String[] args) {
        try {
            File inputFile = new File("tasktoo\\src\\data.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("record");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Name: " + eElement.getElementsByTagName("name").item(0).getTextContent());
                    System.out.println("Postal/Zip: " + eElement.getElementsByTagName("postalZip").item(0).getTextContent());
                    System.out.println("Region: " + eElement.getElementsByTagName("region").item(0).getTextContent());
                    System.out.println("Country: " + eElement.getElementsByTagName("country").item(0).getTextContent());
                    System.out.println("Address: " + eElement.getElementsByTagName("address").item(0).getTextContent());
                    System.out.println("List: " + eElement.getElementsByTagName("list").item(0).getTextContent());
                    System.out.println("-------------------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


