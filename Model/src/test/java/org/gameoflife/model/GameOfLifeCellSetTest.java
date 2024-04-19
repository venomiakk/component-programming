package org.gameoflife.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeCellSetTest {
    PlainGameOfLifeSimulator pgols = new PlainGameOfLifeSimulator();
    GameOfLifeBoard gol = new GameOfLifeBoard(8, 8, pgols);
    GameOfLifeCell[][] golcBoard = new GameOfLifeCell[gol.getBoard().length][gol.getBoard()[0].length];

    boolean[][] newBoard = {
            {false, false, false, true, true, true, false, false},
            {false, true, true, true, false, true, false, true},
            {false, false, false, false, true, false, false, false},
            {true, false, true, false, false, true, true, true},
            {false, true, false, false, true, false, true, false},
            {true, false, true, false, true, true, true, false},
            {false, false, false, true, true, true, true, true},
            {false, true, false, true, true, false, false, true}
    };

    private void setTable() {
        for (int i = 0; i < gol.getBoard().length; i++) {
            for (int j = 0; j < gol.getBoard()[0].length; j++) {
                golcBoard[i][j] = new GameOfLifeCell();
                golcBoard[i][j].updateState(newBoard[i][j]);
            }
        }
        gol.setBoard(golcBoard);
    }

    @Test
    public void rowConstructorTest() {
        try {
            GameOfLifeRow row = new GameOfLifeRow(gol.getBoard()[0]);
            //row.cellsSet.add(new GameOfLifeCell());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

    }

    @Test
    public void columnConstructorTest() {
        try {
            GameOfLifeColumn column = new GameOfLifeColumn(0, gol.getBoard());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @Test
    public void rowCountAliveCellsTest() {
        setTable();
        assertEquals(gol.getRow(0).countAliveCells(), 3);
    }

    @Test
    public void rowCountDeadCellsTest() {
        setTable();
        assertEquals(gol.getRow(0).countDeadCells(), 5);
    }

    @Test
    public void columnCountAliveCellsTest() {
        setTable();
        assertEquals(gol.getColumn(0).countAliveCells(), 2);
    }

    @Test
    public void columnCountDeadCellsTest() {
        setTable();
        assertEquals(gol.getColumn(0).countDeadCells(), 6);
    }

    @Test
    public void HashEqualsStringTest()
    {
        PlainGameOfLifeSimulator pg = new PlainGameOfLifeSimulator();
        GameOfLifeBoard board = new GameOfLifeBoard(4,4, pg);
        GameOfLifeBoard board1 = new GameOfLifeBoard(4,4,pg);
        board1 = board;
        GameOfLifeCellSet set = board1.getColumn(1);
        assertEquals(board.getColumn(1).hashCode(),board.getColumn(1).hashCode());
        assertTrue(board1.getColumn(2).equals(board1.getColumn(2)));
        assertTrue(set.equals(set));
        assertFalse(board1.getColumn(1).equals(board1));
        assertFalse(board1.getColumn(1).equals(null));

        try {
            board.getColumn(1).toString();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        assertTrue(board.getColumn(1).toString().contains("org.gameoflife.model.GameOfLifeColumn@"));
    }

    @Test
    public void cohesionEqualsHashCodeTest() {
        GameOfLifeCell[] cells1 = new GameOfLifeCell[3];
        GameOfLifeCell[] cells2 = new GameOfLifeCell[3];
        for(int i = 0; i < 3; i++) {
            cells1[i] = new GameOfLifeCell();
            cells2[i] = new GameOfLifeCell();
        }

        GameOfLifeRow r1 = new GameOfLifeRow(cells1);
        GameOfLifeRow r2 = new GameOfLifeRow(cells2);
        assertTrue(r1.equals(r2) && r1.hashCode() == r2.hashCode());
    }

    @Test
    public void columnCloneTest() throws CloneNotSupportedException {
        GameOfLifeColumn columnClone = (GameOfLifeColumn) gol.getColumn(0).clone();
        assertEquals(columnClone, gol.getColumn(0));
        gol.set(0,0, !gol.get(0, 0));
        assertNotEquals(columnClone, gol.getColumn(0));
    }

    @Test
    public void rowCloneTest() throws CloneNotSupportedException {
        GameOfLifeRow rowClone = (GameOfLifeRow) gol.getRow(0).clone();
        assertEquals(rowClone, gol.getRow(0));
        gol.set(0,0, !gol.get(0, 0));
        assertNotEquals(rowClone, gol.getRow(0));
    }
}
