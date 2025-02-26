package me.Rl242Dev.CommandHandler.Minecraft.Actions;

import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Items.Ressource.Ores.*;
import me.Rl242Dev.Classes.Items.Ressource.ResourceUtils;
import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.Classes.Utils.Emoji;
import me.Rl242Dev.DisCraft;
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
@U = Mine command
@E = Mine command executed when user type .mine

TODO:
 Pet luck (More luck to have obisidian and diamond), Pet quantity (More Item when mining (Iron, Gold), Pet Selling (Price = More) | Global actions

 */

public class MineHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        User user = event.getAuthor();
        String uuid = user.getId();
        if (user.equals(event.getJDA().getSelfUser())){
            return;
        }

        Player player = new Player(uuid);

        Message message = event.getMessage();
        if(message.getContentRaw().equals(".mine") || message.getContentRaw().equals(".m")){
            MessageChannelUnion channel = event.getChannel();
            StringBuilder description = new StringBuilder();

            /* Retrieve Pickaxe */

            Item pickaxe = player.getPickaxe();

            /* Handle null pickaxe */

            if(pickaxe == null){
                channel.sendMessageEmbeds(Utils.StartErrorEmbed(user.getId()).build()).queue();
                return;
            }

            /* Generate Ores*/
            Map<Ores, Integer> resources = ResourceUtils.getResourceForPickaxe(pickaxe.getMaterial());

            /* Description */
            description.append("<@");
            description.append(user.getId());
            description.append(">");
            description.append(" ➔ You have mined using : ");
            description.append(pickaxe.getEmojiID()).append(" ").append(pickaxe.getDisplayName());

            /* Embed */

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(Color.green);
            embedBuilder.setTitle(Emoji.getPickaxeEmoji() + " Mining action");
            embedBuilder.setDescription(description.toString());

            embedBuilder.addField(Stone.getEmojiID(), Utils.IntToString(resources.get(Ores.STONE)), true);
            embedBuilder.addField(Coal.getEmojiID(), Utils.IntToString(resources.get(Ores.COAL)), true);
            embedBuilder.addField(Iron.getEmojiID(), Utils.IntToString(resources.get(Ores.IRON)), true);
            embedBuilder.addField(Gold.getEmojiID(), Utils.IntToString(resources.get(Ores.GOLD)), true);
            embedBuilder.addField(Diamond.getEmojiID(), Utils.IntToString(resources.get(Ores.DIAMOND)), true);
            embedBuilder.addField(Obsidian.getEmojiID(), Utils.IntToString(resources.get(Ores.OBSIDIAN)), true);

            embedBuilder.setTimestamp(Instant.now());
            embedBuilder.setFooter("DisCraft");

            channel.sendMessageEmbeds(embedBuilder.build()).queue();

            DisCraft.getInstance().getDatabaseManager().saveOresToUUID(uuid, resources);
        }
    }
}
