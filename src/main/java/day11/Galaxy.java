package main.java.day11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Galaxy {
    final private ArrayList<ArrayList<Character>> lines = new ArrayList<>();
    final private ArrayList<Integer> expandedRows = new ArrayList<>();
    final private ArrayList<Integer> expandedCols = new ArrayList<>();

    public Galaxy(List<String> lines) {
        ArrayList<ArrayList<Character>> newLines = new ArrayList<>();
        for (String ignored : lines) {
            newLines.add(new ArrayList<>());
        }
        for (int i = 0; i < lines.get(0).length(); i++) {
            boolean empty = true;
            for (int j = 0; j < lines.size(); j++) {
                char curr = lines.get(j).charAt(i);
                newLines.get(j).add(curr);
                if (curr != '.') {
                    empty = false;
                }
            }
            // expand
            if (empty) {
                expandedCols.add(i);
            }
        }
        for (int row = 0; row < newLines.size(); row++) {
            ArrayList<Character> line = newLines.get(row);
            this.lines.add(line);
            if (line.stream().allMatch(character -> character == '.')) {
                expandedRows.add(row);
            }
        }
    }

    public long getTotalPairsDistance(int expansionFactor) {
        ArrayList<int[]> galaxies = new ArrayList<>();
        for (int row = 0; row < this.lines.size(); row++) {
            ArrayList<Character> line = this.lines.get(row);
            for (int col = 0; col < line.size(); col++) {
                if (line.get(col) == '#') {
                    galaxies.add(new int[] {row, col});
                }
            }
        }
        long total = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                int[] galaxyI = galaxies.get(i);
                int[] galaxyJ = galaxies.get(j);
                long expandedRows = this.expandedRows.stream().filter(row ->
                        (galaxyI[0] < row && row < galaxyJ[0]) || (galaxyJ[0] < row && row < galaxyI[0])
                ).count();
                long expandedCols = this.expandedCols.stream().filter(col ->
                        (galaxyI[1] < col && col < galaxyJ[1]) || (galaxyJ[1] < col && col < galaxyI[1])
                ).count();
                long verticalDistance = Math.abs(galaxyI[0] - galaxyJ[0]) + (expansionFactor - 1) * expandedRows;
                long horizontalDistance = Math.abs(galaxyI[1] - galaxyJ[1]) + (expansionFactor - 1) * expandedCols;
                long distance = verticalDistance + horizontalDistance;
                total += distance;
            }
        }
        return total;
    }

    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("inputs/day11_input.txt");
        Scanner sc = new Scanner(input);
        ArrayList<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        Galaxy galaxy = new Galaxy(lines);
        System.out.println(galaxy.getTotalPairsDistance(1000000));
    }
}
