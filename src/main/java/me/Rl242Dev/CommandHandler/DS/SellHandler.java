package me.Rl242Dev.CommandHandler.DS;

import me.Rl242Dev.Classes.Items.Ressource.Ores.Ores;
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

public class SellHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        User user = event.getAuthor();
        String uuid = user.getId();
        if (user.equals(event.getJDA().getSelfUser())){
            return;
        }

        Message message = event.getMessage();
        MessageChannelUnion channel = event.getChannel();

        if(message.getContentRaw().equals(".sell")){
            String[] args = message.getContentRaw().split(" ");
            if(args.length != 2){
                EmbedBuilder embedBuilder = new EmbedBuilder();
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("<@");
                stringBuilder.append(user.getId());
                stringBuilder.append(">");
                stringBuilder.append(" ➔ You must specify an ore or sell all : .sell [Ore] | .sell all");

                embedBuilder.setColor(Color.green);
                embedBuilder.setTitle("<:emerald:1107339353727979652> Sell Action");
                embedBuilder.setDescription(stringBuilder.toString());

                embedBuilder.setTimestamp(Instant.now());
                embedBuilder.setFooter("DisCraft");

                channel.sendMessageEmbeds(embedBuilder.build()).queue();
            }
            try {
                if(args[1].equals("all")){
                    Map<Ores, Integer> resources = DatabaseUtils.GetRessourcesFromUUID(uuid);


                }
            }catch (IndexOutOfBoundsException e){
                e.printStackTrace();
            }


        }
    }
}
