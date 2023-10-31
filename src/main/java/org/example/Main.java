package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String xmlFileString = "/Users/timofejzabolotko/IdeaProjects/XMLtoJSON/data.xml";
        List<Employee> list = parseXML(xmlFileString);
        String s = listToJson(list);
        writeString(s);


    }

    public static List<Employee> parseXML(String xmlFileString) {
        List<Employee> employees = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFileString));
            NodeList list = document.getElementsByTagName("employee");
            for (int i = 0; i < list.getLength(); i++) {
                Element employee = (Element) list.item(i);
                long id = Long.parseLong(employee.getElementsByTagName("id").item(0).getTextContent());
                String firstName = employee.getElementsByTagName("firstName").item(0).getTextContent();
                String lastName = employee.getElementsByTagName("lastName").item(0).getTextContent();
                String country = employee.getElementsByTagName("country").item(0).getTextContent();
                int age = Integer.parseInt(employee.getElementsByTagName("age").item(0).getTextContent());
                employees.add(new Employee(id, firstName, lastName, country, age));
            }

            return employees;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public static String listToJson(List<Employee> list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        return gson.toJson(list, listType);
    }

    public static void writeString(String jsonString) {
        try (FileWriter fileWriter = new FileWriter("data.json")) {
            fileWriter.write(jsonString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}




