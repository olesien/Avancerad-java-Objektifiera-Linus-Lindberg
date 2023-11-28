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

    ObservableList<Order> read () {
        ObservableList<Order> newData = FXCollections.observableArrayList();
        try {
            File f = new File("src/data.json");
            Scanner sc = new Scanner(f);
            String jsonString = "";
            while (sc.hasNext()) {
                String line = sc.nextLine();
                jsonString += line;
            }
            sc.close();
            System.out.println(jsonString);


            JSONArray arr = new JSONArray(jsonString);
            //String[] cols = {"A", "B", "C", "D", "E", "F", "G", "H"};
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

    void saveJSON(ObservableList<Order> data) {
        List<JSONObject> list = new ArrayList<>();
        //Column Head
        JSONObject jOb = new JSONObject();
        jOb.put("A", "OrderDate");
        jOb.put("B", "Region");
        jOb.put("C", "Rep1");
        jOb.put("D", "Rep2");
        jOb.put("E", "Item");
        jOb.put("F", "Units");
        jOb.put("G", "UnitCost");
        jOb.put("H", "Total");
        list.add(jOb);

        //The rows below
        data.forEach(row -> {
            JSONObject jsonRow = new JSONObject();
            jsonRow.put("A", row.getOrderDate());
            jsonRow.put("B", row.getRegion());
            jsonRow.put("C", row.getRep1());
            jsonRow.put("D", row.getRep2());
            jsonRow.put("E", row.getItem());
            jsonRow.put("F", row.getUnits());
            jsonRow.put("G", row.getUnitCost());
            jsonRow.put("H", row.getTotal());
            list.add(jsonRow);

        });

        JSONArray ja = new JSONArray(list);
        Path path = FileSystems.getDefault().getPath("src", "data.json");

        try (PrintWriter out = new PrintWriter(new FileWriter(path.toFile()))) {
            out.write(ja.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
