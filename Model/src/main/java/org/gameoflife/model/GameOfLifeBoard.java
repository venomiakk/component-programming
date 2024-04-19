package org.gameoflife.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.gameoflife.model.exceptions.CloneException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class GameOfLifeBoard implements Serializable, Cloneable {
    private final GameOfLifeCell[][] board;
    private final GameOfLifeSimulator gols;
    private final Observable<GameOfLifeCellSet> observable = new Observable<>();
    private static final Logger logger = LoggerFactory.getLogger(GameOfLifeBoard.class);

    //constructor
    public GameOfLifeBoard(int paramWidth, int paramHeight, GameOfLifeSimulator paramGols, int compaction) {
        this.board = new GameOfLifeCell[paramWidth][paramHeight];
        this.gols = paramGols;
        for (int i = 0; i < paramWidth; i++) {
            for (int j = 0; j < paramHeight; j++) {
                this.board[i][j] = new GameOfLifeCell();
                int isAlive = ThreadLocalRandom.current().nextInt(0, 100);
                this.board[i][j].updateState(isAlive <= compaction);
                //tablica tak naprawde jest odwrocina (patrzac na terminalu) tj:
                //paramWidth - okresla wysokosc / liczbe rzedow
                //paramHeight - okresla szerokosc / liczbe kolumn
            }
        }
        logger.info("Board created");
        setNeighbors(this.board);
        //logger.info("Neighbors set");
        addObservers();
    }

    public GameOfLifeBoard(int paramWidth, int paramHeight, GameOfLifeSimulator paramGols) {
        this.board = new GameOfLifeCell[paramWidth][paramHeight];
        this.gols = paramGols;
        for (int i = 0; i < paramWidth; i++) {
            for (int j = 0; j < paramHeight; j++) {
                this.board[i][j] = new GameOfLifeCell();
                int isAlive = ThreadLocalRandom.current().nextInt(0, 100);
                this.board[i][j].updateState(isAlive <= 50);
                //tablica tak naprawde jest odwrocina (patrzac na terminalu) tj:
                //paramWidth - okresla wysokosc / liczbe rzedow
                //paramHeight - okresla szerokosc / liczbe kolumn
            }
        }
        logger.info("Board created");
        setNeighbors(this.board);
        //logger.info("Neighbors set");
        addObservers();
    }

    public void doSimulationStep() {
        gols.doStep(this);
        logger.info("Simulation step done");
    }

    public boolean get(int x, int y) {
        return this.board[x][y].getCellValue();
    }

    public void set(int x, int y, boolean value) {
        this.board[x][y].updateState(value);
        observable.notifyObservers();
    }

    public GameOfLifeRow getRow(int y) {
        return new GameOfLifeRow(this.board[y]);
    }

    public GameOfLifeColumn getColumn(int x) {
        return new GameOfLifeColumn(x, this.board);
    }

    /* metody ktorych nie ma na diagramie */
    public GameOfLifeCell[][] getBoard() {
        GameOfLifeCell[][] interim = new GameOfLifeCell[this.board.length][this.board[0].length];
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                interim[i][j] = new GameOfLifeCell();
                interim[i][j].updateState(this.board[i][j].getCellValue());
            }
        }
        //z jakiegos powodu ta linijka dodaje sasiadow do boarda a nie do interim
        setNeighbors(interim);
        return interim;
    }

    public void setBoard(GameOfLifeCell[][] newBoard) {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                //this.board[i][j].updateState(newBoard[i][j].getCellValue());
                set(i, j, newBoard[i][j].getCellValue());
                //this.board[i][j] = newBoard[i][j];
            }
        }
        logger.info("Board was set");
    }

    public void setNeighbors(GameOfLifeCell[][] golcBoard) {
        int width = golcBoard.length;
        int height = golcBoard[0].length;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //czyszczenie listy
                golcBoard[i][j].getNeighbors().clear();
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if (!(k == 0 && l == 0)) {
                            int neighborX = (i + k + width) % width;
                            int neighborY = (j + l + height) % height;
                            golcBoard[i][j].addNeighbor(golcBoard[neighborX][neighborY]);
                        }
                    }
                } //tu mozna by zrobic niemodyfikowalna liste
            }
        }
        //logger.info("Neighbors were set");
    }

    public void addObservers() {
        for (int i = 0; i < this.board.length; i++) {
            observable.addObserver(getRow(i));
        }
        for (int i = 0; i < this.board[0].length; i++) {
            observable.addObserver(getColumn(i));
        }
    }

    //metody apache.commons.lang3
    //czy obserwatorzy tez powinni byc uwzglednieni?
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("board", board)
                .append("simulator", gols)
                .append("observableBy", observable)
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
        GameOfLifeBoard rhs = (GameOfLifeBoard) obj;
        return new EqualsBuilder()
                .append(board, rhs.board)
                .append(gols.getClass(), rhs.gols.getClass())
                .append(observable, rhs.observable)
                .isEquals();
    }

    @Override
    public int hashCode() {
        // you pick a hard-coded, randomly chosen, non-zero, odd number
        // ideally different for each class
        return new HashCodeBuilder(5, 23)
                .append(board)
                .append(gols.getClass())
                .append(observable)
                .toHashCode();
    }

    @Override
    public Object clone() throws CloneException {
        GameOfLifeBoard board = new GameOfLifeBoard(this.board.length, this.board[0].length, this.gols);
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                board.set(i,j,this.get(i, j));
            }
        }
        return board;
        //deep copy
    }
}
