package test.java.day15;

import main.java.day15.Box;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest {

    @Test
    void getFocusingPower() {
        assertEquals(145, Box.getFocusingPower("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"));
    }
}