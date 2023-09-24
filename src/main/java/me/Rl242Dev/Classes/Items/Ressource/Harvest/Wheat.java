package me.Rl242Dev.Classes.Items.Ressource.Harvest;

import me.Rl242Dev.MineMate;

public class Wheat {

    /* Attributes */

    private static final int price = MineMate.getConfigManager().getInt("prices.resources.wheat");
    private static final String EmojiID = "<:wheat:1107291929336610916>";

    /* Methods */

    public static int getPrice(){
        return price;
    }

    public static String getEmojiID() {
        return EmojiID;
    }
}
