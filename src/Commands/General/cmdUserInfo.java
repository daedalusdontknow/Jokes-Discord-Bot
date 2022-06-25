package Commands.General;

import DiscordBot.Functions;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class cmdUserInfo {
    
    public static void info(MessageReceivedEvent event, String[] args){
        if(!Functions.checkStatements(event)) return;
        
        if(args.length == 2) { other(event, args[1]); return;}
        self(event, event.getAuthor().getAsMention());
    }

    private static void self(MessageReceivedEvent event, String username){

    }

    private static void other(MessageReceivedEvent event, String username){

    }
}
