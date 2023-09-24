package me.Rl242Dev.Classes.Items.Ressource.Harvest;

import me.Rl242Dev.MineMate;

public class Potato {

    /* Attributes */

    private static final int price = MineMate.getConfigManager().getInt("prices.resources.potato");
    private static final String EmojiID = "<:MCpotato:1109407247638999101>";

    /* Methods */

    public static int getPrice(){
        return price;
    }

    public static String getEmojiID() {
        return EmojiID;
    }
}
