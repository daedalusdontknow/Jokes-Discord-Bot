package Commands.General;

import DiscordBot.MySQLcommand;
import DiscordBot.daedalus;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class cmdCreateUser {
    
    public static void create(String username, MessageReceivedEvent event){
        if(MySQLcommand.checkUserExists(username)) { daedalus.sendMessageInGuild("User " + username + " already exists!", event); return; }

        MySQLcommand.createNewUser(username);
        daedalus.sendMessageInGuild("User " + username + " created!", event);
    }
}
