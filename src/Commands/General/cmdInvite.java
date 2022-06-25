package Commands.General;

import java.util.ArrayList;
import java.util.List;

import DiscordBot.daedalus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class cmdInvite {
    
    public static void send(MessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(daedalus.emoji_gear + " Invite our bot " + daedalus.emoji_gear, daedalus.Link);
        eb.addField("Nornir bot", "Connect your server \n with thousands of others", false);
        eb.setThumbnail(event.getJDA().getSelfUser().getEffectiveAvatarUrl());

        List<Button> buttons = new ArrayList<>();
        buttons.add(Button.link(daedalus.Link, "Invite"));

        daedalus.sendEmbedInGuildWithButtons(eb, buttons, event);
    }
}