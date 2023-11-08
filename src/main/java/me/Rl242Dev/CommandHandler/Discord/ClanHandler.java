package me.Rl242Dev.CommandHandler.Discord;

import me.Rl242Dev.Classes.Clans.Clan;
import me.Rl242Dev.Classes.Player;
import me.Rl242Dev.MineMate;
import me.Rl242Dev.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.Instant;
import java.util.List;

public class ClanHandler { // TODO: FIX CLANS

    public static boolean handle(MessageReceivedEvent event) {
        User user = event.getAuthor();
        String uuid = user.getId();
        if (user.equals(event.getJDA().getSelfUser())) {
            return false;
        }

        Message message = event.getMessage();
        MessageChannelUnion channel = event.getChannel();

        Player player = new Player(uuid);

        if (MineMate.debug) {
            MineMate.getLogger().appendLogger(player.getUuid() + " Issued .clan");
            MineMate.getLogger().send();
        }
        List<String> args = List.of(message.getContentRaw().split(" "));

        if (args.size() < 2) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            StringBuilder description = new StringBuilder();

            description.append("<@");
            description.append(user.getId());
            description.append(">");
            description.append(" ➔ .clan [action] [(optional) arg] | <#"
                    + MineMate.getConfigManager().getString("channels.help") + ">");

            embedBuilder.setTitle(":shield: Clan Action");
            embedBuilder.setColor(Color.green);

            embedBuilder.setDescription(description.toString());

            embedBuilder.setTimestamp(Instant.now());
            embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return true;
        } else {
            if (args.get(1).equals("leave")) {
                if (player.getClan() == null) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    StringBuilder description = new StringBuilder();

                    description.append("<@");
                    description.append(user.getId());
                    description.append(">");
                    description.append(" ➔ You don't have a Clan | <#"
                            + MineMate.getConfigManager().getString("channels.help") + ">");

                    embedBuilder.setTitle(":shield: Clan Action");
                    embedBuilder.setColor(Color.green);

                    embedBuilder.setDescription(description.toString());

                    embedBuilder.setTimestamp(Instant.now());
                    embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                    channel.sendMessageEmbeds(embedBuilder.build()).queue();
                    return true;
                }
                player.getClan().leave(player);
                return true;
            }
            if (args.get(1).equals("kick")) {
                if (player.getClan() == null) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    StringBuilder description = new StringBuilder();

                    description.append("<@");
                    description.append(user.getId());
                    description.append(">");
                    description.append(" ➔ You don't have a Clan | <#"
                            + MineMate.getConfigManager().getString("channels.help") + ">");

                    embedBuilder.setTitle(":shield: Clan Action");
                    embedBuilder.setColor(Color.green);

                    embedBuilder.setDescription(description.toString());

                    embedBuilder.setTimestamp(Instant.now());
                    embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                    channel.sendMessageEmbeds(embedBuilder.build()).queue();
                    return true;
                }
                if (args.get(2) != null) {
                    player.getClan().kickPlayer(player, new Player(args.get(2)));
                    //TODO: Embed if not owner of clan by return of kickPlayer method
                    return true;
                } else {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    StringBuilder description = new StringBuilder();

                    description.append("<@");
                    description.append(user.getId());
                    description.append(">");
                    description.append(" ➔ .clan kick [user_id] | <#"
                            + MineMate.getConfigManager().getString("channels.help") + ">");

                    embedBuilder.setTitle(":shield: Clan Action");
                    embedBuilder.setColor(Color.green);

                    embedBuilder.setDescription(description.toString());

                    embedBuilder.setTimestamp(Instant.now());
                    embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                    channel.sendMessageEmbeds(embedBuilder.build()).queue();
                    return true;
                }
            }
            if (args.get(1).equals("apply")) {
                if (args.get(2) != null) {
                    if (player.getClan() != null) {
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        StringBuilder description = new StringBuilder();

                        description.append("<@");
                        description.append(user.getId());
                        description.append(">");
                        description.append(" ➔ You already have a Clan | <#"
                                + MineMate.getConfigManager().getString("channels.help") + ">");

                        embedBuilder.setTitle(":shield: Clan Action");
                        embedBuilder.setColor(Color.green);

                        embedBuilder.setDescription(description.toString());

                        embedBuilder.setTimestamp(Instant.now());
                        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                        channel.sendMessageEmbeds(embedBuilder.build()).queue();
                        return true;
                    } else {
                        Clan clan = new Clan(args.get(2));
                        clan.applyForClan(player);
                        //TODO: Message to say he applied
                        return true;
                    }
                }
            }
            if (args.get(1).equals("allow")) {
                if (args.get(2) != null) {
                    player.getClan().allowEntry(player, new Player(args.get(3)));
                    //TODO: Message to say he accepted a player
                    return true;
                } else {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    StringBuilder description = new StringBuilder();

                    description.append("<@");
                    description.append(user.getId());
                    description.append(">");
                    description.append(" ➔ You don't have a Clan | <#"
                            + MineMate.getConfigManager().getString("channels.help") + ">");

                    embedBuilder.setTitle(":shield: Clan Action");
                    embedBuilder.setColor(Color.green);

                    embedBuilder.setDescription(description.toString());

                    embedBuilder.setTimestamp(Instant.now());
                    embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                    channel.sendMessageEmbeds(embedBuilder.build()).queue();
                    return true;
                }
            }
            if (args.get(1).equals("create")) {
                if (player.getClan() != null) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    StringBuilder description = new StringBuilder();

                    description.append("<@");
                    description.append(user.getId());
                    description.append(">");
                    description.append(" ➔ You already have a Clan | <#"
                            + MineMate.getConfigManager().getString("channels.help") + ">");

                    embedBuilder.setTitle(":shield: Clan Action");
                    embedBuilder.setColor(Color.green);

                    embedBuilder.setDescription(description.toString());

                    embedBuilder.setTimestamp(Instant.now());
                    embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                    channel.sendMessageEmbeds(embedBuilder.build()).queue();
                    return true;
                }
                if (player.getBalance() < MineMate.getConfigManager().getInt("prices.clan_creation")) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    StringBuilder description = new StringBuilder();

                    description.append("<@");
                    description.append(user.getId());
                    description.append(">");
                    description.append(" ➔ You don't have enough to afford a Clan | <#"
                            + MineMate.getConfigManager().getString("channels.help") + ">");

                    embedBuilder.setTitle(":shield: Clan Action");
                    embedBuilder.setColor(Color.green);

                    embedBuilder.setDescription(description.toString());

                    embedBuilder.setTimestamp(Instant.now());
                    embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));

                    channel.sendMessageEmbeds(embedBuilder.build()).queue();
                    return true;
                }
                MineMate.getInstance().getDatabaseManager()
                        .addClanOwner(Utils.IntToString(MineMate.getClans().size() + 1), player.getUuid());
                Clan clan = new Clan(Utils.IntToString(MineMate.getClans().size() + 1));
                MineMate.getClans().add(clan);
                return true;
                // TODO: embed to say he created clan
            }
        }
		return false;
    }
}
