package org.spbstu.morozovnikita;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeFieldTest {
    int fieldSize = 9;
    TicTacToeField field = new TicTacToeField(fieldSize);
    char[][] expected;

    @Before
    public void setUp(){
        expected = new char[fieldSize][fieldSize + 2];
        for (int i = 0; i < fieldSize; i++) for (int j = 0; j < fieldSize + 2; j++) expected[i][j] = 'n';
    }

    @Test
    public void setCell() {
        assertTrue(field.setCell('x', 0, 0));
        expected[0][0] = 'x';
        assertTrue(Arrays.deepEquals(field.getCells(), expected));

        assertFalse(field.setCell('o', 0, 0));

        assertFalse(field.setCell('p', 2, 2));
    }

    @Test
    public void removeCell() {
        assertFalse(field.removeCell(0, 0));

        field.setCell('x', 0, 0);
        assertTrue(field.removeCell(0, 0));
        assertTrue(Arrays.deepEquals(field.getCells(), expected));

        field.setCell('x', 0, 0);
        field.setCell('o', 1, 1);
        assertTrue(field.removeCell(1, 1));
        expected[0][0] = 'x';
        assertTrue(Arrays.deepEquals(field.getCells(), expected));
    }

    @Test
    public void findLongest() {
        for (int i = 0; i < fieldSize - 1; i++) {
            field.setCell('x', i, i);
        }
        for (int i = 0; i < 5; i++){
            field.setCell('x', 0, i);
        }
        assertEquals(field.findLongest('x'), fieldSize - 1);
    }
}