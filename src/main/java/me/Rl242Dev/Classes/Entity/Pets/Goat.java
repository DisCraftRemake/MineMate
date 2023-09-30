package me.Rl242Dev.Classes.Entity.Pets;

import me.Rl242Dev.MineMate;

/*

@U = Pets
@E = Class for the goat pet
@A = Rl242Dev

 */

public class Goat implements PetIdentifier {
    private final int price = MineMate.getConfigManager().getInt("prices.pets.goat");
    private final String name = "Goat";
    private final int quantity_bonus = 2;
    private final int luck_bonus = 0;
    private final int increment_bonus = 0;
    private Pets type = Pets.GOAT;

    public Goat(){}

    public Goat(Pets type){
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity_bonus() {
        return quantity_bonus;
    }

    public int getLuck_bonus() {
        return luck_bonus;
    }

    public int getIncrement_bonus() {
        return increment_bonus;
    }

    public int getSellPrice() {
        return getPrice() / 25;
    }

    public Pets getType() {
        return type;
    }
}
