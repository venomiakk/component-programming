package org.gameoflife.model;

import org.gameoflife.model.exceptions.CloneException;

import java.util.Arrays;

public class GameOfLifeRow extends GameOfLifeCellSet implements Cloneable {

    public GameOfLifeRow(GameOfLifeCell[] row) {
        //super.cellsSet = Arrays.asList(gameOfLifeCells[x]);
        //super.cellsSet.addAll(Arrays.asList(row[row.length]));
        //czy da sie zrobic ty w podobny sposob niemodyfikowalna list?
        super.cellsSet.addAll(Arrays.asList(row));
    }

    private GameOfLifeRow() {
    }

    @Override
    public Object clone() throws CloneException {
        GameOfLifeRow row = new GameOfLifeRow();
        for (int i = 0; i < this.cellsSet.size(); i++) {
            GameOfLifeCell cell = new GameOfLifeCell();
            cell.updateState(this.cellsSet.get(i).getCellValue());
            row.cellsSet.add(cell);
        }
        return row;
    }
}
