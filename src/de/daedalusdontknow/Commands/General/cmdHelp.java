package de.daedalusdontknow.Commands.General;

import de.daedalusdontknow.faySystem.daedalus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class cmdHelp {
    public static void run(SlashCommandInteractionEvent event) {
        //embed with all the commands and their description
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Help");
        eb.setDescription("Here you can find all the commands of the bot");
        eb.addField("General", "``/help`` - Shows this message" +
                "\n ``/create-user`` - create an account" +
                "\n ``/delete-user`` - delete your account" +
                "\n ``/userinfo`` - see information about users", false);
        eb.addField("Money", "``/balance`` - see your balance" +
                "\n ``/pay`` - pay someone" +
                "\n ``/work`` - go work for some money" +
                "\n ``/top`` - see the top 10 richest users", false);
        eb.addField("Games", "``/rockpaperscissors`` - play rock paper scissors" +
                "\n ``/coinflip`` - flip a coin" +
                "\n ``/diceroll`` - roll a dice", false);
        eb.setColor(0x00ff00);
        eb.setFooter("Made by PhilippG");
        daedalus.sendReplyEmbed(eb, event);
    }
}