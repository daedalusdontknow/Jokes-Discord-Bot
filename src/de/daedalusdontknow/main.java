package de.daedalusdontknow;

import de.daedalusdontknow.faySystem.*;
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
                    case 1:
                        builder.getPresence().setActivity(Activity.watching("Lars beim arbeiten zu"));
                        break;
                    case 2:
                        builder.getPresence().setActivity(Activity.listening("Joris und seiner Freundin im nebenzimmer"));
                        break;
                    case 3:
                        builder.getPresence().setActivity(Activity.streaming("Emils lächerliche Lungenkapazität", "https://www.twitch.tv/daedalus9879"));
                        break;
                    case 4:
                        builder.getPresence().setActivity(Activity.watching("Philipp G. beim allein sein zu"));
                        break;
                    case 5:
                        builder.getPresence().setActivity(Activity.playing("mit Philipp B."));
                        break;
                }
                mode = mode + 1;
                if (mode == 6) mode = 1;
            }
        }, 0, 15000);
    }

    //OTU1OTExNjU5MTk1NjAwOTU3.GHwQru.c6tXE7Qp3ViipoU3GG1WQMRPFH6XyY3nh2ltmw
    public static void main(String[] args) throws ClassNotFoundException {
        builder = JDABuilder.createLight("OTU1OTExNjU5MTk1NjAwOTU3.GHwQru.c6tXE7Qp3ViipoU3GG1WQMRPFH6XyY3nh2ltmw")
                .setActivity(Activity.playing("Starting..."))
                .setStatus(OnlineStatus.ONLINE)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .enableIntents(GatewayIntent.GUILD_VOICE_STATES)
                .enableIntents(GatewayIntent.GUILD_PRESENCES)
                .addEventListeners(new commandHandler())
                .addEventListeners(new buttonHandler())
                .addEventListeners(new selectMenuHandler())
                .build();

        commands.upsert();
        mysql.connect();

        dependencie.loadall();

        new main();
    }
}