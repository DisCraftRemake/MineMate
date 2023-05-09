package me.Rl242Dev.classes.CommandHandler;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Mine extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        User user = event.getAuthor();
        Message message = event.getMessage();
        MessageChannelUnion channel = event.getChannel();

        channel.sendMessage("HELLO WORLD!");
        System.out.println(message.getContentRaw());
    }
}
