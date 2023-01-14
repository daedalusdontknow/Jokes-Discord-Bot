package de.daedalusdontknow.faySystem;

import de.daedalusdontknow.Commands.Game.cmdCF;
import de.daedalusdontknow.Commands.Game.cmdRPS;
import de.daedalusdontknow.Commands.MultiPlayerGame.cmdMRPS;
import de.daedalusdontknow.Commands.MultiPlayerGame.cmdTTT;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class buttonHandler extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        String[] args = event.getButton().getId().split("_");
        switch (args[0]) {
            case "cf" -> cmdCF.handler(event);
            case "rps" -> cmdRPS.handler(event);
            case "ttt" -> cmdTTT.handler(event);
            case "mrps" -> cmdMRPS.handler(event);
            case "mrps-g" -> cmdMRPS.buttonHandler(event);
        }
    }
}
