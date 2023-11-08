package me.Rl242Dev.Classes.Utils;

import java.time.Instant;

import me.Rl242Dev.MineMate;
import me.Rl242Dev.Classes.Player;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CustomEmbedBuilder {

    private final String action;
    private final String description;
    private final Player player;

    public CustomEmbedBuilder(String Action, Player player, String description){
        this.action = Action;
        this.description = description;
        this.player = player;
    }

    public EmbedBuilder toEmbed(){
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle(this.action);
        embedBuilder.setColor(Color.green);

        embedBuilder.setDescription("<@"+player.getUuid()+"> ➔ "+this.description);

        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
        return embedBuilder;
    }

    //TODO: Change every embed to customEmbedBuilder
}