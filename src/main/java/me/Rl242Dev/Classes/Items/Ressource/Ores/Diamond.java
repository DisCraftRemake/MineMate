package me.Rl242Dev.Classes.Items.Ressource.Ores;

/*

@A = Rl242Dev
@U = Items
@E = Class for Diamond that will be used when user mines

 */

import me.Rl242Dev.MineMate;

public class Diamond {

    /* Attributes */

    private static final int price = MineMate.getConfigManager().getInt("prices.resources.diamond");
    private static final String EmojiID = "<:diamond:1107291950064869386>";

    /* Methods */

    public static int getPrice(){
        return price;
    }

    public static String getEmojiID() {
        return EmojiID;
    }
}
