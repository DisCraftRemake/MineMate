package me.Rl242Dev.DatabaseManager;

import me.Rl242Dev.Classes.Items.Item;
import me.Rl242Dev.Classes.Items.Ressource.Harvest.Crops;
import me.Rl242Dev.Classes.Items.Ressource.Material;
import me.Rl242Dev.Classes.Items.Ressource.Ores.Ores;
import me.Rl242Dev.Classes.Items.Ressource.RessourceUtils;
import me.Rl242Dev.Classes.Items.Ressource.Type;
import me.Rl242Dev.Utils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DatabaseUtils {

    public static Item getPickaxeFromUUID(String UUID){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if (players.has(UUID)) {

                JSONObject player = players.getJSONObject(UUID);

                JSONObject pickaxe = player.getJSONObject("pickaxe");

                Material material = RessourceUtils.getMaterialFromString(pickaxe.get("material").toString());
                Type type = RessourceUtils.getTypeFromString(pickaxe.get("type").toString());
                String id = pickaxe.get("id").toString();

                return new Item(material, type, id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Item getHoeFromUUID(String UUID){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if (players.has(UUID)) {

                JSONObject player = players.getJSONObject(UUID);

                JSONObject pickaxe = player.getJSONObject("hoe");

                Material material = RessourceUtils.getMaterialFromString(pickaxe.get("material").toString());
                Type type = RessourceUtils.getTypeFromString(pickaxe.get("type").toString());
                String id = pickaxe.get("id").toString();

                return new Item(material, type, id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Map<Ores, Integer> GetRessourcesFromUUID(String UUID){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if(players.has(UUID)){
                JSONObject player = players.getJSONObject(UUID);

                JSONObject ressources = player.getJSONObject("resources");

                Map<Ores, Integer> oresIntegerMap = new HashMap<Ores, Integer>();

                int Stone = ressources.getInt("stone");
                int Coal = ressources.getInt("coal");
                int Iron = ressources.getInt("iron");
                int Gold = ressources.getInt("gold");
                int Diamond = ressources.getInt("diamond");
                int Obsidian = ressources.getInt("obsidian");

                oresIntegerMap.put(Ores.STONE, Stone);
                oresIntegerMap.put(Ores.COAL, Coal);
                oresIntegerMap.put(Ores.IRON, Iron);
                oresIntegerMap.put(Ores.GOLD, Gold);
                oresIntegerMap.put(Ores.DIAMOND, Diamond);
                oresIntegerMap.put(Ores.OBSIDIAN, Obsidian);
                return oresIntegerMap;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void SaveOresToUUID(String UUID, Map<Ores, Integer> ressourcesMap){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if(players.has(UUID)){
                JSONObject player = players.getJSONObject(UUID);

                JSONObject ressources = player.getJSONObject("resources");

                /* User data */

                int Stone = ressources.getInt("stone");
                int Coal = ressources.getInt("coal");
                int Iron = ressources.getInt("iron");
                int Gold = ressources.getInt("gold");
                int Diamond = ressources.getInt("diamond");
                int Obsidian = ressources.getInt("obsidian");

                /* Current data */

                int StoneMap = ressourcesMap.get(Ores.STONE);
                int CoalMap = ressourcesMap.get(Ores.COAL);
                int IronMap = ressourcesMap.get(Ores.IRON);
                int GoldMap = ressourcesMap.get(Ores.GOLD);
                int DiamondMap = ressourcesMap.get(Ores.DIAMOND);
                int ObsidianMap = ressourcesMap.get(Ores.OBSIDIAN);

                /* Write data */
                ressources.remove("stone");
                ressources.remove("coal");
                ressources.remove("iron");
                ressources.remove("gold");
                ressources.remove("diamond");
                ressources.remove("obsidian");

                ressources.put("stone", Stone + StoneMap);
                ressources.put("coal", Coal + CoalMap);
                ressources.put("iron", Iron + IronMap);
                ressources.put("gold", Gold + GoldMap);
                ressources.put("diamond", Diamond + DiamondMap);
                ressources.put("obsidian", Obsidian + ObsidianMap);

                FileWriter writer = new FileWriter("src/main/resources/players.json" ,false);
                writer.write(json.toString());
                writer.flush();
                writer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void SaveCropsToUUID(String UUID, Map<Crops, Integer> ressourcesMap){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if(players.has(UUID)){
                JSONObject player = players.getJSONObject(UUID);

                JSONObject ressources = player.getJSONObject("resources");

                /* User data */

                int Wheat = ressources.getInt("wheat");
                int Potato = ressources.getInt("potato");
                int Carrot = ressources.getInt("carrot");
                int SugarCane = ressources.getInt("sugar_cane");

                /* Current data */

                int WheatMap = ressourcesMap.get(Crops.WHEAT);
                int PotatoMap = ressourcesMap.get(Crops.POTATO);
                int CarrotMap = ressourcesMap.get(Crops.CARROT);
                int SugarCaneMap = ressourcesMap.get(Crops.SUGARCANE);

                /* Write data */
                ressources.remove("wheat");
                ressources.remove("potato");
                ressources.remove("carrot");
                ressources.remove("sugar_cane");

                ressources.put("wheat", Wheat + WheatMap);
                ressources.put("potato", Potato + PotatoMap);
                ressources.put("carrot", Carrot + CarrotMap);
                ressources.put("sugar_cane", SugarCane + SugarCaneMap);

                FileWriter writer = new FileWriter("src/main/resources/players.json" ,false);
                writer.write(json.toString());
                writer.flush();
                writer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static boolean UserExist(String UUID){
        try  {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            if(players.has(UUID)){
                return true;
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException exception){
            return false;
        }

        return false;
    }

    public static void RegisterUser(String UUID){
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/main/resources/players.json")), StandardCharsets.UTF_8);

            JSONObject json = new JSONObject(jsonString);

            JSONObject players = json.getJSONObject("players");

            JSONObject player = new JSONObject();

            JSONObject pickaxe = new JSONObject();
            pickaxe.put("material", "Wood");
            pickaxe.put("type", "Pickaxe");
            pickaxe.put("id", UUID + Utils.IntToString(Utils.hash("Pickaxe")));

            JSONObject hoe = new JSONObject();
            hoe.put("material", "Wood");
            hoe.put("type", "Hoe");
            hoe.put("id", UUID + Utils.IntToString(Utils.hash("Hoe")));

            JSONObject sword = new JSONObject();
            sword.put("material", "Wood");
            sword.put("type", "Sword");
            sword.put("id", UUID + Utils.IntToString(Utils.hash("Sword")));

            JSONObject resources = new JSONObject();
            resources.put("stone", 0);
            resources.put("coal", 0);
            resources.put("iron", 0);
            resources.put("gold", 0);
            resources.put("diamond", 0);
            resources.put("obsidian", 0);
            resources.put("wheat", 0);
            resources.put("potato", 0);
            resources.put("carrot", 0);
            resources.put("sugar_cane", 0);

            player.put("pickaxe", pickaxe);
            player.put("resources", resources);

            player.put("hoe", hoe);
            player.put("sword", sword);

            players.put(UUID, player);
            FileWriter writer = new FileWriter("src/main/resources/players.json");
            writer.write(json.toString());
            writer.flush();
            writer.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
