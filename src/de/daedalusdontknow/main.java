package de.daedalusdontknow;

import de.daedalusdontknow.faySystem.buttonHandler;
import de.daedalusdontknow.faySystem.commandHandler;
import de.daedalusdontknow.faySystem.commands;
import de.daedalusdontknow.faySystem.mysql;
import de.daedalusdontknow.faySystem.dependencie;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.Timer;
import java.util.TimerTask;

public class main {

    public static JDA builder;

    Timer t = new Timer();
    int mode = 1;

    public main() {
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                switch (mode) {

                }
                mode = mode + 1;
                if (mode == 6) mode = 1;
            }
        }, 0, 15000);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        builder = JDABuilder.createLight("TOKEN")
                .setActivity(Activity.playing("Starting..."))
                .setStatus(OnlineStatus.ONLINE)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .enableIntents(GatewayIntent.GUILD_VOICE_STATES)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .addEventListeners(new commandHandler())
                .addEventListeners(new buttonHandler())
                .build();

        commands.upsert();
        mysql.connect();

        dependencie.loadall();

        new main();
    }
}