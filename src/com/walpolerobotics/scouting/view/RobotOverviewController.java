package com.walpolerobotics.scouting.view;

/**
 * Created by 1153 on 1/30/2016.
 */
import com.walpolerobotics.scouting.model.RobotMatch;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.walpolerobotics.scouting.mainapp;
import com.walpolerobotics.scouting.model.Robot;
//making the table
public class RobotOverviewController {
    @FXML
    private TableView<String> robotTable;
    @FXML
    private TableColumn<String, String> importedFilesColumn;


//(button on the right side of the GUI)
    @FXML
    private Button importdata;
    @FXML
    private Button exportdata;

//file import drop down option
    @FXML
    private void handleImport(){


        mainApp.importAllRobotMatchData();


    }

    @FXML
    private void exportMysql(){
        mainApp.exportMysql();
    }





    private mainapp mainApp;

    public RobotOverviewController(){

    }





    @FXML
    private void initialize(){
        importedFilesColumn.setCellValueFactory(cellData->new ReadOnlyStringWrapper(cellData.getValue()));






    }
    public void setMainApp(mainapp mainApp){
        this.mainApp = mainApp;
        robotTable.setItems(mainApp.getImportedFilesList());

    }
}

