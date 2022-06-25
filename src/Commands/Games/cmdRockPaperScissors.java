package Commands.Games;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

import DiscordBot.MySQLcommand;
import DiscordBot.daedalus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ContextException;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class cmdRockPaperScissors {

    public static void rps(MessageReceivedEvent event, String username) {

        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args.length < 2) { daedalus.sendMessageInGuild(daedalus.emoji_warning + " Usage: !rps <amount> " + daedalus.emoji_warning, event); return; }

        int bet = Integer.parseInt(args[1]);

        if(bet < 0) { daedalus.sendMessageInGuild(daedalus.emoji_warning + " You can't play negative money! " + daedalus.emoji_warning, event); return; }
        if(MySQLcommand.getUserMoney(username) < bet) { daedalus.sendMessageInGuild(daedalus.emoji_warning + " You don't have enough money! " + daedalus.emoji_warning, event); return; }
        if(MySQLcommand.getUserPlayingTime(username) > 0) { daedalus.sendMessageInGuild(daedalus.emoji_alarm_clock + " You have already played, wait another " + MySQLcommand.getUserPlayingTime(username) + " minutes " + daedalus.emoji_alarm_clock, event); return; }

        List<Button> buttons = new ArrayList<>();

        buttons.add(Button.primary("rps_rock-" + event.getAuthor().getAsMention(), "Rock").withEmoji(Emoji.fromMarkdown(daedalus.unicode_emoji_rock)));
        buttons.add(Button.primary("rps_paper-" + bet, "Paper").withEmoji(Emoji.fromMarkdown(daedalus.unicode_emoji_paper)));
        buttons.add(Button.primary("rps_scissor", "Scissor").withEmoji(Emoji.fromMarkdown(daedalus.unicode_emoji_scissors)));

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Rock Paper Scissors");
        embed.setDescription("Currently playing " + event.getAuthor().getAsMention() + "\n" +
            "React with " + daedalus.emoji_Rock + " to get rock\n" +
            "React with " + daedalus.emoji_Paper + " to get paper\n" +
            "React with " + daedalus.emoji_Scissors + " to get scissor\n" +
            "You have played **" + bet + "** coins\n" +
            "You have to react within 10 seconds or the game will be canceled");
        embed.setColor(Color.MAGENTA);

        daedalus.sendMessageInGuild(daedalus.emoji_right_facing_fist + " " + event.getAuthor().getAsMention() + " executed !rps " + daedalus.emoji_left_facing_fist, event);
    
            try {
                daedalus.sendanddeleteEmbedWithButtons(embed, buttons, 10, event);
            } catch (ErrorResponseException | ContextException | Error e) {}
    }

	public static void response(ButtonInteractionEvent event, int bet, String username, String chosen) {

        if(MySQLcommand.getUserPlayingTime(username) > 0) { event.getChannel().sendMessage(daedalus.emoji_alarm_clock + " You have already played, wait another " + MySQLcommand.getUserPlayingTime(username) + " minutes " + daedalus.emoji_alarm_clock); return; }
        MySQLcommand.setPlayingTime(username, 15);

        int rps_rnd = (int) (Math.random() * 3);

        String rps_toString = "";
        switch (rps_rnd) { case 0: rps_toString = "rock"; break; case 1: rps_toString = "paper"; break; case 2: rps_toString = "scissor"; break; };

        String setEmojiPlayer = ":0:";
        switch(chosen){ case "rock": setEmojiPlayer = daedalus.emoji_Rock; break; case "paper": setEmojiPlayer = daedalus.emoji_Paper; break; case "scissor": setEmojiPlayer = daedalus.emoji_Scissors; break; }
        String setEmojiBot = ":0:";
        switch(rps_toString){ case "rock": setEmojiBot = daedalus.emoji_Rock; break; case "paper": setEmojiBot = daedalus.emoji_Paper; break; case "scissor": setEmojiBot = daedalus.emoji_Scissors; break; }

        if(chosen.equals("scissor") && rps_toString.equals("paper")){ win(event, bet, username, setEmojiPlayer, setEmojiBot); }
        else if(chosen.equals("rock") && rps_toString.equals("scissor")){ win(event, bet, username, setEmojiPlayer, setEmojiBot); }
        else if(chosen.equals("paper") && rps_toString.equals("rock")){ win(event, bet, username, setEmojiPlayer, setEmojiBot); }
        else if(chosen.equals(rps_toString)){ draw(event, bet, username, setEmojiPlayer, setEmojiBot); }
        else{ lose(event, bet, username, chosen, rps_toString); }
    }

    private static void win(ButtonInteractionEvent event, int bet, String username, String chosen, String botchosen) {
        MySQLcommand.addUserMoney(username, bet);
        event.getChannel().sendMessage(daedalus.emoji_right_facing_fist + " You have chosen " + chosen + " The bot chose " + botchosen + "," + username + " You have won " + bet*2 + " $ " + daedalus.emoji_left_facing_fist).queue();
    }

    private static void lose(ButtonInteractionEvent event, int bet, String username, String chosen, String botchosen) {
     MySQLcommand.removeUserMoney(username, bet);
     event.getChannel().sendMessage(daedalus.emoji_right_facing_fist + " You have chosen " + chosen + ", the bot chose " + botchosen + "," + username + " You have lost " + bet + " $ " + daedalus.emoji_left_facing_fist).queue();
    }

    private static void draw(ButtonInteractionEvent event, int bet, String username, String chosen, String botchosen) {
    MySQLcommand.removeUserMoney(username, bet/2);
    event.getChannel().sendMessage(daedalus.emoji_right_facing_fist + " DRAW! You have chosen " + chosen + ", the bot chose " + botchosen + "," + username + " You have lost " + bet/2 + " $ " + daedalus.emoji_left_facing_fist).queue(); 
    }
}