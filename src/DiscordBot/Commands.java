package DiscordBot;

import Commands.Games.*;
import Commands.General.*;
import Commands.Money.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (!event.getMessage().getContentRaw().startsWith(daedalus.Prefix)) return;

        //----------------------------------------------------------------------------------------------------------------------
        //                                      General  Commands
        //----------------------------------------------------------------------------------------------------------------------

        if(args[0].equalsIgnoreCase(daedalus.Prefix+ "create-user"))                                                    { cmdCreateUser.create(event.getAuthor().getAsMention(), event); }
        if(args[0].equalsIgnoreCase(daedalus.Prefix+ "delete-user"))                                                    { cmdDeleteUser.delete(event.getAuthor().getAsMention(), event); }
        if(args[0].equalsIgnoreCase(daedalus.Prefix + "help"))                                                          { cmdHelp.help(event, args); }
        if(args[0].equalsIgnoreCase(daedalus.Prefix + "userinfo") || args[0].equalsIgnoreCase(daedalus.Prefix + "ui"))  { cmdUserInfo.info(event, args); }
        if(args[0].equalsIgnoreCase(daedalus.Prefix + "invite"))                                                        { cmdInvite.send(event); } 

        //----------------------------------------------------------------------------------------------------------------------
        //                                      Money  Commands
        //----------------------------------------------------------------------------------------------------------------------

        if(args[0].equalsIgnoreCase(daedalus.Prefix + "balance") || args[0].equalsIgnoreCase(daedalus.Prefix + "bal"))   { cmdBalance.money(event, event.getAuthor().getAsMention()); }
        if(args[0].equalsIgnoreCase(daedalus.Prefix + "Work"))                                                           { cmdWork.work(event, event.getAuthor().getAsMention()); }
        if(args[0].equalsIgnoreCase(daedalus.Prefix + "Top"))                                                            { cmdTop.top(event, event.getAuthor().getAsMention()); }
        if(args[0].equalsIgnoreCase(daedalus.Prefix + "pay"))                                                            { cmdPay.pay(event, event.getAuthor().getAsMention()); }

        //----------------------------------------------------------------------------------------------------------------------
        //                                      Games  Commands
        //----------------------------------------------------------------------------------------------------------------------

        if(args[0].equalsIgnoreCase(daedalus.Prefix + "DiceRoll") || args[0].equalsIgnoreCase(daedalus.Prefix+ "dr"))       { cmdDiceRoll.roll(event, event.getAuthor().getAsMention()); }
        if(args[0].equalsIgnoreCase(daedalus.Prefix+ "CoinFlip") || args[0].equalsIgnoreCase(daedalus.Prefix+ "cf"))        { cmdCoinFlip.flip(event, event.getAuthor().getAsMention()); }
        if(args[0].equalsIgnoreCase(daedalus.Prefix + "rps"))                                                               { cmdRockPaperScissors.rps(event, event.getAuthor().getAsMention()); }
        if(args[0].equalsIgnoreCase(daedalus.Prefix + "NG"))                                                                { cmdNumberGuess.NG(event, event.getAuthor().getAsMention()); }

        //----------------------------------------------------------------------------------------------------------------------
        //                                      Other  Commands
        //----------------------------------------------------------------------------------------------------------------------
        if(args[0].equalsIgnoreCase(daedalus.Prefix + "reset"))                                                            { MySQLcommand.setPlayingTime(event.getAuthor().getAsMention(), 0); }

        
    }
}