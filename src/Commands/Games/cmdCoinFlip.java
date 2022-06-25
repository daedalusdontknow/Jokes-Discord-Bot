package Commands.Games;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import DiscordBot.MySQLcommand;
import DiscordBot.daedalus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ContextException;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class cmdCoinFlip {

    public static void flip(MessageReceivedEvent event, String username) {

        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args.length < 2) { daedalus.sendMessageInGuild(daedalus.emoji_warning + " Usage: !coinflip <amount> " + daedalus.emoji_warning, event); return; }

        int bet = Integer.parseInt(args[1]);

        if(bet < 0) { daedalus.sendMessageInGuild(daedalus.emoji_warning + " You can't play negative money! " + daedalus.emoji_warning, event); return; }
        if(MySQLcommand.getUserMoney(username) < bet) { daedalus.sendMessageInGuild(daedalus.emoji_warning + " You don't have enough money! " + daedalus.emoji_warning, event); return; }
        if(MySQLcommand.getUserPlayingTime(username) > 0) { daedalus.sendMessageInGuild(daedalus.emoji_alarm_clock + " You have already played, wait another " + MySQLcommand.getUserPlayingTime(username) + " minutes " + daedalus.emoji_alarm_clock, event); return; }

        List<Button> buttons = new ArrayList<>();
        
        buttons.add(Button.primary("cf_head-" + event.getAuthor().getAsMention(), "Head").withEmoji(Emoji.fromMarkdown(daedalus.unicode_emoji_UpsideDownFace)));
        buttons.add(Button.success("cf_tail-" + bet, "Tail").withEmoji(Emoji.fromMarkdown(daedalus.unicode_emoji_1234)));
 
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Coinflip");
        embed.setDescription("Currently playing " + event.getAuthor().getAsMention() + "\n" +
            "React with " + daedalus.emoji_upside_down + " to get heads\n" +
            "React with " + daedalus.emoji_1234 + " to get tails\n" +
            "You have played **" + bet + "** coins\n" +
            "You have to react within 10 seconds or the game will be canceled");
        embed.setColor(Color.MAGENTA);

        daedalus.sendMessageInGuild(daedalus.emoji_coin + " " + event.getAuthor().getAsMention() + " executed !coinflip " + daedalus.emoji_coin, event);
        try {
            daedalus.sendanddeleteEmbedWithButtons(embed, buttons, 10, event);
        } catch (ErrorResponseException | ContextException e) {}
    }

    public static void response(ButtonInteractionEvent event, int bet, String username, String choice) {

        if(MySQLcommand.getUserPlayingTime(username) > 0) { event.getChannel().sendMessage(daedalus.emoji_alarm_clock + " You have already played, wait another " + MySQLcommand.getUserPlayingTime(username) + " minutes " + daedalus.emoji_alarm_clock); return; }

        MySQLcommand.setPlayingTime(username, 10);

        int coin = (int) (Math.random() * 2);

        String setEmoji = ":0:";
        switch(coin){ case 0: setEmoji = ":upside_down:"; break; case 1: setEmoji = ":1234:"; break; }

        if(choice == "head" && coin == 0) {
            MySQLcommand.addUserMoney(username, bet);
            event.getChannel().sendMessage(daedalus.emoji_coin + " The coin landed on " + setEmoji + "," + username + " You have won " + bet + " $ " + daedalus.emoji_coin).queue();
        } else if(choice == "tail" && coin == 1) {
            MySQLcommand.addUserMoney(username, bet);
            event.getChannel().sendMessage(daedalus.emoji_coin + " The coin landed on " + setEmoji + "," + " " + username + " You have won " + bet + " $ " + daedalus.emoji_coin).queue();
        } else {
            MySQLcommand.removeUserMoney(username, bet);
            event.getChannel().sendMessage(daedalus.emoji_coin + " The coin landed on " + setEmoji + "," + " " + username +  " You have lost " + bet + " $ " + daedalus.emoji_coin).queue();
        }
    }
}
