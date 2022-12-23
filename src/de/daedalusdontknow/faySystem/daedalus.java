package de.daedalusdontknow.faySystem;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.List;

public class daedalus {

    public static String MySQLhost = "localhost";
    public static String MySQLport = "3306";
    public static String MySQLdb = "elonv2";
    public static String MySQLuser = "root";
    public static String MySQLpass = "";
    public static String emoji_warning = ":warning:";
    public static String emoji_gear = ":gear:";
    public static String emoji_upside_down = ":upside_down:";
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

    public static void sendReply(String message, SlashCommandInteractionEvent event) {
        event.reply(message).setEphemeral(false).queue();
    }

    public static void sendReplyEmbed(EmbedBuilder embed, SlashCommandInteractionEvent event) {
        event.replyEmbeds(embed.build()).setEphemeral(false).queue();
    }

    public static void sendModals(Modal modal, SlashCommandInteractionEvent event) {
        event.replyModal(modal).queue();
    }

    public static void sendReplyWithButtons(String message, List<Button> buttons, SlashCommandInteractionEvent event) {
        event.reply(message).addActionRow(buttons).setEphemeral(false).queue();
    }

    public static void sendReplyEmbedWithButtons(EmbedBuilder embed, List<Button> buttons, SlashCommandInteractionEvent event) {
        event.replyEmbeds(embed.build()).addActionRow(buttons).setEphemeral(false).queue();
    }
}
