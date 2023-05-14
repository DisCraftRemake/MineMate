package me.Rl242Dev.classes.CommandHandler.MC;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;

import java.awt.*;
import java.time.Instant;

/*

@A = Rl242Dev
@U = Mine command
@E = Mine command executed when user type .mine

 */

public class Mine extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        User user = event.getAuthor();
        if (user.equals(event.getJDA().getSelfUser())){
            return;
        }

        Message message = event.getMessage();
        if(message.getContentRaw().equals(".mine")){
            MessageChannelUnion channel = event.getChannel();

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor("DisCraft", "https://github.com/Rl242Dev/DisCraft", event.getJDA().getSelfUser().getAvatarUrl());
            embedBuilder.setColor(Color.green);
            embedBuilder.setTitle("Mining action");
            embedBuilder.setTimestamp(Instant.now());
            embedBuilder.addField("Status :", "On going", false); // Get Ressource for pickaxe

            channel.sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }
}
