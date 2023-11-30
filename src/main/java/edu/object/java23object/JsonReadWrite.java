package edu.object.java23object;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.*;

public class JsonReadWrite {
    //This is used for the relation between the array indexes and the alphabet used in the JSON format.
    private String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q"}; //That'll do
    TableData read (Path path) throws FileNotFoundException {
        TableData tableData = new TableData();

            File f = path.toFile();
            Scanner sc = new Scanner(f);
            String jsonString = "";
            while (sc.hasNext()) {
                String line = sc.nextLine();
                jsonString += line;
            }
            sc.close();
            System.out.println(jsonString);


            JSONArray arr = new JSONArray(jsonString);
            for (int i = 0; i < arr.length(); i++)
            {
                JSONObject object = arr.getJSONObject(i);
                if (i == 0) {
                    Iterator<String> keys = object.keys();
                    while(keys.hasNext()) {
                        String key = keys.next();
                        tableData.addCol((String) object.get(key));
                    }

                } else {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    Iterator<String> keys = object.keys();
                    while(keys.hasNext()) {
                        String key = keys.next();
                        row.add((String) object.get(key));

                    }
                    tableData.addRow(row);
                }
            }
        return tableData;
    }

    void saveJSON(Path path, TableData data) {
        List<JSONObject> list = new ArrayList<>();

        ArrayList columns = data.getColumns();
        ArrayList<ObservableList<String>> rows = data.getRows();

        //Column Head
        JSONObject jOb = new JSONObject();
        for (int i = 0; i < columns.size(); i++) { //To make it easier to scale later
            String letter = alphabet[i];
            String column = (String) columns.get(i);
            jOb.put(letter, column);
        }
        list.add(jOb);


        //The rows below
        rows.forEach(row -> {

            JSONObject jsonRow = new JSONObject();
            for (int i = 0; i < row.size(); i++) {
                String letter = alphabet[i];
                String cell = row.get(i);
                jsonRow.put(letter, cell);
            }
            list.add(jsonRow);

        });

        JSONArray ja = new JSONArray(list);

        try (PrintWriter out = new PrintWriter(new FileWriter(path.toFile()))) {
            out.write(ja.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
