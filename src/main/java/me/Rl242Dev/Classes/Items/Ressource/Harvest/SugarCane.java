package me.Rl242Dev.Classes.Items.Ressource.Harvest;

import me.Rl242Dev.MineMate;

public class SugarCane {

    /* Attributes */

    private static final int price = MineMate.getConfigManager().getInt("prices.resources.sugarcane");
    private static final String EmojiID = "<:sugarcane:1107291931848998993>";

    /* Methods */

    public static int getPrice(){
        return price;
    }

    public static String getEmojiID() {
        return EmojiID;
    }
}
