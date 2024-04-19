package org.gameoflife.model;

import org.gameoflife.model.exceptions.CloneException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeCellTest {

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
    public void getCellValueTest() {
        setTable();
        assertFalse(golcBoard[0][0].getCellValue());
    }

    @Test
    public void nextStateTest() {
        setTable();
        assertFalse(gol.getBoard()[1][1].nextState());

    }

    @Test
    public void updateStateTest() {
        setTable();
        GameOfLifeCell cell = new GameOfLifeCell();
        assertFalse(cell.getCellValue());
        try {
            cell.updateState(true);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        assertTrue(cell.getCellValue());
    }

    GameOfLifeCell newCell = new GameOfLifeCell();
    @Test
    public void addNeighborTest() {
        try {
            gol.getBoard()[0][0].addNeighbor(newCell);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @Test
    public void getNeighborsTest() {
        GameOfLifeCell cell = new GameOfLifeCell();
        cell.addNeighbor(newCell);
        assertSame(cell.getNeighbors().get(0), newCell);
    }

    @Test
    public void toStringTest() {
        GameOfLifeCell cell = new GameOfLifeCell();
        try {
            cell.toString();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        assertTrue(cell.toString().contains("org.gameoflife.model.GameOfLifeCell@"));
    }

    @Test
    public void equalsTest() {
        GameOfLifeCell cell1 = new GameOfLifeCell();
        GameOfLifeCell cell2 = new GameOfLifeCell();
        assertFalse(cell1.equals(null));
        assertTrue(cell1.equals(cell1));
        assertTrue(cell1.equals(cell1));
        String testObj = "";
        assertFalse(cell1.equals(testObj));
        assertTrue(cell1.equals(cell2));
    }

    @Test
    public void hashCodeTest() {
        GameOfLifeCell cell1 = new GameOfLifeCell();
        GameOfLifeCell cell2 = new GameOfLifeCell();
        GameOfLifeCell cell3 = new GameOfLifeCell();
        cell3.addNeighbor(cell1);
        assertEquals(cell1.hashCode(), 630);
        assertEquals(cell2.hashCode(), 630);
        assertEquals(cell3.hashCode(), 630);
    }

    //czy taki test jest poprawny?
    @Test
    public void cohesionEqualsHashCodeTest() {
        GameOfLifeCell cell1 = new GameOfLifeCell();
        GameOfLifeCell cell2 = cell1;
        assertTrue(cell1.equals(cell2) && cell1.hashCode() == cell2.hashCode());
    }

    @Test
    public void compareToTest() {
        GameOfLifeCell cellF = new GameOfLifeCell();
        GameOfLifeCell cellT = new GameOfLifeCell();
        assertEquals(cellF.compareTo(cellT), 0);
        cellT.updateState(true);
        assertEquals(cellF.compareTo(cellT), -1);
        assertEquals(cellT.compareTo(cellF),1);
        assertThrows(NullPointerException.class, () -> cellT.compareTo(null));
    }

    @Test
    public void cloneTest() throws CloneException {
        GameOfLifeCell cell = gol.getBoard()[0][0];
        GameOfLifeCell cellClone = (GameOfLifeCell) cell.clone();
        assertEquals(cellClone.getCellValue(), cell.getCellValue());
        //assertEquals(cellClone.getNeighbors(), cell.getNeighbors());
        cell.updateState(!cell.getCellValue());
        //cell.getNeighbors().get(0).updateState(!cell.getNeighbors().get(0).getCellValue());
        assertNotEquals(cellClone.getCellValue(), cell.getCellValue());
        //assertNotEquals(cellClone.getNeighbors(), cell.getNeighbors());
    }
}
