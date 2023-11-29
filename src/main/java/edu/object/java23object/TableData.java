package edu.object.java23object;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class TableData {
    private ArrayList<String> columns = new ArrayList<>();
    private ArrayList<ObservableList<String>> rows = new ArrayList<>();

    public ArrayList<ObservableList<String>> getRows() {
        return rows;
    }

    public ArrayList<String> getColumns() {
        return columns;
    }

    public void addRow(ObservableList<String> row)  {
        rows.add(row);
    }

    public void addCol(String col) {
        columns.add(col);
    }

    public void removeByRow(ObservableList row) {
        rows.remove(row);
    }
}
