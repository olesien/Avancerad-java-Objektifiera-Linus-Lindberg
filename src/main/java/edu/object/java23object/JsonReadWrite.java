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

                    newData.add(new Order(orderDate, region, rep1));
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
