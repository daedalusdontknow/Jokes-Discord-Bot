package de.daedalusdontknow.faySystem;

import de.daedalusdontknow.Commands.Game.cmdCF;
import de.daedalusdontknow.Commands.Game.cmdDR;
import de.daedalusdontknow.Commands.Game.cmdRPS;
import de.daedalusdontknow.Commands.General.cmdCreateUser;
import de.daedalusdontknow.Commands.General.cmdDeleteUser;
import de.daedalusdontknow.Commands.General.cmdHelp;
import de.daedalusdontknow.Commands.General.cmdUserInfo;
import de.daedalusdontknow.Commands.Money.cmdBalance;
import de.daedalusdontknow.Commands.Money.cmdPay;
import de.daedalusdontknow.Commands.Money.cmdTop;
import de.daedalusdontknow.Commands.Money.cmdWork;
import de.daedalusdontknow.Commands.MultiPlayerGame.cmdGames;
import de.daedalusdontknow.Commands.MultiPlayerGame.cmdMRPS;
import de.daedalusdontknow.Commands.MultiPlayerGame.cmdTTT;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class commandHandler extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        switch (event.getName()) {
            case "help" -> cmdHelp.run(event);
            case "create-user" -> cmdCreateUser.run(event);
            case "delete-user" -> cmdDeleteUser.run(event);
            case "userinfo", "ui" -> cmdUserInfo.run(event);
            case "balance", "bal" -> cmdBalance.run(event);
            case "pay" -> cmdPay.run(event);
            case "work" -> cmdWork.run(event);
            case "top" -> cmdTop.run(event);
            case "rps", "rockpaperscissors" -> cmdRPS.run(event);
            case "cf", "coinflip" -> cmdCF.run(event);
            case "dr", "diceroll" -> cmdDR.run(event);
            case "games" -> cmdGames.run(event);
            case "ttt", "tictactoe" -> cmdTTT.run(event);
            case "mrps", "multiplayer-rps", "multiplayer-rockpaperscissors" -> cmdMRPS.run(event);
        }
    }
}
