package de.daedalusdontknow.Commands.Game;

import de.daedalusdontknow.faySystem.daedalus;
import de.daedalusdontknow.faySystem.function;
import de.daedalusdontknow.faySystem.mysqlstatements;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;
import net.dv8tion.jda.api.utils.TimeFormat;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class cmdCF {
    public static void run(SlashCommandInteractionEvent event) {
        if (!function.checkStatements(event)) return;
        if (function.checkGameTime(event.getUser().getAsMention()) < 30) {
            event.reply(daedalus.emoji_warning + " " + TimeFormat.RELATIVE.after(Duration.ofMinutes(30 - function.checkGameTime(event.getUser().getAsMention()))) + " you can play again! " + daedalus.emoji_warning).setEphemeral(false).queue();
            return;
        }

        if (event.getOption("amount") == null) {
            daedalus.sendReply(":x: You need to specify an amount :x:", event);
            return;
        }

        int amount = event.getOption("amount").getAsInt();
        if (amount < 1 || amount > 25) {
            daedalus.sendReply(":x: You need to specify an amount (1-25) :x:", event);
            return;
        }

        if (mysqlstatements.getMoney(event.getUser().getAsMention()) < amount) {
            daedalus.sendReply(":x: You don't have enough money :x:", event);
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Coinflip - " + amount + "€");
        eb.setColor(0x00ff00);
        eb.setDescription("Currently playing " + event.getUser().getAsMention() + "\n" +
                "React with " + daedalus.emoji_upside_down + " to get heads\n" +
                "React with " + daedalus.emoji_1234 + " to get tails\n" +
                "You have played **" + amount + "** €");

        List<Button> buttons = new ArrayList<>();
        buttons.add(Button.primary("cf_head", "Head").withEmoji(Emoji.fromUnicode(daedalus.unicode_emoji_UpsideDownFace)));
        buttons.add(Button.success("cf_tail", "Tail").withEmoji(Emoji.fromUnicode(daedalus.unicode_emoji_1234)));

        event.replyEmbeds(eb.build()).setActionRow(buttons).setEphemeral(true).queue();
    }


    public static void handler(ButtonInteraction event) {

        if (function.checkGameTime(event.getUser().getAsMention()) < 30) {
            event.reply(daedalus.emoji_warning + " " + TimeFormat.RELATIVE.after(Duration.ofMinutes(30 - function.checkGameTime(event.getUser().getAsMention()))) + " you can play again! " + daedalus.emoji_warning).setEphemeral(false).queue();
            return;
        }

        String[] tmp = event.getMessage().getEmbeds().get(0).getTitle().split(" - ");
        int amount = Integer.parseInt(tmp[1].replace("€", ""));

        if (mysqlstatements.getMoney(event.getUser().getAsMention()) < amount) {
            event.reply(":x: You don't have enough money :x:").setEphemeral(false).queue();
            return;
        }

        mysqlstatements.setPlayingTime(event.getUser().getAsMention());

        int random = (int) (Math.random() * 2);
        String result = random == 0 ? "Head" : "Tail";

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(daedalus.emoji_upside_down + " Coinflip " + daedalus.emoji_1234);
        eb.setDescription(event.getUser().getAsMention() + " played a game of Coinflip");
        eb.addField("Bet", amount + "€", false);

        switch (result) {
            case "Head" -> {
                eb.addField("Result", daedalus.emoji_upside_down + " Head", false);
                if (event.getButton().getId().equals("cf_head")) {
                    mysqlstatements.addMoney(event.getUser().getAsMention(), amount);
                    eb.addField("Outcome", "You won " + amount + "€", false);
                } else {
                    mysqlstatements.removeMoney(event.getUser().getAsMention(), amount);
                    mysqlstatements.addMoney("Bank", amount);
                    eb.addField("Outcome", "You lost " + amount + "€", false);
                }
            }
            case "Tail" -> {
                eb.addField("Result", daedalus.emoji_1234 + " Tail", false);
                if (event.getButton().getId().equals("cf_tail")) {
                    mysqlstatements.addMoney(event.getUser().getAsMention(), amount);
                    eb.addField("Outcome", "You won " + amount + "€", false);
                } else {
                    mysqlstatements.removeMoney(event.getUser().getAsMention(), amount);
                    mysqlstatements.addMoney("Bank", amount);
                    eb.addField("Outcome", "You lost " + amount + "€", false);
                }
            }
        }

        event.replyEmbeds(eb.build()).setEphemeral(false).queue();

    }
}
