package DiscordBot;

public class MySQL {

    public static java.sql.Connection connection;
    public static java.sql.Statement statement;

    public static void connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            connection = java.sql.DriverManager.getConnection("jdbc:mysql://" + daedalus.MySQLhost + ":" + daedalus.MySQLport +"/" + daedalus.MySQLdb +"", daedalus.MySQLuser, daedalus.MySQLpass);
            statement = connection.createStatement();
            System.out.println("Info : Connected to MySQL");
        }
        catch (Exception e){e.printStackTrace();}
    }
}