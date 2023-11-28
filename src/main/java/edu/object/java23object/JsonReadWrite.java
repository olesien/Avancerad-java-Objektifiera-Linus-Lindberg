package edu.object.java23object;

import java.io.File;
import java.util.ArrayList;

import java.util.Scanner;

import org.json.*;

public class JsonReadWrite {

    void read () {
        ArrayList<String> aryL = new ArrayList<>();
        String[][] array2d = new String[3][11];
        try {
            File f = new File("src/Materialllista.json");
            Scanner sc = new Scanner(f);
            String page = "";
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] array = line.split(",", 3);
                //aryL.addAll(Arrays.asList(array));
                //System.out.println(array[0]);
                //System.out.println(Arrays.deepToString(array));
                //System.out.println(line);

                page += line;
            }
            System.out.println(page);
            String[] split = page.split("}");

            for (int i = 0; i < split.length; i++) {
                String line = split[i];
                String[] cols = line.split(",");
                System.out.println(cols[0] + "\n");
                System.out.println(cols[1] + "\n");
                System.out.println(cols[2] + "\n");

                System.out.println(line + "\n");
            }



            sc.close();

         /*   for (String s : aryL) {
                System.out.println(s);
            }
*/
        } catch (Exception e) {
            System.out.println("ERROR" + e.toString());
        }
    }
}
