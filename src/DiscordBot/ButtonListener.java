package DiscordBot;

import Commands.Games.cmdCoinFlip;
import Commands.Games.cmdRockPaperScissors;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ButtonListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {

        if(event.getButton().getId().startsWith("cf_")){

            if(event.getButton().getId().contains("head")){ cf(event, "head"); }
            else if(event.getButton().getId().contains("tail")){ cf(event, "tail"); }
        }

        if(event.getButton().getId().startsWith("rps_")){

            if(event.getButton().getId().contains("rock")){ rps(event, "rock"); }
            else if(event.getButton().getId().contains("paper")){ rps(event, "paper"); }
            else if(event.getButton().getId().contains("scissor")){ rps(event, "scissor"); }
        }
    }

    private void cf(ButtonInteractionEvent event, String chosen) {
        String[] args = event.getMessage().getActionRows().get(0).toString().split(",");
        String username = args[0].replace("ActionRow([B:Head(cf_head-", "").replace(")", "");
        String bet = args[1].replace("B:Tail(cf_tail-", "").replace(")])", "").replace(" ", "");

        if(event.getInteraction().getUser().getAsMention().equals(username)) cmdCoinFlip.response(event, Integer.parseInt(bet.replace(" ", "")), username, chosen);
    }

    private void rps(ButtonInteractionEvent event, String chosen){ 
        String[] args = event.getMessage().getActionRows().get(0).toString().split(",");
        String username = args[0].replace("ActionRow([B:Rock(rps_rock-", "").replace(")", "");
        String bet = args[1].replace("B:Paper(rps_paper-", "").replace(")", "").replace(" ", "");

        if(event.getInteraction().getUser().getAsMention().equals(username)) cmdRockPaperScissors.response(event, Integer.parseInt(bet), username, chosen);      
    }
}   