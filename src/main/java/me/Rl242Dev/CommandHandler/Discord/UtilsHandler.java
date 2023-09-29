package me.Rl242Dev.CommandHandler.Discord;

import me.Rl242Dev.Classes.Items.Ressource.ResourceUtils;
import me.Rl242Dev.Classes.Levels.Ranks;
import me.Rl242Dev.Classes.Levels.RanksUtils;
import me.Rl242Dev.Classes.Utils.Coin;
import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.Classes.Utils.Emoji;
import me.Rl242Dev.MineMate;
import me.Rl242Dev.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;
import java.awt.Color;
import java.util.Map;
import java.util.Objects;

/*

@A = Rl242Dev
@U = Utils
@E = Class for the UtilsHandler, used when users want to display profil, balance, leaderboard, help

 */

public class UtilsHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        User user = event.getAuthor();
        String uuid = user.getId();
        if (user.equals(event.getJDA().getSelfUser())){
            return;
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
        }
        if(message.getContentRaw().equals(".profil")){
            MineMate.getLogger().appendLogger(player.getUuid()+" Issued .profil");
            MineMate.getLogger().send();

            EmbedBuilder embedBuilder = new EmbedBuilder();
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("<@");
            stringBuilder.append(player.getUuid());
            stringBuilder.append(">");
            stringBuilder.append(" ➔ Profil :");

            embedBuilder.setTitle(":bust_in_silhouette: Profil Action");
            embedBuilder.setColor(Color.green);

            embedBuilder.setDescription(stringBuilder.toString());

            embedBuilder.addField(Coin.getEmojiID() + " **Balance** :", ResourceUtils.PriceToString(player.getBalance()),false);
            embedBuilder.addField(Emoji.getXpEmoji() + " **Level** :", Utils.IntToString(player.getLevel()), false);
            embedBuilder.addField(Emoji.getNametagEmoji() + " **Rank** :", Objects.requireNonNull(RanksUtils.presentRank(Ranks.valueOf(player.getRank()))), false);
            embedBuilder.addField(Emoji.getTotemEmoji()+ " **Prestige** :", Utils.IntToString(player.getPrestige()), false);

            embedBuilder.addField(Emoji.getPickaxeEmoji() + " **Pickaxe** :", player.getPickaxe().getDisplayName(), false);
            embedBuilder.addField(Emoji.getHoeEmoji() + " **Hoe** :", player.getHoe().getDisplayName(), false);
            // Axe
            // Pets

            embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
            embedBuilder.setTimestamp(Instant.now());

            channel.sendMessageEmbeds(embedBuilder.build()).queue();
        }
        if(message.getContentRaw().equals(".help")){
            MineMate.getLogger().appendLogger(player.getUuid()+" Issued .help");
            MineMate.getLogger().send();

            if(event.getMember().hasPermission(Permission.ADMINISTRATOR)){
                event.getGuild()
                        .getTextChannelById(MineMate.getConfigManager().getString("channels.help"))
                        .getHistory()
                        .retrievePast(1)
                        .queue(history -> {
                            int messageCount = history.size();

                            if (messageCount > 0) {
                                // basic help
                            } else {
                                // admin help
                            }
                        });
            }else{
                EmbedBuilder embedBuilder = new EmbedBuilder();
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("<@");
                stringBuilder.append(player.getUuid());
                stringBuilder.append(">");
                stringBuilder.append(" ➔ Help : <#"+MineMate.getConfigManager().getString("help")+">");

                embedBuilder.setTitle(" Help Action");
                embedBuilder.setColor(Color.green);

                embedBuilder.setDescription(stringBuilder.toString());

                embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
                embedBuilder.setTimestamp(Instant.now());

                channel.sendMessageEmbeds(embedBuilder.build()).queue();
            }
        }
        if(message.getContentRaw().equals(".leaderboard")){
            MineMate.getLogger().appendLogger(player.getUuid()+" Issued .leaderboard");
            MineMate.getLogger().send();

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
        }
    }
}
