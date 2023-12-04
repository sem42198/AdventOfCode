package test.java.day3;

import main.java.day3.Schematic;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SchematicTest {

    @Test
    void getNumbersTotal() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("467..114..");
        lines.add("...*......");
        lines.add("..35..633.");
        lines.add("......#...");
        lines.add("617*......");
        lines.add(".....+.58.");
        lines.add("..592.....");
        lines.add("......755.");
        lines.add("...$.*....");
        lines.add(".664.598..");
        Schematic schematic = new Schematic(lines);
        assertEquals(4361, schematic.getNumbersTotal());
    }

    @Test
    void getGearRatioTotal() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("467..114..");
        lines.add("...*......");
        lines.add("..35..633.");
        lines.add("......#...");
        lines.add("617*......");
        lines.add(".....+.58.");
        lines.add("..592.....");
        lines.add("......755.");
        lines.add("...$.*....");
        lines.add(".664.598..");
        Schematic schematic = new Schematic(lines);
        assertEquals(467835, schematic.getGearRatioTotal());
    }
}
