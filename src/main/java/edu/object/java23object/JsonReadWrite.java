package edu.object.java23object;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.*;

public class JsonReadWrite {
    private String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q"}; //That'll do
    ObservableList<Order> read (Path path) {
        ObservableList<Order> newData = FXCollections.observableArrayList();
        try {
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
                if (i != 0) { //Skip the first one as that is just the col heads
                    String orderDate = arr.getJSONObject(i).getString("A");
                    String region = arr.getJSONObject(i).getString("B");
                    String rep1 = arr.getJSONObject(i).getString("C");
                    String rep2 = arr.getJSONObject(i).getString("D");
                    String item = arr.getJSONObject(i).getString("E");
                    String units = arr.getJSONObject(i).getString("F");
                    String unitCost = arr.getJSONObject(i).getString("G");
                    String total = arr.getJSONObject(i).getString("H");

                    newData.add(new Order(orderDate, region, rep1, rep2, item, units, unitCost, total));
                }


            }


         /*   for (String s : aryL) {
                System.out.println(s);
            }
*/
        } catch (Exception e) {
            System.out.println("ERROR" + e.toString());
        }
        return newData;
    }

    void saveJSON(Path path, String[] columns, ObservableList<Order> data) {
        List<JSONObject> list = new ArrayList<>();
        //Column Head
        JSONObject jOb = new JSONObject();
        for (int i = 0; i < columns.length; i++) { //To make it easier to scale later
            String letter = alphabet[i];
            String column = columns[i];
            jOb.put(letter, column);
        }
        list.add(jOb);


        //The rows below
        data.forEach(row -> {

            JSONObject jsonRow = new JSONObject();
            String[] orderArray = row.toCustomArray();
            for (int i = 0; i < orderArray.length; i++) {
                String letter = alphabet[i];
                String cell = orderArray[i];
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
