package edu.object.java23object;
import java.io.File;
import java.io.Reader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.json.*;
import com.opencsv.*;

//Class to read in files, currently CSV and JSON.
public class ReadWriter {

    public TableData readCSV(Path filePath) throws Exception {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .withIgnoreQuotations(true)
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(0)
                    .withCSVParser(parser)
                    .build();

            List<String[]> lines =  csvReader.readAll();
            csvReader.close();

            TableData tableData = new TableData();

            for (int rowI = 0; rowI < lines.size(); rowI++) {
                String[] line = lines.get(rowI);
                if (rowI == 0) {

                    //Get columns and map them with their indexes.
                    for (int colI = 0; colI < line.length; colI++) {
                        String value = line[colI];
                        tableData.addCol(value);
                    }
                } else {
                    //Map rows
                    tableData.addRow(FXCollections.observableArrayList(line));
                }

            }
            return tableData;
        }
    }
    //Convert the CSV to a readable format, then pass on to writeAllLines above
    public void writeCSV(Path path, TableData data) throws Exception {
        List<String[]> lines = new ArrayList<>();
        ArrayList columns = data.getColumns();
        lines.add((String[]) columns.toArray(new String[columns.size()]));

        //Add the rows
        ArrayList<ObservableList<String>> rows = data.getRows();
        rows.forEach(row -> {
            lines.add(row.toArray(new String[columns.size()]));
        });

        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString()))) {
            writer.writeAll(lines);
        }
    }

    //JSON
    private String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    TableData readJSON (Path path) throws FileNotFoundException {
        TableData tableData = new TableData();

        //Read in the file and add it to a string
        File f = path.toFile();
        Scanner sc = new Scanner(f);
        String jsonString = "";
        while (sc.hasNext()) {
            String line = sc.nextLine();
            jsonString += line;
        }
        sc.close();

        //Make it to an array, and loop through each
        JSONArray arr = new JSONArray(jsonString);
        for (int i = 0; i < arr.length(); i++)
        {
            JSONObject object = arr.getJSONObject(i);
            //If it's the first index, it should be treated as the column
            if (i == 0) {
                Iterator<String> keys = object.keys();
                //Loop through all keys
                while(keys.hasNext()) {
                    String key = keys.next();
                    tableData.addCol((String) object.get(key));
                }

            } else {
                ObservableList<String> row = FXCollections.observableArrayList();
                Iterator<String> keys = object.keys();
                //Loop through all keys
                while(keys.hasNext()) {
                    String key = keys.next();
                    row.add((String) object.get(key)); //Convert to string

                }
                tableData.addRow(row); //add the row
            }
        }
        return tableData;
    }

    void writeJSON(Path path, TableData data) {
        List<JSONObject> list = new ArrayList<>();

        ArrayList<String> columns = data.getColumns();
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
