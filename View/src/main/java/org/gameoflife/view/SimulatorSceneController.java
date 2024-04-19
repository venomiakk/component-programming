package org.gameoflife.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.gameoflife.model.*;
import org.gameoflife.model.exceptions.ApplicationException;
import org.gameoflife.model.exceptions.DaoException;

import java.net.URL;
import java.nio.file.Path;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SimulatorSceneController implements Initializable {

    private ToggleButton buttons[][];

    private BooleanProperty properties[][];

    private PlainGameOfLifeSimulator pgols = new PlainGameOfLifeSimulator();
    GameOfLifeBoard board;
    private int Size;

    public void setWidth(int width) {
        this.width = width;
    }

    private int width;

    private String compaction;

    public void setSize(int size) {
        Size = size;
    }

    public void setCompaction(String compaction) {
        this.compaction = compaction;
    }

    DataSingleton data = DataSingleton.getInstance();
    @FXML
    private ToggleButton rec;

    @FXML
    private GridPane grid;

    @FXML
    private Button startSim;

    @FXML
    private Button stopSim;

    @FXML
    private Button nextStep;

    @FXML
    private Button resetSim;

    @FXML
    private Button save;

    @FXML
    private Button saveToBase;

    enum Level {
        LOW(10),
        MID(30),
        HIGH(50);

        private final int value;

        private Level(int value) {
            this.value = value;
        }
    }

    public void changeLanguage(){
        startSim.textProperty().setValue(data.getLangController().messages.getString(data.getLangController().SIM_START));
        nextStep.textProperty().setValue(data.getLangController().messages.getString(data.getLangController().SIM_NEXTSTEP));
        resetSim.textProperty().setValue(data.getLangController().messages.getString(data.getLangController().SIM_RESET));
        stopSim.textProperty().setValue(data.getLangController().messages.getString(data.getLangController().SIM_STOP));
        save.textProperty().setValue(data.getLangController().messages.getString(data.getLangController().SIM_SAVE));
    }
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.4),
            e -> nextSimulationStep(new ActionEvent())
    ));
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        changeLanguage();
        grid.setHgap(0);
        grid.setVgap(0);
        grid.setAlignment(Pos.CENTER);
        setCompaction((data.getCompaction()));
        Level lvl = Level.valueOf(compaction);
        if(data.getBoard()!=null)
        {
                board = data.getBoard();
                Size = board.getBoard().length;
                data.setSize(board.getBoard().length);
                data.setWidth(board.getBoard()[0].length);
        }
        else
        {
            setSize(data.getSize());
            setWidth(data.getWidth());
            board = new GameOfLifeBoard(Size, width, pgols, lvl.value);

        }
        setSize(data.getSize());
        setWidth(data.getWidth());
        grid.setPrefHeight(Size * 50);
        grid.setPrefWidth(width * 50);
        properties = new BooleanProperty[Size][width];
        buttons = new ToggleButton[Size][width];
        drawBoard();
        nextStep.setLayoutY((Size * 50 + 100) - 50);
        nextStep.setLayoutX(50);
        startSim.setLayoutY((Size * 50 + 100) - 50);
        startSim.setLayoutX(150);
        stopSim.setLayoutY((Size * 50 + 100) - 50);
        stopSim.setLayoutX(230);
        timeline.setCycleCount(Animation.INDEFINITE);
        resetSim.setLayoutY((Size * 50 + 100) - 50);
        resetSim.setLayoutX(305);
        save.setLayoutY((Size * 50 + 100) - 50);
        save.setLayoutX(400);
        saveToBase.setLayoutY((Size * 50 + 100) - 50);
        saveToBase.setLayoutX(500);

    }

    private void drawBoard() {
        for (int i = 0; i < Size; i++) {
            for (int j = 0; j < width; j++) {
                rec = new ToggleButton();
                rec.prefHeight(50);
                rec.prefWidth(50);
                rec.setMinHeight(49);
                rec.setMaxHeight(51);
                rec.setMinWidth(49);
                rec.setMaxWidth(51);
                //rec.setText(Integer.toString(i * Size + j));
                if (board.get(i, j)) {
                    rec.setStyle("-fx-background-color: green");
                    rec.setTextFill(Color.GREEN);
                } else {
                    rec.setStyle("-fx-background-color: darkgray");
                    rec.setTextFill(Color.DARKGRAY);
                }
                grid.add(rec, i, j);
                properties[i][j]=new SimpleBooleanProperty((board.get(i,j)));
                rec.selectedProperty().bindBidirectional(properties[i][j]);
                buttons[i][j] = rec;
                buttons[i][j].setOnAction(buttonEventHandler());
            }
        }
        grid.setHgap(0);
        grid.setVgap(0);
        grid.setAlignment(Pos.CENTER);
    }

    public void updateBoard() {
        for (int i = 0; i < Size; i++) {
            for (int j = 0; j < width; j++) {
                buttons[i][j].selectedProperty().setValue(board.get(i,j));
                if(!buttons[i][j].selectedProperty().getValue())
                {
                    buttons[i][j].setStyle("-fx-background-color: darkgray");
                } else {
                    buttons[i][j].setStyle("-fx-background-color: green");

                }
            }
        }
    }

    public void nextSimulationStep(ActionEvent actionEvent) {
        this.board.doSimulationStep();
        updateBoard();
    }

    public void startSimulation(ActionEvent actionEvent) {
        if (timeline.getStatus() != Animation.Status.RUNNING) {
            nextSimulationStep(new ActionEvent());
            timeline.play();
        }
    }

    public void stopSimulation(ActionEvent actionEvent) {
        timeline.stop();
    }

    public void reset() {
        stopSimulation(new ActionEvent());
        grid.setHgap(0);
        grid.setVgap(0);
        setSize(data.getSize());
        setWidth(data.getWidth());
        setCompaction((data.getCompaction()));
        grid.setPrefHeight(Size * 50);
        grid.setPrefWidth(width * 50);
        Level lvl = Level.valueOf(compaction);
        board = new GameOfLifeBoard(Size, width, pgols, lvl.value);
        drawBoard();

        //nextStep.setPrefHeight(25);
        //nextStep.setPrefWidth(50);
        nextStep.setLayoutY((Size * 50 + 100) - 50);
        nextStep.setLayoutX(50);

        //startSim.setPrefHeight(25);
        //startSim.setPrefWidth(50);
        startSim.setLayoutY((Size * 50 + 100) - 50);
        startSim.setLayoutX(150);

        //stopSim.setPrefHeight(25);
        //stopSim.setPrefWidth(50);
        stopSim.setLayoutY((Size * 50 + 100) - 50);
        stopSim.setLayoutX(230);

        timeline.setCycleCount(Animation.INDEFINITE);
    }



    EventHandler<ActionEvent> buttonEventHandler() {
        return event -> {
            ToggleButton numberButton = (ToggleButton) event.getTarget();
            if (!numberButton.selectedProperty().getValue()) {
                numberButton.setStyle("-fx-background-color: darkgray");
            } else {
                numberButton.setStyle("-fx-background-color: green");
            }
            for(int i = 0;i<Size;i++)
            {
                for(int j=0;j<width;j++)
                {
                    if(numberButton == buttons[i][j])
                    {
                        board.set(i,j,properties[i][j].getValue());
                        //printArrayP(board);
                    }
                }
            }


        };
    }

    public void saving() throws ApplicationException {
        Dao dao = new FileGameOfLifeBoardDao("proba1");
        dao.write(board);
    }

    public void saveToDataBase() throws DaoException {
        TextInputDialog td;
        td = new TextInputDialog();
        td.setHeaderText("wpisz tu nazwe");
        Optional<String> result = td.showAndWait();
        if(result.isPresent()) {

            JdbcGOLBoardDao dao = new JdbcGOLBoardDao(td.getEditor().getText());
            dao.write(board);
        }
    }
    //public static void printArrayP(GameOfLifeBoard array) {
    //    for (int i = 0; i < array.getBoard().length; i++) {
    //        for (int j = 0; j < array.getBoard()[i].length; j++) {
    //            if (array.get(j,i)) {
    //                System.out.print(" *");
    //            } else {
    //                System.out.print(" -");
    //            }
    //        }
    //        System.out.println();
    //    }
    //    System.out.println();
    //}

}
