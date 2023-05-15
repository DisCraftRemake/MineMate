package me.Rl242Dev;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.Instant;
import java.util.Random;

/*

@A = Rl242Dev
@U = Main
@E = Utils class

 */

public class Utils {

    /*

    public static int GenerateUUID(int UserID, Type Pickaxetype){

        StringBuilder uuidString = new StringBuilder();
        uuidString.append(Integer.toString(UserID));

        int TypeInt = switch (Pickaxetype){
            case WOOD -> 1;
            case STONE -> 2;
            case IRON -> 3;
            case GOLD -> 4;
            case DIAMOND -> 5;
        };

        uuidString.append(TypeInt);
        int uuid = Integer.parseInt(uuidString.toString());

        return uuid;
    }


     */

    /* Utils */

    public static String IntToString(int integer){
        return Integer.toString(integer);
    }

    public static int StringToInt(String str){
        return Integer.parseInt(str);
    }

    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    /* Embed Utils */

    public static EmbedBuilder StartErrorEmbed(String UserID){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        StringBuilder main = new StringBuilder();

        main.append("<@");
        main.append(UserID);
        main.append(">");
        main.append("➔ To create an adventure and start playing type : .start");

        embedBuilder.setFooter("DisCraft");
        embedBuilder.setColor(Color.green);
        embedBuilder.setTitle("Starting an adventure");
        embedBuilder.setTimestamp(Instant.now());
        embedBuilder.setDescription(main.toString());

        return embedBuilder;
    }
}
