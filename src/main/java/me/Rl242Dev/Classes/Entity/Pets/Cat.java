package me.Rl242Dev.Classes.Entity.Pets;

import me.Rl242Dev.MineMate;

/*

@U = Pets
@E = Class for the cat pet
@A = Rl242Dev

 */

public class Cat implements PetIdentifier{
    private final int price = MineMate.getConfigManager().getInt("prices.pets.cat");
    private final String name = "Cat";
    private final int quantity_bonus = 0;
    private final int luck_bonus = 0;
    private final int increment_bonus = 1;
    private Pets type = Pets.CAT;

    public Cat(){}

    public Cat(Pets type){
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
