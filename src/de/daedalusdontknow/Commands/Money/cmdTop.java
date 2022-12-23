package de.daedalusdontknow.Commands.Money;

import de.daedalusdontknow.faySystem.daedalus;
import de.daedalusdontknow.faySystem.function;
import de.daedalusdontknow.faySystem.mysqlstatements;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class cmdTop {
    public static void run(SlashCommandInteractionEvent event) {
        if (!function.checkStatements(event)) return;
        ResultSet rs = mysqlstatements.getTopMoney();

        try {
            if (rs.next()) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.yellow);

                int i;

                for (i = 1; i <= rs.getRow(); i++) {
                    eb.addField("User " + i, rs.getString("username") + " with " + rs.getInt("money") + " €", false);
                    rs.next();
                }

                eb.setTitle("Top " + (i - 1) + " richest users");

                daedalus.sendReplyEmbed(eb, event);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
