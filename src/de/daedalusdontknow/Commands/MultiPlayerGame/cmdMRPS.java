package de.daedalusdontknow.Commands.MultiPlayerGame;

import de.daedalusdontknow.faySystem.daedalus;
import de.daedalusdontknow.faySystem.function;
import de.daedalusdontknow.faySystem.mysqlstatements;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.List;

public class cmdMRPS {

    public static List<String[]> games = new ArrayList<>();

    public static void run(SlashCommandInteractionEvent event) {
        String player1 = event.getUser().getAsMention();
        String player2 = event.getOption("user").getAsUser().getAsMention();

        int bet = event.getOption("amount").getAsInt();

        if (!function.checkStatements(event)) return;

        if (!mysqlstatements.checkUserExists(player2)) {
            daedalus.sendReply(":x: This user isn´t registrated :x:", event);
            return;
        }

        if (player1.equals(player2)) {
            daedalus.sendReply(":x: You can´t play against yourself! :x:", event);
            return;
        }

        if (bet < 0) {
            daedalus.sendReply(":x: You can´t bet negative amounts! :x:", event);
            return;
        }

        if (bet > 100) {
            daedalus.sendReply(":x: You can´t bet more than 100€! :x:", event);
            return;
        }

        if (bet > mysqlstatements.getMoney(player1) || bet > mysqlstatements.getMoney(player2)) {
            daedalus.sendReply(":x: One of the players doesn´t have enough money! :x:", event);
            return;
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Rock, Paper, Scissors");
        embed.setDescription(player2 + ", You have been invited to play Rock, Paper, Scissors by " + player1);
        embed.addField("Bet", bet + "€", false);

        embed.setColor(0xFFFFFF);

        List<Button> buttons = new ArrayList<>();
        buttons.add(Button.success("mrps_accept", "Accept"));
        buttons.add(Button.danger("mrps_decline", "Decline"));

        daedalus.sendReplyEmbedWithButtons(embed, buttons, event);
    }

    public static void handler(ButtonInteractionEvent event) {
        String[] args = event.getComponentId().split("-");

        String[] players = event.getMessage().getEmbeds().get(0).getDescription().split(", You have been invited to play Rock, Paper, Scissors by ");

        String player1 = players[1].replace(" ", "");
        String player2 = players[0].replace(" ", "");

        int bet = Integer.parseInt(event.getMessage().getEmbeds().get(0).getFields().get(0).getValue().replace("€", ""));

        if(!event.getUser().getAsMention().equalsIgnoreCase(player2)){
            event.reply("You are not allowed to do this!").setEphemeral(true).queue();
            return;
        }

        if (event.getComponentId().startsWith("mrps_accept")) {
            if (bet > mysqlstatements.getMoney(player1) || bet > mysqlstatements.getMoney(player2)) {
                event.reply(":x: One of the players doesn´t have enough money! :x:").setEphemeral(false).queue();
                return;
            }
            else {
                mysqlstatements.removeMoney(player1, bet);
                mysqlstatements.removeMoney(player2, bet);

                start(player1, player2, bet, event);
                event.getMessage().delete().queue();
            }

        } else {
            event.reply(":x: " + player2 + " has declined the invitation! :x:").setEphemeral(false).queue();
            event.getMessage().delete().queue();
        }
    }

    private static void start(String player1, String player2, int bet, ButtonInteractionEvent event) {
        int ID = function.generateRandomID(8);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Rock, Paper, Scissors");
        embed.setDescription(player1 + " vs " + player2);
        embed.addField("Bet", bet + "€", false);

        embed.addField("", player1 + "\n:black_large_square:", true);
        embed.addField("", player2 + "\n:black_large_square:", true);

        embed.setColor(0xFFFFFF);

        List<Button> buttons = new ArrayList<>();
        buttons.add(Button.primary("mrps-g_rock." + ID, "Rock").withEmoji(Emoji.fromUnicode("🗿")));
        buttons.add(Button.primary("mrps-g_paper." + ID, "Paper").withEmoji(Emoji.fromUnicode("🧻")));
        buttons.add(Button.primary("mrps-g_scissors." + ID, "Scissors").withEmoji(Emoji.fromUnicode("✂")));

        event.replyEmbeds(embed.build()).setActionRow(buttons).queue();

        String[] game = {String.valueOf(ID), player1, player2, bet + "", "0", "0"};
        games.add(game);
    }

    public static void buttonHandler(ButtonInteractionEvent event) {
        String[] args = event.getComponentId().replace("mrps-g_", "").split("\\.");
        int ID = Integer.parseInt(args[1]);

        //get the player names from games by ID
        for(int i = 0; i < games.size(); i++) {
            if (Integer.parseInt(games.get(i)[0]) == ID) {
                String player1 = games.get(i)[1];
                String player2 = games.get(i)[2];

                int bet = Integer.parseInt(games.get(i)[3]);

                String choice1 = games.get(i)[4];
                String choice2 = games.get(i)[5];

                if (!event.getUser().getAsMention().equalsIgnoreCase(player1) && !event.getUser().getAsMention().equalsIgnoreCase(player2)) {
                    event.reply("You are not allowed to do this!").setEphemeral(true).queue();
                    return;
                }

                if (event.getUser().getAsMention().equalsIgnoreCase(player1)) {
                    if (choice1.equals("0")) {
                        games.get(i)[4] = args[0];
                        choice1 = args[0];
                    } else {
                        event.reply("You have already chosen!").setEphemeral(true).queue();
                        return;
                    }
                } else if (event.getUser().getAsMention().equalsIgnoreCase(player2)) {
                    if (choice2.equals("0")) {
                        games.get(i)[5] = args[0];
                        choice2 = args[0];
                    } else {
                        event.reply("You have already chosen!").setEphemeral(true).queue();
                        return;
                    }
                }

                if (!choice1.equals("0") && !choice2.equals("0")) {

                    String Emoji1 = ":x:";
                    String Emoji2 = ":x:";

                    switch (choice1) {
                        case "rock": Emoji1 = "🗿"; break;
                        case "paper": Emoji1 = "🧻"; break;
                        case "scissors": Emoji1 = "✂"; break;
                    }

                    switch (choice2) {
                        case "rock": Emoji2 = "🗿"; break;
                        case "paper": Emoji2 = "🧻"; break;
                        case "scissors": Emoji2 = "✂"; break;
                    }


                    if (choice1.equals(choice2)) {

                        EmbedBuilder embed = new EmbedBuilder();
                        embed.setTitle("Rock, Paper, Scissors");
                        embed.setDescription(player1 + " vs " + player2);
                        embed.addField("Bet", bet + "€", false);

                        embed.addField("", player1 + "\n" + Emoji1, true);
                        embed.addField("", player2 + "\n" + Emoji2, true);

                        embed.addField("Winner", "Its a tie", false);
                        embed.setColor(0x00FF00);

                        event.editMessageEmbeds(embed.build()).queue();

                        mysqlstatements.addMoney(player1, bet);
                        mysqlstatements.addMoney(player2, bet);
                        games.remove(i);
                        return;
                    } else if (choice1.equals("rock") && choice2.equals("scissors") || choice1.equals("paper") && choice2.equals("rock") || choice1.equals("scissors") && choice2.equals("paper")) {
                        EmbedBuilder embed = new EmbedBuilder();
                        embed.setTitle("Rock, Paper, Scissors");
                        embed.setDescription(player1 + " vs " + player2);
                        embed.addField("Bet", bet + "€", false);

                        embed.addField("", player1 + "\n" + Emoji1, true);
                        embed.addField("", player2 + "\n" + Emoji2, true);

                        embed.addField("Winner", player1, false);
                        embed.setColor(0x00FF00);

                        event.editMessageEmbeds(embed.build()).queue();

                        mysqlstatements.addMoney(player1, bet * 2);
                        games.remove(i);
                        return;
                    } else if (choice2.equals("rock") && choice1.equals("scissors") || choice2.equals("paper") && choice1.equals("rock") || choice2.equals("scissors") && choice1.equals("paper")) {
                        EmbedBuilder embed = new EmbedBuilder();
                        embed.setTitle("Rock, Paper, Scissors");
                        embed.setDescription(player1 + " vs " + player2);
                        embed.addField("Bet", bet + "€", false);

                        embed.addField("", player1 + "\n" + Emoji1, true);
                        embed.addField("", player2 + "\n" + Emoji2, true);

                        embed.addField("Winner", player2, false);
                        embed.setColor(0x00FF00);

                        event.editMessageEmbeds(embed.build()).queue();

                        mysqlstatements.addMoney(player2, bet * 2);
                        games.remove(i);
                        return;
                    }
                } else {
                    event.reply("Waiting for the other player to choose!").setEphemeral(true).queue();
                }
            } else {
                event.reply("This game already ended!").setEphemeral(false).queue();
            }
        }
    }
}