package Commands.General;

import DiscordBot.MySQLcommand;
import DiscordBot.daedalus;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class cmdDeleteUser {
    public static void delete(String username, MessageReceivedEvent event){
        if(!MySQLcommand.checkUserExists(username)) { daedalus.sendMessageInGuild("User " + username + " doesn't exsist", event); return; }

        MySQLcommand.deleteUser(username);
        daedalus.sendMessageInGuild("User " + username + " Deleted!", event);
    }
}