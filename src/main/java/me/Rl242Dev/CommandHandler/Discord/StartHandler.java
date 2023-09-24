package me.Rl242Dev.CommandHandler.Discord;

import me.Rl242Dev.MineMate;
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
@U = Start
@E = Class for the StartHandler, when users start the adventure

 */

public class StartHandler extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        User user = event.getAuthor();
        String uuid = user.getId();
        if (user.equals(event.getJDA().getSelfUser())){
            return;
        }

        Message message = event.getMessage();
        MessageChannelUnion channel = event.getChannel();

        if(message.getContentRaw().equals(".start")){
            if(MineMate.debug){
                MineMate.getLogger().appendLogger(uuid+" Issued .start");
                MineMate.getLogger().send();
            }

            if(!MineMate.getInstance().getDatabaseManager().userExist(uuid)){
                MineMate.getInstance().getDatabaseManager().registerUser(uuid);

                EmbedBuilder embedBuilder = new EmbedBuilder();
                StringBuilder description = new StringBuilder();

                description.append("<@");
                description.append(user.getId());
                description.append(">");
                description.append(" ➔ You have started an adventure, Good Luck. Help : <#"+MineMate.getConfigManager().getString("channels.help")+"> | .h");

                embedBuilder.setTitle("<:pickaxe:1107341471725649990> Start Action");
                embedBuilder.setColor(Color.green);
                embedBuilder.setDescription(description.toString());

                embedBuilder.setTimestamp(Instant.now());
                embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                channel.sendMessageEmbeds(embedBuilder.build()).queue();
            }else {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                StringBuilder description = new StringBuilder();

                description.append("<@");
                description.append(user.getId());
                description.append(">");
                description.append(" ➔ You already have started an adventure. Help : <#"+MineMate.getConfigManager().getString("channels.help")+">");

                embedBuilder.setTitle("<:pickaxe:1107341471725649990> Start Action");
                embedBuilder.setColor(Color.green);

                embedBuilder.setDescription(description.toString());

                embedBuilder.setTimestamp(Instant.now());
                embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                channel.sendMessageEmbeds(embedBuilder.build()).queue();
            }
        }
    }
}
