package de.daedalusdontknow.faySystem;


import de.daedalusdontknow.Commands.MultiPlayerGame.cmdTTT;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class selectMenuHandler extends ListenerAdapter {

    @Override
    public void onSelectMenuInteraction(SelectMenuInteractionEvent event) {
        String[] args = event.getComponentId().split("_");

        switch (args[0]) {
            case "ttt" -> cmdTTT.menuHandler(event);
        }
    }
}