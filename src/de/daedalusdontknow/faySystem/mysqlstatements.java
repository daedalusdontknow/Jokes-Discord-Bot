package de.daedalusdontknow.faySystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class mysqlstatements {

    //----------------------------------------------------------------------------------------------------------------------
    //                                      General  system
    //----------------------------------------------------------------------------------------------------------------------

    public static void createNewUser(String username) {
        try {
            mysql.statement.execute("INSERT INTO `users` (username) VALUES ('" + username + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(String username) {
        try {
            mysql.statement.execute("DELETE FROM `users` WHERE username = '" + username + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkUserExists(String username) {
        boolean exists = false;
        try {
            ResultSet result = mysql.statement.executeQuery("SELECT username FROM `users` WHERE `username` = '" + username + "'");
            if (result.next()) exists = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    public static ResultSet getUserInfo(String username) {
        ResultSet result = null;
        try {
            result = mysql.statement.executeQuery("SELECT username, money, worked, kingdom FROM `users` WHERE username = '" + username + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //----------------------------------------------------------------------------------------------------------------------
    //                                      Money system
    //----------------------------------------------------------------------------------------------------------------------

    public static ResultSet getTopMoney() {
        ResultSet result = null;
        try {
            result = mysql.statement.executeQuery("SELECT username, money FROM `users` ORDER BY money DESC LIMIT 10");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getMoney(String username) {
        int money = 0;
        try {
            ResultSet resultSet = mysql.statement.executeQuery("SELECT money FROM `users` WHERE username = '" + username + "'");
            resultSet.next();
            money = resultSet.getInt("money");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return money;
    }

    public static void addMoney(String username, int money) {
        try {
            mysql.statement.execute("UPDATE `users` SET money = money + " + money + " WHERE username = '" + username + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeMoney(String username, int money) {
        try {
            mysql.statement.execute("UPDATE `users` SET money = money - " + money + " WHERE username = '" + username + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void transferUserMoney(String username, String receiver, int money) {
        try {
            mysql.statement.execute("UPDATE `users` SET money = money - " + money + " WHERE username = '" + username + "'");
            mysql.statement.execute("UPDATE `users` SET money = money + " + money + " WHERE username = '" + receiver + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------------------------------
    //                                      Time system
    //----------------------------------------------------------------------------------------------------------------------

    public static String getWorkingTime(String username) {
        String time = "NULL";
        try {
            ResultSet resultSet = mysql.statement.executeQuery("SELECT workingTime FROM `users` WHERE username = '" + username + "'");
            resultSet.next();
            time = resultSet.getString("workingTime");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getPlayingTime(String username) {
        String time = "NULL";
        try {
            ResultSet resultSet = mysql.statement.executeQuery("SELECT gameTime FROM `users` WHERE username = '" + username + "'");
            resultSet.next();
            time = resultSet.getString("gameTime");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static void setWorkingTime(String username) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            mysql.statement.execute("UPDATE `users` SET workingTime = '" + sdf.format(timestamp).toString() + "' WHERE username = '" + username + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPlayingTime(String username) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            mysql.statement.execute("UPDATE `users` SET gameTime = '" + sdf.format(timestamp).toString() + "' WHERE username = '" + username + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
