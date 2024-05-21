package main.java.sdp.prac2;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class XMLReader {
    public static void main(String[] args) {
        try {
            // Path to the XML file
            File inputFile = new File("C:\\Users\\anitz\\OneDrive\\Desktop\\SoftwarePrac2\\tasktoo\\src\\data.xml");

            // Validate if the file exists and is a file
            if (!inputFile.exists() || !inputFile.isFile()) {
                System.out.println("The specified file does not exist or is not a valid file.");
                return;
            }

            // Read user input for field names to output
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the field names you want to output, separated by commas:");
            String input = scanner.nextLine();

            // Check if input is null or empty
            if (input == null || input.trim().isEmpty()) {
                System.out.println("No fields entered.");
                return;
            }

            // Split the input string into a set of field names
            String[] fieldArray = input.split(",");
            Set<String> fields = new HashSet<>();
            for (String field : fieldArray) {
                fields.add(field.trim());
            }

            // Create an instance of the SAX parser
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            SAXHandler handler = new SAXHandler(fields);
            xmlReader.setContentHandler(handler);

            // Parse the XML file
            xmlReader.parse(new InputSource(inputFile.getAbsolutePath()));

        } catch (Exception e) {
            // Handle any exceptions that occur during parsing or processing
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
