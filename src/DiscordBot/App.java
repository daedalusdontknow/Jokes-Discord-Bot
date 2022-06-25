package DiscordBot;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class App extends ListenerAdapter {

    static String Token = "TOKEN";
    public static JDA builder;

    Timer t = new Timer();
    int mode = 1;

    public static void main(String[] args) throws LoginException {

        System.out.println("starting bot");

        builder = JDABuilder.createLight(Token)
            .setActivity(Activity.playing("Joke Bot Version " + daedalus.Version))
            .addEventListeners(new Commands())
            .addEventListeners(new ButtonListener())
            .build();

        MySQL.connect();
        new App();
    }

    public App()
    {
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                
                try {
                    MySQLcommand.removeUserPlayingTime();
                    MySQLcommand.removeUserWorkingTime();
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }, 0, 60000);

        t.schedule(new TimerTask() {
            @Override
            public void run() {
                
                switch(mode){
                    case 1: builder.getPresence().setActivity(Activity.watching(MySQLcommand.getUserCount() + " Registrated accounts on Nornir")); mode=0; break;
                    case 0: builder.getPresence().setActivity(Activity.watching(builder.getGuilds().size() + " servers Joke is connecting together")); mode=1; break;
                }

            }
        }, 0, 300000);
    }
}