package org.gameoflife.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.gameoflife.model.exceptions.CloneException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class GameOfLifeCell implements Serializable, Cloneable, Comparable<GameOfLifeCell> {
    private boolean value = false;
    private final List<GameOfLifeCell> neighbors = new ArrayList<>();


    public boolean getCellValue() {
        return this.value;
    }

    public boolean nextState() {
        int count = 0;
        for (GameOfLifeCell neighbor : neighbors) {
            if (neighbor.getCellValue()) {
                count++;
            }
        }
        if (this.value && (count == 2 || count == 3)) {
            return true;
        } else {
            return !value && count == 3;
        }
    }

    public void updateState(boolean newState) {
        this.value = newState;
    }

    /* metody ktorych nie ma na diagramie */
    public void addNeighbor(GameOfLifeCell nearCell) {
        this.neighbors.add(nearCell);
    }

    public List<GameOfLifeCell> getNeighbors() {
        return this.neighbors;
    }

    //metody apache.commons.lang3
    //czy lista sasiadow powinna rowniez byc uwzgledniona w tych metodach?
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                //.append("neighbors", neighbors.toArray())
                .toString();
        //w tym przypadku list neighbors jest wyswietlana dla kazdej komorki
        // w liscie komorki na ktorej wywolana jest metoda
        //org.gameoflife.model.GameOfLifeCell@66a29884[value=false,neighbors=
        // [org.gameoflife.model.GameOfLifeCell@4769b07b[value=false,neighbors=[]]]]
        // czy tak powinno byc?
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
        GameOfLifeCell rhs = (GameOfLifeCell) obj;
        return new EqualsBuilder()
                //ma to znaczenie jak tworzymy 2 obikty tej samej klasy
                //bez .appendSuper() beda sobie rowne a nie powinny (?)
                .append(value, rhs.value)
                //.append(neighbors, rhs.neighbors)
                .isEquals();
    }

    @Override
    public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(17, 37)
                .append(value)
                //.append(neighbors)
                .toHashCode();
    }

    @Override
    public Object clone() throws CloneException {
        GameOfLifeCell cell = new GameOfLifeCell();
        cell.updateState(this.value);
        for (int i = 0; i < this.neighbors.size(); i++) {
            for(GameOfLifeCell neighbour:getNeighbors())
            {
                cell.addNeighbor(neighbour);
            }
        }
        return cell;
    }

    @Override
    public int compareTo(GameOfLifeCell o) {
        try {
            if (this.getCellValue() == o.getCellValue()) {
                return 0;
            } else if (this.getCellValue() && !o.getCellValue()) {
                return 1;
            } else {
                return -1;
            }
        } catch (NullPointerException e) {
            throw e;
        //    TODO NullPointerException: czy to również trzeba opakować?
        }
    }
}
