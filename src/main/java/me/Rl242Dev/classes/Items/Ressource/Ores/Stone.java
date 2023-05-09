package me.Rl242Dev.classes.Items.Ressource.Ores;

/*

@A = Rl242Dev
@U = Items
@E = Class for Stone that will be used when user mines

 */

public class Stone {

    /* Attributes */

    public static int price = 2;
    public static float breakingSpeed = 0.3F;

    /* Methods*/

    public static float getBreakingSpeed(){
        return breakingSpeed;
    }

    public static int getPrice(){
        return price;
    }
}
