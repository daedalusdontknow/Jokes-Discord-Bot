package de.daedalusdontknow.Commands.Game;

import de.daedalusdontknow.faySystem.daedalus;
import de.daedalusdontknow.faySystem.function;
import de.daedalusdontknow.faySystem.mysqlstatements;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.utils.TimeFormat;

import java.time.Duration;

public class cmdDR {
    public static void run(SlashCommandInteractionEvent event) {
        if (!function.checkStatements(event)) return;
        if (function.checkGameTime(event.getUser().getAsMention()) < 15) {
            event.reply(daedalus.emoji_warning + " " + TimeFormat.RELATIVE.after(Duration.ofMinutes(15 - function.checkGameTime(event.getUser().getAsMention()))) + " you can play again! " + daedalus.emoji_warning).setEphemeral(false).queue();
            return;
        }

        if (event.getOption("amount") == null) {
            daedalus.sendReply(":x: You need to specify an amount :x:", event);
            return;
        }

        int amount = event.getOption("amount").getAsInt();
        if (amount < 1 || amount > 10) {
            daedalus.sendReply(":x: You need to specify an amount (1-10) :x:", event);
            return;
        }

        if (mysqlstatements.getMoney(event.getUser().getAsMention()) < amount) {
            daedalus.sendReply(":x: You don't have enough money :x:", event);
            return;
        }

        mysqlstatements.setPlayingTime(event.getUser().getAsMention());

        int diceRoll = (int) (Math.random() * 6) + 1;
        int diceRollBot = (int) (Math.random() * 6) + 1;

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(daedalus.emoji_upside_down + " Coinflip " + daedalus.emoji_1234);
        eb.setDescription(event.getUser().getAsMention() + " played a game of Coinflip");
        eb.addField("Bet", amount + "€", false);

        eb.addField("Dices", ":person_curly_hair: : " + getDiceEmoji(diceRoll) +
                "\n :robot: : " + getDiceEmoji(diceRollBot), false);

        if (diceRoll > diceRollBot) {
            eb.addField("Outcome", ":person_curly_hair: Won " + amount + "€", false);
            mysqlstatements.addMoney(event.getUser().getAsMention(), amount);
        } else if (diceRoll == diceRollBot) {
            eb.addField("Outcome", ":person_curly_hair: :robot: Draw", false);
            mysqlstatements.removeMoney(event.getUser().getAsMention(), amount / 2);
        } else {
            eb.addField("Outcome", ":robot: Won " + amount + "€", false);
            mysqlstatements.removeMoney(event.getUser().getAsMention(), amount);
            mysqlstatements.addMoney("Bank", amount);
        }

        daedalus.sendReplyEmbed(eb, event);
    }


    private static String getDiceEmoji(int diceRoll) {
        switch (diceRoll) {
            case 1:
                return daedalus.emoji_one;
            case 2:
                return daedalus.emoji_two;
            case 3:
                return daedalus.emoji_three;
            case 4:
                return daedalus.emoji_four;
            case 5:
                return daedalus.emoji_five;
            case 6:
                return daedalus.emoji_six;
        }
        return ":0:";
    }
}
