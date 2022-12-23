package de.daedalusdontknow.Commands.General;

import de.daedalusdontknow.faySystem.daedalus;
import de.daedalusdontknow.faySystem.function;
import de.daedalusdontknow.faySystem.mysqlstatements;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cmdUserInfo {
    public static void run(SlashCommandInteractionEvent event) {
        if (!function.checkStatements(event)) return;

        String MentionedUser = null;

        if (event.getOption("user") == null) MentionedUser = event.getUser().getAsMention();
        else {
            if (!mysqlstatements.checkUserExists(event.getOption("user").getAsUser().getAsMention())) {
                daedalus.sendReply(":x: This user isn´t registrated :x:", event);
            } else MentionedUser = event.getOption("user").getAsUser().getAsMention();
        }

        ResultSet rs = mysqlstatements.getUserInfo(MentionedUser);
        try {
            if (rs.next()) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Userinfo");
                eb.setDescription("" + rs.getString("username") +
                        "\n **Money**: _" + rs.getString("money") + "€_" +
                        "\n **Worked**: _" + rs.getString("worked") + " times_");
                eb.setColor(Color.YELLOW);

                daedalus.sendReplyEmbed(eb, event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
