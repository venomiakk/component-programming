package org.gameoflife.model;

import java.io.Serializable;

public class PlainGameOfLifeSimulator implements GameOfLifeSimulator, Serializable {

    @Override
    public void doStep(GameOfLifeBoard board) {
        int width = board.getBoard().length;
        int height = board.getBoard()[0].length;
        GameOfLifeCell[][] interim = new GameOfLifeCell[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                interim[i][j] = new GameOfLifeCell();
                if (board.getBoard()[i][j].nextState()) {
                    interim[i][j].updateState(true);
                }
            }
        }

        board.setBoard(interim);
    }
}
