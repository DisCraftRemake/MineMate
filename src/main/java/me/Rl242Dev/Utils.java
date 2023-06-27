package me.Rl242Dev;

import me.Rl242Dev.Classes.Items.Ressource.Material;
import me.Rl242Dev.Classes.Items.Ressource.Resources;
import me.Rl242Dev.Classes.Items.Ressource.Type;
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

    /* Private Hash Util*/

    public static int hash(String str){
        int hash = 7;
        for(int i = 0; i < str.length(); i++){
            hash = hash*31 + str.charAt(i);
        }
        return hash;
    }

    /* Utils */

    public static boolean ResourcesEnumContainsString(String str){
        for(Resources resources : Resources.values()){
            if (resources.name().equals(str)){
                return true;
            }
        }
        return false;
    }

    public static boolean TypeEnumContainsString(String str){
        for(Type type : Type.values()){
            if(type.name().equals(str)){
                return true;
            }
        }
        return false;
    }

    public static boolean MaterialEnumContainsString(String str){
        for(Material material : Material.values()){
            if(material.name().equals(str)){
                return true;
            }
        }
        return false;
    }

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
