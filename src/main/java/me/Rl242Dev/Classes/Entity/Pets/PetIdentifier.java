package me.Rl242Dev.Classes.Entity.Pets;

public interface PetIdentifier {
    public int getPrice();

    public String getName();

    public int getQuantity_bonus();

    public int getLuck_bonus();

    public int getIncrement_bonus();

    public int getSellPrice();

    public Pets getType();
}
