package de.daedalusdontknow.Commands.MultiPlayerGame;

import de.daedalusdontknow.faySystem.daedalus;
import de.daedalusdontknow.faySystem.function;
import de.daedalusdontknow.faySystem.mysqlstatements;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import java.util.ArrayList;
import java.util.List;

public class cmdTTT {

    public static List<String[]> games = new ArrayList<>();
    public static List<Integer[]> Boards = new ArrayList<>();

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
        embed.setTitle("TickTackToe");
        embed.setDescription(player2 + ", You have been invited to play ticktacktoe by " + player1);
        embed.addField("Bet", bet + "€", false);

        embed.setColor(0xFFFFFF);

        List<Button> buttons = new ArrayList<>();
        buttons.add(Button.success("ttt_accept-" + player1, "Accept"));
        buttons.add(Button.danger("ttt_decline-" + player2, "Decline"));

        daedalus.sendReplyEmbedWithButtons(embed, buttons, event);
    }
    public static void handler(ButtonInteractionEvent event){
        String[] args = event.getComponentId().split("-");

        String[] players = event.getMessage().getEmbeds().get(0).getDescription().split(", You have been invited to play ticktacktoe by ");

        String player1 = players[1].replace(" ", "");
        String player2 = players[0].replace(" ", "");

        int bet = Integer.parseInt(event.getMessage().getEmbeds().get(0).getFields().get(0).getValue().replace("€", ""));

        if(!event.getUser().getAsMention().equalsIgnoreCase(player2)){
            event.reply("You are not allowed to do this!").setEphemeral(true).queue();
            return;
        }

        if (event.getComponentId().startsWith("ttt_accept")) {
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

    private static void start(String player1, String player2, int bet, ButtonInteractionEvent event){
        //generate a random id 8 digits long
        int id = function.generateRandomID(8);
        String[] game = new String[4];
        game[0] = String.valueOf(id);
        game[1] = player1;
        game[2] = player2;
        game[3] = String.valueOf(bet);

        games.add(game);

        Integer[] board = new Integer[10];
        board[0] = id;
        for(int i = 1; i < board.length; i++){
            board[i] = 0;
        }

        Boards.add(board);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("TickTackToe");
        embed.setDescription(player1 + " vs " + player2);
        embed.addField("Bet", bet + "€", true);
        //select random player to start
        int random = Math.random() < 0.5 ? 1 : 2;

        if(random == 1){
            embed.addField("Turn", player1, true);
        }
        else{
            embed.addField("Turn", player2, true);
        }

        embed.addField("Board",
                ":black_large_square::black_large_square::black_large_square::black_large_square::black_large_square:\n" +
                        ":black_large_square::white_large_square::white_large_square::white_large_square::black_large_square:\n" +
                        ":black_large_square::white_large_square::white_large_square::white_large_square::black_large_square:\n" +
                        ":black_large_square::white_large_square::white_large_square::white_large_square::black_large_square:\n" +
                        ":black_large_square::black_large_square::black_large_square::black_large_square::black_large_square:", false);

        embed.setColor(0xFFFFFF);

        event.getChannel().sendMessageEmbeds(embed.build()).addActionRow(
                SelectMenu.create("ttt_select-" + id)
                        .addOptions(SelectOption.of("1", "1")
                                .withEmoji(Emoji.fromUnicode("1️⃣")))
                        .addOptions(SelectOption.of("2", "2")
                                .withEmoji(Emoji.fromUnicode("2️⃣")))
                        .addOptions(SelectOption.of("3", "3")
                                .withEmoji(Emoji.fromUnicode("3️⃣")))
                        .addOptions(SelectOption.of("4", "4")
                                .withEmoji(Emoji.fromUnicode("4️⃣")))
                        .addOptions(SelectOption.of("5", "5")
                                .withEmoji(Emoji.fromUnicode("5️⃣")))
                        .addOptions(SelectOption.of("6", "6")
                                .withEmoji(Emoji.fromUnicode("6️⃣")))
                        .addOptions(SelectOption.of("7", "7")
                                .withEmoji(Emoji.fromUnicode("7️⃣")))
                        .addOptions(SelectOption.of("8", "8")
                                .withEmoji(Emoji.fromUnicode("8️⃣")))
                        .addOptions(SelectOption.of("9", "9")
                                .withEmoji(Emoji.fromUnicode("9️⃣")))
                        .build()
        ).queue();
    }

    public static void menuHandler(SelectMenuInteractionEvent event){
        //get the game id
        String[] args = event.getComponentId().split("-");
        int id = Integer.parseInt(args[1]);


        for(int i = 0; i < games.size(); i++){
            if(Integer.parseInt(games.get(i)[0]) == id){
                String player1 = games.get(i)[1];
                String player2 = games.get(i)[2];

                int bet = Integer.parseInt(games.get(i)[3]);

                if(!event.getUser().getAsMention().equalsIgnoreCase(player1) && !event.getUser().getAsMention().equalsIgnoreCase(player2)){
                    event.reply("You are not allowed to do this!").setEphemeral(true).queue();
                    return;
                }

                if(event.getUser().getAsMention().equalsIgnoreCase(player1) && !event.getMessage().getEmbeds().get(0).getFields().get(1).getValue().equalsIgnoreCase(player1)){
                    event.reply("It´s not your turn!").setEphemeral(true).queue();
                    return;
                }
                else if(event.getUser().getAsMention().equalsIgnoreCase(player2) && !event.getMessage().getEmbeds().get(0).getFields().get(1).getValue().equalsIgnoreCase(player2)){
                    event.reply("It´s not your turn!").setEphemeral(true).queue();
                    return;
                }

                Integer[] board = new Integer[10];
                for(int j = 0; j < Boards.size(); j++){
                    if(Boards.get(j)[0] == id){
                        board = Boards.get(j);
                    }
                }

                int Field = Integer.parseInt(event.getValues().get(0));

                for(int j = 0; j < Boards.size(); j++){
                    if(Boards.get(j)[0] == id){
                        if(Boards.get(j)[Field] != 0){
                            event.reply("This field is already taken!").setEphemeral(true).queue();
                            return;
                        }
                    }
                }

                //update the boards array with the new field value (1 = player1, 2 = player2)
                for(int j = 0; j < Boards.size(); j++){
                    if(Boards.get(j)[0] == id){
                        if(event.getUser().getAsMention().equalsIgnoreCase(player1)){
                            Boards.get(j)[Field] = 1;
                        }
                        else if(event.getUser().getAsMention().equalsIgnoreCase(player2)){
                            Boards.get(j)[Field] = 2;
                        }
                    }
                }

                String boardString = getBoard(id);

                //update the players turn in the embed
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("TickTackToe");
                embed.setDescription(player1 + " vs " + player2);
                embed.addField("Bet", games.get(i)[3] + "€", true);
                if(event.getUser().getAsMention().equalsIgnoreCase(player1)){
                    embed.addField("Turn", player2, true);
                }
                else if(event.getUser().getAsMention().equalsIgnoreCase(player2)){
                    embed.addField("Turn", player1, true);
                }

                //update the board in the embed, for loop through the boards array, 0 = id, 1-9 = fields, 0 = :white_large_square:, 1 = :regional_indicator_x:, 2 = :o2:
                embed.addField("Board", boardString, false);
                event.getMessage().editMessageEmbeds(embed.build()).queue();

                //check if every field is filled in Board array
                int filled = 0;
                for(int j = 0; j < Boards.size(); j++){
                    if(Boards.get(j)[0] == id){
                        for(int k = 1; k < 10; k++){
                            if(Boards.get(j)[k] != 0){
                                filled++;
                            }
                        }
                    }
                }

                //check if someone has 3 in a row in Board array (1 = player1, 2 = player2) (0 = id, 1-9 = fields)
                //first check if someone has 3 in a row horizontally and execute setWinner() if true
                for (int j = 0; j < Boards.size(); j++) {
                    if (Boards.get(j)[0] == id) {
                        if (Boards.get(j)[1] == Boards.get(j)[2] && Boards.get(j)[2] == Boards.get(j)[3] && Boards.get(j)[1] != 0) {
                            setWinner(id, player1, player2, bet, event);
                            return;
                        }
                        if (Boards.get(j)[4] == Boards.get(j)[5] && Boards.get(j)[5] == Boards.get(j)[6] && Boards.get(j)[4] != 0) {
                            setWinner(id, player1, player2, bet, event);
                            return;
                        }
                        if (Boards.get(j)[7] == Boards.get(j)[8] && Boards.get(j)[8] == Boards.get(j)[9] && Boards.get(j)[7] != 0) {
                            setWinner(id, player1, player2, bet, event);
                            return;
                        }
                    }
                }
                //vertical
                for (int j = 0; j < Boards.size(); j++) {
                    if (Boards.get(j)[0] == id) {
                        if (Boards.get(j)[1] == Boards.get(j)[4] && Boards.get(j)[4] == Boards.get(j)[7] && Boards.get(j)[1] != 0) {
                            setWinner(id, player1, player2, bet, event);
                            return;
                        }
                        if (Boards.get(j)[2] == Boards.get(j)[5] && Boards.get(j)[5] == Boards.get(j)[8] && Boards.get(j)[2] != 0) {
                            setWinner(id, player1, player2, bet, event);
                            return;
                        }
                        if (Boards.get(j)[3] == Boards.get(j)[6] && Boards.get(j)[6] == Boards.get(j)[9] && Boards.get(j)[3] != 0) {
                            setWinner(id, player1, player2, bet, event);
                            return;
                        }
                    }
                }
                //diagonal
                for (int j = 0; j < Boards.size(); j++) {
                    if (Boards.get(j)[0] == id) {
                        if (Boards.get(j)[1] == Boards.get(j)[5] && Boards.get(j)[5] == Boards.get(j)[9] && Boards.get(j)[1] != 0) {
                            setWinner(id, player1, player2, bet, event);
                            return;
                        }
                        if (Boards.get(j)[3] == Boards.get(j)[5] && Boards.get(j)[5] == Boards.get(j)[7] && Boards.get(j)[3] != 0) {
                            setWinner(id, player1, player2, bet, event);
                            return;
                        }
                    }
                }

                if (filled == 9){
                    EmbedBuilder embed2 = new EmbedBuilder();
                    embed2.setTitle("TickTackToe");
                    embed2.setDescription(player1 + " vs " + player2);
                    embed2.addField("Bet", games.get(i)[3] + "€", true);
                    embed2.addField("Turn", "Game Over", true);
                    embed2.addField("Board", boardString, false);
                    embed2.setColor(0xFFFFFF);
                    event.getMessage().editMessageEmbeds(embed2.build()).queue();

                    mysqlstatements.addMoney(player1, bet);
                    mysqlstatements.addMoney(player2, bet);

                    for(int h = 0; i < games.size(); i++){
                        if(Integer.parseInt(games.get(i)[0]) == id){
                            games.remove(i);
                        }
                    }

                    for(int h = 0; i < Boards.size(); i++){
                        if(Boards.get(i)[0] == id){
                            Boards.remove(i);
                        }
                    }
                    return;
                }
                return;
            }
        }
        event.reply("This game already ended!").setEphemeral(false).queue();
    }

    private static void setWinner(int id, String player1, String player2, int bet, SelectMenuInteractionEvent event) {
        for(int i = 0; i < games.size(); i++){
            if(games.get(i)[0].equalsIgnoreCase(String.valueOf(id))){
                if(event.getUser().getAsMention().equalsIgnoreCase(player1)){
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("TickTackToe");
                    embed.setDescription(player1 + " vs " + player2);
                    embed.addField("Bet", games.get(i)[3] + "€", true);
                    embed.addField("Turn", "Game Over", true);
                    embed.addField("winner", player1, true);
                    embed.addField("Board", getBoard(id), false);
                    embed.setColor(0x00FF00);
                    event.getMessage().editMessageEmbeds(embed.build()).queue();

                    mysqlstatements.addMoney(player1, bet*2);
                    return;
                }
                else if(event.getUser().getAsMention().equalsIgnoreCase(player2)){
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("TickTackToe");
                    embed.setDescription(player1 + " vs " + player2);
                    embed.addField("Bet", games.get(i)[3] + "€", true);
                    embed.addField("Turn", "Game Over", true);
                    embed.addField("Winner", player2, true);
                    embed.addField("Board", getBoard(id), false);
                    embed.setColor(0x00FF00);
                    event.getMessage().editMessageEmbeds(embed.build()).queue();

                    mysqlstatements.addMoney(player2, bet*2);
                    return;
                }
            }
        }

        for(int i = 0; i < games.size(); i++){
            if(Integer.parseInt(games.get(i)[0]) == id){
                games.remove(i);
            }
        }

        for(int i = 0; i < Boards.size(); i++){
            if(Boards.get(i)[0] == id){
                Boards.remove(i);
            }
        }
    }


    private static String getBoard(int ID) {
        String board = ":black_large_square::black_large_square::black_large_square::black_large_square::black_large_square:\n";
        board += ":black_large_square:";

        for(int j = 1; j < 10; j++){
            for(int k = 0; k < Boards.size(); k++){
                if(Boards.get(k)[0] == ID){

                    if(Boards.get(k)[j] == 0){
                        board += ":white_large_square:";
                    }
                    else if(Boards.get(k)[j] == 1){
                        board += ":regional_indicator_x:";
                    }
                    else if(Boards.get(k)[j] == 2){
                        board += ":o2:";
                    }
                }
            }
            if(j == 3 || j == 6){
                board += ":black_large_square:\n:black_large_square:";
            }
        }

        board += ":black_large_square:";
        board += "\n:black_large_square::black_large_square::black_large_square::black_large_square::black_large_square:";
        return board;
    }
}