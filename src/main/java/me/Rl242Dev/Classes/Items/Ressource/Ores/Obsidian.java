package me.Rl242Dev.Classes.Items.Ressource.Ores;

/*

@A = Rl242Dev
@U = Items
@E = Class for Obsidian that will be used when user mines

 */

import me.Rl242Dev.MineMate;

public class Obsidian {
    /* Attributes */

    private final static int price = MineMate.getConfigManager().getInt("prices.resources.obsidian");
    private final static String EmojiID = "<:obsidian:1107341809459396638>";

    /* Methods */

    public static int getPrice(){
        return price;
    }

    public static String getEmojiID() {
        return EmojiID;
    }
}
