package me.Rl242Dev.DatabaseManager;

import me.Rl242Dev.Classes.Entity.Pets.Pets;
import me.Rl242Dev.Classes.Items.Ressource.Material;
import me.Rl242Dev.Classes.Items.Ressource.Type;

/*

@A = Rl242Dev
@U = Database
@E = Class for the DatabaseUtils, used to query convert data queried

 */

public class DatabaseUtils {
    public static String getNameFromMaterial(Material material){
        String displayName = switch (material){
            case WOOD -> "Wood";
            case STONE -> "Stone";
            case GOLD -> "Gold";
            case IRON -> "Iron";
            case DIAMOND -> "Diamond";
        };
        return displayName;
    }

    public static String getNameFromType(Type type){
        String displayName = switch (type){
            case PICKAXE -> "Pickaxe";
            case HOE -> "Hoe";
            case SWORD -> "Sword";
        };
        return displayName;
    }

    public static String getNameFromPet(Pets pets){
        String displayName = switch (pets){
            case BEE -> "Bee";
            case CAT -> "Cat";
            case GOAT -> "Goat";
        };
        return displayName;
    }
}
