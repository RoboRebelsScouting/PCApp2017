package com.walpolerobotics.scouting.view;

/**
 * Created by 1153 on 2/4/2016.
 */
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.walpolerobotics.scouting.mainapp;
import com.walpolerobotics.scouting.model.Robot;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;

public class RootLayoutController {
    private mainapp mainApp;

    public void setMainApp(mainapp mainApp){
        this.mainApp = mainApp;
    }
    @FXML
    private void handleImport(){


        mainApp.importAllRobotMatchData();


    }

    @FXML
    private void exportMysql(){
       mainApp.exportMysql();
    }






}
