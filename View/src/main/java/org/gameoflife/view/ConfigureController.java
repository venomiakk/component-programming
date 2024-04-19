package org.gameoflife.view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.gameoflife.model.Dao;
import org.gameoflife.model.FileGameOfLifeBoardDao;
import org.gameoflife.model.GameOfLifeBoard;
import org.gameoflife.model.JdbcGOLBoardDao;
import org.gameoflife.model.exceptions.ApplicationException;
import org.gameoflife.model.exceptions.DaoException;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Locale;

public class ConfigureController implements Initializable {


    @FXML
    private Button dataBaseLoad;

    @FXML
    private Text heightTxt;
    @FXML
    private TextArea sizeField;

    @FXML
    private Text widthTxt;
    @FXML
    private TextArea widthField;

    @FXML
    private Text compacTxt;

    @FXML
    private ChoiceBox<String> compaction;

    @FXML
    private Text chLan;

    @FXML
    private Button polish;

    @FXML
    private Button english;

    @FXML
    private Button zatwierdz;

    @FXML
    private Text authors;

    @FXML
    private Button load;

    TextInputDialog td;


    private String[] options = {"LOW","MID","HIGH"};

    private Scene simulator;
    private Parent root;
    private Stage stage;

    DataSingleton data = DataSingleton.getInstance();

    LangController langController;
    Path path;

    public void changeScene(ActionEvent event) throws IOException {
        System.out.println(td.getEditor().getText());
        if(data.getBoard()==null)
        {
            data.setSize((Integer.parseInt(widthField.getText())));
            data.setWidth((Integer.parseInt(sizeField.getText())));
        }
        data.setCompaction(compaction.getValue());
        data.setLangController(langController);
        Parent root = FXMLLoader.load(getClass().getResource("SimulatorScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        simulator = new Scene(root,(double)(data.getSize()*50),(double)((data.getSize()*50)+100));
        stage.setResizable(true);
        stage.setScene(simulator);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO ten dialog nawet na cancel wywoluje metode ;d
        td = new TextInputDialog();
        td.setHeaderText("wpisz nazwe bazy");
        compaction.getItems().addAll(options);
        langController = new LangController();
        printAuthors();
    }

    public ChoiceBox getChoiceBox()
    {
        return compaction;
    }

    public TextArea getSizeField()
    {
        return sizeField;
    }

    public void changeLangToPL() {
        langController.setPL();
        changeLanguage();
    }

    public void changeLangToEN() {
        langController.setEN();
        changeLanguage();
    }

    public void changeLanguage() {
        heightTxt.textProperty().setValue(langController.messages.getString(langController.MENU_SETHEIGHT));
        sizeField.promptTextProperty().setValue(langController.messages.getString(langController.MENU_SETHEIGHT));
        widthTxt.textProperty().setValue(langController.messages.getString(langController.MENU_SETWIDTH));
        widthField.promptTextProperty().setValue(langController.messages.getString(langController.MENU_SETWIDTH));
        compacTxt.textProperty().setValue(langController.messages.getString(langController.MENU_SETCOMP));
        chLan.textProperty().setValue(langController.messages.getString(langController.MENU_CHOOSELANG));
        polish.textProperty().setValue(langController.messages.getString(langController.MENU_PL));
        english.textProperty().setValue(langController.messages.getString(langController.MENU_EN));
        zatwierdz.textProperty().setValue(langController.messages.getString(langController.MENU_CONFIRM));
        load.textProperty().setValue(langController.messages.getString(langController.MENU_LOAD));
    }

    public void load() throws ApplicationException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            path = file.toPath();
            Dao dao = new FileGameOfLifeBoardDao(path.toString());
            data.setBoard((GameOfLifeBoard) dao.read());
        }
    }

    public void printAuthors() {
        Locale locale = new Locale("pl", "PL");
        ResourceBundle rb = ResourceBundle.getBundle("org.gameoflife.view.authors");
        System.out.println(rb.getString("1") + ", " + rb.getString("2"));
        authors.textProperty().setValue(rb.getString("1") + ", " + rb.getString("2"));
    }

    public void dataBasaLoading() throws DaoException {
        Optional<String> result = td.showAndWait();
        if(result.isPresent())
        {
            JdbcGOLBoardDao dao = new JdbcGOLBoardDao(td.getEditor().getText());
            data.setBoard(dao.read());
        }


    }
}
