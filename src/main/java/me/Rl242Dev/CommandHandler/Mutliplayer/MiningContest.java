package me.Rl242Dev.CommandHandler.Mutliplayer;

import me.Rl242Dev.Classes.Items.Ressource.Material;
import me.Rl242Dev.Classes.Items.Ressource.Ores.Ores;
import me.Rl242Dev.Classes.Items.Ressource.ResourceUtils;
import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.Classes.Utils.Coin;
import me.Rl242Dev.Classes.Utils.Emoji;
import me.Rl242Dev.DisCraft;
import me.Rl242Dev.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*

@A = Rl242Dev
@U = Game
@E = Class for the MiningContest, used when users want to play a MiningContest Game

 */

public class MiningContest extends ListenerAdapter {
    private final Map<String, Long> cooldowns = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        User user = event.getAuthor();
        String uuid = user.getId();
        if (user.equals(event.getJDA().getSelfUser())) {
            return;
        }

        Player player = new Player(uuid);

        MessageChannelUnion channel = event.getChannel();

        if (event.getMessage().getContentRaw().equals(".miningContest")) {
            if (isOnCooldown(uuid)) {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                StringBuilder description = new StringBuilder();

                description.append("<@");
                description.append(user.getId());
                description.append(">");
                description.append(" ➔ You can only play once a minute. Please wait a moment.");

                embedBuilder.setTitle(Emoji.getXpEmoji() + " Game Action");
                embedBuilder.setColor(Color.green);

                embedBuilder.setDescription(description.toString());

                embedBuilder.setTimestamp(Instant.now());
                embedBuilder.setFooter("DisCraft");

                channel.sendMessageEmbeds(embedBuilder.build()).queue();
                return;
            }

            startCooldown(uuid);

            DisCraft.getInstance().getDatabaseManager().removeBalanceFromUUID(uuid, 500);
            Map<Ores, Integer> playerMapGlobal = new HashMap<>();
            Map<Ores, Integer> serverMapGlobal = new HashMap<>();

            for (int i = 0; i < 5; i++) {
                Map<Ores, Integer> playerMap = ResourceUtils.getResourceForPickaxe(Material.DIAMOND);
                Map<Ores, Integer> serverMap = ResourceUtils.getResourceForPickaxe(Material.DIAMOND);

                for (Ores ore : playerMap.keySet()) {
                    playerMapGlobal.put(ore, playerMap.get(ore));
                }

                for (Ores ore : serverMap.keySet()) {
                    serverMapGlobal.put(ore, serverMap.get(ore));
                }
            }

            int playerScore = 0;
            int serverScore = 0;

            for (Integer integer : playerMapGlobal.values()) {
                playerScore = playerScore + integer;
            }

            for (Integer integer : serverMapGlobal.values()) {
                serverScore = serverScore + integer;
            }

            EmbedBuilder embedBuilder = new EmbedBuilder();
            StringBuilder description = new StringBuilder();

            description.append("<@");
            description.append(user.getId());
            description.append(">");
            description.append(" ➔ Mining Contest");

            embedBuilder.setTitle(Coin.getEmojiID() + " Game Action");
            embedBuilder.setColor(Color.green);

            embedBuilder.setTimestamp(Instant.now());
            embedBuilder.setFooter("DisCraft");

            embedBuilder.addField(Coin.getEmojiID() + "| " + DisCraft.getBot().getSelfUser().getName(), Utils.IntToString(serverScore), false);
            embedBuilder.addField(Coin.getEmojiID() + "| " + user.getName(), Utils.IntToString(playerScore), false);

            if (playerScore < serverScore) {
                embedBuilder.addField(":crown: Winner", DisCraft.getBot().getSelfUser().getName(), false);
            } else {
                embedBuilder.addField(":crown: Winner", user.getName(), false);
                DisCraft.getInstance().getDatabaseManager().addToBalanceFromUUID(uuid, 1000);
            }

            embedBuilder.setDescription(description.toString());
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
        }
    }

    private void startCooldown(String uuid) {
        cooldowns.put(uuid, System.currentTimeMillis());
        scheduler.schedule(() -> cooldowns.remove(uuid), 1, TimeUnit.MINUTES);
    }

    private boolean isOnCooldown(String uuid) {
        Long lastPlayTime = cooldowns.get(uuid);
        if (lastPlayTime == null) {
            return false;
        }

        long currentTime = System.currentTimeMillis();
        return currentTime - lastPlayTime < TimeUnit.MINUTES.toMillis(1);
    }
}