package me.Rl242Dev.Classes.Entity.Pets;

import me.Rl242Dev.MineMate;

public class Goat implements PetIdentifier {
    private final int price = MineMate.getConfigManager().getInt("prices.pets.goat");
    private final String name = "Goat";
    private final int quantity_bonus = 2;
    private final int luck_bonus = 0;
    private final int increment_bonus = 0;
    private final Pets type = Pets.GOAT;

    public Goat(){}

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
