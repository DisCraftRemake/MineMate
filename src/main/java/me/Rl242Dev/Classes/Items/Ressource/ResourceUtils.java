package me.Rl242Dev.Classes.Items.Ressource;

import me.Rl242Dev.Classes.Entity.Pets.Bee;
import me.Rl242Dev.Classes.Entity.Pets.Goat;
import me.Rl242Dev.Classes.Entity.Pets.Pets;
import me.Rl242Dev.Classes.Items.Ressource.Harvest.Crops;
import me.Rl242Dev.Classes.Items.Ressource.Ores.Ores;
import me.Rl242Dev.Classes.Utils.Emoji;
import me.Rl242Dev.MineMate;
import me.Rl242Dev.Utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/*

@A = Rl242Dev
@U = Main
@E = Utils for class in package /Ressource

 */

public class ResourceUtils {

    public static String getEmojiIDFromType(Type type){
        String EmojiID = switch (type){
            case SWORD -> Emoji.getSwordEmoji();
            case PICKAXE -> Emoji.getPickaxeEmoji();
            case HOE -> Emoji.getHoeEmoji();
        };
        return EmojiID;
    }

    public static int getPriceFromString(String str){
        int price = switch (str.toUpperCase()){
            case "STONE" -> 2;
            case "COAL" -> 10;
            case "IRON" -> 25;
            case "GOLD" -> 50;
            case "DIAMOND" -> 100;
            case "OBSIDIAN" -> 200;
            case "WHEAT" -> 10;
            case "POTATO" -> 20;
            case "CARROT" -> 30;
            case "SUGARCANE" -> 50;
            default -> throw new IllegalArgumentException("Invalid type: " + str);
        };
        return price;
    }

    public static int getItemPriceFromMaterial(Material material){
        int price = switch (material){
            case WOOD -> MineMate.getConfigManager().getInt("prices.materials.wood");
            case STONE -> MineMate.getConfigManager().getInt("prices.materials.stone");
            case IRON -> MineMate.getConfigManager().getInt("prices.materials.iron");
            case GOLD -> MineMate.getConfigManager().getInt("prices.materials.gold");
            case DIAMOND -> MineMate.getConfigManager().getInt("prices.materials.diamond");
        };
        return price;
    }

    public static String PriceToString(int price){
        String pattern = "###,###,###";
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);

        String format = decimalFormat.format(price);
        return format;
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
            case HOE -> "Hoe";
            case SWORD -> "Sword";
        };
        return displayName;
    }

    public static Material getMaterialFromString(String str) {
        Material type = switch (str.toUpperCase()) {
            case "WOODEN", "WOOD" -> Material.WOOD;
            case "STONE" -> Material.STONE;
            case "IRON" -> Material.IRON;
            case "GOLD" -> Material.GOLD;
            case "DIAMOND" -> Material.DIAMOND;
            default -> throw new IllegalArgumentException("Invalid type: " + str);
        };
        return type;
    }

    public static Type getTypeFromString(String str){
        Type type = switch (str.toUpperCase()){
            case "PICKAXE" -> Type.PICKAXE;
            case "SWORD" -> Type.SWORD;
            case "HOE" -> Type.HOE;
            default -> throw new IllegalArgumentException("Invalid type: " + str);
        };
        return type;
    }

    /* Ressource generation*/

    public static Map<Ores, Integer> getResourcesForPickaxe(Material material){
        Map<Ores, Integer> ressources = new HashMap<Ores, Integer>();

        switch (material) {
            case WOOD -> {
                ressources.put(Ores.STONE, Utils.getRandomNumberInRange(1, 7));
                ressources.put(Ores.COAL, Utils.getRandomNumberInRange(1, 4));
                ressources.put(Ores.IRON, 0);
                ressources.put(Ores.GOLD, 0);
                ressources.put(Ores.DIAMOND, 0);
                ressources.put(Ores.OBSIDIAN, 0);
            }
            case STONE -> {
                ressources.put(Ores.STONE, Utils.getRandomNumberInRange(4, 9));
                ressources.put(Ores.COAL, Utils.getRandomNumberInRange(2, 6));
                ressources.put(Ores.IRON, Utils.getRandomNumberInRange(1, 3));
                ressources.put(Ores.GOLD, 0);
                ressources.put(Ores.DIAMOND, 0);
                ressources.put(Ores.OBSIDIAN, 0);
            }
            case IRON -> {
                ressources.put(Ores.STONE, 0);
                ressources.put(Ores.COAL, Utils.getRandomNumberInRange(7, 12));
                ressources.put(Ores.IRON, Utils.getRandomNumberInRange(4, 8));
                ressources.put(Ores.GOLD, Utils.getRandomNumberInRange(2, 5));
                ressources.put(Ores.DIAMOND, 0);
                ressources.put(Ores.OBSIDIAN, 0);
            }
            case GOLD -> {
                ressources.put(Ores.STONE, 0);
                ressources.put(Ores.COAL, 0);
                ressources.put(Ores.IRON, Utils.getRandomNumberInRange(8, 13));
                ressources.put(Ores.GOLD, Utils.getRandomNumberInRange(6, 11));
                ressources.put(Ores.DIAMOND, Utils.getRandomNumberInRange(0, 3));
                ressources.put(Ores.OBSIDIAN, 0);
            }
            case DIAMOND -> {
                ressources.put(Ores.STONE, 0);
                ressources.put(Ores.COAL, 0);
                ressources.put(Ores.IRON, 0);
                ressources.put(Ores.GOLD, Utils.getRandomNumberInRange(11, 24));
                ressources.put(Ores.DIAMOND, Utils.getRandomNumberInRange(3, 7));
                ressources.put(Ores.OBSIDIAN, Utils.getRandomNumberInRange(0, 2));
            }
        }
        return ressources;
    }

    public static Map<Ores, Integer> getResourcesForPickaxe(Material material, Pets pet){
        Map<Ores, Integer> globalResources = getResourcesForPickaxe(material);

        switch (pet){
            case BEE -> {
                Bee bee = new Bee();
                Random random = new Random();
                for(Ores ore : globalResources.keySet()){
                    if(random.nextInt(0,1) < bee.getLuck_bonus()){
                        int loot = globalResources.get(ore) * 2;
                        globalResources.remove(ore);
                        globalResources.put(ore, loot);
                    }
                }
            }
            case GOAT -> {
                for(Ores ore : globalResources.keySet()){
                    int loot = globalResources.get(ore) + new Goat().getQuantity_bonus();
                    globalResources.remove(ore);
                    globalResources.put(ore, loot);
                }
            }
            case CAT -> globalResources = getResourcesForPickaxe(getNextMaterial(material));
        }
        return globalResources;
    }

    public static Map<Crops, Integer> getResourcesForHoe(Material material){
        Map<Crops, Integer> resources = new HashMap<Crops, Integer>();

        switch (material) {
            case WOOD -> {
                resources.put(Crops.WHEAT, Utils.getRandomNumberInRange(1, 7));
                resources.put(Crops.POTATO, 0);
                resources.put(Crops.CARROT, 0);
                resources.put(Crops.SUGARCANE, 0);
            }
            case STONE -> {
                resources.put(Crops.WHEAT, Utils.getRandomNumberInRange(3, 9));
                resources.put(Crops.POTATO, Utils.getRandomNumberInRange(1, 5));
                resources.put(Crops.CARROT, 0);
                resources.put(Crops.SUGARCANE, 0);
            }
            case IRON -> {
                resources.put(Crops.WHEAT, 0);
                resources.put(Crops.POTATO, Utils.getRandomNumberInRange(4, 10));
                resources.put(Crops.CARROT, Utils.getRandomNumberInRange(1, 7));
                resources.put(Crops.SUGARCANE, 0);
            }
            case GOLD -> {
                resources.put(Crops.WHEAT, 0);
                resources.put(Crops.POTATO, Utils.getRandomNumberInRange(12, 23));
                resources.put(Crops.CARROT, Utils.getRandomNumberInRange(5, 12));
                resources.put(Crops.SUGARCANE, 0);
            }
            case DIAMOND -> {
                resources.put(Crops.WHEAT, 0);
                resources.put(Crops.POTATO, 0);
                resources.put(Crops.CARROT, Utils.getRandomNumberInRange(10, 17));
                resources.put(Crops.SUGARCANE, Utils.getRandomNumberInRange(0, 3));
            }
        }
        return resources;
    }

    public static Map<Crops, Integer> getResourcesForHoe(Material material, Pets pets){
        Map<Crops, Integer> globalResources = getResourcesForHoe(material);

        switch (pets){
            case BEE -> {
                Bee bee = new Bee();
                Random random = new Random();
                for(Crops crop : globalResources.keySet()){
                    if(random.nextInt(0,1) < bee.getLuck_bonus()){
                        int loot = globalResources.get(crop) * 2;
                        globalResources.remove(crop);
                        globalResources.put(crop, loot);
                    }
                }
            }
            case GOAT -> {
                for(Crops crop : globalResources.keySet()){
                    int loot = globalResources.get(crop) + 1;
                    globalResources.remove(crop);
                    globalResources.put(crop, loot);
                }
            }
            case CAT -> globalResources = getResourcesForHoe(getNextMaterial(material));
        }
        return globalResources;
    }

    public static Material getNextMaterial(Material material){
        Material next = null;
        Material[] allValues = Material.values();
        for (int i = 0; i < allValues.length - 1; i++) {
            if (allValues[i] == material) {
                next = allValues[i + 1];
                break;
            }
        }
        return next;
    }
}
