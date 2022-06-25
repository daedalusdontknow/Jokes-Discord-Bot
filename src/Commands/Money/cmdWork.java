package Commands.Money;

import DiscordBot.Functions;
import DiscordBot.MySQLcommand;
import DiscordBot.daedalus;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class cmdWork {

    public static void work(MessageReceivedEvent event, String username) {
        if(!Functions.checkStatements(event)) return;
        if(MySQLcommand.getUserWorkingTime(username) > 0) { daedalus.sendMessageInGuild("You have already worked, wait another " + MySQLcommand.getUserWorkingTime(username) + " minutes", event); return; }

        int wage = (int) (Math.random() * 28) + 10;
        
        MySQLcommand.setWorkingTime(username, 60);
        daedalus.sendMessageInGuild(daedalus.emoji_moneybag + " You have earned " + wage + " $ " + daedalus.emoji_moneybag, event);
        MySQLcommand.addUserMoney(username, wage);

    }
}