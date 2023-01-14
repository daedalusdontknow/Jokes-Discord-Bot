package de.daedalusdontknow.Commands.MultiPlayerGame;

import de.daedalusdontknow.faySystem.function;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.List;

public class cmdGames {
    public static void run(SlashCommandInteractionEvent event) {
        if (!function.checkStatements(event)) return;

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Currently running games");
        eb.addField("MRPS", "_There are currently " + cmdMRPS.games.size() + " running Rock Paper Scissor games_", true);
        eb.addField("TTT", "_There are currently " + cmdTTT.games.size() + " running Tic Tac Toe games_", true);

        //list all game ids
        String mrps = "";
        String ttt = "";
        for (int i = 0; i < cmdMRPS.games.size(); i++) {
            mrps += cmdMRPS.games.get(i)[0] + "\n";
        }
        for (int i = 0; i < cmdTTT.games.size(); i++) {
            ttt += cmdTTT.games.get(i)[0] + "\n";
        }

        if(!mrps.equals("")) eb.addField("MRPS IDs", mrps, false);
        if (!ttt.equals("")) eb.addField("TTT IDs", ttt, false);

        event.replyEmbeds(eb.build()).setEphemeral(false).queue();
    }
}
