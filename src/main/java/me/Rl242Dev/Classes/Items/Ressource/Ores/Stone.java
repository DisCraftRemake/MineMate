package me.Rl242Dev.Classes.Items.Ressource.Ores;

/*

@A = Rl242Dev
@U = Items
@E = Class for Stone that will be used when user mines

 */

import me.Rl242Dev.MineMate;

public class Stone {

    /* Attributes */

    private static int price = MineMate.getConfigManager().getInt("prices.resources.stone");
    private static String EmojiID = "<:stone:1107294365463891998>";

    /* Methods*/

    public static int getPrice(){
        return price;
    }

    public static String getEmojiID() {
        return EmojiID;
    }
}
