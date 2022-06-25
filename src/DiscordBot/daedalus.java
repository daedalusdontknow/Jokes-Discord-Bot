package DiscordBot;

import java.util.List;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ContextException;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class daedalus {
    
    static String Prefix = "!";
    static String Version = "B 0.0.1";

    public static String Link = "https://github.com/daedalusdontknow";

    static String MySQLhost = "localhost";
    static String MySQLport = "3306";
    static String MySQLdb = "Joke";
    static String MySQLuser = "JokeBot";
    static String MySQLpass = "Joke";

    static int KingdomMaxUsers = 2;

    public static String getPrefix() {
        return Prefix;
    }

    public static void sendMessageInGuild(String message, MessageReceivedEvent event) { event.getMessage().reply(message).queue(); }
    public static void sendMessageInGuildWithButtons(String message, List<Button> buttons, MessageReceivedEvent event) { event.getMessage().reply(message).setActionRow(buttons).queue(); }
    public static void sendEmbedInGuild(EmbedBuilder message, MessageReceivedEvent event) { 
        message.setFooter("Joke - Developed by Moonfire and daedalus", event.getJDA().getSelfUser().getEffectiveAvatarUrl());
        event.getChannel().sendMessageEmbeds(message.build()).queue(); 
    }
    public static void sendEmbedInGuildWithButtons(EmbedBuilder message, List<Button> buttons, MessageReceivedEvent event) { 
        message.setFooter("Joke - Developed by Moonfire and daedalus", event.getJDA().getSelfUser().getEffectiveAvatarUrl());
        event.getChannel().sendMessageEmbeds(message.build()).setActionRow(buttons).queue(); 
    }

    public static void sendanddeleteEmbedWithButtons(EmbedBuilder message, List<Button> buttons, int Time, MessageReceivedEvent event) throws ErrorResponseException, ContextException, Error {
        MessageChannel channel = event.getChannel();
        message.setFooter("Joke - Developed by Moonfire and daedalus", event.getJDA().getSelfUser().getEffectiveAvatarUrl());
        channel.sendMessageEmbeds(message.build()).setActionRow(buttons).queue(message1 -> message1.delete().queueAfter(Time, TimeUnit.SECONDS));
    }

    //https://github.com/DV8FromTheWorld/JDA/wiki/Interactions

    public static String emoji_warning = ":warning:";
    public static String emoji_gear = ":gear:";
    public static String emoji_upside_down  = ":upside_down:";
    public static String emoji_1234 = ":1234:";
    public static String emoji_x = ":x:";
    public static String emoji_coin = ":coin:";
    public static String emoji_moneybag = ":moneybag:";
    public static String emoji_money_with_wings = ":money_with_wings:";
    public static String emoji_alarm_clock = ":alarm_clock:";
    public static String emoji_white_check_mark = ":white_check_mark:";
    public static String emoji_cry = ":cry:";
    public static String emoji_thumbsup = ":thumbsup:";
    public static String emoji_Rock = ":rock:";
    public static String emoji_Paper = ":roll_of_paper:";
    public static String emoji_Scissors = ":scissors:";
    public static String emoji_right_facing_fist = ":right_facing_fist:";
    public static String emoji_left_facing_fist = ":left_facing_fist:";
    public static String emoji_dice = ":game_die:";
    public static String emoji_one = ":one:";
    public static String emoji_two = ":two:";
    public static String emoji_three = ":three:";
    public static String emoji_four = ":four:";
    public static String emoji_five = ":five:";
    public static String emoji_six = ":six:";

    public static String unicode_emoji_UpsideDownFace = "U+1F643";
    public static String unicode_emoji_1234 = "U+1F522";
    public static String unicode_emoji_grayX = "U+2716";
    public static String unicode_emoji_rock = "U+1FAA8";
    public static String unicode_emoji_paper = "U+1F9FB";
    public static String unicode_emoji_scissors = "U+2702";
}