package me.Rl242Dev.Classes.Entity.Pets;

public interface PetIdentifier {
    int getPrice();

    String getName();

    int getQuantity_bonus();

    int getLuck_bonus();

    int getIncrement_bonus();

    int getSellPrice();

    Pets getType();
}