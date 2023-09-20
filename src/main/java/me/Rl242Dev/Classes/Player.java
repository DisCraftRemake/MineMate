package me.Rl242Dev.Classes;

/*

@U = Main
@E = Main class of the game
@A = Rl242Dev

 */

import me.Rl242Dev.Classes.Entity.Pets.Pets;
import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Levels.RanksUtils;
import me.Rl242Dev.DisCraft;

public class Player {

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

    public Item getAxe() {
        return axe;
    }

    public Item getHoe() {
        return hoe;
    }

    public Pets getPet() {
        return pet;
    }

    public String getUuid() {
        return uuid;
    }

    public int getBalance() {
        return balance;
    }

    public int getLevel() {
        return level;
    }

    public String getRank() {
        return rank;
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
}
