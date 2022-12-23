package de.daedalusdontknow.faySystem;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class mysql {

    public static java.sql.Connection connection;
    public static java.sql.Statement statement;

    public static void connect() throws ClassNotFoundException {
        //import the sqlite driver from the internet and add it to the project as a library
        Class.forName("org.sqlite.JDBC");
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:data.sqlite");
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS `users` (username VARCHAR(255), money INT DEFAULT 0, worked INT DEFAULT 0, kingdom VARCHAR(255), workingTime VARCHAR(255), gameTime VARCHAR(255))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}