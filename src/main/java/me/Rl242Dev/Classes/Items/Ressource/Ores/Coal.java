package me.Rl242Dev.Classes.Items.Ressource.Ores;

/*

@A = Rl242Dev
@U = Items
@E = Class for Coal that will be used when user mines

 */

public class Coal {

    /* Attributes*/

    private static final int price = 10;
    private static final String EmojID = "<:coal:1107291933623210045>";

    /* Methods */

    public static int getPrice(){
        return price;
    }

    public static String getEmojiID() {
        return EmojID;
    }
}
