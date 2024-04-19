package org.gameoflife.model;

import java.sql.*;
import java.util.Objects;

import org.gameoflife.model.exceptions.DaoException;

import org.gameoflife.model.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JdbcGOLBoardDao implements Dao<GameOfLifeBoard>, AutoCloseable{

    private static final Logger logger = LoggerFactory.getLogger(JdbcGOLBoardDao.class);

    private final GameOfLifeSimulator gols = new PlainGameOfLifeSimulator();
    private String fileName;

    private String serverName="localhost";
    private String DBname="goldb";
    private String username="sa";
    private String password="123";
    public JdbcGOLBoardDao(String fileName) {
        this.fileName = fileName;

    }
    //TODO: tworzymy bazę w try catch, jeżeli istnieje pomijamy?

    private Connection connectToDB(String server,String db, String username,String password) throws DatabaseException {
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = "jdbc:sqlserver://"+server+";" +
                    "DatabaseName="+db+";" +
                    "encrypt=true;" +
                    "trustServerCertificate=true" +
                    ";user="+username+";password="+password;
            con = DriverManager.getConnection(connectionUrl);
            logger.info("Connection to database established");
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(DaoException.OPEN_ERROR, e);
        }
        return con;
    }

    @Override
    public GameOfLifeBoard read() throws DaoException {
        int fileID;
        int sizex;
        int sizey;
        try(Connection con = connectToDB(serverName, DBname, username, password)) {
            fileID = getFileID(con);
            sizex = getMaxSize(con, "x", fileID);
            sizey = getMaxSize(con, "y", fileID);
            GameOfLifeBoard board = new GameOfLifeBoard(sizex, sizey, gols);
            //ResultSet resultSet = getCells(con, fileID); //TODO The result set is closed
            String getCell = "select * from cell where boardid=?";
            //try(PreparedStatement preparedStatement = con.prepareStatement(getCell)){
            //    preparedStatement.setInt(1, fileID);
            //    ResultSet resultSet = preparedStatement.executeQuery();
            //    while (resultSet.next()){
            //        board.set(resultSet.getInt("x"),
            //                resultSet.getInt("y"),
            //                resultSet.getBoolean("value"));
            //    }
            //} catch (Exception e) {
            //    throw new RuntimeException();
            //}
            PreparedStatement preparedStatement = con.prepareStatement(getCell);
            preparedStatement.setInt(1, fileID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                board.set(resultSet.getInt("x"),
                        resultSet.getInt("y"),
                        resultSet.getBoolean("value"));
            }
            logger.info("Read from DB");
            resultSet.close();
            preparedStatement.close();
            return board;
        } catch (SQLException e) {
            //TODO
            throw new DatabaseException(DatabaseException.CONNECTION_ERROR, e);
        }
    }

    @Override
    public void write(GameOfLifeBoard obj) throws DaoException {
        try (Connection con = connectToDB(serverName, DBname, username, password)) {
            int id = writeBoard(con, fileName);
            for (int i = 0; i < obj.getBoard().length; i++){
                for (int j = 0; j < obj.getBoard()[0].length; j++){
                    writeCell(con, i, j, obj.get(i, j), id);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(DatabaseException.INSERINTO_ERROR, e);
        }
        logger.info("Wrote to DB");
    }

    private int getMaxSize(Connection con, String param, int bid) throws DatabaseException {
        String getMax;
        if (Objects.equals(param, "x")) {
            getMax = "select MAX(x) from cell where boardid=?";
        } else
            getMax = "select MAX(y) from cell where boardid=?";

        try(PreparedStatement preparedStatement = con.prepareStatement(getMax)){
            preparedStatement.setInt(1, bid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int size = resultSet.getInt(1);
                resultSet.close();
                return size + 1;
            } else {
                resultSet.close();
                //TODO
                throw new RuntimeException("No cell with given bid");
            }
        } catch (SQLException e) {
            //TODO
            throw new DatabaseException(DatabaseException.SELECT_ERROR, e);
        }
    }

    private int getFileID(Connection con) throws DatabaseException {
        String selectFile = "select bid from board where name=?";
        try(PreparedStatement preparedStatement = con.prepareStatement(selectFile)){
            preparedStatement.setString(1, fileName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                int id = resultSet.getInt("bid");
                resultSet.close();
                return id;
            } else {
                resultSet.close();
                //TODO
                throw new DatabaseException(DatabaseException.INSERINTO_ERROR);
            }
        } catch (SQLException e) {
            //TODO
            throw new DatabaseException(DatabaseException.INSERINTO_ERROR, e);
        }
    }

    private ResultSet getCells(Connection con, int id) throws DatabaseException {
        String getCell = "select value from cell where boardid=?";
        try(PreparedStatement preparedStatement = con.prepareStatement(getCell)){
            preparedStatement.setInt(1, id);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            //TODO
            throw new DatabaseException(DatabaseException.SELECT_ERROR, e);
        }
    }

    private int writeBoard(Connection con, String name) throws SQLException, DatabaseException {
        con.setAutoCommit(false);
        String insertQuery = "insert into board (name) values(?)";
        String[] generaterdID = {"ID"};
        try(PreparedStatement preparedStatement = con.prepareStatement(insertQuery, generaterdID)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            con.commit();
            ResultSet genID = preparedStatement.getGeneratedKeys();
            if (genID.next()){
                int id = genID.getInt(1);
                genID.close();
                return id;
            } else
                throw new RuntimeException();
        } catch (SQLException e) {
            throw new DatabaseException(DatabaseException.INSERINTO_ERROR, e);
        }
    }

    private void writeCell(Connection con, int x, int y, boolean value, int bid) throws SQLException, DatabaseException {
        con.setAutoCommit(false);
        String insertQuery = "insert into cell (x, y, value, boardid) values(?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = con.prepareStatement(insertQuery)){
            preparedStatement.setInt(1, x);
            preparedStatement.setInt(2, y);
            preparedStatement.setBoolean(3, value);
            preparedStatement.setInt(4, bid);
            preparedStatement.executeUpdate();
            con.commit();
        } catch (SQLException e){
            throw new DatabaseException(DatabaseException.INSERINTO_ERROR, e);
        }
    }

    @Override
    public void close() throws Exception {
        logger.info("Close-");
    }
}
