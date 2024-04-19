package org.gameoflife.model;

import org.gameoflife.model.exceptions.CloneException;

public class GameOfLifeColumn extends GameOfLifeCellSet implements Cloneable {

    public GameOfLifeColumn(int x, GameOfLifeCell[][] array) {
        for (GameOfLifeCell[] gameOfLifeCells : array) {
            super.cellsSet.add(gameOfLifeCells[x]);
        }
    }

    private GameOfLifeColumn() {
    }

    @Override
    public Object clone() throws CloneException {
        GameOfLifeColumn column = new GameOfLifeColumn();
        for (int i = 0; i < this.cellsSet.size(); i++) {
            GameOfLifeCell cell = new GameOfLifeCell();
            cell.updateState(this.cellsSet.get(i).getCellValue());
            column.cellsSet.add(cell);
        }
        return column;
    }
}
