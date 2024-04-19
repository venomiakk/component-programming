module org.gameoflife.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires ModelProject;


    opens org.gameoflife.view to javafx.fxml;
    exports org.gameoflife.view;
}