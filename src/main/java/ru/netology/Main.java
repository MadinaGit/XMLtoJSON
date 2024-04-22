package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        String fileName = "data.xml";
        List<Employee> list = parseXML(fileName);

        String json = listToJson(list);
        writeString(json);
        System.out.println(json);
    }


    public static List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(fileName);
        Node root = doc.getDocumentElement();
        List<Employee> list = read(root);
        return list;
    }

    private static List<Employee> read(Node node) {

        ArrayList<String> al = new ArrayList<>();
        List<Employee> list = new ArrayList<>();
        NodeList nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node1 = nodeList.item(i);
            if (Node.ELEMENT_NODE == node1.getNodeType()) {
                Element element = (Element) node1;

                String text1 = element.getTextContent();
                for (String a : text1.split("\n")) {
                    al.add(a.trim());
                }
            }
        }
        Employee employee1 = new Employee(Long.parseLong(al.get(1)), al.get(2), al.get(3), al.get(4), Integer.parseInt(al.get(5)));
        Employee employee2 = new Employee(Long.parseLong(al.get(8)), al.get(9), al.get(10), al.get(11), Integer.parseInt(al.get(12)));

        list.add(employee1);
        list.add(employee2);
        return list;
    }


    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();

        String json = gson.toJson(list, listType);
        return json;
    }

    public static void writeString(String json) {
        try (FileWriter file = new FileWriter("data2.json")) {
            file.write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}