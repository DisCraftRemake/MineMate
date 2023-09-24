package me.Rl242Dev.Classes.Items.Ressource.Ores;

/*

@A = Rl242Dev
@U = Items
@E = Class for Iron that will be used when user mines

 */

import me.Rl242Dev.MineMate;

public class Iron {

    /* Attributes */

    private final static int price = MineMate.getConfigManager().getInt("prices.resources.iron");
    private final static String EmojiID = "<:iron:1107295865875480657>";

    /* Methods */

    public static int getPrice(){
        return price;
    }

    public static String getEmojiID() {
        return EmojiID;
    }
}
