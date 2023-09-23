package me.Rl242Dev.CommandHandler.Discord;

import me.Rl242Dev.Classes.Levels.RanksUtils;
import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.Classes.Utils.Emoji;
import me.Rl242Dev.DisCraft;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;

/*

@A = Rl242Dev
@U = Action
@E = Class for the LevelHandler, used when users level up

 */

public class LevelHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        User user = event.getAuthor();
        String uuid = user.getId();
        if (user.equals(event.getJDA().getSelfUser())){
            return;
        }

        Message message = event.getMessage();
        MessageChannelUnion channel = event.getChannel();

        Player player = new Player(uuid);

        if(message.getContentRaw().equals(".levelUP") || message.getContentRaw().equals(".lUP") || message.getContentRaw().equals(".level")){
            int level = player.getLevel();
            if(level == 301){
                EmbedBuilder embedBuilder = new EmbedBuilder();
                StringBuilder description = new StringBuilder();

                description.append("<@");
                description.append(user.getId());
                description.append(">");
                description.append(" ➔ You can't level up above level 301");
                description.append(player.getLevel() + 1);

                embedBuilder.setTitle(Emoji.getXpEmoji() + " Level Up Action");
                embedBuilder.setColor(Color.green);

                embedBuilder.setDescription(description.toString());

                embedBuilder.setTimestamp(Instant.now());
                embedBuilder.setFooter("DisCraft");

                channel.sendMessageEmbeds(embedBuilder.build()).queue();
                return;
            }
            int balance = player.getBalance();

            int price = RanksUtils.getPriceForLevelUp(level);

            if(price < balance){
                DisCraft.getInstance().getDatabaseManager().saveLevelToUUID(uuid, level + 1);
                DisCraft.getInstance().getDatabaseManager().removeBalanceFromUUID(uuid, price);

                EmbedBuilder embedBuilder = new EmbedBuilder();
                StringBuilder description = new StringBuilder();

                description.append("<@");
                description.append(user.getId());
                description.append(">");
                description.append(" ➔ You have leveled up to : ");
                description.append(player.getLevel() + 1);
                description.append(". Your balance is : ");
                description.append(player.getBalance() - price);

                embedBuilder.setTitle(Emoji.getXpEmoji() + " Level Up Action");
                embedBuilder.setColor(Color.green);

                embedBuilder.setDescription(description.toString());

                embedBuilder.setTimestamp(Instant.now());
                embedBuilder.setFooter("DisCraft");

                channel.sendMessageEmbeds(embedBuilder.build()).queue();
            }else {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                StringBuilder description = new StringBuilder();

                description.append("<@");
                description.append(user.getId());
                description.append(">");
                description.append(" ➔ You don't have enough coins to level up");

                embedBuilder.setTitle(Emoji.getXpEmoji() + " Level Up Action");
                embedBuilder.setColor(Color.green);

                embedBuilder.setDescription(description.toString());

                embedBuilder.setTimestamp(Instant.now());
                embedBuilder.setFooter("DisCraft");

                channel.sendMessageEmbeds(embedBuilder.build()).queue();
            }
        }
    }
}
