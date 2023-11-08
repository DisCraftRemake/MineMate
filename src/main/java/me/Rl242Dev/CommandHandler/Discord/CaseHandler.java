package me.Rl242Dev.CommandHandler.Discord;

import me.Rl242Dev.MineMate;
import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.Classes.Cases.CaseUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CaseHandler {
    
    public static boolean handle(MessageReceivedEvent event){
        User user = event.getAuthor();
        String uuid = user.getId();
        if (user.equals(event.getJDA().getSelfUser())){
            return false;
        }

        Player player = new Player(uuid);

        Message message = event.getMessage();
        MessageChannelUnion channel = event.getChannel();

        if(message.getContentRaw().equals(".case open")){
            Object loot = MineMate.getInstance().getCases().get(0).open(player.getUuid());
            if(loot == null){
                return false;
            }
            EmbedBuilder embedBuilder = CaseUtils.process(player.getUuid(), loot.toString(), event.getMember(), event.getGuild());
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return true;
        }

        return false;
    }
}
