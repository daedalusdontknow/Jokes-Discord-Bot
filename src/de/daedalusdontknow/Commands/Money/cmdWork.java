package de.daedalusdontknow.Commands.Money;

import de.daedalusdontknow.faySystem.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.TimeFormat;

import java.time.Duration;

public class cmdWork {
    public static void run(SlashCommandInteractionEvent event) {
        if (!function.checkStatements(event)) return;
        long time = function.checkTime(event.getMember().getUser().getAsMention());

        if (time < 30) {
            daedalus.sendReply(daedalus.emoji_warning + " " + TimeFormat.RELATIVE.after(Duration.ofMinutes(30 - time)) + " you can work again! " + daedalus.emoji_warning, event);
            return;
        }

        mysqlstatements.setWorkingTime(event.getUser().getAsMention());

        int randomLine = (int) (Math.random() * dependencie.Histories.size());
        int money = (int) (Math.random() * 30 + 10);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Work");
        embed.setDescription(event.getUser().getAsMention() + " worked");
        embed.addField("Earned money", daedalus.emoji_money_with_wings + " " + money + "€", false);
        embed.addField("History", dependencie.Histories.get(randomLine), false);
        embed.setFooter("You can work again in " + TimeFormat.RELATIVE.after(Duration.ofMinutes(30)));

        daedalus.sendReplyEmbed(embed, event);

        mysqlstatements.addMoney(event.getUser().getAsMention(), money);
    }
}
