package me.Rl242Dev.CommandHandler.MC;

import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Items.Ressource.Material;
import me.Rl242Dev.Classes.Items.Ressource.Ores.*;
import me.Rl242Dev.Classes.Items.Ressource.RessourceUtils;
import me.Rl242Dev.Classes.Items.Ressource.Type;
import me.Rl242Dev.DatabaseManager.DatabaseUtils;
import me.Rl242Dev.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;

/*

@A = Rl242Dev
@U = Mine command
@E = Mine command executed when user type .mine

 */

public class MineHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        User user = event.getAuthor();
        // int id = Integer.valueOf(user.getId());
        if (user.equals(event.getJDA().getSelfUser())){
            return;
        }

        Message message = event.getMessage();
        if(message.getContentRaw().equals(".mine")){
            MessageChannelUnion channel = event.getChannel();

            /* Retrieve Pickaxe */

            /*

            Item pickaxe;
            try {
                 pickaxe = DatabaseUtils.getPickaxeFromUUID(id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

             */

            /* Handle null pickaxe */

            /*
            if(pickaxe == null){
                channel.sendMessageEmbeds(Utils.StartErrorEmbed(user.getId()).build()).queue();
                return;
            }

             */

            Item pickaxe = new Item("Test", Material.WOOD, Type.PICKAXE, "10686170366929921314");

            /* Generate Ores*/
            Map<Ores, Integer> ressources = RessourceUtils.getRessourceForPickaxe(pickaxe.getMaterial());

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setFooter("DisCraft");
            embedBuilder.setColor(Color.green);
            embedBuilder.setTitle("Mining action");
            embedBuilder.addField(Stone.getEmojiID(), Utils.IntToString(ressources.get(Ores.STONE)), true);
            embedBuilder.addField(Coal.getEmojiID(), Utils.IntToString(ressources.get(Ores.COAL)), true);
            embedBuilder.addField(Iron.getEmojiID(), Utils.IntToString(ressources.get(Ores.IRON)), true);
            embedBuilder.addField(Gold.getEmojiID(), Utils.IntToString(ressources.get(Ores.GOLD)), true);
            embedBuilder.addField(Diamond.getEmojiID(), Utils.IntToString(ressources.get(Ores.DIAMOND)), true);
            embedBuilder.addField(Obsidian.getEmojiID(), Utils.IntToString(ressources.get(Ores.OBSIDIAN)), true);


            embedBuilder.setTimestamp(Instant.now());

            channel.sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }
}
