package org.gameoflife.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeBoardTest {

    public GameOfLifeBoardTest() {

    }

    PlainGameOfLifeSimulator pgols = new PlainGameOfLifeSimulator();

    @Test
    public void constructorTest() {
        try {
            GameOfLifeBoard gol = new GameOfLifeBoard(8, 8, pgols);
            GameOfLifeBoard gol2 = new GameOfLifeBoard(8, 8, pgols,50);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @Test
    public void sequenceConstructorTest() {
        GameOfLifeBoard gol1 = new GameOfLifeBoard(8, 8, pgols);
        GameOfLifeBoard gol2 = new GameOfLifeBoard(8, 8, pgols);
        assertFalse(Arrays.deepEquals(gol1.getBoard(), gol2.getBoard()));
    }

    @Test
    public void getBoardTest() {
        GameOfLifeBoard gol = new GameOfLifeBoard(8, 8, pgols);
        try {
            gol.getBoard();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @Test
    public void setBoardTest() {
        GameOfLifeBoard gol = new GameOfLifeBoard(8, 8, pgols);
        GameOfLifeCell[][] newBoard = new GameOfLifeCell[gol.getBoard().length][gol.getBoard()[0].length];
        //for (GameOfLifeCell[] row : newBoard) {
        //    Arrays.fill(row, true);
        //}
        for (int i = 0; i < gol.getBoard().length; i++) {
            for (int j = 0; j < gol.getBoard()[0].length; j++) {
                newBoard[i][j] = new GameOfLifeCell();
                newBoard[i][j].updateState(true);
            }
        }
        gol.setBoard(newBoard);
        for (int i = 0; i < gol.getBoard().length; i++) {
            for (int j = 0; j < gol.getBoard()[0].length; j++) {
                assertEquals(gol.getBoard()[i][j].getCellValue(), newBoard[i][j].getCellValue());
            }
        }
    }

    @Test
    public void doStepTest() {
        GameOfLifeBoard gol = new GameOfLifeBoard(8, 8, pgols);
        try {
            gol.doSimulationStep();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @Test
    public void doStepExtendedTest() {
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
        boolean[][] expectedBoard = {
                {false, true, false, false, false, true, false, false},
                {false, false, true, false, false, true, true, false},
                {false, false, false, false, true, false, false, false},
                {true, true, false, true, true, false, true, true},
                {false, false, true, false, true, false, false, false},
                {true, true, true, false, false, false, false, false},
                {false, true, false, false, false, false, false, false},
                {true, false, false, false, false, false, false, true}
        };

        for (int i = 0; i < gol.getBoard().length; i++) {
            for (int j = 0; j < gol.getBoard()[0].length; j++) {
                golcBoard[i][j] = new GameOfLifeCell();
                golcBoard[i][j].updateState(newBoard[i][j]);
            }
        }

        gol.setBoard(golcBoard);
        gol.doSimulationStep();

        for (int i = 0; i < gol.getBoard().length; i++) {
            for (int j = 0; j < gol.getBoard()[0].length; j++) {
                assertEquals(gol.getBoard()[i][j].getCellValue(), expectedBoard[i][j]);
            }
        }
    }

    @Test
    public void getTest() {
        GameOfLifeBoard gol = new GameOfLifeBoard(2, 2, pgols);
        GameOfLifeCell[][] golcBoard = new GameOfLifeCell[2][2];
        boolean[][] newBoard = {{false, false}, {false, false}};
        for (int i = 0; i < gol.getBoard().length; i++) {
            for (int j = 0; j < gol.getBoard()[0].length; j++) {
                golcBoard[i][j] = new GameOfLifeCell();
                golcBoard[i][j].updateState(newBoard[i][j]);
            }
        }

        gol.setBoard(golcBoard);
        assertFalse(gol.get(1, 1));
    }

    @Test
    public void setTest() {
        GameOfLifeBoard gol = new GameOfLifeBoard(2, 2, pgols);
        GameOfLifeCell[][] golcBoard = new GameOfLifeCell[2][2];
        boolean[][] newBoard = {{false, false}, {false, false}};

        for (int i = 0; i < gol.getBoard().length; i++) {
            for (int j = 0; j < gol.getBoard()[0].length; j++) {
                golcBoard[i][j] = new GameOfLifeCell();
                golcBoard[i][j].updateState(newBoard[i][j]);
            }
        }

        gol.setBoard(golcBoard);
        gol.set(0, 0, true);
        assertTrue(gol.get(0, 0));
    }

    @Test
    public void getNeighborsTest() {
        GameOfLifeBoard gol = new GameOfLifeBoard(2, 2, pgols);
        assertTrue(gol.getBoard()[0][0].getNeighbors().get(0).getCellValue() == gol.getBoard()[1][1].getCellValue());
    }

    @Test
    public void HashEqualsStringTest() {
        GameOfLifeBoard board = new GameOfLifeBoard(4, 4, pgols);
        assertEquals(board.hashCode(), board.hashCode());
        GameOfLifeBoard board1 = new GameOfLifeBoard(4, 4, pgols);
        board1 = board;
        assertTrue(board1.equals(board));
        GameOfLifeBoard board2 = new GameOfLifeBoard(4, 4, pgols);
        assertFalse(board1.equals(board2));
        assertFalse(board1.equals(null));
        String tescik = "123";
        board2.setBoard(board1.getBoard());
        assertTrue(board1.equals(board1));
        assertFalse(board1.equals(tescik));
        GameOfLifeBoard gol = new GameOfLifeBoard(8, 8, pgols);

        try {
            board.toString();

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        assertTrue(board.toString().contains("org.gameoflife.model.GameOfLifeCell@"));
    }

    @Test
    public void cohesionEqualsHashCodeTest() {
        GameOfLifeBoard b1 = new GameOfLifeBoard(4,4,pgols);
        GameOfLifeBoard b2 = new GameOfLifeBoard(4,4,pgols);
        b2.setBoard(b1.getBoard());
        assertTrue(b1.equals(b2) && b1.hashCode()==b2.hashCode());
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        GameOfLifeBoard board = new GameOfLifeBoard(3,3,pgols);
        GameOfLifeBoard boardClone = (GameOfLifeBoard) board.clone();
        assertEquals(board, boardClone);
        assertEquals(board.getBoard()[0][1].getNeighbors(), boardClone.getBoard()[0][1].getNeighbors());
        board.set(0,0, !board.get(0,0));
        assertNotEquals(board, boardClone);
        assertNotEquals(board.getBoard()[0][1].getNeighbors(), boardClone.getBoard()[0][1].getNeighbors());
    }
}
