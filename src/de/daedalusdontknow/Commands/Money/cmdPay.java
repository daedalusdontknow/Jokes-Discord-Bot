package de.daedalusdontknow.Commands.Money;

import de.daedalusdontknow.faySystem.daedalus;
import de.daedalusdontknow.faySystem.function;
import de.daedalusdontknow.faySystem.mysqlstatements;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class cmdPay {
    public static void run(SlashCommandInteractionEvent event) {
        if (!function.checkStatements(event)) return;

        if (event.getOption("user") == null) {
            daedalus.sendReply(":x: You need to mention a user! :x:", event);
            return;
        }
        if (!mysqlstatements.checkUserExists(event.getOption("user").getAsUser().getAsMention())) {
            daedalus.sendReply(":x: This user isn´t registrated :x:", event);
            return;
        }

        if (event.getOption("amount") == null) {
            daedalus.sendReply(":x: You need to specify an amount! :x:", event);
            return;
        }

        if (event.getOption("amount").getAsInt() < 0) {
            daedalus.sendReply(":x: You can´t pay negative amounts! :x:", event);
            return;
        }
        if (event.getOption("amount").getAsInt() > mysqlstatements.getMoney(event.getUser().getAsMention())) {
            daedalus.sendReply(":x: You don´t have enough money! :x:", event);
            return;
        }

        mysqlstatements.transferUserMoney(event.getUser().getAsMention(), event.getOption("user").getAsUser().getAsMention(), event.getOption("amount").getAsInt());
        daedalus.sendReply(":white_check_mark: You have successfully transfered " + event.getOption("amount").getAsInt() + "€ to " + event.getOption("user").getAsUser().getAsMention() + " :white_check_mark:", event);
    }
}
