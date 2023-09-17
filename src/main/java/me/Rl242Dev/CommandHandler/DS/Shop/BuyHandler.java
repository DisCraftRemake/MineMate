package me.Rl242Dev.CommandHandler.DS.Shop;

import java.awt.Color;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.Classes.Utils.Coin;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BuyHandler extends ListenerAdapter{

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

        List<String> args = Arrays.asList(message.getContentRaw().split(" "));

        if(args.contains(".buy")){
            if(args.size() != 3) {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("<@");
                stringBuilder.append(user.getId());
                stringBuilder.append(">");
                stringBuilder.append(" ➔ .buy [Type] [Material] | .help");

                embedBuilder.setTitle(Coin.getEmojiID() + " Buy Action");
                embedBuilder.setColor(Color.green);

                embedBuilder.setDescription(stringBuilder.toString());

                embedBuilder.setTimestamp(Instant.now());
                embedBuilder.setFooter("DisCraft");

                channel.sendMessageEmbeds(embedBuilder.build()).queue();
            }
        }
    }
}
