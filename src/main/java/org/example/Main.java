package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
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
        System.out.println(s);
        writeString(s);


    }

    public static List<Employee> parseXML(String xmlFileString) {
        String[] attributes = {"id", "firstName", "lastName", "country", "age"};
        String[][] params = new String[2][5];
        List<Employee> employees = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFileString));
            for (int i = 0; i < attributes.length; i++) {
                NodeList nodeList = document.getElementsByTagName(attributes[i]);
                for (int j = 0; j < nodeList.getLength(); j++) {
                    params[j][i] = nodeList.item(j).getTextContent();
                }

            }


            Employee e1 = new Employee(Long.parseLong(params[0][0]), params[0][1], params[0][2], params[0][3], Integer.parseInt(params[0][4]));
            Employee e2 = new Employee(Long.parseLong(params[1][0]), params[1][1], params[1][2], params[1][3], Integer.parseInt(params[1][4]));
            employees.add(e1);
            employees.add(e2);


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




