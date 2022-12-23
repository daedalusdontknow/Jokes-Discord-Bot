package de.daedalusdontknow.Commands.General;

import de.daedalusdontknow.faySystem.daedalus;
import de.daedalusdontknow.faySystem.mysqlstatements;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class cmdCreateUser {
    public static void run(SlashCommandInteractionEvent event) {
        if (mysqlstatements.checkUserExists(event.getUser().getAsMention())) {
            daedalus.sendReply("You already have an account!", event);
            return;
        }

        mysqlstatements.createNewUser(event.getUser().getAsMention());
        daedalus.sendReply("Your account has been created!", event);
    }
}
