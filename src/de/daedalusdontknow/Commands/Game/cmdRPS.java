package de.daedalusdontknow.Commands.Game;

import de.daedalusdontknow.faySystem.daedalus;
import de.daedalusdontknow.faySystem.function;
import de.daedalusdontknow.faySystem.mysqlstatements;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.TimeFormat;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class cmdRPS {
    public static void run(SlashCommandInteractionEvent event) {
        if (!function.checkStatements(event)) return;
        if (function.checkGameTime(event.getUser().getAsMention()) < 20) {
            event.reply(daedalus.emoji_warning + " " + TimeFormat.RELATIVE.after(Duration.ofMinutes(20 - function.checkGameTime(event.getUser().getAsMention()))) + " you can play again! " + daedalus.emoji_warning).setEphemeral(false).queue();
            return;
        }

        if (event.getOption("amount") == null) {
            daedalus.sendReply(":x: You need to specify an amount :x:", event);
            return;
        }

        int amount = event.getOption("amount").getAsInt();
        if (amount < 1 || amount > 20) {
            daedalus.sendReply(":x: You need to specify an amount (1-20) :x:", event);
            return;
        }

        if (mysqlstatements.getMoney(event.getUser().getAsMention()) < amount) {
            daedalus.sendReply(":x: You don't have enough money :x:", event);
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Rock Paper Scissors - " + amount + "€");
        eb.setColor(0x00ff00);
        eb.setDescription("Currently playing " + event.getUser().getAsMention() + "\n" +
                "React with " + daedalus.emoji_Rock + " to pick rock\n" +
                "React with " + daedalus.emoji_Paper + " to pick paper\n" +
                "React with " + daedalus.emoji_Scissors + " to pick scissor\n" +
                "You have played **" + amount + "** €");

        List<Button> buttons = new ArrayList<>();

        buttons.add(Button.primary("rps_rock", "Rock").withEmoji(Emoji.fromUnicode(daedalus.unicode_emoji_rock)));
        buttons.add(Button.primary("rps_paper", "Paper").withEmoji(Emoji.fromUnicode(daedalus.unicode_emoji_paper)));
        buttons.add(Button.primary("rps_scissor", "Scissor").withEmoji(Emoji.fromUnicode(daedalus.unicode_emoji_scissors)));

        event.replyEmbeds(eb.build()).setActionRow(buttons).setEphemeral(true).queue();
    }

    public static void handler(ButtonInteractionEvent event) {

        if (function.checkGameTime(event.getUser().getAsMention()) < 20) {
            event.reply(daedalus.emoji_warning + " " + TimeFormat.RELATIVE.after(Duration.ofMinutes(20 - function.checkGameTime(event.getUser().getAsMention()))) + " you can play again! " + daedalus.emoji_warning).setEphemeral(false).queue();
            return;
        }

        String[] tmp = event.getMessage().getEmbeds().get(0).getTitle().split(" - ");
        int amount = Integer.parseInt(tmp[1].replace("€", ""));

        if (mysqlstatements.getMoney(event.getUser().getAsMention()) < amount) {
            event.reply(":x: You don't have enough money :x:").setEphemeral(false).queue();
            return;
        }

        mysqlstatements.setPlayingTime(event.getUser().getAsMention());

        int random = (int) (Math.random() * 3) + 1;
        String result = random == 1 ? "rock" : random == 2 ? "paper" : "scissor";

        String userChoice = event.getButton().getId().split("_")[1];

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Rock, Paper, scissors");
        eb.setDescription(event.getUser().getAsMention() + " played a game of Rock, Paper, scissors against the bot");
        eb.addField("Bet", amount + "€", false);

        switch (result) {
            case "rock" -> {
                eb.addField("Choice", ":person_curly_hair: : " + getEmoji(userChoice) +
                        "\n :robot: : " + daedalus.emoji_Rock, false);
                if (userChoice.equals("rock")) {
                    eb.addField("Outcome", "Tie, you lost " + amount / 2 + "€", false);
                    mysqlstatements.removeMoney(event.getUser().getAsMention(), amount / 2);
                } else if (userChoice.equals("paper")) {
                    eb.addField("Outcome", "You won, you gained " + amount + "€", false);
                    mysqlstatements.addMoney(event.getUser().getAsMention(), amount);
                } else if (userChoice.equals("scissor")) {
                    eb.addField("Outcome", "You lost " + amount + "€", false);
                    mysqlstatements.removeMoney(event.getUser().getAsMention(), amount);
                }
            }

            case "paper" -> {
                eb.addField("Choice", ":person_curly_hair: : " + getEmoji(userChoice) +
                        "\n :robot: : " + daedalus.emoji_Paper, false);
                if (userChoice.equals("rock")) {
                    eb.addField("Outcome", "You lost " + amount + "€", false);
                    mysqlstatements.removeMoney(event.getUser().getAsMention(), amount);
                } else if (userChoice.equals("paper")) {
                    eb.addField("Outcome", "Tie, you lost " + amount / 2 + "€", false);
                    mysqlstatements.removeMoney(event.getUser().getAsMention(), amount / 2);
                } else if (userChoice.equals("scissor")) {
                    eb.addField("Outcome", "You won, you gained " + amount + "€", false);
                    mysqlstatements.addMoney(event.getUser().getAsMention(), amount);
                }
            }

            case "scissor" -> {
                eb.addField("Choice", ":person_curly_hair: : " + getEmoji(userChoice) +
                        "\n :robot: : " + daedalus.emoji_Scissors, false);
                if (userChoice.equals("rock")) {
                    eb.addField("Outcome", "You won, you gained " + amount + "€", false);
                    mysqlstatements.addMoney(event.getUser().getAsMention(), amount);
                } else if (userChoice.equals("paper")) {
                    eb.addField("Outcome", "You lost " + amount + "€", false);
                    mysqlstatements.removeMoney(event.getUser().getAsMention(), amount);
                } else if (userChoice.equals("scissor")) {
                    eb.addField("Outcome", "Tie, you lost " + amount / 2 + "€", false);
                    mysqlstatements.removeMoney(event.getUser().getAsMention(), amount / 2);
                }
            }
        }
        event.replyEmbeds(eb.build()).setEphemeral(false).queue();
    }

    private static String getEmoji(String emoji) {
        switch (emoji) {
            case "rock":
                return daedalus.emoji_Rock;
            case "paper":
                return daedalus.emoji_Paper;
            case "scissor":
                return daedalus.emoji_Scissors;
        }
        return ":0:";
    }
}
