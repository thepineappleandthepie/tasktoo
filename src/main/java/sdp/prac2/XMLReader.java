package main.java.sdp.prac2;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Scanner;
import org.json.JSONObject;

public class XMLReader {
    public static void main(String[] args) {
        try {
            // Path to the XML file
            File inputFile = new File("tasktoo\\src\\data.xml");

            // Validate if the file exists and is a file
            if (!inputFile.exists() || !inputFile.isFile()) {
                System.out.println("The specified file does not exist or is not a valid file.");
                return;
            }
            
            // Create a DocumentBuilderFactory and DocumentBuilder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Parse the XML file and normalize the document
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Read user input for field names to output
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the field names you want to output, separated by commas:");
            String input = scanner.nextLine();
            
            // Check if input is null or empty
            if (input == null || input.trim().isEmpty()) {
                System.out.println("No fields entered.");
                return;
            }

            // Split the input string into an array of field names
            String[] fields = input.split(",");

            // Validate if the document contains any records
            NodeList nList = doc.getElementsByTagName("record");
            if (nList.getLength() == 0) {
                System.out.println("No records found in the XML file.");
                return;
            }

            // Iterate through each "record" node
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                // Check if the current node is an element node
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    // Create a JSON object to store the selected fields and their values
                    JSONObject jsonObject = new JSONObject();

                    // Iterate through each user-specified field
                    for (String field : fields) {
                        field = field.trim();  // Remove leading/trailing whitespace
                        // Validate field name is not empty
                        if (field.isEmpty()) {
                            System.out.println("Empty field name detected, skipping.");
                            continue;
                        }
                        // Check if the field exists in the current "record" element
                        if (eElement.getElementsByTagName(field).getLength() > 0) {
                            // Add the field and its value to the JSON object
                            jsonObject.put(field, eElement.getElementsByTagName(field).item(0).getTextContent());
                        } else {
                            // Add a placeholder if the field is not found
                            jsonObject.put(field, "not found");
                        }
                    }

                    // Print the JSON object with pretty printing (indentation of 4 spaces)
                    System.out.println(jsonObject.toString(4));
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid argument: " + e.getMessage());
        } catch (Exception e) {
            // Handle any other exceptions that occur during parsing or processing
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
