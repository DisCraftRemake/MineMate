package me.Rl242Dev.classes.Items.Ressource;

import me.Rl242Dev.classes.Items.Enchants;

public class RessourceUtils {

    public static int getBreakingSpeedFromType(Type type) {
        int speed = switch (type) {
            case WOOD -> 1;
            case STONE -> 2;
            case IRON -> 3;
            case GOLD -> 4;
            case DIAMOND -> 6;
        };
        return speed;
    }

    public static double getBreakingSpeedFromEnchants(Enchants enchants){
        double speed = switch (enchants){
            case EFFICIENCY1 -> 0.5;
            case EFFICIENCY2 -> 1.0;
            case EFFICIENCY3 -> 1.5;
            case EFFICIENCY4 -> 2.0;
            case EFFICIENCY5 -> 2.5;
            case SHARPNESS1 -> 0.5;
            case SHARPNESS2 -> 1.0;
            case SHARPNESS3 -> 1.5;
            case SHARPNESS4 -> 2.0;
            case SHARPNESS5 -> 2.5;
        };
        return speed;
    }
}
