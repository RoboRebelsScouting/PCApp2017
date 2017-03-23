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
            ss = "INSERT INTO pitinfo(competition, team, scouterName, ballCapacity, tallFootPrint, frame, weight, shooting, gearCollect, ballCollection, climb, customRope, email)\nVALUES\n(" + "\"" + rpd.competition +"\"";
            ss += ", \"" + rpd.team + "\"";
            ss += ", \"" + rpd.scouterName + "\"";
            ss += ", \"" + rpd.ballCapacity + "\"";
            ss += ", \"" + rpd.footPrint + "\"";
            ss += ", \"" + rpd.frame + "\"";
            ss += ", \"" + rpd.weight + "\"";
            ss += ", \"" + rpd.shooting + "\"";
            ss += ", \"" + rpd.gearCollect + "\"";
            ss += ", \"" + rpd.ballCollection + "\"";
            ss += ", \"" + rpd.climbAbility + "\"";
            ss += ", \"" + rpd.rope + "\"";
            ss += ", \"" + rpd.email + "\")";


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
                                ss = "INSERT INTO matchdata (robotNumber, matchNumber, gameEvent, subEvent, boilerTime, timeStamp, scouterName, firstCompetition)\nVALUES\n(" + rm.robotNumber.intValue();
                                ss += ", \"" + rm.matchNumber.get() + "\"";
                                ss += ", \"" + rmd.gameEvent + "\"";
                                ss += ", \"" + rmd.subEvent + "\"";
                                ss += ", \"" + rmd.boilerTime + "\"";
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

    public void writeFirstdataToDB (FirstMatchData fmd) {
        Statement stmt = null;
        ResultSet rs = null;


        try {
            stmt = con.createStatement();
             String ss = new String();

                    stmt = con.createStatement();
                    ss = "INSERT INTO firstalliancedata(teamNumber, eventCode, qualRank, avgscore, avgHighFuel, avgTeleHigh, avgTeleLow, avgClimbPoints, avgRotorsEngaged)\nVALUES\n(" + "\"" + fmd.teamNumber + "\"";
                    ss += ", \"" + fmd.eventCode + "\"";
                    ss += ", \"" + fmd.qualRank + "\"";
                    ss += ", \"" + fmd.avgScore + "\"";
                    ss += ", \"" + fmd.avgHighFuel + "\"";
                    ss += ", \"" + fmd.avgTeleHigh + "\"";
                    ss += ", \"" + fmd.avgTeleLow + "\"";
                    ss += ", \"" + fmd.avgClimbPoints + "\"";
                    ss += ", \"" + fmd.avgRotorsEngaged + "\")";


                    System.out.println(ss);

                    stmt.execute(ss);



        }catch(SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
        }

    }
    public void constructAllianceData (AllianceData ad) {
        Statement stmt = null;
        ResultSet rs = null;



            try {
                stmt = con.createStatement();
                String ss = new String();


                ss = "insert into alliances set\n" +
                        "allianceNumber = "+ ad.allianceNumber +",\n" +
                        "Robot1 = "+ ad.robot1 +",\n" +
                        "Robot2 = "+ ad.robot2 +",\n" +
                        "Robot3 = "+ ad.robot3 +",\n" +
                        "avgGears = (select\n" +
                        "\t(((SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'gearPlacedAuto',1,null))) +\n" +
                        "\t(SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'gearPlacedTeleop',1,null))))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot1 +" THEN matchNumber END))) +\n" +
                        "    (((SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'gearPlacedAuto',1,null))) +\n" +
                        "\t(SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'gearPlacedTeleop',1,null))))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot2 +" THEN matchNumber END))) +\n" +
                        "    (((SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'gearPlacedAuto',1,null))) +\n" +
                        "\t(SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'gearPlacedTeleop',1,null))))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot3 +" THEN matchNumber END))) AS 'AvgGears'\n" +
                        "\tfrom matchdata),\n" +
                        "avgClimbed = (select\n" +
                        "\t\t\t(SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'climbed',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot1 +" THEN matchNumber END)) +\n" +
                        "    (SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'climbed',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot2 +" THEN matchNumber END)) +\n" +
                        "    (SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'climbed',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot3 +" THEN matchNumber END)) as 'AvgClimbs'\n" +
                        "    from matchdata),\n" +
                        "avgAutoGears = (select\n" +
                        "\t((SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'gearPlacedAuto',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot1 +" THEN matchNumber END))) +\n" +
                        "    ((SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'gearPlacedAuto',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot2 +" THEN matchNumber END))) +\n" +
                        "    ((SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'gearPlacedAuto',1,null)))/\n" +
                        "\t(SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot3 +" THEN matchNumber END))) AS 'AvgGears'\n" +
                        "\tfrom matchdata),\n" +
                        "avgAutoHigh = (select\n" +
                        "\t(((SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'highGoalAuto' AND subEvent = '1',1,null))) +\n" +
                        "\t(SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'highGoalAuto' AND subEvent = '5',1,null)))*5 +\n" +
                        "\t(SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'highGoalAuto' AND subEvent = '10',1,null)))*10)/\n" +
                        "    (SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot1 +" THEN matchNumber END))) +\n" +
                        "    (((SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'highGoalAuto' AND subEvent = '1',1,null))) +\n" +
                        "\t(SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'highGoalAuto' AND subEvent = '5',1,null)))*5 +\n" +
                        "\t(SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'highGoalAuto' AND subEvent = '10',1,null)))*10)/\n" +
                        "    (SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot2 +" THEN matchNumber END))) +\n" +
                        "    (((SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'highGoalAuto' AND subEvent = '1',1,null))) +\n" +
                        "\t(SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'highGoalAuto' AND subEvent = '5',1,null)))*5 +\n" +
                        "\t(SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'highGoalAuto' AND subEvent = '10',1,null)))*10)/\n" +
                        "    (SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot3 +" THEN matchNumber END))) as 'AvgAutoHigh'\n" +
                        "    from matchdata),\n" +
                        "avgTeleHigh = (select\n" +
                        "\t(((SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'highGoal' AND subEvent = '1',1,null))) +\n" +
                        "\t(SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'highGoal' AND subEvent = '5',1,null)))*5 +\n" +
                        "\t(SELECT COUNT(IF(robotNumber = "+ ad.robot1 +" and gameEvent = 'highGoal' AND subEvent = '10',1,null)))*10)/\n" +
                        "    (SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot1 +" THEN matchNumber END))) +\n" +
                        "    (((SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'highGoal' AND subEvent = '1',1,null))) +\n" +
                        "\t(SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'highGoal' AND subEvent = '5',1,null)))*5 +\n" +
                        "\t(SELECT COUNT(IF(robotNumber = "+ ad.robot2 +" and gameEvent = 'highGoal' AND subEvent = '10',1,null)))*10)/\n" +
                        "    (SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot2 +" THEN matchNumber END))) +\n" +
                        "    (((SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'highGoal' AND subEvent = '1',1,null))) +\n" +
                        "\t(SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'highGoal' AND subEvent = '5',1,null)))*5 +\n" +
                        "\t(SELECT COUNT(IF(robotNumber = "+ ad.robot3 +" and gameEvent = 'highGoal' AND subEvent = '10',1,null)))*10)/\n" +
                        "    (SELECT COUNT(DISTINCT CASE WHEN robotNumber = "+ ad.robot3 +" THEN matchNumber END))) as 'AvgTeleHigh'\n" +
                        "    from matchdata)\n";

                System.out.println(ss);
                stmt.execute(ss);


            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            }
        }
    }





