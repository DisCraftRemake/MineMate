package me.Rl242Dev.CommandHandler.Discord;

import me.Rl242Dev.Classes.Utils.Coin;
import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.MineMate;
import me.Rl242Dev.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.Instant;
import java.awt.Color;
import java.util.Map;

/*

@A = Rl242Dev
@U = Utils
@E = Class for the UtilsHandler, used when users want to display profil, balance, leaderboard, help

 */

public class UtilsHandler  {

    public static boolean handle(MessageReceivedEvent event){
        User user = event.getAuthor();
        String uuid = user.getId();
        if (user.equals(event.getJDA().getSelfUser())){
            return false;
        }

        Player player = new Player(uuid);

        Message message = event.getMessage();
        MessageChannelUnion channel = event.getChannel();

        if(message.getContentRaw().equals(".bal") || message.getContentRaw().equals(".balance")){
            if(MineMate.debug){
                MineMate.getLogger().appendLogger(player.getUuid()+" Issued .balance");
                MineMate.getLogger().send();
            }

            EmbedBuilder embedBuilder = new EmbedBuilder();
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("<@");
            stringBuilder.append(player.getUuid());
            stringBuilder.append(">");
            stringBuilder.append(" ➔ You have a total of : ");
            stringBuilder.append(player.getBalance());
            stringBuilder.append(" ").append(Coin.getEmojiID());

            embedBuilder.setTitle(Coin.getEmojiID() + " Balance Action");
            embedBuilder.setColor(Color.green);

            embedBuilder.setDescription(stringBuilder.toString());

            embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
            embedBuilder.setTimestamp(Instant.now());

            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return true;
        }
        if(message.getContentRaw().equals(".profil")){
            if(MineMate.debug){
                MineMate.getLogger().appendLogger(player.getUuid()+" Issued .profil");
                MineMate.getLogger().send();
            }

            channel.sendMessageEmbeds(player.getProfil().build()).queue();
            return true;
        }
        if(message.getContentRaw().equals(".help")){
            if(MineMate.debug){
                MineMate.getLogger().appendLogger(player.getUuid()+" Issued .help");
                MineMate.getLogger().send();
            }

            EmbedBuilder embedBuilder = new EmbedBuilder();
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("<@");
            stringBuilder.append(player.getUuid());
            stringBuilder.append(">");
            stringBuilder.append(" ➔ Help : <#"+MineMate.getConfigManager().getString("channels.help")+">");

            embedBuilder.setTitle(" Help Action");
            embedBuilder.setColor(Color.green);

            embedBuilder.setDescription(stringBuilder.toString());

            embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
            embedBuilder.setTimestamp(Instant.now());

            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return true;
        }
        if(message.getContentRaw().equals(".leaderboard")){
            if(MineMate.debug){
                MineMate.getLogger().appendLogger(player.getUuid()+" Issued .leaderboard");
                MineMate.getLogger().send();
            }

            Map<String, Integer> leaderbord = MineMate.getInstance().getDatabaseManager().getLeaderboard();

            EmbedBuilder embedBuilder = new EmbedBuilder();
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("<@");
            stringBuilder.append(player.getUuid());
            stringBuilder.append(">");
            stringBuilder.append(" ➔ LeaderBoard :");

            embedBuilder.setTitle(Coin.getEmojiID() + " LeaderBoard Action");
            embedBuilder.setColor(Color.green);
            embedBuilder.setDescription(stringBuilder.toString());

            int iterator = 1;
            for(String userLeaderBoard : leaderbord.keySet()){
                String nameBuilder = MineMate.getBot().retrieveUserById(userLeaderBoard).complete().getName();
                MineMate.getBot().retrieveUserById(userLeaderBoard).complete().getName();

                embedBuilder.addField(
                        Coin.getEmojiID()+" "+Utils.IntToString(iterator)+" | "+ nameBuilder,
                        Utils.IntToString(leaderbord.get(userLeaderBoard)),
                        true
                );
                iterator++;
            }

            embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
            embedBuilder.setTimestamp(Instant.now());

            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return true;
        }
		return false;
    }
}
