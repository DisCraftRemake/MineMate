package me.Rl242Dev.CommandHandler.MC.Actions;

import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Items.Ressource.Harvest.*;
import me.Rl242Dev.Classes.Items.Ressource.ResourceUtils;
import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.Classes.Utils.Emoji;
import me.Rl242Dev.DatabaseManager.DatabaseUtils;
import me.Rl242Dev.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;
import java.util.Map;

/*

@A = Rl242Dev
@U = Harvest command
@E = Harvest command executed when user type .harvest

 */

public class HarvestHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        User user = event.getAuthor();
        String uuid = user.getId();
        if (user.equals(event.getJDA().getSelfUser())){
            return;
        }

        Player player = new Player(uuid);


        Message message = event.getMessage();
        if(message.getContentRaw().equals(".harvest") || message.getContentRaw().equals(".h")){
            MessageChannelUnion channel = event.getChannel();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            StringBuilder stringBuilder = new StringBuilder();

            /* Retrieve Hoe */
            Item hoe = player.getHoe();

            if(hoe == null){
                channel.sendMessageEmbeds(Utils.StartErrorEmbed(user.getId()).build()).queue();
                return;
            }

            Map<Crops, Integer> resources = ResourceUtils.getResourcesForHoe(hoe.getMaterial());

            stringBuilder.append("<@");
            stringBuilder.append(user.getId());
            stringBuilder.append(">");
            stringBuilder.append(" ➔ You have mined using : ");
            stringBuilder.append(hoe.getEmojiID()).append(" ").append(hoe.getDisplayName());

            embedBuilder.setTitle(Emoji.getHoeEmoji() + " Harvesting Action");
            embedBuilder.setColor(Color.green);
            embedBuilder.setDescription(stringBuilder.toString());

            embedBuilder.addField(Wheat.getEmojiID(), Utils.IntToString(resources.get(Crops.WHEAT)), false);
            embedBuilder.addField(Potato.getEmojiID(), Utils.IntToString(resources.get(Crops.POTATO)), false);
            embedBuilder.addField(Carrot.getEmojiID(), Utils.IntToString(resources.get(Crops.CARROT)), false);
            embedBuilder.addField(SugarCane.getEmojiID(), Utils.IntToString(resources.get(Crops.SUGARCANE)), false);

            embedBuilder.setTimestamp(Instant.now());
            embedBuilder.setFooter("DisCraft");

            channel.sendMessageEmbeds(embedBuilder.build()).queue();

            DatabaseUtils.SaveCropsToUUID(uuid, resources);
        }
    }
}
