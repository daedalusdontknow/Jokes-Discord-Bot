package Commands.Money;

import DiscordBot.Functions;
import DiscordBot.MySQLcommand;
import DiscordBot.daedalus;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class cmdPay {

    public static void pay(MessageReceivedEvent event, String username) {
        if(!Functions.checkStatements(event)) return;
        
        String[] args = event.getMessage().getContentRaw().split(" ");

        // args[0] = !pay args[1] = receiver args[2] = amount
        if(args.length < 3) { daedalus.sendMessageInGuild("Usage: !pay <@user> <amount>", event); return; }
        
        if(!(MySQLcommand.checkUserExists(args[1]))) { daedalus.sendMessageInGuild(daedalus.emoji_warning + " User does not exist " + daedalus.emoji_warning, event); return; }
        if(Integer.parseInt(args[2]) < 0) { daedalus.sendMessageInGuild(daedalus.emoji_warning + " You can't pay negative money! " + daedalus.emoji_warning, event); return; }
        if(MySQLcommand.getUserMoney(username) < Integer.parseInt(args[2])) { daedalus.sendMessageInGuild(daedalus.emoji_warning + " You don't have enough money! " + daedalus.emoji_warning, event); return; }
        
        //MySQLcommand.transferUserMoney(String username, String receiver, int money)
        //Noch eine hübsche nachricht senden
        MySQLcommand.transferUserMoney(username, args[1], Integer.parseInt(args[2])); 
        daedalus.sendMessageInGuild( daedalus.emoji_money_with_wings + " You payed " + args[2] + " $ to " + args[1] + " " + daedalus.emoji_money_with_wings , event);
    }
}