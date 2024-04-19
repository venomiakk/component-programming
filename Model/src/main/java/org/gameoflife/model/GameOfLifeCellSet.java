package org.gameoflife.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public abstract class GameOfLifeCellSet implements Observer<GameOfLifeCellSet>, Serializable {
    protected final List<GameOfLifeCell> cellsSet = new ArrayList<>();

    public int countAliveCells() {
        int count = 0;
        for (GameOfLifeCell cell : cellsSet) {
            if (cell.getCellValue()) {
                count++;
            }
        }
        return count;
    }

    public int countDeadCells() {
        int count = 0;
        for (GameOfLifeCell cell : cellsSet) {
            if (!cell.getCellValue()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void update() {
        countAliveCells();
        countDeadCells();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("set", cellsSet.toArray())
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        GameOfLifeCellSet rhs = (GameOfLifeCellSet) obj;
        return new EqualsBuilder()
                .append(cellsSet, rhs.cellsSet)
                .isEquals();
    }

    @Override
    public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(13, 41)
                .append(cellsSet)
                .toHashCode();
    }
}
