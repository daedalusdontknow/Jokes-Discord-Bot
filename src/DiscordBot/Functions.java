package DiscordBot;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Functions {
    
    //if(!Functions.checkStatements(event)) return;
    public static boolean checkStatements(MessageReceivedEvent event) {
        boolean statement = false;
        if(event.isFromType(ChannelType.PRIVATE)) { daedalus.sendMessageInGuild(daedalus.emoji_warning + " You can't use the bot in private messages but we are working on it (!help Privat)! " + daedalus.emoji_warning, event); }
        else if(!MySQLcommand.checkUserExists(event.getAuthor().getAsMention())) { daedalus.sendMessageInGuild(daedalus.emoji_warning + " You are not registered yet! Use !create-user " + daedalus.emoji_warning, event); }
        else statement = true;

        return statement;
    }

}