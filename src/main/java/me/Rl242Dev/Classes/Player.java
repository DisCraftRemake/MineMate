package me.Rl242Dev.Classes;

/*

@U = Main
@E = Main class of the game
@A = Rl242Dev

 */

import me.Rl242Dev.Classes.Clans.Clan;
import me.Rl242Dev.Classes.Entity.Pets.PetIdentifier;
import me.Rl242Dev.Classes.Entity.Pets.PetUtils;
import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Items.Ressource.ResourceUtils;
import me.Rl242Dev.Classes.Levels.Ranks;
import me.Rl242Dev.Classes.Levels.RanksUtils;
import me.Rl242Dev.Classes.Utils.Coin;
import me.Rl242Dev.Classes.Utils.Emoji;
import me.Rl242Dev.MineMate;
import me.Rl242Dev.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;

import java.awt.*;
import java.time.Instant;
import java.util.Objects;

public class Player {

    /* Attributes */

    private Item pickaxe;
    private Item axe;
    private Item hoe;

    private int balance;

    private int level;
    private int prestige;

    private Class<? extends PetIdentifier> pet;

    private Clan clan;

    private final String uuid;
    private String rank;

    /* Methods */

    public Item getPickaxe() {
        return pickaxe;
    }

    public Item getAxe() {
        return axe;
    }

    public Item getHoe() {
        return hoe;
    }

    public int getBalance() {
        return balance;
    }

    public int getLevel() {
        return level;
    }

    public Class<? extends PetIdentifier> getPet() {
        return pet;
    }

    public String getUuid() {
        return uuid;
    }

    public Clan getClan(){
        return clan;
    }


    public String getRank() {
        return rank;
    }

    public int getPrestige() {
        return prestige;
    }

    /* Constructor */

    public Player(String UUID){
        this.pickaxe = MineMate.getInstance().getDatabaseManager().getPickaxeFromUUID(UUID);
        this.hoe = MineMate.getInstance().getDatabaseManager().getHoeFromUUID(UUID);
        this.balance = MineMate.getInstance().getDatabaseManager().getBalanceFromUUID(UUID);

        this.level = MineMate.getInstance().getDatabaseManager().getLevelFromUUID(UUID);
        this.rank = RanksUtils.GetRankFromLevel(this.level).toString();

        if(MineMate.getInstance().getDatabaseManager().getPetFromUUID(UUID) == null){
            this.pet = null;
        }else{
            this.pet = MineMate.getInstance().getDatabaseManager().getPetFromUUID(UUID);
        }

        this.prestige = MineMate.getInstance().getDatabaseManager().getPrestige(UUID);

        /* if(MineMate.getInstance().getDatabaseManager().getClanFromMember(UUID) == null){
            this.clan = null;
        }else{
            this.clan = MineMate.getInstance().getDatabaseManager().getClanFromMember(UUID);
        }
        */

        this.uuid = UUID;
    }

    public EmbedBuilder getProfil(){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<@");
        stringBuilder.append(this.getUuid());
        stringBuilder.append(">");
        stringBuilder.append(" ➔ Profil :");

        embedBuilder.setTitle(":bust_in_silhouette: Profil Action");
        embedBuilder.setColor(Color.green);

        embedBuilder.setDescription(stringBuilder.toString());

        embedBuilder.addField(Coin.getEmojiID() + " **Balance** :", ResourceUtils.PriceToString(this.getBalance()),false);
        embedBuilder.addField(Emoji.getXpEmoji() + " **Level** :", Utils.IntToString(this.getLevel()), false);
        embedBuilder.addField(Emoji.getNametagEmoji() + " **Rank** :", Objects.requireNonNull(RanksUtils.presentRank(Ranks.valueOf(this.getRank()))), false);
        embedBuilder.addField(Emoji.getTotemEmoji()+ " **Prestige** :", Utils.IntToString(this.getPrestige()), false);

        embedBuilder.addField(Emoji.getPickaxeEmoji() + " **Pickaxe** :", this.getPickaxe().getDisplayName(), false);
        embedBuilder.addField(Emoji.getHoeEmoji() + " **Hoe** :", this.getHoe().getDisplayName(), false);
        // Axe
        if(this.getPet() != null){
            embedBuilder.addField(PetUtils.getEmojiFromPet(this.getPet()) + " **Pet** :", PetUtils.getOnlyNameFromPet(this.getPet()), false);
        }

        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
        embedBuilder.setTimestamp(Instant.now());

        return embedBuilder;
    }

    public void sendMessage(String action, StringBuilder message){
        User user = MineMate.getBot().retrieveUserById(this.uuid).complete();

        assert user != null;
        PrivateChannel channel = user.openPrivateChannel().complete();

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("\uD83D\uDD10 " + action);
        embedBuilder.setColor(Color.green);

        embedBuilder.setDescription(message.toString());

        embedBuilder.setFooter(MineMate.getConfigManager().getString("general.name"));
        embedBuilder.setTimestamp(Instant.now());

        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    public void sendMessage(EmbedBuilder embedBuilder){
        User user = MineMate.getBot().retrieveUserById(this.uuid).complete();

        assert user != null;
        PrivateChannel channel = user.openPrivateChannel().complete();

        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
