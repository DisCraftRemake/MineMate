package me.Rl242Dev.Classes.Items.Ressource.Ores;

/*

@A = Rl242Dev
@U = Items
@E = Class for Gold that will be used when user mines

 */

public class Gold {

    /* Attributes */

    private final static int price = 50;
    private final static String EmojiID = "<:gold:1107291948789813250>";

    /* Methods */

    public static int getPrice(){
        return price;
    }

    public static String getEmojiID() {
        return EmojiID;
    }
}
