package edu.object.java23object;

import java.io.File;
import java.util.ArrayList;

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
}
