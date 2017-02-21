package com.walpolerobotics.scouting.model;

/**
 * Created by 1153 on 2/10/2016.
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;



public class DataBase {
    Connection con = null;


    String url = "jdbc:mysql://localhost:3306/roborebels";
    String user = "root";
    String password = "roborebels1153";

    public DataBase() {


        try {
            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DataBase.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);


        }
    }









    public void writePitDataToDB (RobotPitData rpd) {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            String ss = new String();
            //ss = "INSERT INTO pitInfo(Scout)\nVALUES\n(";
            ss = "INSERT INTO pitinfo(competition, team, scouterName, ballCapacity, tallFootPrint, shooting, gearCollect, ballCollection, customRope, frame)\nVALUES\n(" + "\"" + rpd.competition +"\"";
            ss += ", \"" + rpd.team + "\"";
            ss += ", \"" + rpd.scouterName + "\"";
            ss += ", \"" + rpd.ballCapacity + "\"";
            ss += ", \"" + rpd.footPrint + "\"";
            ss += ", \"" + rpd.shooting + "\"";
            ss += ", \"" + rpd.gearCollect + "\"";
            ss += ", \"" + rpd.ballCollection + "\"";
            ss += ", \"" + rpd.rope + "\"";
            ss += ", \"" + rpd.frame + "\")";

            stmt.execute(ss);


        }catch(SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());

            }



    }

    public void writePilotDataToDB (PilotData pd) {
        Statement stmt = null;
        ResultSet rs = null;


            try {
                stmt = con.createStatement();
                String ss = "SELECT * FROM pilotdata;";

                rs = stmt.executeQuery(ss);
                Boolean dataExists = false;


                while (rs.next()) {


                    String FirstCompetition = rs.getString("competition");
                    String MatchNumber = rs.getString("matchNumber");
                    String AllianceColor = rs.getString("allianceColor");


                    if ((FirstCompetition.equals(pd.firstCompetition.getValue())) && (MatchNumber.equals(pd.matchNumber.getValue())) && (AllianceColor.equals(pd.allianceColor.getValue()))) {
                        dataExists = true;
                    }
                }


                if (!dataExists) {
                    for (PilotMatchData pmd : pd.getEventList()) {

                        stmt = con.createStatement();
                        ss = "INSERT INTO pilotdata(competition, matchNumber, allianceColor, timeStamp, gameEvent, teamNumber, totalGearTime, ropeTime)\nVALUES\n(" + "\"" + pmd.firstCompetition + "\"";
                        ss += ", \"" + pmd.matchNumber + "\"";
                        ss += ", \"" + pmd.allianceColor + "\"";
                        ss += ", \"" + pmd.timeStamp + "\"";
                        ss += ", \"" + pmd.gameEvent + "\"";
                        ss += ", \"" + pmd.teamNumber + "\"";
                        ss += ", \"" + pmd.gearTime + "\"";
                        ss += ", \"" + pmd.ropeTime + "\")";


                        System.out.println(ss);

                        stmt.execute(ss);
                    }
                }

            }catch(SQLException ex){
                        System.out.println("SQLException: " + ex.getMessage());
                    }

    }




    public void writeRobotToDB(Robot r) {
        Statement stmt = null;
        ResultSet rs = null;

        for (RobotMatch rm : r.robotMatch) {
            try {
                stmt = con.createStatement();
                String ss = "SELECT * FROM matchdata;";

                rs = stmt.executeQuery(ss);
                Boolean dataExists = false;


                while (rs.next()) {
                    Integer RobotNumber = rs.getInt("robotNumber");
                    String MatchNumber = rs.getString("matchNumber");
                    String ScouterName = rs.getString("scouterName");
                    String FirstCompetition = rs.getString("firstCompetition");


                    if ((RobotNumber == rm.robotNumber.intValue()) && (MatchNumber.equals(rm.matchNumber.getValue())) && (ScouterName.equals(rm.scouterName.getValue())) && (FirstCompetition.equals(rm.firstCompetition.getValue()))) {
                        dataExists = true;
                    }
                }



                if (!dataExists) {
                    for(RobotMatchData rmd : rm.getEventList()){
                        try{

                                stmt = con.createStatement();
                                ss = "INSERT INTO matchdata (robotNumber, matchNumber, gameEvent, subEvent, highGoalsScore, timeStamp, scouterName, firstCompetition)\nVALUES\n(" + rm.robotNumber.intValue();
                                ss += ", \"" + rm.matchNumber.get() + "\"";
                                ss += ", \"" + rmd.gameEvent + "\"";
                                ss += ", \"" + rmd.subEvent + "\"";
                                ss += ", \"" + rmd.highGoalsScore + "\"";
                                ss += ", \"" + rmd.timeStamp + "\"";
                                ss += ", \"" + rm.scouterName.get() + "\"";
                                ss += ", \"" + rm.firstCompetition.get() + "\"";
                                ss += ");";
                                    stmt.execute(ss);
                                System.out.println(ss);

                        }catch (SQLException ex) {
                            System.out.println("SQLException: " + ex.getMessage());

                        }

                    }
                }


                //System.out.println(ss);

            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            }
        }


    }
}
