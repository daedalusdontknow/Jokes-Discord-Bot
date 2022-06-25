package Commands.General;

import java.awt.Color;
import DiscordBot.daedalus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class cmdHelp {
    
    public static void help(MessageReceivedEvent event, String[] args){
        if(!(args.length >1)) {defaultHelp(event); return;}

        switch(args[1].toLowerCase()){
            case "account": accountHelp(event); break;
            case "money": moneyHelp(event); break;
            case "games": gamesHelp(event); break;
            case "kingdom": kingdomHelp(event); break;
            case "other": otherHelp(event); break;
            case "Privat": privatHelp(event); break;
            default: defaultHelp(event);
        }
    }

    private static void defaultHelp(MessageReceivedEvent event){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(daedalus.emoji_gear + " Help " + daedalus.emoji_gear, "");
        embed.setDescription("All commands are listed here:");
        embed.addField("!help", "Display this list", false);
        embed.addField("!help account", "Help for account", false);
        embed.addField("!help money", "Help for money System", false);
        embed.addField("!help games", "Help for games", false);
        embed.addField("!help kingdom", "Help for kingdoms", false);
        embed.addField("!help other", "Help for other things", false);
        embed.setColor(Color.MAGENTA);
        daedalus.sendEmbedInGuild(embed, event);
    }

    private static void accountHelp(MessageReceivedEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(daedalus.emoji_gear + " Account help " + daedalus.emoji_gear, "");
        embed.setDescription("All commands are listed here:");
        embed.addField("!create-user", "Create your account", false);
        embed.addField("!delete-user", "Delete your account", false);
        embed.addField("!Userinfo", "View your personal information or that of other players", false);
        embed.setColor(Color.MAGENTA);
        daedalus.sendEmbedInGuild(embed, event);
    }

    private static void moneyHelp(MessageReceivedEvent event){
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(daedalus.emoji_gear + " Money help " + daedalus.emoji_gear, "");
        embed.setDescription("All commands are listed here:");
        embed.addField("!balance", "Show your balance", false);
        embed.addField("!Work", "Work to get Money", false);
        embed.addField("!Times", "See how long you have to wait until you can Work again", false);
        embed.addField("!Top", "Show the global top Players", false);
        embed.addField("!Pay", "Transfer money to another user", false);
        embed.setColor(Color.MAGENTA);
        daedalus.sendEmbedInGuild(embed, event);
    }

    private static void gamesHelp(MessageReceivedEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle(daedalus.emoji_gear + " Game help " + daedalus.emoji_gear, "");
        embed.setDescription("All Games are listed here:");
        embed.addField("!Times", "See how long you have to wait until you can start the next game", false);
        embed.addField("!DiceRoll", "Roll two dices, if your roll is higher than the bots roll you win.", false);
        embed.addField("!CoinFlip", "Flip a coin and guess how it will land", false);
        embed.addField("!RPS", "Rock paper scissors", false);
        embed.addField("!NG", "Number Guess, guess a number between 1-10, if its the same as the bots choice you win", false);
        embed.setColor(Color.MAGENTA);
        daedalus.sendEmbedInGuild(embed, event);
    }

    private static void kingdomHelp(MessageReceivedEvent event) {
    }

    private static void otherHelp(MessageReceivedEvent event) {
    }

    private static void privatHelp(MessageReceivedEvent event) {
    }
}