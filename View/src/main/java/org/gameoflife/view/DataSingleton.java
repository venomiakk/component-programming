package org.gameoflife.view;

import org.gameoflife.model.GameOfLifeBoard;

import java.nio.file.Path;

public class DataSingleton {
    private static final DataSingleton instance = new DataSingleton();

    public GameOfLifeBoard getBoard() {
        return board;
    }

    public void setBoard(GameOfLifeBoard board) {
        this.board = board;
    }

    private GameOfLifeBoard board;
    private int size;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    private int width;
    private String compaction;

    private Path path;

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public LangController getLangController() {
        return langController;
    }

    public void setLangController(LangController langController) {
        this.langController = langController;
    }

    private LangController langController;

    public void setSize(int size) {
        this.size = size;
    }

    public void setCompaction(String compaction) {
        this.compaction = compaction;
    }

    public int getSize() {
        return size;
    }

    public String getCompaction() {
        return compaction;
    }

    public static DataSingleton getInstance()
    {
        return instance;
    }



}
