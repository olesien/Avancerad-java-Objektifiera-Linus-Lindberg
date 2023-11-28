package edu.object.java23object;

import com.opencsv.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvReadWriter {
    public ObservableList<Person> parseLines (List<String[]> lines) {
        ObservableList<Person> newData = FXCollections.observableArrayList();

        int firstNameIndex = 0;
        int lastNameIndex = 1;
        int emailIndex = 1;
        for (int rowI = 0; rowI < lines.size(); rowI++) {
            String[] line = lines.get(rowI);
            if (rowI == 0) {

                //Get columns and map them with their indexes.
                for (int colI = 0; colI < line.length; colI++) {
                    String value = line[colI];
                    switch (value) {
                        case "FirstName":
                            firstNameIndex = colI;
                        case "LastName":
                            lastNameIndex = colI;
                        case "Email":
                            emailIndex = colI;
                    }
                }
            } else {
                //Map rows
                String firstName = "";
                String lastName = "";
                String email = "";

                for (int colI = 0; colI < line.length; colI++) {
                    String value = line[colI];
                    if (firstNameIndex == colI) {
                        firstName = value;
                    }
                    if (lastNameIndex == colI) {
                        lastName = value;
                    }
                    if (emailIndex == colI) {
                        email = value;
                    }
                }
                newData.add(new Person(firstName, lastName, email));
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

    public List<String[]>  saveCSV(String[] columns, ObservableList<Person> data) throws Exception {
        List<String[]> lines = new ArrayList<>();
        lines.add(columns);

        //Add the rows
        data.forEach(person -> {
            String[] row = {person.getFirstName(), person.getLastName(), person.getEmail()};
            lines.add(row);
        });

        Path path = FileSystems.getDefault().getPath("src", "data.csv");
        return writeAllLines(lines, path);
    }
}
