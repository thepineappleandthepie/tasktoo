package main.java.sdp.prac2;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Scanner;

public class XMLReader {
    public static void main(String[] args) {
        try {
            File inputFile = new File("C:\\Users\\anitz\\OneDrive\\Desktop\\SoftwarePrac2\\tasktoo\\src\\data.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the field names you want to output, separated by commas:");
            String input = scanner.nextLine();
            if (input == null || input.trim().isEmpty()) {
                System.out.println("No fields entered.");
                return;
            }
            String[] fields = input.split(",");

            NodeList nList = doc.getElementsByTagName("record");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    for (String field : fields) {
                        field = field.trim();
                        if (eElement.getElementsByTagName(field).getLength() > 0) {
                            System.out.println(field + ": " + eElement.getElementsByTagName(field).item(0).getTextContent());
                        } else {
                            System.out.println(field + " not found.");
                        }
                    }
                    System.out.println("-------------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
