package Commands.Games;

import DiscordBot.MySQLcommand;
import DiscordBot.daedalus;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class cmdDiceRoll {

    public static void roll(MessageReceivedEvent event, String username) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args.length < 2) { daedalus.sendMessageInGuild(daedalus.emoji_warning + " Usage: !diceroll <amount> " + daedalus.emoji_warning, event); return; }

        int bet = Integer.parseInt(args[1]);

        if(bet < 0) { daedalus.sendMessageInGuild(daedalus.emoji_warning + " You can't play negative money! " + daedalus.emoji_warning, event); return; }
        if(MySQLcommand.getUserMoney(username) < bet) { daedalus.sendMessageInGuild(daedalus.emoji_warning + " You don't have enough money! " + daedalus.emoji_warning, event); return; }
        if(MySQLcommand.getUserPlayingTime(username) > 0) { daedalus.sendMessageInGuild(daedalus.emoji_alarm_clock + " You have already played, wait another " + MySQLcommand.getUserPlayingTime(username) + " minutes " + daedalus.emoji_alarm_clock, event); return; }

        MySQLcommand.setPlayingTime(username, 15);

        daedalus.sendMessageInGuild(daedalus.emoji_dice + " " + event.getAuthor().getAsMention() + " executed !diceroll " + daedalus.emoji_dice, event);

        int diceRoll = (int) (Math.random() * 6) + 1;
        int diceRollBot = (int) (Math.random() * 6) + 1;

        if(diceRoll > diceRollBot) { win(event, username, bet, diceRollBot, diceRoll); }
        else if(diceRoll == diceRollBot) { draw(event, username, bet, diceRollBot, diceRoll); }
        else { lose(event, username, bet, diceRollBot, diceRoll); }

    }

    private static void win(MessageReceivedEvent event, String username, int bet, int bot, int player) {
    MySQLcommand.addUserMoney(username, bet);
    daedalus.sendMessageInGuild(daedalus.emoji_dice + " You have rolled " + getDiceEmoji(player) + ", the Bot rolled " + getDiceEmoji(player) + ", You have won " + bet*2 + "$" + daedalus.emoji_dice, event);
    }

    private static void lose(MessageReceivedEvent event, String username, int bet, int bot, int player) {
    MySQLcommand.removeUserMoney(username, bet);
    daedalus.sendMessageInGuild(daedalus.emoji_dice + " You have rolled " + getDiceEmoji(player) + ", the Bot rolled " + bot + ", You have lost " + bet + "$" + daedalus.emoji_dice, event);
    }

    private static void draw(MessageReceivedEvent event, String username, int bet, int bot, int player) {
    MySQLcommand.removeUserMoney(username, bet/2);
    daedalus.sendMessageInGuild(daedalus.emoji_dice + " DRAW! You have rolled " + getDiceEmoji(player) + ", the Bot rolled " + bot + ", You have lost " + bet/2 + "$" + daedalus.emoji_dice, event);
    }

    private static String getDiceEmoji(int diceRoll) {
        switch(diceRoll) {
            case 1: return daedalus.emoji_one;
            case 2: return daedalus.emoji_two;
            case 3: return daedalus.emoji_three; 
            case 4: return daedalus.emoji_four; 
            case 5: return daedalus.emoji_five; 
            case 6: return daedalus.emoji_six; 
        }
        return ":0:";
    }
}