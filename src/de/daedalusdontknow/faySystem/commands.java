package de.daedalusdontknow.faySystem;

import de.daedalusdontknow.main;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public class commands {

    public static void upsert() {
        //----------------------------------------------------------------------------------------------------------------------
        //                                      General  Commands
        //----------------------------------------------------------------------------------------------------------------------

        main.builder.upsertCommand("create-user", "Create you as a new user").queue();
        main.builder.upsertCommand("delete-user", "Delete you as a user").queue();
        main.builder.upsertCommand("help", "Get help").queue();
        main.builder.upsertCommand("userinfo", "Get user info").addOption(OptionType.USER, "user", "Get the data of a user or leave blank to get your own").queue();
        main.builder.upsertCommand("ui", "Get user info").addOption(OptionType.USER, "user", "Get the data of a user or leave blank to get your own").queue();

        //----------------------------------------------------------------------------------------------------------------------
        //                                      Money Commands
        //----------------------------------------------------------------------------------------------------------------------

        main.builder.upsertCommand("balance", "Get your balance").queue();
        main.builder.upsertCommand("bal", "Get your balance").queue();
        main.builder.upsertCommand("pay", "Pay someone").addOption(OptionType.USER, "user", "The user you want to pay").addOption(OptionType.INTEGER, "amount", "the amount you want to pay").queue();
        main.builder.upsertCommand("work", "Work").queue();
        main.builder.upsertCommand("top", "Get the top 10").queue();

        //----------------------------------------------------------------------------------------------------------------------
        //                                      Game  Commands
        //----------------------------------------------------------------------------------------------------------------------

        main.builder.upsertCommand("rps", "Play rock paper scissors").addOption(OptionType.INTEGER, "amount", "the amount you want to play with").queue();
        main.builder.upsertCommand("rockpaperscissors", "Play rock paper scissors").addOption(OptionType.INTEGER, "amount", "the amount you want to play with").queue();
        main.builder.upsertCommand("cf", "Flip a coin").addOption(OptionType.INTEGER, "amount", "the amount you want to play with").queue();
        main.builder.upsertCommand("coinflip", "Flip a coin").addOption(OptionType.INTEGER, "amount", "the amount you want to play with").queue();
        main.builder.upsertCommand("dr", "Roll a dice").addOption(OptionType.INTEGER, "amount", "the amount you want to play with").queue();
        main.builder.upsertCommand("diceroll", "Roll a dice").addOption(OptionType.INTEGER, "amount", "the amount you want to play with").queue();

        //----------------------------------------------------------------------------------------------------------------------
        //                                      Multiplayer Game  Commands
        //----------------------------------------------------------------------------------------------------------------------

        main.builder.upsertCommand("games", "Get all running games").queue();
        main.builder.upsertCommand("ttt", "Play Tic Tac Toe").addOption(OptionType.USER, "user", "The user you want to play with").addOption(OptionType.INTEGER, "amount", "the amount you want to play with").queue();
        main.builder.upsertCommand("tictactoe", "Play Tic Tac Toe").addOption(OptionType.USER, "user", "The user you want to play with").addOption(OptionType.INTEGER, "amount", "the amount you want to play with").queue();
        main.builder.upsertCommand("mrps", "Play Multiplayer Rock Paper Scissors").addOption(OptionType.USER, "user", "The user you want to play with").addOption(OptionType.INTEGER, "amount", "the amount you want to play with").queue();
        main.builder.upsertCommand("multiplayer-rps", "Play Multiplayer Rock Paper Scissors").addOption(OptionType.USER, "user", "The user you want to play with").addOption(OptionType.INTEGER, "amount", "the amount you want to play with").queue();
        main.builder.upsertCommand("multiplayer-rockpaperscissors", "Play Multiplayer Rock Paper Scissors").addOption(OptionType.USER, "user", "The user you want to play with").addOption(OptionType.INTEGER, "amount", "the amount you want to play with").queue();
    }
}