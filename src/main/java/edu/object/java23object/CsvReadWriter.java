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
import java.util.Arrays;
import java.util.List;

public class CsvReadWriter {
    public TableData parseLines (List<String[]> lines) {
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

    //Write each line to the CSV
    public List<String[]> writeAllLines(List<String[]> lines, Path path) throws Exception {
        try (CSVWriter writer = new CSVWriter(new FileWriter(path.toString()))) {
            writer.writeAll(lines);
        }
        return readAllLines(path);
    }

    //Convert the CSV to a readable format, then pass on to writeAllLines above
    public void  saveCSV(Path path, TableData data) throws Exception {
        List<String[]> lines = new ArrayList<>();
        ArrayList columns = data.getColumns();
        lines.add((String[]) columns.toArray(new String[columns.size()]));

        //Add the rows
        ArrayList<ObservableList<String>> rows = data.getRows();
        rows.forEach(row -> {
            lines.add(row.toArray(new String[columns.size()]));
        });

        writeAllLines(lines, path);
    }
}
