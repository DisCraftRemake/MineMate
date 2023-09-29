package me.Rl242Dev.Classes;

/*

@U = Main
@E = Main class of the game
@A = Rl242Dev

 */

import me.Rl242Dev.Classes.Entity.Pets.PetIdentifier;
import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Levels.RanksUtils;
import me.Rl242Dev.MineMate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;

import java.awt.*;
import java.time.Instant;

public class Player { //TODO: [SET Methods, Serialize, Deserialize]

    /* Attributes */

    private Item pickaxe;
    private Item axe;
    private Item hoe;

    private int balance;

    private int level;
    private int prestige;

    private Class<? extends PetIdentifier> pet;

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

        this.uuid = UUID;
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
}
