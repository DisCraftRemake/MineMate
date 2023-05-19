package me.Rl242Dev.Classes.Items.Ressource;

import me.Rl242Dev.Classes.Items.Ressource.Ores.Ores;
import me.Rl242Dev.Utils;

import java.util.HashMap;
import java.util.Map;

/*

@A = Rl242Dev
@U = Main
@E = Utils for class in package /Ressource

 */

public class RessourceUtils {

    public static String getEmojiIDFromType(Type type){
        String EmojiID = switch (type){
            case SWORD -> "<:sword:1107339351324643388>";
            case PICKAXE -> "<:pickaxe:1107341471725649990>";
            case AXE -> "Not Yet";
        };
        return EmojiID;
    }

    public static String getNameFromMaterial(Material material){
        String displayName = switch (material){
            case WOOD -> "Wooden ";
            case STONE -> "Stone ";
            case GOLD -> "Gold ";
            case IRON -> "Iron ";
            case DIAMOND -> "Diamond ";
        };
        return displayName;
    }

    public static String getNameFromType(Type type){
        String displayName = switch (type){
            case PICKAXE -> "Pickaxe";
            case AXE -> "Axe";
            case SWORD -> "Sword";
        };
        return displayName;
    }

    public static Material getMaterialFromString(String str) {
        Material type = switch (str) {
            case "Wood" -> Material.WOOD;
            case "Stone" -> Material.STONE;
            case "Iron" -> Material.IRON;
            case "Gold" -> Material.GOLD;
            case "Diamond" -> Material.DIAMOND;
            default -> throw new IllegalArgumentException("Invalid type: " + str);
        };
        return type;
    }

    public static Type getTypeFromString(String str){
        Type type = switch (str){
            case "Pickaxe" -> Type.PICKAXE;
            case "Sword" -> Type.SWORD;
            case "Axe" -> Type.AXE;
            default -> throw new IllegalArgumentException("Invalid type: " + str);
        };
        return type;
    }


    /* Ressource generation*/
    public static Map<Ores, Integer> getRessourceForPickaxe(Material material){
        Map<Ores, Integer> ressources = new HashMap<Ores, Integer>();

        switch (material){
            case WOOD :
                int stoneAmountWood = Utils.getRandomNumberInRange(1, 7);
                int coalAmoutWood = Utils.getRandomNumberInRange(1,4);

                ressources.put(Ores.STONE, stoneAmountWood);
                ressources.put(Ores.COAL, coalAmoutWood);
                ressources.put(Ores.IRON, 0);
                ressources.put(Ores.GOLD, 0);
                ressources.put(Ores.DIAMOND, 0);
                ressources.put(Ores.OBSIDIAN, 0);
                break;
            case STONE:
                int stoneAmountStone = Utils.getRandomNumberInRange(4, 9);
                int coalAmoutStone = Utils.getRandomNumberInRange(2,6);
                int ironAmoutStone = Utils.getRandomNumberInRange(1,3);

                ressources.put(Ores.STONE, stoneAmountStone);
                ressources.put(Ores.COAL, coalAmoutStone);
                ressources.put(Ores.IRON, ironAmoutStone);
                ressources.put(Ores.GOLD, 0);
                ressources.put(Ores.DIAMOND, 0);
                ressources.put(Ores.OBSIDIAN, 0);
                break;
            case IRON:
                int coalAmoutIron = Utils.getRandomNumberInRange(7,12);
                int ironAmoutIron = Utils.getRandomNumberInRange(4, 8);
                int goldAmoutIron = Utils.getRandomNumberInRange(2, 5);

                ressources.put(Ores.STONE, 0);
                ressources.put(Ores.COAL, coalAmoutIron);
                ressources.put(Ores.IRON, ironAmoutIron);
                ressources.put(Ores.GOLD, goldAmoutIron);
                ressources.put(Ores.DIAMOND, 0);
                ressources.put(Ores.OBSIDIAN, 0);
                break;
            case GOLD:
                int ironAmoutGold = Utils.getRandomNumberInRange(8, 13);
                int goldAmoutGold = Utils.getRandomNumberInRange(6, 11);
                int diamondAmoutGold = Utils.getRandomNumberInRange(0, 3);

                ressources.put(Ores.STONE, 0);
                ressources.put(Ores.COAL, 0);
                ressources.put(Ores.IRON, ironAmoutGold);
                ressources.put(Ores.GOLD, goldAmoutGold);
                ressources.put(Ores.DIAMOND, diamondAmoutGold);
                ressources.put(Ores.OBSIDIAN, 0);
                break;

            case DIAMOND:
                int goldAmoutDiamond = Utils.getRandomNumberInRange(11, 24);
                int diamondAmoutDiamond = Utils.getRandomNumberInRange(3, 7);
                int obsidianAmoutDiamond = Utils.getRandomNumberInRange(0,2);

                ressources.put(Ores.STONE, 0);
                ressources.put(Ores.COAL, 0);
                ressources.put(Ores.IRON, 0);
                ressources.put(Ores.GOLD, goldAmoutDiamond);
                ressources.put(Ores.DIAMOND, diamondAmoutDiamond);
                ressources.put(Ores.OBSIDIAN, obsidianAmoutDiamond);
                break;
        }
        return ressources;
    }
}
