package me.Rl242Dev.Classes.Entity.Pets;

import me.Rl242Dev.MineMate;

public class Bee implements PetIdentifier{
    private final int price = MineMate.getConfigManager().getInt("prices.pets.bee");
    private final String name = "Bee";
    private final int quantity_bonus = 0;
    private final int luck_bonus = 20;
    private final int increment_bonus = 0;
    private final Pets type = Pets.BEE;

    public Bee(){}

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getQuantity_bonus() {
        return quantity_bonus;
    }

    @Override
    public int getLuck_bonus() {
        return luck_bonus;
    }

    @Override
    public int getIncrement_bonus() {
        return increment_bonus;
    }

    @Override
    public int getSellPrice() {
        return getPrice() / 25;
    }

    @Override
    public Pets getType() {
        return type;
    }
}
