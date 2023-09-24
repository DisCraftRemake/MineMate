package me.Rl242Dev.Classes.Items.Ressource.Harvest;

/*

@A = Rl242Dev
@U = Items
@E = Class for Carrot that will be used when user harvests

 */

import me.Rl242Dev.MineMate;

public class Carrot {

    /* Attributes */

    private static final int price = MineMate.getConfigManager().getInt("prices.resources.carrot");
    private static final String EmojiID = "<:MCcarrot:1109407077715157025>";

    /* Methods */

    public static int getPrice(){
        return price;
    }

    public static String getEmojiID() {
        return EmojiID;
    }
}
