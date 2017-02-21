package com.walpolerobotics.scouting.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;

/**
 * Created by 1153 on 2/5/2017.
 */
public class PilotData {

        public StringProperty allianceColor;
        public StringProperty matchNumber;
        public StringProperty firstCompetition;

        private ArrayList<PilotMatchData> eventList;






        public PilotData() {
            this("", "", "");
        }

        public PilotData(String firstCompetition, String matchNumber, String allianceColor) {
            this.matchNumber = new SimpleStringProperty(matchNumber);
            this.allianceColor = new SimpleStringProperty(allianceColor);
            this.firstCompetition = new SimpleStringProperty(firstCompetition);
            this.eventList = new ArrayList<PilotMatchData>();

        }
        public ArrayList<PilotMatchData> getEventList(){
            return eventList;
        }

        public void addEvent(PilotMatchData eventToAdd){
            eventList.add(eventToAdd);
        }


        public String getMatchNumber(){
            return matchNumber.get();
        }
        public void setMatchNumber(String matchNumber){
            this.matchNumber.set(matchNumber);
        }
        public StringProperty matchNumberProperty(){return matchNumber;}
        public String getAllianceColor(){
            return allianceColor.get();
        }
        public void setAllianceColor(String allianceColor){
            this.allianceColor.set(allianceColor);
        }
        public StringProperty allianceColorProperty(){
            return allianceColor;
        }
        public String getFirstCompetition(){
            return firstCompetition.get();
        }
        public void setFirstCompetition(String firstCompetition){
            this.firstCompetition.set(firstCompetition);
        }
        public StringProperty firstCompetitionProperty(){
            return firstCompetition;
        }


    }

 /*list of pilot times

        if (lineList[1] = "p1go"){
        p1go = this.timeStamp;
        }

        if (lineList[1] = "p2go"){
        p2go=this.timeStamp
        }

        if (lineList[1] = "p3go"){
        p3go = this.timeStamp
        }

        if (lineList[1] = "p1stop"){
        p1stop = this.timeStamp;
        p1time = p1stop - p1go;
                add to list of times;
        }

        if (lineList[1] = "p2stop"){
        p2stop = this.timeStamp;
        p2time = p2stop - p2go;
                add to list of times;
        }

        if (lineList[1] = "p3stop"){
        p3stop = this.timeStamp;
        p3time = p3stop - p3go;
                add to list of times;
        }*/



