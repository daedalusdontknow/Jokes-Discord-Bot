package de.daedalusdontknow.faySystem;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class function {

    //if(!Functions.checkStatements(event)) return;
    public static boolean checkStatements(SlashCommandInteractionEvent event) {
        boolean statement = false;
        if (!mysqlstatements.checkUserExists(event.getMember().getUser().getAsMention())) {
            daedalus.sendReply(daedalus.emoji_warning + " You are not registered yet! Use ``/create-user`` " + daedalus.emoji_warning, event);
        } else statement = true;
        return statement;
    }

    public static long checkTime(String username) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String time = mysqlstatements.getWorkingTime(username);

        if (time == null) return 99999;

        return calculateDifference(sdf.format(timestamp).toString(), time, "minute");
    }

    public static long checkGameTime(String username) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String time = mysqlstatements.getPlayingTime(username);

        if (time == null) return 99999;

        return calculateDifference(sdf.format(timestamp).toString(), time, "minute");
    }

    private static Long calculateDifference(String date1, String date2, String value) {
        Timestamp date_1 = stringToTimestamp(date1);
        Timestamp date_2 = stringToTimestamp(date2);

        long milliseconds = date_1.getTime() - date_2.getTime();
        if (value.equals("second"))
            return milliseconds / 1000;
        if (value.equals("minute"))
            return milliseconds / 1000 / 60;
        if (value.equals("hours"))
            return milliseconds / 1000 / 3600;
        else
            return 999999999L;
    }

    private static Timestamp stringToTimestamp(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parsedDate = dateFormat.parse(date);
            return new Timestamp(parsedDate.getTime());
        } catch (Exception e) {
            return null;
        }
    }

}