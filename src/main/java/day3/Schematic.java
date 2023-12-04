package main.java.day3;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class NumberCoordinate {
    int number;
    int row;
    int colStart;
    int colEnd;

    public NumberCoordinate(int number, int row, int colStart, int colEnd) {
        this.number = number;
        this.row = row;
        this.colStart = colStart;
        this.colEnd = colEnd;
    }

    private HashSet<int[]> getBorderingCoordinates() {
        HashSet<int[]> coordinates = new HashSet<>();
        coordinates.add(new int[] {this.row, this.colStart - 1});
        coordinates.add(new int[] {this.row, this.colEnd});
        for (int i = this.colStart - 1; i < this.colEnd + 1; i++) {
            coordinates.add(new int[] {this.row - 1, i});
            coordinates.add(new int[] {this.row + 1, i});
        }
        return coordinates;
    }

    public boolean borders(int row, int column) {
        return (
                (row == this.row && (column == this.colStart - 1 || column == this.colEnd)) ||
                        ((row == this.row - 1 || row == this.row + 1) && column >= this.colStart - 1 && column <= this.colEnd));

    }

    public boolean shouldInclude(Schematic schematic) {
        for (int[] coordinate: this.getBorderingCoordinates()) {
            if (schematic.isSymbol(coordinate[0], coordinate[1])) {
                return true;
            }
        }
        return false;
    }
}


class SymbolCoordinate {
    int row;
    int col;

    public SymbolCoordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

public class Schematic {

    ArrayList<String> lines;

    public Schematic(ArrayList<String> lines) {
        this.lines = lines;
    }

    private ArrayList<NumberCoordinate> getNumberCoordinates() {
        ArrayList<NumberCoordinate> numCoordinates = new ArrayList<>();
        for (int row = 0; row < this.lines.size(); row++) {
            String line = this.lines.get(row);
            Pattern stringPattern = Pattern.compile("\\d+");
            Matcher m = stringPattern.matcher(line);
            int start = 0;
            while (m.find(start)) {
                NumberCoordinate coordinate = new NumberCoordinate(Integer.parseInt(m.group()), row, m.start(), m.end());
                numCoordinates.add(coordinate);
                start = coordinate.colEnd;
            }
        }
        return numCoordinates;
    }

    public boolean isSymbol(int row, int col) {
        char data;
        try {
            data = this.lines.get(row).charAt(col);
        } catch (IndexOutOfBoundsException ignored) {
            data = '.';
        }
        return (data != '.' && (data < '0' || data > '9'));
    }

    private ArrayList<Integer> findGearRatios() {
        ArrayList<NumberCoordinate> numberCoordinates = this.getNumberCoordinates();
        ArrayList<Integer> gearRatios = new ArrayList<>();
        for (int row = 0; row < this.lines.size(); row++) {
            String line = this.lines.get(row);
            for (int col = 0; col < line.length(); col++) {
                char data = this.lines.get(row).charAt(col);
                if (data == '*') {
                    int borderingNums = 0;
                    ArrayList<Integer> numbers = new ArrayList<>();
                    for (NumberCoordinate coordinate: numberCoordinates) {
                        if (coordinate.borders(row, col)) {
                            borderingNums++;
                            numbers.add(coordinate.number);
                        }
                        if (borderingNums > 2) {
                            break;
                        }
                    }
                    if (borderingNums == 2) {
                        gearRatios.add(numbers.get(0) * numbers.get(1));
                    }
                }

            }
        }
        return gearRatios;
    }

    public int getGearRatioTotal() {
        int total = 0;
        ArrayList<Integer> gearRatios = this.findGearRatios();
        for (int ratio: gearRatios) {
            total += ratio;
        }
        return total;
    }

    public int getNumbersTotal() {
        int total = 0;
        ArrayList<NumberCoordinate> numberCoordinates = this.getNumberCoordinates();
        for (NumberCoordinate coordinate: numberCoordinates) {
            if (coordinate.shouldInclude(this)) {
                total += coordinate.number;
            }
        }
        return total;
    }
}
