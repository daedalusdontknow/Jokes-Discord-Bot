package DiscordBot;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLcommand {
    
    //----------------------------------------------------------------------------------------------------------------------
    //                                      General  system
    //----------------------------------------------------------------------------------------------------------------------

    public static void createNewUser(String username){
        try {
            MySQL.statement.execute("INSERT INTO `users` (username) VALUES ('" + username + "')");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void deleteUser(String username){
        try {
            MySQL.statement.execute("DELETE FROM `users` WHERE username = '" + username + "'");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static boolean checkUserExists(String username){
        boolean exists = false;
        try {
            ResultSet result = MySQL.statement.executeQuery("SELECT username FROM `users` WHERE `username` = '" + username + "'");
            if(result.next()) exists = true;
        } catch (Exception e) { e.printStackTrace(); }
        return exists;
    }

    public static boolean checkUserBeta(String username) {
        boolean beta = false;
        try {
            ResultSet resultSet = MySQL.statement.executeQuery("SELECT beta FROM `users` WHERE username = '" + username + "'");
            resultSet.next();
            beta = resultSet.getBoolean("beta");
        } catch (SQLException e) { e.printStackTrace(); }
        return beta;
    }

    public static ResultSet getUserInfo(String username){
        ResultSet result = null;
        try {
            result = MySQL.statement.executeQuery("SELECT username, money, worked, kingdom FROM `users` WHERE username = '" + username + "'");
        } catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    public static int getUserCount(){
        int count = 0;
        try {
            ResultSet result = MySQL.statement.executeQuery("SELECT COUNT(*) FROM `users`");
            result.next();
            count = result.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }

    //----------------------------------------------------------------------------------------------------------------------
    //                                      Money system
    //----------------------------------------------------------------------------------------------------------------------

    public static int getUserMoney(String username){
        int money = 0;
        try {
            ResultSet resultSet = MySQL.statement.executeQuery("SELECT money FROM `users` WHERE username = '" + username + "'");
            resultSet.next();
            money = resultSet.getInt("money");
        } catch (SQLException e) { e.printStackTrace(); }
        return money;
    }

    public static void addUserMoney(String username, int money){
        try {
            MySQL.statement.execute("UPDATE `users` SET money = money + " + money + " WHERE username = '" + username + "'");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void removeUserMoney(String username, int money){
        try {
            MySQL.statement.execute("UPDATE `users` SET money = money - " + money + " WHERE username = '" + username + "'");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void transferUserMoney(String username, String receiver, int money){
        try {
            MySQL.statement.execute("UPDATE `users` SET money = money - " + money + " WHERE username = '" + username + "'");
            MySQL.statement.execute("UPDATE `users` SET money = money + " + money + " WHERE username = '" + receiver + "'");
        } catch (Exception e) { e.printStackTrace(); }
    }

    //----------------------------------------------------------------------------------------------------------------------
    //                                      Time system
    //----------------------------------------------------------------------------------------------------------------------

    public static int getUserWorkingTime(String username){
        int time = 0;
        try {
            ResultSet resultSet = MySQL.statement.executeQuery("SELECT workingTime FROM `users` WHERE username = '" + username + "'");
            resultSet.next();
            time = resultSet.getInt("workingTime");
        } catch (SQLException e) { e.printStackTrace(); }
        return time;
    }

    public static int getUserPlayingTime(String username){
        int time = 0;
        try {
            ResultSet resultSet = MySQL.statement.executeQuery("SELECT gameTime FROM `users` WHERE username = '" + username + "'");
            resultSet.next();
            time = resultSet.getInt("gameTime");
        } catch (SQLException e) { e.printStackTrace(); }
        return time;
    }

    public static void setWorkingTime(String username, int time){
        try {
            MySQL.statement.execute("UPDATE `users` SET workingTime = " + time + " WHERE username = '" + username + "'");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void setPlayingTime(String username, int time){
        try {
            MySQL.statement.execute("UPDATE `users` SET gameTime = " + time + " WHERE username = '" + username + "'");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void removeUserWorkingTime() throws SQLException {
        MySQL.statement.execute("UPDATE `users` SET workingTime = workingTime - 1");
    }

    public static void removeUserPlayingTime() throws SQLException{
        MySQL.statement.execute("UPDATE `users` SET gameTime = gameTime - 1");
    }

    //----------------------------------------------------------------------------------------------------------------------
    //                                      Kingdom system
    //----------------------------------------------------------------------------------------------------------------------

    public static boolean checkKingdomIsFull(String kingdomname){
        int count = getUserCountKingdom(kingdomname);
        if(count >= daedalus.KingdomMaxUsers) return true;
        return false;
    }

    public static boolean checkKingdomIsEmpty(String kingdomname){
        int count = getUserCountKingdom(kingdomname);
        if(count <= 0) return true;
        return false;
    }

    public static boolean checkUserHasKingdom(String username){
        boolean hasKingdom = false;
        try {
            ResultSet resultSet = MySQL.statement.executeQuery("SELECT kingdom FROM `users` WHERE username = '" + username + "'");
            resultSet.next();
            if(resultSet.getString("kingdom") != null) hasKingdom = true;
        } catch (SQLException e) { e.printStackTrace(); }
        return hasKingdom;
    }

    public static String getKingdomLeader(String kingdomname){
        String leader = "";
        try {
            ResultSet resultSet = MySQL.statement.executeQuery("SELECT leader FROM `kingdoms` WHERE name = '" + kingdomname + "'");
            resultSet.next();
            leader = resultSet.getString("leader");
        } catch (SQLException e) { e.printStackTrace(); }
        return leader;
    }

    public static int getUserCountKingdom(String kingdomname){
        int count = 0;
        try {
            ResultSet resultSet = MySQL.statement.executeQuery("SELECT usercount FROM `kingdoms` WHERE name = '" + kingdomname + "'");
            resultSet.next();
            count = resultSet.getInt("users");
        } catch (SQLException e) { e.printStackTrace(); }
        return count;
    }

    public static ResultSet getKingdomUsernames(String kingdomname){
        ResultSet resultSet = null;
        try {
            resultSet = MySQL.statement.executeQuery("SELECT username FROM `users` WHERE kingdom = '" + kingdomname + "'");
        } catch (SQLException e) { e.printStackTrace(); }
        return resultSet;
    }

    public static void createNewKingdom(String kingdomname, String username){
        try {
            MySQL.statement.execute("INSERT INTO `kingdoms` (name, leader VALUES ('" + kingdomname + "', '" + username + "')");
            MySQL.statement.execute("UPDATE `users` SET `kingdom` = '" + kingdomname + "' WHERE `username` = '" + username + "'");
        } catch (Exception e) { e.printStackTrace(); }
    }

}