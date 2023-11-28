package edu.object.java23object;

import com.opencsv.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvReadWriter {
    public ObservableList<Order> parseLines (List<String[]> lines) {
        ObservableList<Order> newData = FXCollections.observableArrayList();

        int orderDateIndex = 0;
        int regionIndex = 1;
        int rep1index = 2;
        int rep2index = 3;
        int itemIndex = 4;
        int unitsIndex = 5;
        int unitCostIndex = 6;
        int totalIndex = 7;
        for (int rowI = 0; rowI < lines.size(); rowI++) {
            String[] line = lines.get(rowI);
            if (rowI == 0) {

                //Get columns and map them with their indexes.
                for (int colI = 0; colI < line.length; colI++) {
                    String value = line[colI];
                    switch (value) {
                        case "OrderDate":
                            orderDateIndex = colI;
                        case "Region":
                            regionIndex = colI;
                        case "Rep1":
                            rep1index = colI;
                        case "Rep2":
                            rep2index = colI;
                        case "Item":
                            itemIndex = colI;
                        case "Units":
                            unitsIndex = colI;
                        case "UnitCost":
                            unitCostIndex = colI;
                        case "Total":
                            totalIndex = colI;
                    }
                }
            } else {
                //Map rows
                String orderDate = "";
                String region = "";
                String rep1 = "";
                String rep2 = "";
                String item = "";
                String units = "";
                String unitCost = "";
                String total = "";

                for (int colI = 0; colI < line.length; colI++) {
                    String value = line[colI];
                    if (orderDateIndex == colI) {
                        orderDate = value;
                    }
                    if (regionIndex == colI) {
                        region = value;
                    }
                    if (rep1index == colI) {
                        rep1 = value;
                    }

                    if (rep2index == colI) {
                        rep2 = value;
                    }
                    if (itemIndex == colI) {
                        item = value;
                    }
                    if (unitsIndex == colI) {
                        units = value;
                    }
                    if (unitCostIndex == colI) {
                        unitCost = value;
                    }
                    if (totalIndex == colI) {
                        total = value;
                    }
                }
                newData.add(new Order(orderDate, region, rep1, rep2, item, units, unitCost, total));
            }

        }
        return newData;
    }

    public List<String[]> readAllLines(Path filePath) throws Exception {
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
            return lines;
        }
    }

    public List<String[]> writeAllLines(List<String[]> lines, Path path) throws Exception {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString()))) {
            writer.writeAll(lines);
        }
        return readAllLines(path);
    }

    public List<String[]>  saveCSV(String[] columns, ObservableList<Order> data) throws Exception {
        List<String[]> lines = new ArrayList<>();
        lines.add(columns);

        //Add the rows
        data.forEach(person -> {
            String[] row = {person.getOrderDate(), person.getRegion(), person.getRep1()};
            lines.add(row);
        });

        Path path = FileSystems.getDefault().getPath("src", "data.csv");
        return writeAllLines(lines, path);
    }
}
