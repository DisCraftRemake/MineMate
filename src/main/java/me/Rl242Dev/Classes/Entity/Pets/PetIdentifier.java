package me.Rl242Dev.Classes.Entity.Pets;

/*

@U = Pets
@E = Interface to identifie pets class and inherate methods
@A = Rl242Dev

 */

public interface PetIdentifier {
    int getPrice();

    String getName();

    int getQuantity_bonus();

    int getLuck_bonus();

    int getIncrement_bonus();

    int getSellPrice();

    Pets getType();
}