package me.Rl242Dev.Classes;

/*

@U = Main
@E = Main class of the game
@A = Rl242Dev

 */

import me.Rl242Dev.Classes.Entity.Pets.Pets;
import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Levels.RanksUtils;
import me.Rl242Dev.Classes.Utils.Coin;
import me.Rl242Dev.DisCraft;
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

    private Pets pet;

    private final String uuid;
    private String rank;

    /* Methods */

    public Item getPickaxe() {
        return pickaxe;
    }

    public void setPickaxe(Item pickaxe) {
        this.pickaxe = pickaxe;
    }

    public Item getAxe() {
        return axe;
    }

    public void setAxe(Item axe) {
        this.axe = axe;
    }

    public Item getHoe() {
        return hoe;
    }

    public void setHoe(Item hoe) {
        this.hoe = hoe;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Pets getPet() {
        return pet;
    }

    public void setPet(Pets pet) {
        this.pet = pet;
    }

    public String getUuid() {
        return uuid;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    /* Constructor */

    public Player(String UUID){
        this.pickaxe = DisCraft.getInstance().getDatabaseManager().getPickaxeFromUUID(UUID);
        this.hoe = DisCraft.getInstance().getDatabaseManager().getHoeFromUUID(UUID);
        this.balance = DisCraft.getInstance().getDatabaseManager().getBalanceFromUUID(UUID);

        this.level = DisCraft.getInstance().getDatabaseManager().getLevelFromUUID(UUID);
        this.rank = RanksUtils.GetRankFromLevel(this.level).toString();

        this.uuid = UUID;
    }

    public void sendMessage(String action, StringBuilder message){
        User user = DisCraft.getBot().retrieveUserById(this.uuid).complete();

        assert user != null;
        PrivateChannel channel = user.openPrivateChannel().complete();

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setTitle("\uD83D\uDD10 " + action);
        embedBuilder.setColor(Color.green);

        embedBuilder.setDescription(message.toString());

        embedBuilder.setFooter("DisCraft");
        embedBuilder.setTimestamp(Instant.now());

        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
