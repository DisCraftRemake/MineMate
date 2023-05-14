package me.Rl242Dev.classes.Items.Ressource;

import java.util.Random;

public class RessourceUtils {

    /*
    public static int getEmojiIDFromType(Type type){
        int EmojiID = switch (type){

        }
    }
    
     */

    /* Group by for Wood Pickaxe*/

    public static int getStoneForPickaxe(){
        Random random = new Random();

        int min = 1;
        int max = 7;

        return random.nextInt((max - min) + 1) + min;
    }
}
