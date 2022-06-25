package Commands.Money;

import DiscordBot.Functions;
import DiscordBot.MySQLcommand;
import DiscordBot.daedalus;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class cmdBalance {

    public static void money(MessageReceivedEvent event, String username) {
        if(!Functions.checkStatements(event)) return;

        int money = MySQLcommand.getUserMoney(username);
        daedalus.sendMessageInGuild( daedalus.emoji_coin + " you've got " + money + " $ " + daedalus.emoji_coin, event);
 
    }
}