package org.gameoflife.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main {

    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    //public static void printArrayP(GameOfLifeBoard array) {
    //    for (int i = 0; i < array.getBoard().length; i++) {
    //        for (int j = 0; j < array.getBoard()[i].length; j++) {
    //            if (array.get(i,j)) {
    //                System.out.print(" *");
    //            } else {
    //                System.out.print(" -");
    //            }
    //        }
    //        System.out.println();
    //    }
    //    System.out.println();
    //}
    //
    public static void main(String[] args) throws Exception {
        //    //GameOfLifeBoardDaoFactory boardDaoFactory = new GameOfLifeBoardDaoFactory();
        //    //Dao<GameOfLifeBoard> golbd = boardDaoFactory.getFileDao("zapis.bin");
        //    //
        //    //
        //    PlainGameOfLifeSimulator pgols = new PlainGameOfLifeSimulator();
        //    //System.out.println(pgols.getClass());
        //    GameOfLifeBoard golb = new GameOfLifeBoard(3, 3, pgols);
        //    GameOfLifeBoard golb2 = new GameOfLifeBoard(2, 2, pgols);
        //    //GameOfLifeCell cell = new GameOfLifeCell();
        //    //
        //    //printArrayP(golb);
        //    //golbd.write(golb);
        //    //
        //    ////wczytujac z pliku binarnego tworzy sie objekt,
        //    ////tj nawet jak zadeklarujemy mniejszy board to zostanie on nadpisany
        //    //GameOfLifeBoard gb = new GameOfLifeBoard(2, 1, pgols);
        //    ////gb = (GameOfLifeBoard)golbd.read();
        //    //
        //    //gb = (GameOfLifeBoard) golbd.read();
        //    //printArrayP(gb);
        //    //GameOfLifeCell cell = new GameOfLifeCell();
        //    //GameOfLifeCell clone = (GameOfLifeCell) cell.clone();
        //    //System.out.println(cell.toString());
        //    //System.out.println(clone.toString());
        //    //cell.updateState(true);
        //    //System.out.println(cell.toString());
        //    //System.out.println(clone.toString());
        //
        //    //GameOfLifeBoard cloneB = (GameOfLifeBoard) golb.clone();
        //    //printArrayP(golb);
        //    //printArrayP(cloneB);
        //    //printArrayP(golb2);
        //    //cloneB.setBoard(golb2.getBoard());
        //    //printArrayP(golb);
        //    //printArrayP(cloneB);
        //
        //    printArrayP(golb2);
        //    GameOfLifeBoard boardC = (GameOfLifeBoard) golb.clone();
        //    GameOfLifeColumn columnC = (GameOfLifeColumn) golb2.getColumn(0).clone();
        //    GameOfLifeCell cellC = (GameOfLifeCell) golb2.getBoard()[0][0];
        //    //System.out.println(cellC.toString());
        //    //System.out.println(cellC.getNeighbors());
        //    golb2.set(0,0, !golb2.get(0, 0));
        //    System.out.println(golb2.getColumn(0).toString());
        //    //golb2.set(1,0, !golb.get(1, 0));
        //    //printArrayP(golb2);
        //    System.out.println(columnC.toString());
        //    //System.out.println(cellC.toString());
        //    //System.out.println(cellC.getNeighbors());
        //    //printArrayP(golb);
        //    //golb.set(0,0, !golb.get(0,0));
        //    //System.out.println(golb.getBoard()[1][1].getNeighbors().toString());
        //    //System.out.println(boardC.getBoard()[1][1].getNeighbors().toString());
        //    //printArrayP(golb);
        //
        //    //printArrayP(boardC);
        //    //System.out.println(golb.getBoard()[1][1].getNeighbors().toString());
        //    //System.out.println(boardC.getBoard()[1][1].getNeighbors().toString());
        //
        //    logger.trace("Logger: trace");
        //    logger.debug("Logger: debug");
        //    logger.info("Logger: info");
        //    logger.warn("Logger: warn");
        //    logger.error("Logger: error");

        //Locale locale = Locale.getDefault(Locale.Category.DISPLAY);
        //Locale locale = new Locale("en", "GB");
        //System.out.println(locale);
        //ResourceBundle bndle = ResourceBundle.getBundle("lang", locale);
        //System.out.println(bndle.getString("open.error"));

        //try(JdbcGOLBoardDao db = new JdbcGOLBoardDao("test", "test");){
        //    db.connectToDB("localhost", "goldb","sa", "123");
        //}catch (RuntimeException e){
        //    logger.error("DB exception");
        //    throw new RuntimeException(e);
        //}

        String serverName = "localhost";
        String DBname = "goldb";
        String username = "sa";
        String password = "123";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://" + serverName + ";" +
                "DatabaseName=" + DBname + ";" +
                "encrypt=true;" +
                "trustServerCertificate=true" +
                ";user=" + username + ";password=" + password;
        Connection con = DriverManager.getConnection(connectionUrl);
        con.setAutoCommit(false);
        String insertQuery = "insert into board (name) values('test3')";
        String generaterdID[] = {"ID"};
        //String insertQuery = "insert into cell (x,y,value,)"
        //Statement statement = con.createStatement();
        //statement.executeUpdate(insertQuery);
        PreparedStatement statement = con.prepareStatement(insertQuery, generaterdID);
        statement.executeUpdate();
        con.commit();
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int id = generatedKeys.getInt(1);
            System.out.println(id);
        }

        //PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

        //String query = "select * from biura";
        //PreparedStatement preparedStatement = con.prepareStatement(query);
        //ResultSet resultSet = preparedStatement.executeQuery();
        //while (resultSet.next()){
        //    String str = resultSet.getString(1) + resultSet.getString(2)
        //            + resultSet.getString(3) + resultSet.getString(4);
        //    System.out.println(str);
        //}

        //ResultSet resultSet = con.getMetaData().getCatalogs();
        //// Iterate each catalog in the ResultSet
        //while (resultSet.next()) {
        //    // Get the database name, which is at position 1
        //    String databaseName = resultSet.getString(1);
        //    if (Objects.equals(databaseName, "goldb")) System.out.println(databaseName);
        //
        //}
        //resultSet.close();
        //String test = "select  * from board";
        //PreparedStatement preparedStatement = con.prepareStatement(test);
        //ResultSet resultSet = preparedStatement.executeQuery();
        //while (resultSet.next()){
        //    String r = resultSet.getString(1) +", " + resultSet.getString(2);
        //    System.out.println(r);
        //}
    }

}
//TODO internacjonalizacja wyjątków
//TODO Collections.unmodifiableList
