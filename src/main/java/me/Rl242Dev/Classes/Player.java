package me.Rl242Dev.Classes;

/*

@U = Main
@E = Main class of the game
@A = Rl242Dev

 */

import me.Rl242Dev.Classes.Entity.Pets.Pets;
import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Levels.RanksUtils;
import me.Rl242Dev.DatabaseManager.DatabaseUtils;

public class Player {

    /* Attributes */

    private Item pickaxe;
    private Item axe;
    private Item hoe;

    private int balance;

    public int getLevel() {
        return level;
    }

    public String getRank() {
        return rank;
    }

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

    /* Constructor */

    public Player(String UUID){
        this.pickaxe = DatabaseUtils.getPickaxeFromUUID(UUID);
        this.hoe = DatabaseUtils.getHoeFromUUID(UUID);
        this.balance = DatabaseUtils.GetBalanceFromUUID(UUID);

        this.level = DatabaseUtils.getLevelFromUUID(UUID);
        this.rank = RanksUtils.EnumToString(RanksUtils.GetRankFromLevel(this.level));

        this.uuid = UUID;
    }
}
