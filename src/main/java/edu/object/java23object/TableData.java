package edu.object.java23object;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

//TableData is the class which contains both the rows and columns of the table
public class TableData {
    final private ArrayList<String> columns = new ArrayList<>();
    //rows is a 2d array where the outer arrayList is for each column, and the Ob list is
    //The cell in each row.
    //In a table with 2 columns and 10 rows, ArrayList would be 2 and Ob list would be 10
    final private ArrayList<ObservableList<String>> rows = new ArrayList<>();

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
