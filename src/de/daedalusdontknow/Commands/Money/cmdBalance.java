package de.daedalusdontknow.Commands.Money;

import de.daedalusdontknow.faySystem.daedalus;
import de.daedalusdontknow.faySystem.function;
import de.daedalusdontknow.faySystem.mysqlstatements;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class cmdBalance {
    public static void run(SlashCommandInteractionEvent event) {
        if (!function.checkStatements(event)) return;
        daedalus.sendReply("Your current balance is: _" + mysqlstatements.getMoney(event.getUser().getAsMention()) + "€_", event);
    }
}
