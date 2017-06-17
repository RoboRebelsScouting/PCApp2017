package com.walpolerobotics.scouting;/**
 * Created by 1153 on 1/30/2016.
 */

import com.walpolerobotics.scouting.model.*;
import com.walpolerobotics.scouting.model.Robot;
import com.walpolerobotics.scouting.view.RobotOverviewController;
import com.walpolerobotics.scouting.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class mainapp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Robot> robotData = FXCollections.observableArrayList();
    private ObservableList<RobotMatch> robotMatchInfo = FXCollections.observableArrayList();
    private ObservableList<PilotData> pilotMatchInfo = FXCollections.observableArrayList();
    private ObservableList<RobotPitData> robotPitDataList = FXCollections.observableArrayList();
    private ObservableList<String> importedFilesList = FXCollections.observableArrayList();
    private ObservableList<TD> TableauDataList = FXCollections.observableArrayList();
    private ObservableList<FirstMatchData> FirstDataList = FXCollections.observableArrayList();
    private ObservableList<AllianceData> AllianceDataList = FXCollections.observableArrayList();


    public DataBase db;



    public ObservableList<Robot> getRobotData() {
        return robotData;
    }
    public ObservableList<String> getImportedFilesList() {
        return importedFilesList;
    }
    public ObservableList<RobotMatch> getRobotMatchInfo() {
        return robotMatchInfo;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Robo Rebels Scouting");

        this.db = new DataBase();

        initRootLayout();

        showRobotOverview();
    }

    public void importAllRobotMatchData() {
        File folder = new File("C:/Users/1153/Documents/ImportedMatchFiles");

        String pathName = folder.getAbsolutePath();
        String[] listOfFiles = folder.list();
        for (int c = 0; c < listOfFiles.length; c++) {
            String fullPathName = pathName + "/" + listOfFiles[c];
            File newFile = new File(fullPathName);
            if (fullPathName.endsWith(".csv")) {
                if (fullPathName.endsWith("-pit.csv")) {
                    importRobotPitData(newFile);
                } else {
                    if (fullPathName.endsWith("-pilot.csv")) {
                        importPilotMatchData(newFile);
                    } else {
                        if (fullPathName.endsWith("-TD.csv")) {
                            importTableauData(newFile);
                        } else {
                            if (fullPathName.endsWith("-first.csv")) {
                                importFirstData(newFile);
                            } else {
                                if (fullPathName.endsWith("-alliance.csv")) {
                                    importAlliances(newFile);
                                } else {
                                    importRobotMatchData(newFile);
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    public void importRobotMatchData(File file) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            Robot r = new Robot();
            RobotMatch rm = new RobotMatch();
            int lineCount = 0;
            int robotNumber = 0;
            int approachBoiler = 0;
            int leaveBoiler = 0;
            int approachGear = 0;
            int leaveGear = 0;
            String firstCompetition = "";
            String matchNumber = "";
            String scouterName = "";
            System.out.println("reading file: " + file.getName());




            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] lineList = line.split(",");
                if (lineCount == 0) {
                    robotNumber = Integer.parseInt(lineList[2]);
                    matchNumber = lineList[1];
                    scouterName = lineList[3];
                    firstCompetition = lineList[0];}
                else{
                    RobotMatchData rmd = new RobotMatchData();
                    rmd.robotNumber = robotNumber;
                    rmd.firstCompetition = firstCompetition;
                    rmd.matchNumber = matchNumber;
                    rmd.timeStamp = Integer.parseInt(lineList[0]);
                    rmd.gameEvent = lineList[1];
                    rmd.boilerTime = 0;
                    rm.setRobotNumber(robotNumber);
                    rm.setFirstCompetition(firstCompetition);
                    rm.setMatchNumber(matchNumber);
                    rm.setScouterName(scouterName);


                    if(lineList[1].equals("allianceColor")&& lineList[2].equals("Blue")){
                        rmd.subEvent = 0;
                    }else{
                        if(lineList[1].equals("allianceColor")&& lineList[2].equals("Red")) {
                            rmd.subEvent = 1;
                        }else{
                            rmd.subEvent = Integer.parseInt(lineList[2]);
                        }
                    }


                    if(lineList[1].equals("approachBoiler")){
                        approachBoiler = Integer.parseInt(lineList[0]);
                    }
                    if(lineList[1].equals("leaveBoiler")){
                        leaveBoiler = Integer.parseInt(lineList[0]);
                        rmd.boilerTime = leaveBoiler - approachBoiler;
                    }



                    rm.getEventList().add(rmd);
                }






                lineCount++;
                }

            r.robotMatch.add(rm);
            importedFilesList.add(file.getName());
            //robotData.add(new Robot(8976,7,3,72,7,3,5,"shooting",0));
            r.setRobotNumber(robotNumber);
            System.out.println("the robot number was set to" + r.getRobotNumber());


            robotData.add(r);
            robotMatchInfo.add(rm);
            br.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());
            alert.showAndWait();
        }

        try {
            String newPath = "C:\\Users\\1153\\Documents\\PreviouslyAddedMatches";
            String newName = newPath+File.separator+file.getName();

            Files.move(Paths.get(file.getAbsolutePath()), Paths.get(newName));
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public void importPilotMatchData(File file) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            PilotData pd = new PilotData();
            int lineCount = 0;
            String firstCompetition = "";
            String matchNumber = "";
            String allianceColor = "";
            Integer p1go = 0;
            Integer p2go = 0;
            Integer p3go = 0;
            Integer p1stop = 0;
            Integer p2stop = 0;
            Integer p3stop = 0;
            Integer ropeGo = 0;
            Integer rope = 0;





            System.out.println("reading file: " + file.getName());


            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] lineList = line.split(",");
                if (lineCount == 0) {
                    firstCompetition = lineList[0];
                    matchNumber = lineList[1];
                    allianceColor = lineList[2];}
                else{

                    PilotMatchData pmd = new PilotMatchData();
                    pmd.firstCompetition = firstCompetition;
                    pmd.matchNumber = matchNumber;
                    pmd.allianceColor = allianceColor;
                    pmd.timeStamp = Integer.parseInt(lineList[0]);
                    pmd.gameEvent = lineList[1];
                    pd.setFirstCompetition(firstCompetition);
                    pd.setMatchNumber(matchNumber);
                    pd.setAllianceColor(allianceColor);
                    pmd.gearTime = 0;
                    pmd.ropeTime = 0;

                                if (lineList[1].equals("pos1go")){
                                p1go = Integer.parseInt(lineList[0]);
                                }

                                if (lineList[1].equals("pos2go")){
                                p2go = Integer.parseInt(lineList[0]);
                                }

                                if (lineList[1].equals("pos3go")){
                                p3go = Integer.parseInt(lineList[0]);
                                }

                                if (lineList[1].equals("pos1stop")){
                                p1stop = Integer.parseInt(lineList[0]);
                                pmd.gearTime = p1stop - p1go;
                                }

                                if (lineList[1].equals("pos2stop")){
                                p2stop = Integer.parseInt(lineList[0]);
                                  pmd.gearTime = p2stop - p2go;
                                }

                                if (lineList[1].equals("pos3stop")){
                                p3stop = Integer.parseInt(lineList[0]);
                                pmd.gearTime = p3stop - p3go;
                                }

                                if (lineList[1].equals("ropeGo")){
                                    ropeGo = Integer.parseInt(lineList[0]);
                                }

                                if (lineList[1].equals("rope")){
                                    rope = Integer.parseInt(lineList[0]);
                                    pmd.ropeTime = rope - ropeGo;
                                }


                    if (lineList[2].equals("null")== false){
                        pmd.teamNumber = Integer.parseInt(lineList[2]);
                    }else{
                        pmd.teamNumber= 0;
                    }

                    pd.getEventList().add(pmd);

                    }
                lineCount++;



                }
            pilotMatchInfo.add(pd);
            importedFilesList.add(file.getName());



        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());
            alert.showAndWait();
        }


    }

     public void importTableauData(File file) {
        try {
            System.out.println("Reading tableau file " + file.getName());

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            TD td = new TD();

            int lineCount = 0;

            while ((line = br.readLine()) != null) {
                String[] lineList = line.split(",");
                if(lineList.length != 7){
                    System.out.println("didn't get all columns");
                    for(int c= 0;c<lineList.length;c++){
                        System.out.println("column: " + c + " ;value: "  + lineList[c]);
                    }
                }
                if (lineCount == 0) {

                    td.name = lineList[0];
                    td.team = lineList[1];
                    td.hairColor = lineList[2];
                    td.hairStyle = lineList[3];
                    td.eyeColor = lineList[4];
                    td.height = lineList[5];
                    td.personalityNotes = lineList[6];


                    lineCount++;

                    TableauDataList.add(td);
                    importedFilesList.add(file.getName());
                }
            }
            br.close();

        } catch (IOException e){
            e.printStackTrace();
        }
         try {
             String newPath = "C:\\Users\\1153\\Documents\\PreviouslyAddedTableauData";
             String newName = newPath+File.separator+file.getName();
             if(Files.exists(Paths.get(newName))){
                 newName += System.currentTimeMillis();
             }
             Files.move(Paths.get(file.getAbsolutePath()), Paths.get(newName));
         }catch (IOException e){
             e.printStackTrace();
         }

    }

    public void importFirstData(File file) {
        try {
            System.out.println("Reading first file " + file.getName());

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            FirstMatchData fmd = new FirstMatchData();

            int lineCount = 0;

            while ((line = br.readLine()) != null) {
                String[] lineList = line.split(",");
                if(lineList.length != 9){
                    System.out.println("didn't get all columns");
                    for(int c= 0;c<lineList.length;c++){
                        System.out.println("column: " + c + " ;value: "  + lineList[c]);
                    }
                }
                if (lineCount == 0) {


                    fmd.teamNumber = Integer.parseInt(lineList[0]);
                    fmd.eventCode = lineList[1];
                    fmd.qualRank = Integer.parseInt(lineList[2]);
                    fmd.avgScore = Float.parseFloat(lineList[3]);
                    fmd.avgHighFuel = Float.parseFloat(lineList[4]);
                    fmd.avgTeleHigh = Float.parseFloat(lineList[5]);
                    fmd.avgTeleLow = Float.parseFloat(lineList[6]);
                    fmd.avgClimbPoints = Float.parseFloat(lineList[7]);
                    fmd.avgRotorsEngaged = Float.parseFloat(lineList[8]);





                    lineCount++;

                    // add this robot pit data to the list
                    FirstDataList.add(fmd);
                    importedFilesList.add(file.getName());
                }
            }
            br.close();

        } catch (IOException e){
            e.printStackTrace();
        }
        try {
            String newPath = "C:\\Users\\1153\\Documents\\PreviouslyAddedPitInfo";
            String newName = newPath+File.separator+file.getName();
            if(Files.exists(Paths.get(newName))){
                newName += System.currentTimeMillis();
            }
            Files.move(Paths.get(file.getAbsolutePath()), Paths.get(newName));
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void importRobotPitData(File file) {
        try {
            System.out.println("Reading first file " + file.getName());

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            RobotPitData rpd = new RobotPitData();

            int lineCount = 0;

            while ((line = br.readLine()) != null) {
                String[] lineList = line.split(",");
                if(lineList.length != 11){
                    System.out.println("didn't get all columns");
                    for(int c= 0;c<lineList.length;c++){
                        System.out.println("column: " + c + " ;value: "  + lineList[c]);
                    }
                }
                if (lineCount == 0) {

                    rpd.competition = lineList[0];
                    rpd.team = lineList[1];
                    rpd.scouterName = lineList[2];
                    rpd.ballCapacity = lineList[3];
                    rpd.footPrint = lineList[4];
                    rpd.shooting = lineList[5];
                    rpd.gearCollect = lineList[6];
                    rpd.ballCollection = lineList[7];
                    rpd.rope = lineList[8];
                    rpd.frame = lineList[9];
                    rpd.climbAbility = lineList[10];
                    rpd.weight = Integer.parseInt(lineList[11]);
                    rpd.email = lineList[12];



                    lineCount++;

                    // add this robot pit data to the list
                    robotPitDataList.add(rpd);
                    importedFilesList.add(file.getName());
                }
            }
            br.close();

        } catch (IOException e){
            e.printStackTrace();
        }
        try {
            String newPath = "C:\\Users\\1153\\Documents\\PreviouslyAddedPitInfo";
            String newName = newPath+File.separator+file.getName();
            if(Files.exists(Paths.get(newName))){
                newName += System.currentTimeMillis();
            }
            Files.move(Paths.get(file.getAbsolutePath()), Paths.get(newName));
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public void importAlliances(File file) {
        try {
            System.out.println("Reading first file " + file.getName());

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;


            int lineCount = 0;

          ;

            while ((line = br.readLine()) != null) {
                AllianceData ad = new AllianceData();
                String[] lineList = line.split(",");

                    ad.allianceNumber = Integer.parseInt(lineList[0]);
                    ad.robot1 = Integer.parseInt(lineList[1]);
                    ad.robot2 = Integer.parseInt(lineList[2]);
                    ad.robot3 = Integer.parseInt(lineList[3]);


                    AllianceDataList.add(ad);
                    lineCount++;

                    // add this robot pit data to the list



            }
            importedFilesList.add(file.getName());
            br.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(mainapp.class.getResource("view/root layout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRobotOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(mainapp.class.getResource("view/Robot Overview.fxml"));
            AnchorPane robotOverview = loader.load();
            rootLayout.setCenter(robotOverview);

            RobotOverviewController controller = loader.getController();
            controller.setMainApp(this);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void exportMysql(){
        db = new DataBase();

        for (Robot r : this.getRobotData()){
            db.writeRobotToDB(r);
        }

        // also save pit scout data to pit table
        for (RobotPitData rpd : this.robotPitDataList) {
            db.writePitDataToDB(rpd);
        }

        for (PilotData pd : this.pilotMatchInfo) {
            db.writePilotDataToDB(pd);
        }
        for (AllianceData ad : this.AllianceDataList){
            db.constructAllianceData(ad);
        }
    }






    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
